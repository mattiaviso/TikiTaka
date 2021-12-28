package it.unibs.pajc;

import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;


public class GameField {
    protected ArrayList<FieldObject> objects;
    protected Rectangle2D.Float bordersFloat;
    //protected SpaceShip ship;

    public GameField() {
        objects = new ArrayList<>();
        bordersFloat = new Rectangle2D.Float(-500f, -500f, 1000f, 1000f);

        //createNewSpaceShip();

        objects.add(createAsteroid(50,-400,-400,1f,1f,0.1f));


    }

    /*
    public void createNewSpaceShip() {
        ship = new SpaceShip();
        objects.add(ship);
    }
*/
    public Piece createAsteroid(float r, float x, float y, float vx, float vy, float vr) {
        Piece a = new Piece(r);
        a.setX(x);a.setY(y);
        a.setVelocity(vx, vy, vr);

        return a;
    }

    public void stepNext() {
        for (FieldObject o: objects) {
            o.stepNext();
            applyCloseUniverse(o);
        }
        checkCollision();

        removeZombie();

    }

    public void checkCollision() {
        int nobjs = objects.size();
        if(nobjs<2)
            return;

        FieldObject[] objs = new FieldObject[nobjs];
        objects.toArray(objs);

        for(int i=0;i<nobjs-1;i++) {
            for(int j=i+1;j<nobjs;j++) {
                if(!objs[i].alive || !objs[j].alive)
                    continue;

                if(objs[i].checkCollision(objs[j])) {
                    objs[i].collisionDetected();
                    objs[j].collisionDetected();
                }
            }
        }


    }

    public void removeZombie() {
        ArrayList<FieldObject> aliveObjects = new ArrayList<>();

        objects.forEach(o -> {
            if(o.isAlive()) aliveObjects.add(o);

        });

        objects = aliveObjects;
    }

    //se la navicella esce rientra dall'altra parte
    private void applyCloseUniverse(FieldObject o) {
        if(o.getY()> bordersFloat.getMaxY())
            o.setY((float) bordersFloat.getMinY());
        else if (o.getY()<bordersFloat.getMinY())
            o.setY((float) bordersFloat.getMaxY());

        if(o.getX()> bordersFloat.getMaxX())
            o.setX((float) bordersFloat.getMinX());
        else if(o.getX()<bordersFloat.getMinX())
            o.setX((float) bordersFloat.getMaxX());
    }


        /*
    public SpaceShip getShip() {
        return ship;
    }


    public void fire() {
        Bullet bullet = new Bullet(ship, 5, 100);

        objects.add(bullet);
    }
*/

}
