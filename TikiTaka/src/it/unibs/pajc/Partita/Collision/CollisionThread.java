package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameFieldInterface;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.Computer;

public class CollisionThread extends Thread {

    private FieldObject[] objectsPiece;
    private GameFieldInterface gameFieldInterface;
    private  Collision collision ;

    public CollisionThread(FieldObject[] objectsPiece, GameFieldInterface gameFieldInterface) {
        this.objectsPiece = objectsPiece;
        this.gameFieldInterface = gameFieldInterface;
         collision = new Collision();
    }



    @Override
    public void run() {

        collision.checkCollision(objectsPiece, gameFieldInterface);
    }
}
