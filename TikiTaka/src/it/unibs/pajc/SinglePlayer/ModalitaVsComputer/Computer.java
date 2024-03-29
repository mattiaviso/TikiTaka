package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Utility;
import it.unibs.pajc.Partita.Vector2d;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static it.unibs.pajc.Partita.GameField.EPSILON;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;

/**
 * La classe Computer rappresenta l'avversario controllato dal computer nella modalità contro il computer.
 * Viene utilizzato per calcolare la direzione in cui muovere la pedina del computer per raggiungere la palla.
 */
public class Computer {

    public static final int NUMPUNTICONSIDERATI = 5;
    private FieldObject piece;
    private double distance;
    private double angle;
    private ArrayList<Double> anglePuntoThread ;

    public Computer(FieldObject piece, double distance) {
        this.piece = piece;
        this.distance = distance;
        anglePuntoThread = new ArrayList<>();
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
        double angleIncrement = 2 * Math.PI / 5;
        double yminima = Double.MAX_VALUE;


        for (int i = 0; i < 5; i++) {

            double angleball = i * angleIncrement;
            double xStima = ball.getPosition().getX() + ball.getRadius() * Math.cos(angleball);
            double yStima = ball.getPosition().getY() + ball.getRadius() * Math.sin(angleball);
            double angoloPunto = Utility.calcolaAngoloConposizione(piece, xStima, yStima);
            piece.start((int) distance, angoloPunto);
            FieldObject ballAfterCollision = simulazioneCollisione(ball);


            if (ballAfterCollision.getPosition().getY() < yminima) {
                yminima = ballAfterCollision.getPosition().getY();
                setAngle(angoloPunto);
            }

        }
    }


   /*
    Funzione angoloPedinaColpirePalla(palla)
    angoloIncremento <-- 2*π/5
    yMinore <-- 1000000

    Per ogni i da 0 a 4

     angoloPalla <-- i*angoloIncremento
     xStimata <-- posizionePallaX+ raggioPalla * cos(AngoloPalla)
     yStimata <-- posizionePallaY+ raggioPalla * cos(AngoloPalla)

     angoloPedina <-- calcoloANgoloDirezionePallina(pedina, xStimata,yStimata)
     Simulazionetiro (distanzaPedinaPalla, angoloPedina)

     se pallaDopoCollisionePosY < yMinore allora
       yMinore = pallaDopoCollisionePosY
       angoloPedina <-- angoloPedina
     fine se

    fine per

fine Funzione
*/


 




    /**
     * Controlla la collisione tra due oggetti nel campo di gioco e gestisce la risoluzione della collisione.
     * Vengono aggiornate le posizioni e le velocità degli oggetti coinvolti e viene applicato un attrito alla palla.
     *
     * @param ball L'oggetto palla  con cui controllare la collisione.
     * @return L'oggetto FieldObject ball dopo la risoluzione della collisione e l'applicazione dell'attrito.
     */
    public FieldObject simulazioneCollisione(FieldObject ball) {

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
