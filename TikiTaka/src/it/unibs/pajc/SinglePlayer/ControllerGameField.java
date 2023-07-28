

package it.unibs.pajc.SinglePlayer;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.Partita.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ControllerGameField extends MouseAdapter {

    private GameFieldViewSingle1 viewGame;
    private GameField modelGameField;


    public ControllerGameField() {

        modelGameField = new GameFieldSingol();
        this.viewGame = new GameFieldViewSingle1(this);
        Timer timer = new Timer(20, (e) -> {
            modelGameField.updateGame();
            viewGame.repaint();
            if (modelGameField.getScore1() == 3) {
                JOptionPane.showConfirmDialog(null, "vintoGiocatore1");
                modelGameField.checkVincitore();
            }
            if (modelGameField.getScore2() == 3) {
                JOptionPane.showConfirmDialog(null, "vintoGiocatore2");
                modelGameField.checkVincitore();
            }
        });
        timer.start();

        viewGame.addMouseListener(this);
        viewGame.addMouseMotionListener(this);


    }


    // ci permette di trovare l'angolo giusto
    public Computer direzionePieceBall() {

        FieldObject ball = modelGameField.selezionaBall();
        Computer piecePiuVicina = modelGameField.piecePiuVicina(ball);


        piecePiuVicina.settoAngoloPerAlcuniPuntidellaBallScelgoIlMigliore(ball);


        return piecePiuVicina;
    }


    public GameFieldViewSingle1 getViewGame() {
        return viewGame;
    }

    public void setViewGame(GameFieldViewSingle1 viewGame) {
        this.viewGame = viewGame;
    }

    public GameField getModelGameField() {
        return modelGameField;
    }

    public void setModelGameField(GameField modelGameField) {
        this.modelGameField = modelGameField;
    }


    @Override
    public void mousePressed(MouseEvent e) {

        //prendiamo coordinate x e y di dove è stato premuto il mouse
        int x = e.getX() - viewGame.getW() / 2;
        int y = -(e.getY() - viewGame.getH() / 2);
        if (modelGameField.allStop) {
            viewGame.setValido(modelGameField.pedinaSelezionata(x, y));
        }

        viewGame.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {


        // il rilascio lo step next
        System.out.println("ciao");

        if (viewGame.getValido() != null) {
            viewGame.getValido().start(viewGame.getDistance(), viewGame.getAngle());


        }

        BallMovementMonitor monitor = new BallMovementMonitor(this);
        monitor.run();


    }


    @Override
    public void mouseDragged(MouseEvent e) {

        viewGame.setXnew(e.getX() - viewGame.getW() / 2);

        viewGame.setYnew(-(e.getY() - viewGame.getH() / 2));


        if (viewGame.getValido() != null)
            viewGame.setNewradius(Math.min((int) (Math.sqrt(((viewGame.getValido().getPosition().getX() - viewGame.getXnew()) * (viewGame.getValido().getPosition().getX() - viewGame.getXnew())) + ((viewGame.getValido().getPosition().getY() - viewGame.getYnew()) * (viewGame.getValido().getPosition().getY() - viewGame.getYnew())))), 150));

        viewGame.repaint();
    }


    public class BallMovementMonitor implements Runnable {

        private ControllerGameField controllerGameField;

        public BallMovementMonitor(ControllerGameField controllerGameField) {
            this.controllerGameField = controllerGameField;
        }

        @Override
        public synchronized void run() {


            modelGameField.setAllStop(false);
            CompletableFuture<Boolean> allStopFuture = CompletableFuture.supplyAsync(() -> allStop());
            while (true) {


                try {
                    if (!allStopFuture.get()) {
                        viewGame.repaint();
                        viewGame.setValido(null);
                        viewGame.setNewradius(0);
                        break; // Esci dal ciclo while una volta che tutte le palline si sono fermate.
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }


                // Puoi aggiungere una piccola pausa qui per ridurre l'utilizzo della CPU.
                try {
                    Thread.sleep(10); // Pausa di 100 millisecondi.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }


            modelGameField.cambioTurno();
            modelGameField.setAllStop(true);
            viewGame.repaint();

            System.out.println(modelGameField.getTurno());
            if (modelGameField.getTurno().equalsIgnoreCase("T2")) {
                viewGame.removeMouseListener(controllerGameField);
                viewGame.removeMouseMotionListener(controllerGameField);
                startThreadIfT2(controllerGameField);


            } if (modelGameField.getTurno().equalsIgnoreCase("T1")) {
                System.out.println("sono entrato");
                viewGame.addMouseListener(controllerGameField);
                viewGame.addMouseMotionListener(controllerGameField);
            }


        }

        public final boolean allStop() {
            for (FieldObject o : modelGameField.getObjectsPiece()) {
                if (!o.speedIsZero()) return false;
            }
            return true;
        }


    }

    public void startThreadIfT2(ControllerGameField controllerGameField) {

        Runnable task = () -> {
            // Gestione del computer

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Computer pieceComputer = direzionePieceBall();
            FieldObject ricercaPedina = modelGameField.findPieceByPosition(pieceComputer.getPiece());

            if (ricercaPedina != null) {

                FieldObject valido = ricercaPedina;


                valido.start((int) pieceComputer.getDistance(), pieceComputer.getAngle());

                BallMovementMonitor monitor = new BallMovementMonitor(controllerGameField);
                monitor.run();
            }
        };


        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
        executor.shutdown();
    }
}









