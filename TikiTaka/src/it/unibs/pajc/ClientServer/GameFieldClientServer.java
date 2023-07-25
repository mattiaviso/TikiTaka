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

    @Override
    public int gool(FieldObject o, double sup, double min) {
        return 0;
    }


}
