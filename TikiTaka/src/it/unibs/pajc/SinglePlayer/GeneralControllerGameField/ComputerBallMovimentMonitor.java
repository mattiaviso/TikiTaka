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

    /**
     * Metodo che esegue il monitoraggio del movimento della pallina controllata dal computer.
     * Quando tutte le palline si sono fermate, il turno viene cambiato e avviato un nuovo thread per gestire il movimento della pallina del computer.
     */
    @Override
    public void run() {

        controllerGameField.getModelGameField().setAllStop(false);

        while (true) {
            if (allStop()) {
                break;
            }
            controllerGameField.getViewGame().repaint();
            controllerGameField.getViewGame().setValido(null);
            controllerGameField.getViewGame().setNewradius(0);


            try {
                Thread.sleep(300);
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
        // Aggiungi i listener del mouse se è il turno del giocatore.
        if (controllerGameField.getModelGameField().getTurno().equalsIgnoreCase("T1")) {
            controllerGameField.getViewGame().addMouseListener(controllerGameField);
            controllerGameField.getViewGame().addMouseMotionListener(controllerGameField);
        }


    }


    /**
     * Verifica se tutte le palline nel campo di gioco sono ferme.
     *
     * @return true se tutte le palline sono ferme, altrimenti false.
     */

    public final boolean allStop() {
        for (FieldObject o : controllerGameField.getModelGameField().getObjectsPiece()) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }


    /**
     * Avvia un nuovo thread per gestire il movimento della pallina del computer.
     *
     * @param controllerGameField Il controller del gioco associato al monitor del movimento della pallina.
     */
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


    /**
     * Calcola la direzione in cui la pedina del computer deve muoversi per colpire la pallina.
     *
     * @return Un oggetto Computer contenente informazioni sulla distanza e l'angolo tra la pedina del computer e la pallina.
     */
    public Computer direzionePieceBall() {

        FieldObject ball = controllerGameField.getModelGameField().selezionaBall();
        Computer piecePiuVicina = controllerGameField.getModelGameField().piecePiuVicina(ball);
        piecePiuVicina.settoAngoloPerAlcuniPuntidellaBallScelgoIlMigliore(ball);

        return piecePiuVicina;
    }
}
