package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameFieldInterface;
import it.unibs.pajc.Partita.Vector2d;

import static it.unibs.pajc.Partita.GameField.MIN_Y;



/**
 * La classe CollisionBorderleft implementa l'interfaccia StrategyCollision ed è responsabile di gestire le collisioni di un oggetto (pedina) con il bordodown del campo di gioco.
 */
public class collisionBorederDown implements StrategyCollision {
    GameFieldInterface gameFieldInterface;


    @Override
    public void collisionBoard(FieldObject object) {
        object.getPosition().setY(object.getRadius() + MIN_Y);
        object.setVelocita( new Vector2d(object.getVelocita().getX(), -object.getVelocita().getY()));
    }
}
