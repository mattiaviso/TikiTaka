package it.unibs.pajc;

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
            o.stepNext();

        }
    }

    public boolean checkClickAble(int xMouse, int yMouse) {
        for(FieldObject f : objectsPiece){
            if(f instanceof Piece)
            if(Math.pow(xMouse-f.getX(),2)+Math.pow(yMouse-f.getY(),2)<Math.pow((f.radius),2) && ((Piece)f).team.equals(turno)){
                System.out.println("ciao ");
                return true;
            }

        }
        return false;
    }

    public void cambioTurno(){
        if(turno.equals("T1")){
            turno = "T2";
        }else{
            turno="T1";
        }
    }
}

