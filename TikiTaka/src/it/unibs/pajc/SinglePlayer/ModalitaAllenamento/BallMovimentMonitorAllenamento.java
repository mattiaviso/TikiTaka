package it.unibs.pajc.SinglePlayer.ModalitaAllenamento;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.GeneralBallMovementMonitor;
import it.unibs.pajc.SinglePlayer.View;

import javax.swing.*;

public class BallMovimentMonitorAllenamento implements GeneralBallMovementMonitor {


    private GameFieldTraining modelGameField;
    private View viewGame;

    public BallMovimentMonitorAllenamento(GameFieldTraining modelGameField, View viewGame) {
        this.modelGameField = modelGameField;
        this.viewGame = viewGame;
    }

    /**
     * Implementa il metodo run() dell'interfaccia GeneralBallMovementMonitor.
     * Si occupa di controllare il movimento delle palline durante la modalità di allenamento.
     * Una volta che tutte le palline si sono fermate, mostra un messaggio di perdita se ci sono determinate condizioni .
     */
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

    /**
     * Controlla se tutte le palline si sono fermate.
     *
     * @return True se tutte le palline si sono fermate, altrimenti False.
     */
    public  boolean allStop() {
        for (FieldObject o : modelGameField.getObjectsPiece()) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }
}
