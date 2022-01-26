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
        objectsPiece.add(new Piece(40, 520, 0, "Pedina1.png", "T1"));
        objectsPiece.add(new Piece(40, 170, 70, "Pedina1.png", "T1"));
        objectsPiece.add(new Piece(40, 170, -70, "Pedina1.png", "T1"));
        objectsPiece.add(new Piece(40, 350, 180, "Pedina1.png", "T1"));
        objectsPiece.add(new Piece(40, 350, -180, "Pedina1.png", "T1"));
        objectsPiece.add(new Ball(18, 0, 0));
        objectsPiece.add(new Piece(40, -520, 0, "Pedina2.png", "T2"));
        objectsPiece.add(new Piece(40, -170, 70, "Pedina2.png", "T2"));
        objectsPiece.add(new Piece(40, -170, -70, "Pedina2.png", "T2"));
        objectsPiece.add(new Piece(40, -350, 180, "Pedina2.png", "T2"));
        objectsPiece.add(new Piece(40, -350, -180, "Pedina2.png", "T2"));
    }

    public void stepNext() {
        for (FieldObject o : objectsPiece) {
            collisionCheck();
            o.move();



        }
    }

    public FieldObject checkClickAble(int xMouse, int yMouse) {
        if (!allStop()) return null;
        for (FieldObject f : objectsPiece) {
            if (f instanceof Piece)
                if (Math.pow((xMouse - f.position.getX()), 2) + Math.pow((yMouse - f.position.getY()), 2) < Math.pow((f.radius), 2) && ((Piece) f).team.equals(turno)) {
                    System.out.println("ciao ");
                    return f;
                }

        }
        return null;
    }

    public void cambioTurno() {
        if (turno.equals("T1")) {
            turno = "T2";
        } else if (turno.equals("T2")) {
            turno = "T1";
        }
    }

    public boolean allStop() {
        for (FieldObject o : objectsPiece) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }


    public  void collisionCheck (){
        for (int i = 0; i < objectsPiece.size(); i++)
        {
            for (int j = i+1; j < objectsPiece.size(); j++)
            {
                    objectsPiece.get(i).collision(objectsPiece.get(j));

            }
        }
    }











}

