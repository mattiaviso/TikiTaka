package it.unibs.pajc;

import java.awt.image.BufferedImage;


abstract public class FieldObject {
    public static final int MAXSPEED = 150;
    public static final double DMURO = 0.15;
    protected Vector2d position;
    protected double radius;
    protected BufferedImage imageObj;
    protected Vector2d velocita = new Vector2d();
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

        /*velocita = new Vector2d(distance/7.5);*/
        velocita.setSum(distance/7.5);
        if(velocita.sum > MAXSPEED) velocita.setSum(MAXSPEED);
        velocita.setXY(angle);

    }

    protected void setDirection(double angle){
        direction = angle;
    }

    public void setVelocita(double velocita){
        if(velocita >MAXSPEED) velocita =MAXSPEED;
        this.velocita.setSum(velocita);
    }

    /*public int move(){
        // decremento della velocita
        if(this.velocita.getSum()<=1 ) velocita.setSum(0);
        else {
               velocita.setSum(velocita.getSum()- velocita.getSum() *(0.017));
        }

        // angolo direzione pareti
        this.position.setX(this.position.getX() + velocita.getX());
        this.position.setY(this.position.getY() + velocita.getY());

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
                    setVelocita(0);
                }
            }else{ //bordo non porta
                this.direction = -(this.direction-Math.PI);
                velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);
            }

        } else if (maxX > 566.0D) { //bordo a DX

            if(maxY >-98.0D && maxY< 112.0D && isBall()){
                if(this.position.getX()>630)
                    setVelocita(0);

            }else{ //bordo fuori dalla porta
                this.direction = -(this.direction-Math.PI);
                velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);
            }

        } else if (minY < -302.0D) { //bordo Down
            this.direction = this.direction + 2 * (3/2*Math.PI - this.direction);
            velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);

        } else if (maxY > 312.0D) { //bordo UP
            this.direction = this.direction + 2 * (3/2*Math.PI - this.direction);
            velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);
        }

        return 0;
    }*/

    // vedere se la pallina e ferma
    public boolean speedIsZero(){
        final double EPSILON = 1E-4;
        return Math.abs(velocita.sum) < EPSILON ? true : false;
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

        Vector2d v = this.velocita.subtract(ball.velocita);
        double  vn = v.dot(mtd.normalize());

        // sphere intersecting but moving away from each other already
        if (vn > 0.0f) return;

        // collision impulse
        double i = (-(1.0f + 0.85D) * vn) / (im1 + im2);
        Vector2d impulse = mtd.normalize().multiply(i);


        this.velocita =  this.velocita.add(impulse.multiply(im1));
        ball.velocita =  ball.velocita.subtract(impulse.multiply(im2));


    }





















}
