package it.unibs.pajc;

import java.awt.image.BufferedImage;


abstract public class FieldObject {
    public static final int MAXSPEED = 150; // era 150
    public static final double DMURO = 0.15;
    public static final double DECVEL = 0.05;// era 0.007
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
        // controllo che non serve
        if (vel >MAXSPEED) vel = MAXSPEED/7.5;
        velocita.setXY(vel, angle);
        velocita.totalXY();

    }


    public void setVelocita(double velocita) {
        if (velocita > MAXSPEED) velocita = MAXSPEED;
        this.velocita.setSum(velocita);
    }

    public void move() {

        // angolo direzione pareti

        this.velocita.totalXY();
        this.position.totalXY();



        this.position.setX(this.position.getX() + velocita.getX());
        this.position.setY(this.position.getY() + velocita.getY());


        //COLLISSIONI DELLE PARETI
        double minX = this.position.getX() - this.radius;
        double maxX = this.position.getX() + this.radius;
        double minY = this.position.getY() - this.radius;
        double maxY = this.position.getY() + this.radius;
        // per vedere chi segna
       /* if (this.isBall() && this.position.getY() > -302.0D && this.position.getY() < 312.0D) {
            if (minX < -566.0D) {
                // return 2;
                System.out.println("ciao");
            } else if (maxX > 566.0D) {
                //return 1;
                System.out.println("mamma");
            }
        }
*/

        if (minX < -566.0D) { //bordo a SX

            if (minY >= -98.0D && maxY < 112.0D && isBall()) { //bordo porta
                if (minX < -630.0D) {// direzione palla quando tocca il fine porta
                    setVelocita(0);
                }
            } else { //bordo non porta

                velocita = new Vector2d(- this.velocita.getX(), this.velocita.getY());
                velocita.setSum(velocita.getSum() - (velocita.getSum() * DMURO));
            }

        } else if (maxX > 566.0D) { //bordo a DX

            if (maxY > -98.0D && maxY < 112.0D && isBall()) {
                if (this.position.getX() > 630)
                    setVelocita(0);

            } else { //bordo fuori dalla porta
                velocita = new Vector2d(- this.velocita.getX(), this.velocita.getY());
                velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);
            }

        } else if (minY < -302.0D) { //bordo Down

            velocita = new Vector2d( this.velocita.getX(), -this.velocita.getY());
            velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);

        } else if (maxY > 312.0D) { //bordo UP
            velocita = new Vector2d( this.velocita.getX(), -this.velocita.getY());
            velocita.setSum(velocita.getSum() - velocita.getSum() * DMURO);
        }

    }

    // vedere se la pallina e ferma
    public boolean speedIsZero() {
        final double EPSILON = 1E-4;
        return Math.abs(velocita.getLength()) < EPSILON ? true : false;
    }


    // METODO COLLISIONI
    public void collision(FieldObject ball) {
        this.velocita.totalXY();
        ball.velocita.totalXY();




        Vector2d n = this.position.subtract(ball.position);
        double dist = n.getLength();
        double r = getRadius() + ball.getRadius();


        if (dist> r) {
            return;
        }


     Vector2d mtb = n.multiply((ball.radius - dist) / dist);
        this.position = this.position.add(mtb.multiply(1 /4));
        ball.position = ball.position.subtract(mtb.multiply(1 / 4));

        Vector2d un = n.multiply(1 / n.getLength());
        Vector2d ut = new Vector2d(-un.y, un.x);
        double v1n = un.dot(this.velocita);
        double v1t = ut.dot(this.velocita);
        double v2n = un.dot(ball.velocita);
        double v2t = ut.dot(ball.velocita);



        // VETTORI PESATI
        double v1nTag = (((this.massa - ball.getMassa())* v1n) + (2*ball.getMassa()*v2n))/ (this.massa + ball.massa);
        double v2nTag = (((ball.getMassa()- this.massa) * v2n )+ (2*this.massa*v1n))/ (this.massa + ball.massa);


        Vector2d v1nTagChange = un.multiply(v1nTag);
        Vector2d v1tTag = un.multiply(v1t);
        Vector2d v2nTagChange = un.multiply(v2nTag);
        Vector2d v2tTag = un.multiply(v2t);

        this.velocita = v1nTagChange.add(v1tTag);
        ball.velocita = v2nTagChange.add(v2tTag);

    }



 /* double v1F = ((this.massa - ball.getMassa())* this.velocita.total + 2*ball.getMassa()*ball.velocita.total)/ (this.massa + ball.massa);
        double v2F = ((ball.getMassa()- this.massa) * ball.velocita.total + 2*this.massa*this.velocita.total)/ (this.massa + ball.massa);
        // direzione triangolo
         double angle = Math.atan2(this.position.getY()- ball.position.getY(), this.position.getX()- ball.position.getX());
         //System.out.println( "collisione " + angle);

             velocita.setXY(v1F, angle);
             ball.velocita.setXY(v2F , Math.PI/2  - angle);*/

    public void friction (){

        if(this.speedIsZero())return;

         velocita.multiply(0.984);
        if( velocita.getLength() <5 ){
            velocita = new Vector2d(0,0);

        }
    }



}















/*


    //Calculate distance between 2 balls


    //Assign velocity and angle of each ball from their current state
    double velocityA = this.velocita.total;
    double velocityB = ball.velocita.total;
    double radA = this.velocita.angle;
    double radB = ball.velocita.angle;

    //Calculate distance in x and y for each ball and calculate phi
    double deltaY = ball.position.getY() - this.position.getY();
    double deltaX = ball.position.getX() - this.position.getX();
    double phi = Math.atan2(deltaY, deltaX);

    //Calculate total mass
    double totalMass = this.massa + ball.massa;
    double cosA = Math.cos(phi);
    double sinA = Math.sin(phi);

    //Calculate Normal and Perpendicular velocities
    double velANorm = velocityA*Math.cos(-radA + phi);
    double velAPerp = velocityA*Math.sin(-radA + phi);
    double velBNorm = velocityB*Math.cos(-radB + phi);
    double velBPerp = velocityB*Math.sin(-radB + phi);

    //energy conservation
    //not really used since ball masses are equal
    double velA2Norm = ( (this.massa-ball.massa)/totalMass) * velANorm
            + ( (2*ball.massa/totalMass)*velBNorm);
    double velB2Norm = ( (ball.massa-this.massa)/totalMass) * velBNorm
            + ( (2*this.massa/totalMass)*velANorm);

    //Caculate new positions for each ball
    double velA2X = velA2Norm * cosA-velAPerp * Math.cos(phi + Math.PI/2);
    double velA2Y = velA2Norm * sinA-velAPerp * Math.sin(phi + Math.PI/2);
    double velB2X = velB2Norm * cosA-velBPerp * Math.cos(phi + Math.PI/2);
    double velB2Y = velB2Norm * sinA-velBPerp * Math.sin(phi + Math.PI/2);

//If a collision is detected, the collision is resolved for each ball
//with new velocities and directions


                velocityA = Math.sqrt(velA2X * velA2X + velA2Y * velA2Y);
                        velocityB = Math.sqrt(velB2X * velB2X + velB2Y * velB2Y);

                        double angle = Math.atan2(velA2Y, velA2X);
                        double angleBall  = Math.atan2(velB2Y, velB2X);

                        this.velocita.setX(Math.cos(angle) * velocityA);
                        this.velocita.setY(Math.sin(angle) * velocityA);
                        ball.velocita.setX( Math.cos(angleBall) * velocityB);
                        ball.velocita.setY( Math.sin(angleBall) * velocityB);
























*/
