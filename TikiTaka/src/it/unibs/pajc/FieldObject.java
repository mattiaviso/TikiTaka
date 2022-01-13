package it.unibs.pajc;

import java.awt.image.BufferedImage;

abstract public class FieldObject {
    public static final int MAXSPEED = 150;
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
        if(speed <=1 ) speed = 0;
        else {
               speed = speed - speed*(0.017);
        }

        // probabile errore
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
            if(getY()+getRadius()>-98.0D && getY()+getRadius() < 108.0D){ //bordo porta
                if(getX()<-630){
                    setSpeed(0);
                }
            } else{ //bordo non porta
            this.direction = -(this.direction-Math.PI);
            this.position[0] = -566.0D + this.radius;
            }
        } else if (maxX > 566.0D) { //bordo a DX
            //quando la pedina entra in porta
            if(getY()+getRadius()>-98.0D && getY()+getRadius() < 108.0D){
                if(getX()>630){
                    setSpeed(0);
                }
            }else{ //bordo fuori dalla porta
                this.direction = -(this.direction-Math.PI);
                this.position[0] = 566.0D - this.radius;
            }

        } else if (minY < -302.0D) { //bordo Down
            this.direction = this.direction + 2 * (3/2f*Math.PI - this.direction);
            this.position[1] = -302.0D + this.radius;
        } else if (maxY > 312.0D) { //bordo UP
            this.direction = this.direction + 2 * (3/2f*Math.PI - this.direction);
            this.position[1] = 312.0D - this.radius;
        }



        return 0;
    }



}
