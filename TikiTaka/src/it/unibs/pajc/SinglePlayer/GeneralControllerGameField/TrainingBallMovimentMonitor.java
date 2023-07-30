package it.unibs.pajc.SinglePlayer.GeneralControllerGameField;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.SinglePlayer.ModalitaAllenamento.GameFieldTraining;
import it.unibs.pajc.SinglePlayer.ViewGameField;

import javax.swing.*;

public class TrainingBallMovimentMonitor implements GeneralBallMovementMonitor{


    private GameFieldTraining modelGameField;
    private ViewGameField viewGame;

    public TrainingBallMovimentMonitor(GameFieldTraining modelGameField, ViewGameField viewGame) {
        this.modelGameField = modelGameField;
        this.viewGame = viewGame;
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

    public  boolean allStop() {
        for (FieldObject o : modelGameField.getObjectsPiece()) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }
}
