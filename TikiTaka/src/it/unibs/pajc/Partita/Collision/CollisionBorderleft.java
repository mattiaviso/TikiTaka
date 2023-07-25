package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.Partita.*;

import static it.unibs.pajc.Partita.GameFieldClientServer.MIN_X;

public class CollisionBorderleft implements StrategyCollision {
    GameFieldInterface gameFieldInterface ;
    // utilizziamo l'interfaccia per andare a settare dei paramentri
    public CollisionBorderleft(GameFieldInterface gameFieldInterface) {
        this.gameFieldInterface = gameFieldInterface;
    }

    @Override
    public void collisionBoard(FieldObject object ) {

          gameFieldInterface.setCollisionForBouard(true);
        if ((object instanceof Ball)&& object.getPosition().getY() + object.getRadius() > -98 && object.getPosition().getY() + object.getRadius() < 112) {
            gameFieldInterface.setCollisionForBouard(false);
            if (object.getPosition().getX() + object.getRadius() < MIN_X) {
                // utilizzato dependency invertion

                gameFieldInterface.positionStart();
                gameFieldInterface.setScore(2);
                gameFieldInterface.setTurno("T2");
            }
        } else {
            object.getPosition().setX(object.getRadius() + MIN_X);
            object.setVelocita(new Vector2d(-object.getVelocita().getX(), +object.getVelocita().getY()));
        }
    }

}
