package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Vector2d;

import static it.unibs.pajc.Partita.GameField.MAX_Y;

public class CollisionBorderUp implements StrategyCollision {

    @Override
    public void collisionBoard(FieldObject object) {
        object.getPosition().setY(MAX_Y - object.getRadius());
        object.setVelocita(new Vector2d(object.getVelocita().getX(), -object.getVelocita().getY()));
    }
}
