package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;


import it.unibs.pajc.MainSinglePlayer;
import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.Partita.Piece;

import java.util.ArrayList;

public class GameFieldSinglePlayer extends GameField {


    public GameFieldSinglePlayer() {
        setScore1(0);
        setScore2(0);
        setTurno("T1");
        setObjectsPiece(new FieldObject[11]);
        positionStart();
    }

    //scopo per fase di testing
    public GameFieldSinglePlayer(FieldObject[] arrayFaseTest, String turno) {
        setScore1(0);
        setScore2(0);
        setTurno(turno);
        setObjectsPiece(arrayFaseTest);

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

    /**
     * Sovrascrive il metodo setScore per aggiornare i punteggi del gioco e visualizzarli nella vista.
     *
     * @param score Il valore del punteggio da aggiornare.
     */
    @Override
    public void setScore(int score) {

        if (score == 1) {
            setScore1(getScore1() + 1);
            MainSinglePlayer.panel.setScore1(getScore1());


        }
        if (score == 2) {
            setScore2(getScore2() + 1);
            MainSinglePlayer.panel.setScore2(getScore2());
        }

    }

    /**
     * Trova e restituisce l'oggetto FieldObject selezionato alle coordinate date per il turno del giocatore corrente.
     *
     * @param x La coordinata x del punto selezionato.
     * @param y La coordinata y del punto selezionato.
     * @return Il FieldObject selezionato se trovato e soddisfa i criteri, altrimenti restituisce null.
     */
    @Override
    public FieldObject pedinaSelezionata(double x, double y) {
        for (FieldObject f : getObjectsPiece()) {
            if (f instanceof Piece)
                if (Math.pow((x - f.getPosition().getX()), 2) + Math.pow((y - f.getPosition().getY()), 2) < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(getTurno())) {
                    return f;
                }
        }
        return null;
    }

    /**
     * Controlla se un giocatore ha raggiunto il punteggio vincente e reimposta i punteggi se un giocatore vince.
     */
    public void checkVincitore() {
        if (getScore1() == 3 || getScore2() == 3) {
            setScore1(0);
            setScore2(0);
            MainSinglePlayer.panel.setTable(0, 0);

        }


    }


    public Computer piecePiuVicina(FieldObject ball) {
        FieldObject piece = null;
        ArrayList<FieldObject> rightpiece= new ArrayList<>();
        double distanzaMinima = Double.MAX_VALUE;
        for (FieldObject o : getObjectsPiece()) {
            if (o instanceof Piece) {
                if (o.getTeam().equalsIgnoreCase("T2")) {
                    double distanza = Math.sqrt(Math.pow(o.getPosition().getX() - ball.getPosition().getX(), 2)
                            + Math.pow(o.getPosition().getY() - ball.getPosition().getY(), 2));
                    if (o.getPosition().getX() - ball.getPosition().getX() < 0) {
                        if (distanza < distanzaMinima) {
                            distanzaMinima = distanza;
                            piece = o;
                        }
                    }else{
                        rightpiece.add(o);
                    }
                }
            }
        }
        if (piece == null) {
            for (FieldObject c : rightpiece) {
                if (c instanceof Piece) {
                    if (c.getTeam().equalsIgnoreCase("T2")) {
                        double distanza = Math.sqrt(Math.pow(c.getPosition().getX() - ball.getPosition().getX(), 2)
                                + Math.pow(c.getPosition().getY() - ball.getPosition().getY(), 2));
                        if (distanza < distanzaMinima) {
                            distanzaMinima = distanza;
                            piece = c;
                        }
                    }
                }
            }
        }
        return new Computer(piece, distanzaMinima);

    }


    /**
     * Funzione piecePiuVicina(palla):
     *     oggettoPiece <-- null
     *     ArraylistOggettiDestra <-- 0
     *     distanzaMinima <-- ValoreMassimoDouble
     *
     *     Per ogni i nell'elencoDeiPezzetti():
     *         Se i è una Pedina E il suo team è "T2":
     *             distanza <-- CalcolaDistanzaTra(palla, i)
     *             Se la coordinata X dell'oggetto i è a sinistra della palla e a distanza < distanzaMinima :
     *                     distanzaMinima <-- distanza
     *                     oggettoPiece <-- oggetto
     *             Altrimenti:
     *                 Aggiungi oggetto alla ArraylistOggettiDestra
     *
     *     Se oggettoPiece è nullo:
     *         Per ogni oggetto in listaOggettiDestra:
     *             Se l'oggetto è una Pedina E il suo team è "T2":
     *                 distanza <-- CalcolaDistanzaTra(palla, oggetto)
     *                 Se distanza < distanzaMinima:
     *                     distanzaMinima <-- distanza
     *                     oggettoPiece <-- oggetto
     *
     *     Ritorna un nuovo Computer con oggettoPiece e distanzaMinima
     * Fine Funzione
     */


}
