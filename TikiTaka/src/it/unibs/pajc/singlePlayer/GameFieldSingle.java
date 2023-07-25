package it.unibs.pajc.singlePlayer;

import it.unibs.pajc.Partita.*;
import it.unibs.pajc.Partita.Collision.Collision;

public class GameFieldSingle extends GameFiled {

    public static final float EPSILON = 0.09f;







    public GameFieldSingle() {
        super(0,0,new FieldObject[11],"T1");
        positionStart();
    }

    /**
     * posizione iniziale gia inserita, quindi dovremmo trovare un modo tramite templete per non riscriverla
     */
 /*   @Override
    public void positionStart() {
        objectsPiece[0] = (new Piece(40, 520, 0, "Pedina1.png", "T1"));
        objectsPiece[1] = (new Piece(40, 170, 70, "Pedina1.png", "T1"));
        objectsPiece[2] = (new Piece(40, 170, -70, "Pedina1.png", "T1"));
        objectsPiece[3] = (new Piece(40, 350, 180, "Pedina1.png", "T1"));
        objectsPiece[4] = (new Piece(40, 350, -180, "Pedina1.png", "T1"));
        objectsPiece[5] = (new Ball(18, 0, 0));
        objectsPiece[6] = (new Piece(40, -520, 0, "Pedina2.png", "T2"));
        objectsPiece[7] = (new Piece(40, -170, 70, "Pedina2.png", "T2"));
        objectsPiece[8] = (new Piece(40, -170, -70, "Pedina2.png", "T2"));
        objectsPiece[9] = (new Piece(40, -350, 180, "Pedina2.png", "T2"));
        objectsPiece[10] = (new Piece(40, -350, -180, "Pedina2.png", "T2"));
    }*/

    /*
        public FieldObject checkClickAble(int xMouse, int yMouse) {
           // if (!allStop()) return null;
            for (FieldObject f : objectsPiece) {
                if (f instanceof Piece)
                    if (Math.pow((xMouse - f.getPosition().getX()), 2) + Math.pow((yMouse - f.getPosition().getY()), 2) < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(turno)) {
                        return f;
                    }
            }
            return null;
        }*/
  /*  public void cambioTurno() {
        if (turno.equals("T1")) {
            turno = "T2";
        } else if (turno.equals("T2")) {
            turno = "T1";
        }
    }

    @Override
    public void setTurno(String team) {
        turno = team;
    }

    @Override
    public void setScore(int score) {
        if (score == 1) {
            score1 = score1 + 1;
        }
        if (score == 2) {
            score2 = score2 + 1;
        }
    }*/




    /*public boolean allStop() {
            for (FieldObject o : objectsPiece) {
                if (!o.speedIsZero()) return false;
            }
            return true;
        }*/


    /**
     * Metodo che se invocato manda avanti il gioco, si occupa di spostamenti ecc
     * Come se fosse lo StepNext
     *//*
        public void updateGame() {
            for (int i = 0; i < objectsPiece.length; i++) {
                // Update the X and Y positions based on the X and Y velocities
                double newX = objectsPiece[i].getPosition().getX() + (objectsPiece[i].getVelocita().getX() * (1));
                double newY = objectsPiece[i].getPosition().getY() + (objectsPiece[i].getVelocita().getY() * (1));

                // Update the position using setter methods or a setPosition method in the FieldObject class
                objectsPiece[i].setXY(newX, newY);

                // Check if the velocities are close to zero, and set them to zero if they are within a threshold (EPSILON)
                if (Math.abs(objectsPiece[i].getVelocita().getX()) < EPSILON) {
                    objectsPiece[i].getVelocita().setX(0);
                }
                if (Math.abs(objectsPiece[i].getVelocita().getY()) < EPSILON) {
                    objectsPiece[i].getVelocita().setY(0);
                }
            }

            Collision gestioneCollisioni= new Collision();
            gestioneCollisioni.checkCollision(objectsPiece, this);
        }
*/
    public int gool(FieldObject o, double sup, double min) {
        // Retrieve the position and radius using getter methods
        double posY = o.getPosition().getY();
        double radius = o.getRadius();

        if (posY + radius < 108 && posY - radius > -98 && o.isBall()) {
            return 1;
        } else if (posY + radius < 108 && posY - radius > min && o.isBall()) {
            return 2;
        } else {
            return 0;
        }
    }
    // questi metodi sarebbero da togliere vedere un po
    @Override
    public void checkVincitore() {

    }

    @Override
    public String messaggioPos() {
        return null;
    }




}


