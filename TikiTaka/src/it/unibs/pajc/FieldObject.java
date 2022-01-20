package it.unibs.pajc;

import javax.tools.FileObject;
import java.awt.image.BufferedImage;



abstract public class FieldObject {
    public static final int MAXSPEED = 150;
    public static final double DMURO = 0.25;
    protected double [] position;
    protected double radius;
    protected BufferedImage imageObj;
    protected double speed;
    protected double direction;
    protected double massa;


    abstract public boolean isBall();

    public FieldObject(double radius, double x,double y,  double massa) {
        position = new double[2];
        this.radius = radius;
        this.position[0] = x;
        this.position[1] = y;
        this.massa = massa;
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

    public double getMassa() {
        return massa;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }

    public void start(int distance, double angle){
        speed = distance/7.5;
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
        // decremento della velocita
        if(speed <=1 ) speed = 0;
        else {
               speed = speed - speed*(0.017);
        }

        // angolo direzione pareti
        this.position[0] += (speed * Math.cos(direction));
        this.position[1] += (speed * Math.sin(direction));

        //COLLISSIONI DELLE PARETI
        double minX = this.position[0] - this.radius;
        double maxX = this.position[0] + this.radius;
        double minY = this.position[1] - this.radius ;
        double maxY = this.position[1] + this.radius;

        if (this.isBall() && this.position[1] > -302.0D && this.position[1] < 312.0D) {
            if (this.position[0] - this.radius < -566.0D) {
                return 2;
            }
            else if (this.position[0] + this.radius > 566.0D) {
                return 1;
            }
        }



        if (minX < -566.0D) { //bordo a SX

            if(minY>=-98.0D && maxY <112.0D && isBall()){ //bordo porta
                if(minX<-630.0D){// direzione palla quando tocca il fine porta
                    setSpeed(0);
                }
            }else{ //bordo non porta
                this.direction = -(this.direction-Math.PI);
                speed-=(speed* DMURO);
            }

        } else if (maxX > 566.0D) { //bordo a DX

            if(maxY >-98.0D && maxY< 112.0D && isBall()){
                if(getX()>630)
                    setSpeed(0);

            }else{ //bordo fuori dalla porta
                this.direction = -(this.direction-Math.PI);
                speed-=(speed* DMURO);
            }

        } else if (minY < -302.0D) { //bordo Down
            this.direction = this.direction + 2 * (3/2*Math.PI - this.direction);
            speed-=(speed* DMURO);

        } else if (maxY > 312.0D) { //bordo UP
            this.direction = this.direction + 2 * (3/2*Math.PI - this.direction);
            speed-=(speed* DMURO);
        }

        return 0;
    }

    // vedere se la pallina e ferma
    public boolean speedIsZero(){
        final double EPSILON = 1E-4;
        return Math.abs(speed) < EPSILON ? true : false;
    }

    // collissione
    public boolean  collision(FieldObject ball) {

        // distanza 2 palline
        double xd = position[0] -ball.getX();
        double yd = position[1] - ball.getY();
        double powRadius = Math.pow(getRadius() + ball.getRadius(),2);

        double distance = Math.pow(xd,2) + Math.pow(yd,2);

        if ( distance <= powRadius)
            return true;

        return false;

    }


    public void resolveCollision (FieldObject ball){




        double ms1 = 1/massa;
        double ms2 = 1/ ball.getMassa();


    }




/*
    public Vector2d add(Vector2d v2)
    {
        Vector2d result = new Vector2d();
        result.setX(getX() + v2.getX());
        result.setY(getY() + v2.getY());
        return result;
    }

    public Vector2d subtract(Vector2d v2)
    {
        Vector2d result = new Vector2d();
        result.setX(this.getX() - v2.getX());
        result.setY(this.getY() - v2.getY());
        return result;
    }

    public Vector2d multiply(float scaleFactor)
    {
        Vector2d result = new Vector2d();
        result.setX(this.getX() * scaleFactor);
        result.setY(this.getY() * scaleFactor);
        return result;
    }

    public Vector2d normalize()
    {
        float len = getLength();
        if (len != 0.0f)
        {
            this.setX(this.getX() / len);
            this.setY(this.getY() / len);
        }
        else
        {
            this.setX(0.0f);
            this.setY(0.0f);
        }

        return this;
    }*/
}
