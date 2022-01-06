package it.unibs.pajc;

import java.awt.image.BufferedImage;

abstract public class FieldObject {
    protected double [] position;
    protected double radius;
    protected BufferedImage imageObj;
    protected double [] velocity;


    abstract public boolean isBall();

    public FieldObject(double radius, double x,double y) {
        position = new double[2];
        this.radius = radius;
        this.position[0] = x;
        this.position[1] = y;
    }

    public double getX() {
        return position[0];
    }

    public double getY() {
        return position[1];
    }

    public double getRadius() {
        return radius;
    }

    public BufferedImage getImageObj() {
        return imageObj;
    }



    public void stepNext(){
        for (int i = 0; i < 2; i++) {
            position[i]+= velocity[i];
        }
    }
}
