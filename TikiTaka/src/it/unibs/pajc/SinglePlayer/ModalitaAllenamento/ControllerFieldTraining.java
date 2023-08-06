

package it.unibs.pajc.SinglePlayer.ModalitaAllenamento;

import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.ControllerGeneral;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.GeneralBallMovementMonitor;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.TrainingBallMovimentMonitor;
import it.unibs.pajc.SinglePlayer.ViewGameField;

import javax.swing.*;
import java.awt.event.MouseEvent;


public class ControllerFieldTraining extends ControllerGeneral {


    /**
     * Costruttore della classe ControllerFieldTraining. Inizializza il modello del campo di gioco (GameFieldTraining) e la vista di gioco (ViewTraining).
     * Avvia un timer per l'aggiornamento del gioco e aggiunge i listener del mouse alla vista di gioco.
     */
    public ControllerFieldTraining() {
        setModelGameField(new GameFieldTraining());
        setViewGame( new ViewTraining(this));
        Timer timer = new Timer(20, (e) -> {
            getModelGameField().updateGame();
            getViewGame().repaint();

        });
        timer.start();

        getViewGame().addMouseListener(this);
        getViewGame().addMouseMotionListener(this);

    }


    /**
     * Metodo che gestisce l'evento del rilascio del mouse. Se è stata selezionata una pedina valida, viene avviato TrainingBallMovimentMonitor
     * per gestire il movimento delle palline durante il tiro.
     *
     * @param e L'evento del mouse rilasciato
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (getViewGame().getValido() != null) {
            getViewGame().getValido().start(getViewGame().getDistance(), getViewGame().getAngle());
            GeneralBallMovementMonitor monitor = new TrainingBallMovimentMonitor((GameFieldTraining) getModelGameField(), getViewGame());
            Thread ballMovementThread = new Thread(monitor);
            ballMovementThread.start();

        }
    }
}












