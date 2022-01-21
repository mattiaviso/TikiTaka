package it.unibs.pajc;

import javax.tools.FileObject;
import java.awt.*;
import java.util.ArrayList;

public class GameField {

    protected ArrayList<FieldObject> objectsPiece;
    protected String turno;

    public GameField() {
        objectsPiece = new ArrayList<>();
        turno = "T1";

        positionStart();
    }

    private void positionStart() {
        objectsPiece.add(new Piece(40,520,0,"Pedina1.png","T1"));
        objectsPiece.add(new Piece(40,170,70,"Pedina1.png","T1"));
        objectsPiece.add( new Piece(40,170,-70,"Pedina1.png","T1"));
        objectsPiece.add(new Piece(40,350,180,"Pedina1.png","T1"));
        objectsPiece.add(new Piece(40,350,-180,"Pedina1.png","T1"));
        objectsPiece.add(new Ball(18, 0, 0 ));
        objectsPiece.add(new Piece(40,-520,0,"Pedina2.png","T2"));
        objectsPiece.add(new Piece(40,-170,70,"Pedina2.png","T2"));
        objectsPiece.add(new Piece(40,-170,-70,"Pedina2.png","T2"));
        objectsPiece.add(new Piece(40,-350,180,"Pedina2.png","T2"));
        objectsPiece.add(new Piece(40,-350,-180,"Pedina2.png","T2"));
    }

    public void stepNext() {
        for (FieldObject o: objectsPiece) {
           o.move();
            //collisionDetection();
            //checkCollisions();



        }
    }

    public FieldObject checkClickAble(int xMouse, int yMouse) {
        if(!allStop()) return null;
        for(FieldObject f : objectsPiece){
            if(f instanceof Piece)
            if(Math.pow((xMouse- f.position.getX()),2)+Math.pow((yMouse-f.position.getY()),2)<Math.pow((f.radius),2) && ((Piece)f).team.equals(turno)){
                System.out.println("ciao ");
                return f;
            }

        }
        return null;
    }

    public void cambioTurno(){
        if(turno.equals("T1") ){
            turno = "T2";
        }else if (turno.equals("T2")){
            turno="T1";
        }
    }

    public boolean allStop(){
        for(FieldObject o: objectsPiece){
            if(!o.speedIsZero()) return false;
        }
        return true;
    }


    public void collisionDetection(){
        for (int i = 0; i <objectsPiece.size() ; i++) {
            if( !objectsPiece.get(i).speedIsZero()){
                for (int j = 0; j <objectsPiece.size() ; j++) {
                    if (objectsPiece.get(i).collision(objectsPiece.get(j)))
                        objectsPiece.get(i).resolveCollision(objectsPiece.get(j));

                }
            }
        }
    }
























    public void checkCollisions()
    {

        // Check for collision with walls
        for (int i = 0;  i< objectsPiece.size(); i++)
        {
            //	System.out.println("Ball #" + i + ": " + (balls[i].position.getX() - balls[i].getRadius()));

            if (objectsPiece.get(i).position.getX() - objectsPiece.get(i).getRadius() < -566)
            {
                objectsPiece.get(i).position.setX(objectsPiece.get(i).getRadius()); // Place ball against edge
                objectsPiece.get(i).velocita.setX(-(objectsPiece.get(i).velocita.getX() )); // Reverse direction and account for friction
                objectsPiece.get(i).velocita.setY(objectsPiece.get(i).velocita.getY() );
            }
            else if (objectsPiece.get(i).position.getX() + objectsPiece.get(i).getRadius() > 566) // Right Wall
            {
                objectsPiece.get(i).position.setX(566 - objectsPiece.get(i).getRadius());		// Place ball against edge
                objectsPiece.get(i).velocita.setX(-(objectsPiece.get(i).velocita.getX() )); // Reverse direction and account for friction
                objectsPiece.get(i).velocita.setY((objectsPiece.get(i).velocita.getY() ));
            }

            if (objectsPiece.get(i).position.getY() - objectsPiece.get(i).getRadius() < 312)				// Top Wall
            {
                objectsPiece.get(i).position.setY(objectsPiece.get(i).getRadius());				// Place ball against edge
                objectsPiece.get(i).velocita.setY(-(objectsPiece.get(i).velocita.getY() )); // Reverse direction and account for friction
                objectsPiece.get(i).velocita.setX((objectsPiece.get(i).velocita.getX() ));
            }
            else if (objectsPiece.get(i).position.getY() + objectsPiece.get(i).getRadius() > -302) // Bottom Wall
            {
                objectsPiece.get(i).position.setY(-302 - objectsPiece.get(i).getRadius());		// Place ball against edge
                objectsPiece.get(i).velocita.setY(-(objectsPiece.get(i).velocita.getY()));    // Reverse direction and account for friction
                objectsPiece.get(i).velocita.setX((objectsPiece.get(i).velocita.getX() ));
            }

            // Ball to Ball collision
            for(int j =  + 1; j < objectsPiece.size(); j++)
            {
                if ((objectsPiece.get(i).position.getX() + objectsPiece.get(i).getRadius()) < (objectsPiece.get(j).position.getX() - objectsPiece.get(j).getRadius()))
                    break;

                if((objectsPiece.get(i).position.getY() + objectsPiece.get(i).getRadius()) < (objectsPiece.get(j).position.getY() - objectsPiece.get(j).getRadius()) ||
                        (objectsPiece.get(j).position.getY() + objectsPiece.get(j).getRadius()) < (objectsPiece.get(i).position.getY() - objectsPiece.get(i).getRadius()))
                    continue;

                objectsPiece.get(i).resolveCollision(objectsPiece.get(j));

            }
        }

    }


}

