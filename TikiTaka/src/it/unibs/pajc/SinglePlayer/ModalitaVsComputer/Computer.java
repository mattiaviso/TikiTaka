package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Utility;
import it.unibs.pajc.Partita.Vector2d;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.ComputerBallMovimentMonitor;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.ControllerGeneral;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.GeneralBallMovementMonitor;

import java.awt.event.MouseEvent;

import static it.unibs.pajc.Partita.GameField.EPSILON;

/**
 * La classe Computer rappresenta l'avversario controllato dal computer nella modalità contro il computer.
 * Viene utilizzato per calcolare la direzione in cui muovere la pedina del computer per raggiungere la palla.
 */
public class Computer {

    public static final int NUMPUNTICONSIDERATI = 5;
    private FieldObject piece;
    private double distance;
    private double angle;


    public Computer(FieldObject piece, double distance) {
        this.piece = piece;
        this.distance = distance;
    }

    public void setPiece(FieldObject piece) {
        this.piece = piece;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public FieldObject getPiece() {
        return piece;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Imposta l'angolo migliore per alcuni punti intorno alla palla rispetto all'oggetto `piece` nel campo di gioco.
     * Viene considerato un insieme di punti intorno alla palla e, per ciascuno di essi, viene calcolato l'angolo tra la palla
     * e la posizione stimata. L'oggetto `piece` viene spostato in ciascuna posizione stimata, e viene controllata la collisione
     * tra la palla e l'oggetto `piece`. L'angolo che dà luogo alla posizione Y minima della palla dopo la collisione viene impostato come l'angolo migliore.
     *
     * @param ball L'oggetto FieldObject (palla) per il quale trovare l'angolo migliore rispetto all'oggetto `piece`.
     */
    public void settoAngoloPerAlcuniPuntidellaBallScelgoIlMigliore(FieldObject ball) {
        double angleIncrement = 2 * Math.PI / NUMPUNTICONSIDERATI;
        double y = Double.MAX_VALUE;
        double yminima = y;


        for (int i = 0; i < NUMPUNTICONSIDERATI; i++) {

            double angleball = i * angleIncrement;
            // queste sono le posizioni della pedina
            double xStima = ball.getPosition().getX() + ball.getRadius() * Math.cos(angleball);
            double yStima = ball.getPosition().getY() + ball.getRadius() * Math.sin(angleball);

            // calcoliano l'angolo delle posizioni
            double angoloPunto = Utility.calcolaAngoloConposizione(piece, xStima, yStima);

            piece.start((int) distance, angoloPunto);
            FieldObject ballAfterCollision = controllacollisione(ball);
            // angolo piu corretto rispetto a quelli presi
            if (ballAfterCollision.getPosition().getY() < yminima) {
                yminima = ballAfterCollision.getPosition().getY();
                setAngle(angoloPunto);
            }

        }
    }
    /**
     * Controlla la collisione tra due oggetti nel campo di gioco e gestisce la risoluzione della collisione.
     * Vengono aggiornate le posizioni e le velocità degli oggetti coinvolti e viene applicato un attrito alla palla.
     *
     * @param ball L'oggetto FieldObject (palla) con cui controllare la collisione.
     * @return L'oggetto FieldObject ball dopo la risoluzione della collisione e l'applicazione dell'attrito.
     */
    public FieldObject controllacollisione(FieldObject ball) {

        Vector2d velocity = piece.getVelocita();
        Vector2d position = piece.getPosition();

        velocity.setY(velocity.getY());
        position.setX(position.getX() + (velocity.getX() * 1));
        position.setY(position.getY() + (velocity.getY() * 1));

        if (Math.abs(velocity.getX()) < EPSILON) {
            velocity.setX(0);
        }
        if (Math.abs(velocity.getY()) < EPSILON) {
            velocity.setY(0);
        }


        piece.resolveCollision(ball);
        ball.friction(0.015);


        return ball;

    }



}
