package it.unibs.pajc.Allenamento.Livelli;

import it.unibs.pajc.Allenamento.GameFieldTraining;
import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Piece;

public class livelloState2  implements  ILivelli{

    private GameFieldTraining gioco;

    public livelloState2(GameFieldTraining gioco) {
        this.gioco = gioco;
    }

    @Override
    public void positionStart() {
        gioco.setObj(0, new Piece(40, 500, 0, "Pedina1.png", "T1"));
        gioco.setObj(1, new Ball(18, 0, 0));
        gioco.setObj(2, new Piece(40, -520, 0, "Pedina2.png", "T2"));

        gioco.setObj(3, new Piece(40, -170, 70, "Pedina2.png", "T2"));
        gioco.setObj(4, new Piece(40, -170, -70, "Pedina2.png", "T2"));
        gioco.setObj(5, new Piece(40, -350, 180, "Pedina2.png", "T2"));
        gioco.setObj(6, new Piece(40, -350, -180, "Pedina2.png", "T2"));

    }



    @Override
    public void cambiaLivello() {
        gioco.impostaStato(null);
    }


}
