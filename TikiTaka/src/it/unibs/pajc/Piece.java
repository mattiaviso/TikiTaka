package it.unibs.pajc;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class Piece extends FieldObject{

    protected float radius;

    public Piece(float radius) {
        this.radius = radius;
        this.shape = new Area(createPiece(radius,10));
        this.color = Color.red;
    }

    private GeneralPath createPiece(float radius, int nVertex) {
        Random rnd = new Random();

        GeneralPath s = new GeneralPath();

        float dt = (float) Math.PI*2 / nVertex;
        float t = 0;
        for(int i=0;i<nVertex;i++, t += dt) {
            float r = radius * (1 + rnd.nextFloat()/10);
            float x = (float) (r * Math.cos(t));
            float y = (float) (r * Math.sin(t));


            if(i==0) {
                s.moveTo(x, y);
            }
            else {

                switch (rnd.nextInt(3)) {
                    case 1:
                        s.curveTo(
                                x*(1+(2-rnd.nextDouble())/5),
                                y*(1+(2-rnd.nextDouble())/5),
                                x*(1+(2-rnd.nextDouble())/5),
                                y*(1+(2-rnd.nextDouble())/5),
                                x,
                                y);
                        break;
                    case 2:
                        s.quadTo(
                                x*(1+(2-rnd.nextDouble())/5),
                                y*(1+(2-rnd.nextDouble())/5),
                                x,
                                y);
                        break;
                    case 0:
                        s.lineTo(x, y);
                        break;


                }

            }

        }

        s.closePath();

        return s;
    }



    /*public Piece(double diameter, double positionX, double positionY, Image image ) {
        this.diameters = diameter;
        this.position[0] = positionX;
        this.position[1] = positionY;
        this.image = image;
    }*/
}
