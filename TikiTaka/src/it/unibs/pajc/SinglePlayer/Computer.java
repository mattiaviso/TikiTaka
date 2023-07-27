package it.unibs.pajc.SinglePlayer;

import it.unibs.pajc.Partita.Collision.Collision;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.Partita.Utility;
import it.unibs.pajc.Partita.Vector2d;

import static it.unibs.pajc.Partita.GameField.EPSILON;

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
            FieldObject ballAfterCollision =controllacollisione(ball);
            // angolo piu corretto rispetto a quelli presi
            if(ballAfterCollision.getPosition().getY()  <  yminima){
                yminima= ballAfterCollision.getPosition().getY();
                setAngle(angoloPunto);
            }

        }
    }

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
