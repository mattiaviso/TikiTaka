package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameFieldInterface;

public class CollisionThread extends Thread {

    private FieldObject[] objectsPiece;
    private GameFieldInterface gameFieldInterface;

    public CollisionThread(FieldObject[] objectsPiece, GameFieldInterface gameFieldInterface) {
        this.objectsPiece = objectsPiece;
        this.gameFieldInterface = gameFieldInterface;
    }

    @Override
    public void run() {
        Collision collision = new Collision();
        collision.checkCollision(objectsPiece, gameFieldInterface);
    }
}
