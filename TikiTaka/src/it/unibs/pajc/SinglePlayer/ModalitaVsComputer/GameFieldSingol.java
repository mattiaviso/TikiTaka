package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;


import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.Partita.Piece;

public class GameFieldSingol extends GameField {


    public GameFieldSingol() {
       setScore1(0);
       setScore2(0);
        setTurno("T1");
        setObjectsPiece(new FieldObject[11]);
        positionStart();
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

    /**
     * Sovrascrive il metodo setScore per aggiornare i punteggi del gioco.
     * @param score Il valore del punteggio
     */
    @Override
    public void setScore(int score) {

        if ( score ==1){
            setScore1( getScore1()+1);
            TikiTakaGame.panel.setScore1(getScore1());


        }
        if ( score ==2){
            setScore2( getScore2()+1);
            TikiTakaGame.panel.setScore2(getScore2());
        }

    }

    /**
     * Trova e restituisce l'oggetto FieldObject selezionato alle coordinate date per il turno del giocatore corrente.
     * @param x La coordinata x del punto selezionato.
     * @param y La coordinata y del punto selezionato.
     * @return Il FieldObject selezionato se trovato e soddisfa i criteri, altrimenti restituisce null.
     */
    @Override
    public FieldObject pedinaSelezionata(double x, double y) {
        for (FieldObject f : getObjectsPiece()) {
            if (f instanceof Piece )
                if (Math.pow((x - f.getPosition().getX()), 2) + Math.pow((y - f.getPosition().getY()), 2)
                        < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(getTurno())) {
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
            TikiTakaGame.panel.setTable(0,0);

        }


    }







    


}
