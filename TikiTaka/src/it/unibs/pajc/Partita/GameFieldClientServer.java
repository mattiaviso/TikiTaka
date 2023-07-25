package it.unibs.pajc.Partita;

import it.unibs.pajc.Partita.Collision.Collision;
import it.unibs.pajc.singlePlayer.GameFieldSingle;
import it.unibs.pajc.singlePlayer.GameFiled;

import java.io.ObjectInputStream;

/**
 * Model
 */
public class GameFieldClientServer extends GameFiled {

    public static final int MAX_X = 566;
    public static final int MIN_X = -566;
    public static final int MIN_Y = -302;
    public static final int MAX_Y = 312;
    public static final float EPSILON = 0.09f;







    /**
     * Metodo costruttore che inizializza gli 11 oggetti del gioco
     * e le relative componenti grafiche
     */
    public GameFieldClientServer() {
        super(0,0, new FieldObject[11], "T1");
        positionStart();
    }



    /**
     * Creazione stringa con posizioni di tutte le pedine, che verra in seguito invitato al server
     *
     * @return String con informazioni sulle coordiante delle pedine
     */
    public String messaggioPos() {
        String string = "";
        for (FieldObject f : getObjectsPiece()) {
            string += f.toString() + "\n";
        }
        return string;
    }

    /**
     * Metodo che ritorna la pedina premuta  date le coordinate x e y
     *
     * @param x Double x
     * @param y Double y
     * @return pedina seleziononata
     */
    public FieldObject pedinaSelezionata(double x, double y) {

        double EPS = 1E-3;
        for (int i = 0; i < getObjectsPiece().length; i++) {
            if (Math.abs(x - getObjectsPiece()[i].getPosition().getX()) < EPS && Math.abs(y - getObjectsPiece()[i].getPosition().getY()) < EPS) {
                return getObjectsPiece()[i];
            }
        }
        return null;
    }







    @Override
    public int gool(FieldObject o, double sup, double min) {
        return 0;
    }





    /**
     * metodo per resettare il risulato alla vittoria
     */
    public void checkVincitore() {
        if (getScore1()== 3 || getScore2() == 3) {
            setScore1(0);
            setScore2(0);
        }
    }

}

