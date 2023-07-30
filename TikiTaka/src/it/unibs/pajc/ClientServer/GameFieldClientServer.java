package it.unibs.pajc.ClientServer;

import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.Partita.Piece;

public class GameFieldClientServer extends GameField {


    public  FieldObject pedinaSelezionata(double x, double y) {
        double EPS = 1E-3;
        for (int i = 0; i < getObjectsPiece().length; i++) {
            if (Math.abs(x - selezionaElemento(i).getPosition().getX()) < EPS && Math.abs(y - selezionaElemento(i).getPosition().getY()) < EPS) {
                return selezionaElemento(i);
            }
        }
        return null;
    }




    @Override
    public void setTurnoAlternativo(String turno) {
        setTurno(turno);
    }

    @Override
    public void positionStart() {
        setObj(0, new Piece(40, 500, 0, "Pedina1.png", "T1"));
        setObj(1, new Piece(40, 170, 70, "Pedina1.png", "T1"));
        setObj(2, new Piece(40, 170, -70, "Pedina1.png", "T1"));
        setObj(3, new Piece(40, 350, 180, "Pedina1.png", "T1"));
        setObj(4, new Piece(40, 350, -180, "Pedina1.png", "T1"));
        setObj(5, new Ball(18, 0, 0));
        setObj(6, new Piece(40, -520, 0, "Pedina2.png", "T2"));
        setObj(7, new Piece(40, -170, 70, "Pedina2.png", "T2"));
        setObj(8, new Piece(40, -170, -70, "Pedina2.png", "T2"));
        setObj(9, new Piece(40, -350, 180, "Pedina2.png", "T2"));
        setObj(10, new Piece(40, -350, -180, "Pedina2.png", "T2"));
    }

    @Override
    public void setScore(int score) {
        if ( score ==1){
            setScore1( getScore1()+1);
        }
        if ( score ==2){
            setScore2( getScore2()+1);
        }
    }


    public void checkVincitore() {
        if (getScore1() == 3 || getScore2() == 3) {
            setScore1(0);
            setScore2(0);
        }
    }




}
