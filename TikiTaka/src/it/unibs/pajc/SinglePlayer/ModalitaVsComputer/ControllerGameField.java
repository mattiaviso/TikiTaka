package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;

import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.ComputerBallMovimentMonitor;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.ControllerGeneral;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.GeneralBallMovementMonitor;
import it.unibs.pajc.SinglePlayer.ViewGameField;

import javax.swing.*;
import java.awt.event.MouseEvent;


public class ControllerGameField extends ControllerGeneral {
    public ControllerGameField() {
            setModelGameField( new GameFieldSingol());
            setViewGame(new ViewComputer(this));
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
