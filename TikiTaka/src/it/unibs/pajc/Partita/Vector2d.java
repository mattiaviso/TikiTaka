package it.unibs.pajc.Partita;

/**
 * Classe presa in prestito su internet
 * che ci permette di gestire la posizione delle pedine
 */
public class Vector2d {

    protected double x;
    protected double y;
    protected double total;
    protected double angle;


    public Vector2d() {

    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }
    public void setPosition(double x , double y){
        this.x = x;
        this.y = y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void set(double x, double y) {
        this.setX(x);
        this.setY(y);
    }


    public double dot(Vector2d v2) {
        double result;
        result = this.getX() * v2.getX() + this.getY() * v2.getY();
        return result;
    }

    public double getLength() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }


    public Vector2d add(Vector2d v2) {
        Vector2d result = new Vector2d(x, y);
        result.setX(getX() + v2.getX());
        result.setY(getY() + v2.getY());
        return result;
    }

    public Vector2d subtract(Vector2d v2) {
        Vector2d result = new Vector2d(x, y);
        result.setX(this.getX() - v2.getX());
        result.setY(this.getY() - v2.getY());
        return result;
    }

    public Vector2d multiply(double scaleFactor) {
        Vector2d result = new Vector2d(x, y);
        result.setX(this.getX() * scaleFactor);
        result.setY(this.getY() * scaleFactor);
        return result;
    }

    public Vector2d normalize() {
        double len = getLength();
        if (len != 0.0f) {
            this.setX(this.getX() / len);
            this.setY(this.getY() / len);
        } else {
            this.setX(0.0f);
            this.setY(0.0f);
        }

        return this;
    }

    public String toString() {
        return "X: " + getX() + " Y: " + getY();
    }


    public void setXY(double total, double angle) {
        if (angle < 0) angle = (2 * Math.PI) + angle;
        this.total = total;
        x = this.total * Math.cos(angle);
        y = this.total * Math.sin(angle);
        this.angle = angle;

    }


    public void totalXY() {
        total = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }


}
