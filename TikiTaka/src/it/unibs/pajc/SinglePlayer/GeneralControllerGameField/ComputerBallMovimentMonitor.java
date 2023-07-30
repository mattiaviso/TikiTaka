package it.unibs.pajc.SinglePlayer.GeneralControllerGameField;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.Computer;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.ControllerGameField;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComputerBallMovimentMonitor implements GeneralBallMovementMonitor {
    private ControllerGeneral controllerGameField;

    public ComputerBallMovimentMonitor(ControllerGeneral controllerGameField) {
        this.controllerGameField = controllerGameField;
    }

    @Override
    public void run() {


        controllerGameField.getModelGameField().setAllStop(false);

        while (true) {
            if (allStop()) {
                break; // Esci dal ciclo while una volta che tutte le palline si sono fermate.
            }
            controllerGameField.getViewGame().repaint();
            controllerGameField.getViewGame().setValido(null);
            controllerGameField.getViewGame().setNewradius(0);


            // Puoi aggiungere una piccola pausa qui per ridurre l'utilizzo della CPU.
            try {
                Thread.sleep(300); // Pausa di 100 millisecondi.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


        controllerGameField.getModelGameField().cambioTurno();
        controllerGameField.getModelGameField().setAllStop(true);
        controllerGameField.getViewGame().repaint();

        System.out.println(controllerGameField.getModelGameField().getTurno());
        if (controllerGameField.getModelGameField().getTurno().equalsIgnoreCase("T2")) {
            controllerGameField.getViewGame().removeMouseListener(controllerGameField);
            controllerGameField.getViewGame().removeMouseMotionListener(controllerGameField);
            startThreadIfT2(controllerGameField);


        }
        if (controllerGameField.getModelGameField().getTurno().equalsIgnoreCase("T1")) {
            controllerGameField.getViewGame().addMouseListener(controllerGameField);
            controllerGameField.getViewGame().addMouseMotionListener(controllerGameField);
        }


    }

    public final boolean allStop() {
        for (FieldObject o : controllerGameField.getModelGameField().getObjectsPiece()) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }


    public void startThreadIfT2(ControllerGeneral controllerGameField) {

        Runnable task = () -> {
            // Gestione del computer

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Computer pieceComputer = direzionePieceBall();
            FieldObject ricercaPedina = controllerGameField.getModelGameField().findPieceByPosition(pieceComputer.getPiece());

            if (ricercaPedina != null) {

                FieldObject valido = ricercaPedina;


                valido.start((int) pieceComputer.getDistance(), pieceComputer.getAngle());

                GeneralBallMovementMonitor monitor = new ComputerBallMovimentMonitor(controllerGameField);
                monitor.run();
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
        executor.shutdown();
    }


    public Computer direzionePieceBall() {

        FieldObject ball = controllerGameField.getModelGameField().selezionaBall();
        Computer piecePiuVicina = controllerGameField.getModelGameField().piecePiuVicina(ball);


        piecePiuVicina.settoAngoloPerAlcuniPuntidellaBallScelgoIlMigliore(ball);


        return piecePiuVicina;
    }
}
