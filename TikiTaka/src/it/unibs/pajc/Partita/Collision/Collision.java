package it.unibs.pajc.Partita.Collision;

import it.unibs.pajc.ClientServer.SoundClip;
import it.unibs.pajc.Partita.*;

import java.awt.geom.Point2D;

import static it.unibs.pajc.Partita.GameField.*;


/**
 * La classe Collision gestisce le collisioni tra le pedine e i muri all'interno del campo di gioco.
 */
public class Collision {


    /**
     * Gestisce tutte le collisioni tra le pedine e i muri.
     *
     * @param objectsPiece       Array contenente le pedine da controllare per le collisioni.
     * @param gameFieldInterface Interfaccia del campo di gioco per gestire le collisioni.
     */
    public void checkCollision(FieldObject[] objectsPiece, GameFieldInterface gameFieldInterface) {

        insertionSort(objectsPiece);

        for (int i = 0; i < objectsPiece.length; i++) {
            borderCollision(objectsPiece[i], gameFieldInterface);
            // Applica attrito agli oggetti
            objectsPiece[i].friction(0.02);
            for (int j = i + 1; j < objectsPiece.length; j++) {
                double dx = objectsPiece[i].getPosition().getX() - objectsPiece[j].getPosition().getX();
                double dy = objectsPiece[i].getPosition().getY() - objectsPiece[j].getPosition().getY();
                double distanceBetweenCenters = Math.sqrt(dx * dx + dy * dy);
                double sumOfRadii = objectsPiece[i].getRadius() + objectsPiece[j].getRadius();
                if (distanceBetweenCenters < sumOfRadii) {

                    objectsPiece[i].resolveCollision(objectsPiece[j]);


                    objectsPiece[i].friction(0.035);
                    objectsPiece[j].friction(0.015);

                    SoundClip collision = new SoundClip("collision");
                    collision.play();


                }
            }
        }
    }

    /**
     * Gestisce la collisione di una pedina con i bordi del campo.
     *
     * @param object             pedina da controllare per la collisione con i bordi.
     * @param gameFieldInterface Interfaccia del campo di gioco per gestire la collisione.
     */
    private void borderCollision(FieldObject object, GameFieldInterface gameFieldInterface) {
        double x = object.getPosition().getX();
        double y = object.getPosition().getY();
        double radius = object.getRadius();
        if (x - radius < MIN_X) {
            StrategyCollision collisionSx = new CollisionBorderleft(gameFieldInterface);
            collisionSx.collisionBoard(object);
        } else if (x + radius > MAX_X) {
            StrategyCollision collisionDx = new CollisionBorderRight(gameFieldInterface);
            collisionDx.collisionBoard(object);
        }
        if (y - radius < MIN_Y) {
            gameFieldInterface.setCollisionForBouard(true);
            StrategyCollision collisionDown = new collisionBorederDown();
            collisionDown.collisionBoard(object);
            SoundClip collision = new SoundClip("collision");
            collision.play();
        } else if (y + radius > MAX_Y) {
            gameFieldInterface.setCollisionForBouard(true);
            StrategyCollision collisionUp = new CollisionBorderUp();
            collisionUp.collisionBoard(object);
            SoundClip collision = new SoundClip("collision");
            collision.play();

        }




    }

    /**
     * Ordina l'array di oggetti comparabili in modo crescente utilizzando l'algoritmo di ordinamento per inserimento (Insertion Sort).
     *
     * @param a L'array di oggetti comparabili da ordinare.
     */
    public void insertionSort(Comparable[] a) {
        for (int p = 1; p < a.length; p++) {
            Comparable tmp = a[p];
            int left = 0;
            int right = p - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (tmp.compareTo(a[mid]) < 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            for (int j = p - 1; j >= left; j--) {
                a[j + 1] = a[j];
            }


            a[left] = tmp;
        }
    }

}






