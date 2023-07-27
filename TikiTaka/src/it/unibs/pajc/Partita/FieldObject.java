package it.unibs.pajc.Partita;

import it.unibs.pajc.ClientServer.SoundClip;

import java.awt.image.BufferedImage;


abstract public class FieldObject implements Comparable<FieldObject> {
    public static final int MAXDISTANCE = 150;

    private  Vector2d position;
    private  Vector2d velocita = new Vector2d();
    private double radius;
    protected BufferedImage imageObj;
    private double massa;

    public boolean isBall;

    public Vector2d getVelocita() {
        return velocita;
    }

    public void setVelocita(Vector2d velocita) {
        this.velocita = velocita;
    }

    public Vector2d getPosition() {
        return position;
    }



    public FieldObject(double radius, double x, double y, double massa) {
        this.radius = radius;
        this.position = new Vector2d(x, y);
        this.massa = massa;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setImageObj(BufferedImage imageObj) {
        this.imageObj = imageObj;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }

    public boolean isBall() {
        return isBall;
    }

    public void setBall(boolean ball) {
        isBall = ball;
    }

    /**
     * Metodo che ritorno il team di appartenenza della pedina
     */
    public String getTeam() {
        if (this instanceof Piece) {
            return this.getTeam();
        }
        return null;
    }

    /**
     * Metodo che setta coordinate x e y della pedina
     * @param x Double X
     * @param y Double Y
     */
    public void setXY(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public double getRadius() {
        return radius;
    }

    public BufferedImage getImageObj() {
        return imageObj;
    }

    public double getMassa() {
        return massa;
    }



    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return radius + "@" + position.getX() + "@" + position.getY();
    }

    /**
     * Metodo che serve per far muovere la prima pallina, quella selezionata dal giocatore
     *
     * @param distance Potenza
     * @param angle    Angolo di tiro
     */
    public void start(int distance, double angle) {
        double vel;
        if (distance > MAXDISTANCE) {
            distance = MAXDISTANCE;
        }

        vel = distance / 7.5;// 7.5
        velocita.setXY(vel, angle);
        velocita.totalXY();
    }

    /**
     * Metodo che ritorna true se la pallina risulta ferma
     *
     * @return True se la pallina è ferma
     */
    public boolean speedIsZero() {
        final double EPSILON = 1E-3;
        return Math.abs(velocita.getLength()) < EPSILON ? true : false;
    }

    /**
     * confrontare se si toccano oppure no 2 pedine
     * in base alle coordiante
     *
     * @param ball FieldObj ball
     * @return 1 Se this.X > ball.X
     * @return -1 se this.X < ball.X
     * @return 0 In nessuno dei casi precedenti
     */
    @Override
    public int compareTo(FieldObject ball) {
        if (this.position.getX() - this.getRadius() > ball.position.getX() - ball.getRadius()) {
            return 1;
        } else if (this.position.getX() - this.getRadius() < ball.position.getX() - ball.getRadius()) {
            return -1;
        } else {
            return 0;
        }
    }


    /**
     * Metodo per risolvere le collissioni con un altro oggetto
     *
     * @param p2
     */
    public void resolveCollision(FieldObject p2) {


        Vector2d delta = (position.subtract(p2.position));
        double r = getRadius() + p2.getRadius();
        double dist2 = delta.dot(delta);

        // non esiste collisoni
        if (dist2 > r * r) return;

        GameField.collision = true;

        double d = delta.getLength();

        Vector2d mtd;
        if (d != 0.0f) {
            mtd = delta.multiply(((getRadius() + p2.getRadius()) - d) / d); // minimum translation distance to push balls apart after intersecting
            this.position = this.position.add(mtd.multiply(1 / 4));
            p2.position = p2.position.subtract(mtd.multiply(1 / 4));

        } else
        {
            d = p2.getRadius() + getRadius() - 1.0f;
            delta = new Vector2d(p2.getRadius() + getRadius(), 0.0f);

            mtd = delta.multiply(((getRadius() + p2.getRadius()) - d) / d);
        }

        double im1 = 1 / getMassa();
        double im2 = 1 / p2.getMassa();


        position = position.add(mtd.multiply(im1 / (im1 + im2)));
        p2.position = p2.position.subtract(mtd.multiply(im2 / (im1 + im2)));

        // velocità dopo l'urto
        Vector2d v = (this.velocita.subtract(p2.velocita));
        double vn = v.dot(mtd.normalize());

        if (vn > 0.0f) return;

        double i = (-(1.0D + 0.99D) * vn) / (im1 + im2);
        Vector2d impulse = mtd.multiply(i);

        this.velocita = this.velocita.add(impulse.multiply(im1));
        p2.velocita = p2.velocita.subtract(impulse.multiply(im2));
    }

    /**
     * Attrito che diminuisce la velocita delle pedine
     * @param num
     */
    public void friction(double num) {
        velocita.set(velocita.getX() - (velocita.getX() * num), velocita.getY() - (velocita.getY() * num));
        if (velocita.getLength() < 0.75) velocita = new Vector2d(0, 0);
    }

}
