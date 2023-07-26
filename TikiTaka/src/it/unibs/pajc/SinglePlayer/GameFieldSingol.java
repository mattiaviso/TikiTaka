package it.unibs.pajc.SinglePlayer;

import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.Partita.Piece;

public class GameFieldSingol extends GameField {

    public int gool(FieldObject o, double sup, double min) {


        if (o.getPosition().getY() + o.getRadius() < 108 && o.getPosition().getY() - o.getRadius() > -98 && o.isBall) {
            return 1;

        } else if (o.getPosition().getY() + o.getRadius() < 108 && o.getPosition().getY() - o.getRadius() > min && o.isBall) {
            return 2;
        } else return 0;
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

    @Override
    public FieldObject pedinaSelezionata(double x, double y) {
        for (FieldObject f : getObjectsPiece()) {
            if (f instanceof Piece)
                if (Math.pow((x - f.getPosition().getX()), 2) + Math.pow((y - f.getPosition().getY()), 2)
                        < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(getTurno())) {
                    return f;
                }
        }
        return null;
    }

    @Override
    public void checkVincitore() {

    }

    


}
