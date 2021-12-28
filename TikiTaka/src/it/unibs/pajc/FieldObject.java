package it.unibs.pajc;

import java.awt.*;
import java.awt.geom.*;

public class FieldObject {

    protected boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    protected float[] position = {0, 0, 0};
    protected float[] velocity = {0, 0, 0};
    protected float[] maxVelocity = {10, 10, 10};

    protected Color color;

    protected Shape shape;

    /**
     * Metodo che che modifica la velocita dell'oggetto
     * @param a Acceleration
     */
    public void accelerate(float a) {
        /* Dobbiamo scomporre la velocita su x e y data la posizione di rotazione dell'oggetto
         * position[2] indica la rotazione dell'oggetto di quanto è inclinato rispetto alla
         * posizione originale
         */


        this.setVelocity(
                (float)(velocity[0] + a * Math.cos(position[2])),
                (float)(velocity[1] + a * Math.sin(position[2])),
                (float)velocity[2]
        );
    }

    public void setVelocity(float vx, float vy, float vr) {
        velocity[0] = Math.max(Math.min(maxVelocity[0], vx), -maxVelocity[0]);
        velocity[1] = Math.max(Math.min(maxVelocity[1], vy), -maxVelocity[1]);
        velocity[2] = Math.max(Math.min(maxVelocity[2], vr), -maxVelocity[2]);
    }

    //data la variazione di velocita devo cambiare la poszione dell'oggetto
    public void stepNext() {
        for (int i = 0; i < 3; i++) {
            position[i] += velocity[i];
        }
    }

    public Shape getShape() {
        AffineTransform t = new AffineTransform();
        t.translate(position[0], position[1]);
        t.rotate(position[2]);
        return t.createTransformedShape(shape);

    }

    public Color getColor() {
        return this.color;
    }

    public float getX() {
        return position[0];
    }

    public float getY() {
        return position[1];
    }
    public float getR() {
        return position[2];
    }


    public float getSpeedX() {
        return velocity[0];
    }

    public float getSpeedY() {
        return velocity[1];
    }
    public float getSpeedR() {
        return velocity[2];
    }

    public void setX(float x) {
        position[0] = x;
    }

    public void setY(float y) {
        position[1] = y;
    }
    public void setR(float z) {
        position[2] = z;
    }


    public void rotate(float r) {
        position[2]+=r;
    }

    public boolean checkCollision(FieldObject o) {
        Area a = new Area(this.getShape());
        a.intersect(new Area(o.getShape()));

        return !a.isEmpty();
    }


    public void collisionDetected() {
        alive = false;
    }

}
