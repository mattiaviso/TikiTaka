package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.ClientServer.SoundClip;
import it.unibs.pajc.Partita.*;

import static it.unibs.pajc.Partita.GameField.MAX_X;


/**
 * La classe CollisionBorderleft implementa l'interfaccia StrategyCollision ed è responsabile di gestire le collisioni di un oggetto (pedina) con il destro del campo di gioco.
 */
public class CollisionBorderRight implements StrategyCollision {
    GameFieldInterface gameFieldInterface ;

    public CollisionBorderRight(GameFieldInterface gameFieldInterface) {
        this.gameFieldInterface = gameFieldInterface;
    }

    @Override
    public void collisionBoard(FieldObject object) {
         gameFieldInterface.setCollisionForBouard(true);
        if ((object instanceof Ball) && object.getPosition().getY() + object.getRadius() > -98 && object.getPosition().getY() + object.getRadius() < 112) {

            gameFieldInterface.setCollisionForBouard(false);
            if (object.getPosition().getX() - object.getRadius() > MAX_X) {
                gameFieldInterface.positionStart();
                gameFieldInterface.setScore(1);
                gameFieldInterface.setTurno("T2");
            }
        } else {

            object.getPosition().setX(MAX_X - object.getRadius());
            object.setVelocita( new Vector2d(-object.getVelocita().getX(), object.getVelocita().getY()));
            SoundClip collision = new SoundClip("collision");
            collision.play();
        }

    }
}
