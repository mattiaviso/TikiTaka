package it.unibs.pajc;

import javax.tools.FileObject;
import java.awt.image.BufferedImage;
import java.util.Vector;


abstract public class FieldObject {
    public static final int MAXSPEED = 150;
    public static final double DMURO = 0.15;
    protected Vector2d position;
    protected double radius;
    protected BufferedImage imageObj;
    protected double  speed;
    protected double direction;
    protected double massa;


    abstract public boolean isBall();

    public FieldObject(double radius, double x,double y,  double massa) {

        this.radius = radius;
        this.position = new Vector2d(x,y);

        this.massa = massa;
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
        this.position.setX(this.position.getX() + (speed * Math.cos(direction)));
        this.position.setY(this.position.getY() + (speed * Math.sin(direction)));

        //COLLISSIONI DELLE PARETI
        double minX = this.position.getX() - this.radius;
        double maxX = this.position.getX() + this.radius;
        double minY = this.position.getY() - this.radius ;
        double maxY = this.position.getY() + this.radius;

        if (this.isBall() && this.position.getY() > -302.0D && this.position.getY() < 312.0D) {
            if (minX < -566.0D) {
                return 2;
            }
            else if (maxX > 566.0D) {
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
                if(this.position.getX()>630)
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
        double xd = position.getX()-ball.position.getX();
        double yd = position.getY()- ball.position.getY();
        double powRadius = Math.pow(getRadius() + ball.getRadius(),2);

        double distance = Math.pow(xd,2) + Math.pow(yd,2);

        if ( distance <= powRadius)
            return true;

        return false;

    }


    public void resolveCollision (FieldObject ball){

        Vector2d delta = (position.subtract(ball.position));
        double d = delta.getLength();
        Vector2d mtd = delta.multiply( (((getRadius() + ball.getRadius())-d)/d));


        double im1 = 1/massa;
        double im2 = 1/ ball.getMassa();

        position = position.add(mtd.multiply(im1 / (im1 + im2)));
        ball.position = ball.position.subtract(mtd.multiply(im2 / (im1 + im2)));

        double speedX = (speed*Math.cos(this.direction))- (ball.speed * Math.cos(ball.direction));
        double speedY =(speed*Math.sin(this.direction))- (ball.speed * Math.sin(ball.direction));
        Vector2d v = new Vector2d(speedX, speedY);
        double  vn = v.dot(mtd.normalize());

        // sphere intersecting but moving away from each other already
        if (vn > 0.0f) return;

        // collision impulse
        double i = (-(1.0f + 0.85D) * vn) / (im1 + im2);
        Vector2d impulse = mtd.normalize().multiply(i);


        double sX = speed*Math.cos(this.direction);
        double sY = speed*Math.sin(this.direction);


        sX+= impulse.multiply(im1).getX();
        sY+= impulse.multiply(im1).getY();

        this.speed = Math.sqrt((sX*sX)+(sY*sY));


         double sX2 =ball.speed* Math.cos(ball.direction);
         double sY2 =ball.speed* Math.sin(ball.direction);

         sX2-= impulse.multiply(im2).getX();
         sY2-= impulse.multiply(im2).getY();
         ball.setSpeed(Math.sqrt((sX2*sX2)+(sY2*sY2)));



        this.position.setX(this.position.getX() + sX);
        this.position.setY(this.position.getY() + sY);



        ball.position.setX(ball.position.getX() + sX2);
        ball.position.setY(ball.position.getY() + sY2);








    }



}
