package it.unibs.pajc;

import java.awt.image.BufferedImage;


abstract public class FieldObject {
    public static final int MAXSPEED = 150;
    public static final double DMURO = 0.15;
    public static final double DECVEL = 0.017;
    protected Vector2d position;
    protected double radius;
    protected BufferedImage imageObj;
    protected Vector2d velocita = new Vector2d();
    protected double massa;


    abstract public boolean isBall();

    public FieldObject(double radius, double x, double y, double massa) {
        this.radius = radius;
        this.position = new Vector2d(x, y);
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

    public void start(int distance, double angle) {
        double vel = distance/7.5;
        if (vel > MAXSPEED) vel = MAXSPEED;
        velocita.setXY(vel ,angle);

    }


    public void setVelocita(double velocita) {
        if (velocita > MAXSPEED) velocita = MAXSPEED;
        this.velocita.setSum(velocita);
    }

    public int move() {
        // decremento della velocita
        velocita.totalXY();
        if (this.velocita.getSum() <= 1) velocita.set(0,0);
        else velocita.subtract(velocita.getX() * DECVEL, velocita.getY() * DECVEL );


        // angolo direzione pareti
        this.position.setX(this.position.getX() + velocita.getX());
        this.position.setY(this.position.getY() + velocita.getY());

        //COLLISSIONI DELLE PARETI
        double minX = this.position.getX() - this.radius;
        double maxX = this.position.getX() + this.radius;
        double minY = this.position.getY() - this.radius;
        double maxY = this.position.getY() + this.radius;

        if (this.isBall() && this.position.getY() > -302.0D && this.position.getY() < 312.0D) {
            if (minX < -566.0D) {
                return 2;
            } else if (maxX > 566.0D) {
                return 1;
            }
        }


        if (minX < -566.0D) { //bordo a SX

            if (minY >= -98.0D && maxY < 112.0D && isBall()) { //bordo porta
                if (minX < -630.0D) {// direzione palla quando tocca il fine porta
                    setVelocita(0);
                }
            } else { //bordo non porta
                System.out.println(this.velocita.getAngle());
                velocita.change(-1,1);
                velocita.setSum(velocita.getSum() - (velocita.getSum() * DMURO));
            }

        } else if (maxX > 566.0D) { //bordo a DX

            if (maxY > -98.0D && maxY < 112.0D && isBall()) {
                if (this.position.getX() > 630)
                    setVelocita(0);

            } else { //bordo fuori dalla porta
                velocita.change(-1,1);
                velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);
            }

        } else if (minY < -302.0D) { //bordo Down
            System.out.println("angolo prima"+ this.velocita.getAngle());
            velocita.change(1,-1);
            velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);

        } else if (maxY > 312.0D) { //bordo UP
            velocita.change(1,-1);
            velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);
        }
        return 0;
    }

    // vedere se la pallina e ferma
    public boolean speedIsZero() {
        final double EPSILON = 1E-4;
        return Math.abs(velocita.getX()) < EPSILON ? true : false;
    }



    // metodo per vedere se esiste la collisione


    public boolean colliding(FieldObject ball) {
        double xd = position.getX() - ball.position.getX();
        double yd = position.getY() - ball.position.getY();

        double sumRadius = getRadius() + ball.getRadius();
        double sqrRadius = sumRadius * sumRadius;

        double distSqr = (xd * xd) + (yd * yd);

        if (distSqr <= sqrRadius)
            return true;

        return false;
    }


        // METODO COLLISIONI


        public void collision (FieldObject ball){
        // velocita finali
            this.velocita.totalXY();
            ball.velocita.totalXY();
        double v1F = ((this.massa - ball.getMassa())* this.velocita.total + 2*ball.getMassa()*ball.velocita.total)/ (this.massa + ball.massa);
        double v2F = ((ball.getMassa()- this.massa) * ball.velocita.total + 2*this.massa*this.velocita.total)/ (this.massa + ball.massa);
        // direzione triangolo
         double angle = Math.atan2(this.position.getY()- ball.position.getY(), this.position.getX()- ball.position.getX());
         //System.out.println( "collisione " + angle);

             velocita.setXY(v1F, angle);
             ball.velocita.setXY(v2F , Math.PI/2  - angle);








        }




}

















