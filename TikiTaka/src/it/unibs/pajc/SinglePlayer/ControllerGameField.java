/*

package it.unibs.pajc.SinglePlayer;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ControllerGameField {

    private GameFieldViewSingle1 viewGame;
     private GameField modelGameField;


    public ControllerGameField() {

        modelGameField = new GameFieldSingol();
        this.viewGame = new GameFieldViewSingle1(modelGameField);
        Timer timer = new Timer(10, (e) -> {
            modelGameField.updateGame();
            viewGame.repaint();
        });
        timer.start();


        if(modelGameField.getTurno().equalsIgnoreCase("T1")){

            viewGame.addMouseListener(mouseMyListener);
            viewGame.addMouseMotionListener(mouseMyListener);



        }else{
            // getstione del computer
            Computer pieceComputer = direzionePieceBall();
            FieldObject ricercaPedina = modelGameField.findPieceByPosition(pieceComputer.getPiece());

            if (ricercaPedina != null) {
                System.out.println("diverso da null");
                viewGame.setValido(ricercaPedina);
                System.out.println(pieceComputer.getDistance());

                viewGame.getValido().start((int) pieceComputer.getDistance(), pieceComputer.getAngle());
                BallMovementMonitor  ballMovementMonitor = new BallMovementMonitor();
                ballMovementMonitor.run();

            }
        }







    }



    // ci permette di trovare l'angolo giusto
    public Computer direzionePieceBall() {

        FieldObject ball = modelGameField.selezionaBall();
        Computer piecePiuVicina = modelGameField.piecePiuVicina(ball);

        // angolo sara su piu punti della circonferenza cosi da testare quale sara' quello migliore per il tiro
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


    public  class BallMovementMonitor implements Runnable {


        @Override
        public void run() {
            modelGameField.setAllStop(false);
            while (true) {
                if (!allStop()) {
                    viewGame.repaint();
                    viewGame.setValido(null);
                    viewGame.setNewradius(0);

                    break; // Esci dal ciclo while una volta che tutte le palline si sono fermate.
                }

                // Puoi aggiungere una piccola pausa qui per ridurre l'utilizzo della CPU.
                try {
                    Thread.sleep(100); // Pausa di 100 millisecondi.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            modelGameField.cambioTurno();
            modelGameField.setAllStop(true);
            viewGame.repaint();
            System.out.println("Tutte le palline si sono fermate.");




        }

        public final boolean allStop() {
            for (FieldObject o : modelGameField.getObjectsPiece()) {
                if (!o.speedIsZero()) return false;
            }
            return true;
        }


    }

}





*/
