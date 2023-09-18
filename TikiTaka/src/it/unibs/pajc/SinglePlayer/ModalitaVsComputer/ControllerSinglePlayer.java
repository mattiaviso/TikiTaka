package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;

import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.ComputerBallMovimentMonitor;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.Controller;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.GeneralBallMovementMonitor;

import javax.swing.*;
import java.awt.event.MouseEvent;


public class ControllerSinglePlayer extends Controller {

    /**
     * Costruttore della classe ControllerSinglePlayer. Inizializza il modello del campo di gioco e la vista,
     * e avvia un timer per aggiornare il gioco a intervalli regolari. Inoltre, aggiunge i listener del mouse per gestire gli eventi del mouse.
     */
    public ControllerSinglePlayer() {
        setModelGameField(new GameFieldSinglePlayer());
        setViewGame(new ViewSinglePlayer(this));
        Timer timer = new Timer(15, (e) -> {
            getModelGameField().updateGame();
            getViewGame().repaint();
            if (getModelGameField().getScore1() == 3) {
                getModelGameField().checkVincitore();
                JOptionPane.showConfirmDialog(null, "vintoGiocatore1");

            }
            if (getModelGameField().getScore2() == 3) {
                getModelGameField().checkVincitore();
                JOptionPane.showConfirmDialog(null, "vintoGiocatore2");
            }

        });
        timer.start();

        getViewGame().addMouseListener(this);
        getViewGame().addMouseMotionListener(this);


    }

    /**
     *
     * Avvia il movimento della palla e il monitoraggio del movimento delle pedine del computer.
     *
     * @param e L'evento del mouse rilasciato.
     */
    @Override
    public void mouseReleased(MouseEvent e) {


        if (getViewGame().getValido() != null) {
            getViewGame().getValido().start(getViewGame().getDistance(), getViewGame().getAngle());
        }
        GeneralBallMovementMonitor monitor = new ComputerBallMovimentMonitor(this);
        Thread ballMovementThread = new Thread(monitor);
        ballMovementThread.start();


    }


}
