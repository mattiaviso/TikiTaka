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


        // Controllo collisioni con bordi del campo
        for (int i = 0; i < objectsPiece.length; i++) {
            borderCollision(objectsPiece[i], gameFieldInterface);

            objectsPiece[i].friction(0.02);

            // Controllo collisione pedine contro pedina
            for (int j = i + 1; j < objectsPiece.length; j++) {

                if ((objectsPiece[i].getPosition().getX() + objectsPiece[i].getRadius()) <= (objectsPiece[j].getPosition().getX() - objectsPiece[j].getRadius()))
                    break;

                if ((objectsPiece[i].getPosition().getY() + objectsPiece[i].getRadius()) <= (objectsPiece[j].getPosition().getY() - objectsPiece[j].getRadius()) ||
                        (objectsPiece[j].getPosition().getY() + objectsPiece[j].getRadius()) <= (objectsPiece[i].getPosition().getY() - objectsPiece[i].getRadius()))
                    continue;

                objectsPiece[i].resolveCollision(objectsPiece[j]);
                objectsPiece[i].friction(0.035);
                objectsPiece[j].friction(0.015);






                }
            }

        }

                /*if (distance <= objectsPiece[i].getRadius() + objectsPiece[j].getRadius() - 10 ) {
                    SoundClip collision = new SoundClip("collision");
                    collision.play();
                    System.out.println("collision");*/

    //}
        /**
         * Gestisce la collisione di una pedina con i bordi del campo.
         *
         * @param object            pedina da controllare per la collisione con i bordi.
         * @param gameFieldInterface Interfaccia del campo di gioco per gestire la collisione.
         */
        private void borderCollision (FieldObject object, GameFieldInterface gameFieldInterface){
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


            } else if (y + radius > MAX_Y) {
                gameFieldInterface.setCollisionForBouard(true);
                StrategyCollision collisionUp = new CollisionBorderUp();
                collisionUp.collisionBoard(object);


            }

            //manca tutta la seconda parte


        }

        /**
         * Ordina l'array di oggetti comparabili in modo crescente utilizzando l'algoritmo di ordinamento per inserimento (Insertion Sort).
         * @param a L'array di oggetti comparabili da ordinare.
         */
        public void insertionSort (Comparable[]a){
            for (int p = 1; p < a.length; p++) {
                Comparable tmp = a[p];
                int j = p;

                for (; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--)
                    a[j] = a[j - 1];

                a[j] = tmp;
            }
        }

    }






