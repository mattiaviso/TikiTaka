package it.unibs.pajc.Allenamento.Livelli;

import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Piece;

public class livelloState2  implements  ILivelli{

    private FieldObject[] objectPiece;

    public livelloState2(FieldObject[] objectPiece) {
        this.objectPiece = objectPiece;
    }

    @Override
    public void positionStart() {
        setObj(0, new Piece(40, 500, 0, "Pedina1.png", "T1"));
        setObj(1, new Ball(18, 0, 0));
        setObj(2, new Piece(40, -520, 0, "Pedina2.png", "T2"));
        setObj(3, new Piece(40, -170, 70, "Pedina2.png", "T2"));
        setObj(4, new Piece(40, -170, -70, "Pedina2.png", "T2"));
        setObj(5, new Piece(40, -350, 180, "Pedina2.png", "T2"));
        setObj(6, new Piece(40, -350, -180, "Pedina2.png", "T2"));

    }

    @Override
    public FieldObject[] getFieldObject() {
        return objectPiece;
    }

    @Override
    public ILivelli cambiaLivello() {
        return null;
    }

    public void setObj(int i, FieldObject object) {
        objectPiece[i] = object;
    }
}
