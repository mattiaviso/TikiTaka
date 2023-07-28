

package it.unibs.pajc.Allenamento;

import it.unibs.pajc.Allenamento.Livelli.LivelloState1;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.SinglePlayer.Computer;
import it.unibs.pajc.SinglePlayer.ControllerGameField;
import it.unibs.pajc.SinglePlayer.GameFieldSingol;
import it.unibs.pajc.SinglePlayer.GameFieldViewSingle1;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ControllerFieldTraining extends MouseAdapter {

    private ViewGameFieldTraining viewGame;
    private GameFieldTraining modelGameField;


    public ControllerFieldTraining() {

        modelGameField = new GameFieldTraining();
        this.viewGame = new ViewGameFieldTraining(this);
        Timer timer = new Timer(20, (e) -> {
            modelGameField.updateGame();
            viewGame.repaint();

        });
        timer.start();

        viewGame.addMouseListener(this);
        viewGame.addMouseMotionListener(this);


    }

    public ViewGameFieldTraining getViewGame() {
        return viewGame;
    }

    public void setViewGame(ViewGameFieldTraining viewGame) {
        this.viewGame = viewGame;
    }

    public GameFieldTraining getModelGameField() {
        return modelGameField;
    }

    public void setModelGameField(GameFieldTraining modelGameField) {
        this.modelGameField = modelGameField;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        //prendiamo coordinate x e y di dove è stato premuto il mouse
        int x = e.getX() - viewGame.getW() / 2;
        int y = -(e.getY() - viewGame.getH() / 2);

        System.out.println("posizione X" + x + "posizione Y " + y);
        if (modelGameField.allStop) {
            viewGame.setValido(modelGameField.pedinaSelezionata(x, y));
        }

        viewGame.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {


        if (viewGame.getValido() != null) {
            viewGame.getValido().start(viewGame.getDistance(), viewGame.getAngle());
            BallMovementMonitor monitor = new BallMovementMonitor();
            Thread ballMovementThread = new Thread(monitor);
            ballMovementThread.start();

        }


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


        public BallMovementMonitor() {

        }

        @Override
        public void run() {


            modelGameField.setAllStop(false);
            while (true) {
                if (allStop()) {
                    break; // Esci dal ciclo while una volta che tutte le palline si sono fermate.
                }
                viewGame.repaint();
                viewGame.setValido(null);
                viewGame.setNewradius(0);


                // Puoi aggiungere una piccola pausa qui per ridurre l'utilizzo della CPU.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }


            modelGameField.setAllStop(true);
            boolean continuoPartita = modelGameField.setLifeIfPlayerDosentScore();
            if (!continuoPartita) {
                JOptionPane.showMessageDialog(null, "hai perso");

                System.exit(0);
                viewGame.repaint();
            }

        }

        public final boolean allStop() {
            for (FieldObject o : modelGameField.getObjectsPiece()) {
                if (!o.speedIsZero()) return false;
            }
            return true;
        }


    }


}












