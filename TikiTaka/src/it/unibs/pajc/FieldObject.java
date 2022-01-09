package it.unibs.pajc;

import java.awt.image.BufferedImage;

abstract public class FieldObject {
    public static final int MAXSPEED = 20;
    protected double [] position;
    protected double radius;
    protected BufferedImage imageObj;
    protected double speed;
    protected double direction;


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



    public void start(int distance, double angle){
        speed = distance;
        if(speed> MAXSPEED)speed= MAXSPEED;
        setDirection(angle);
    }

    protected void setDirection(double angle){
        direction = angle;
    }

    public void setSpeed(double speed){
        if(speed>MAXSPEED) speed=MAXSPEED;
        this.speed = speed;
    }

    public int move(){
        if(speed <=0 ) speed = 0;

        else {
               speed = speed - speed*0.045;

        }

        // probabile errore
        this.position[0] += (speed* Math.cos(direction));
        this.position[1] += (speed * Math.sin(direction));
        return 0;


        // COLLISSIONI DELLE PARETI
    }



}
