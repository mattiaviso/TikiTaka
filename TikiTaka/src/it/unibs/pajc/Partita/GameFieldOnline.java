package it.unibs.pajc.Partita;

public class GameFieldOnline extends GameField{

    public GameFieldOnline() {
        objectsPiece = new FieldObject[6];
        positionStart();

    }



    /**
     * Posizione iniziale delle pedine
     */
    private void positionStart() {
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
    }


    // online
    public void cambioTurno() {
        if (getTurno().equals("T1")) {
            setTurno("T2");
        } else if (getTurno().equals("T2")) {
            setTurno("T1");
        }
    }



    @Override
    public void checkVincitore() {
        super.checkVincitore();
        if (getScore1() == 3 || getScore2()== 3) {
            setScore1(0);
            setScore2(0);
        }
    }


    @Override
    public void ifGoal2() {
        super.ifGoal1();

    }


    @Override
    public void ifGoal(String who) {
        super.ifGoal(who);

        if (who == "T2"){
            positionStart();
            setScore2(getScore2()+1);
            setTurno("T2");
        }else if (who == "T1"){
            s
        }
    }
}
