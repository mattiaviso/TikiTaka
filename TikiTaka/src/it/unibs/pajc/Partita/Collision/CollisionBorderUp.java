package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Vector2d;

import static it.unibs.pajc.Partita.GameField.MAX_Y;


/**
 * La classe CollisionBorderleft implementa l'interfaccia StrategyCollision ed è responsabile di gestire le collisioni di un oggetto (pedina) con il bordo up del campo di gioco.
 */
public class CollisionBorderUp implements StrategyCollision {

    @Override
    public void collisionBoard(FieldObject object) {
        object.getPosition().setY(MAX_Y - object.getRadius());
        object.setVelocita(new Vector2d(object.getVelocita().getX(), -object.getVelocita().getY()));
    }
}
