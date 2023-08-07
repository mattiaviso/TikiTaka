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
        // Ordina gli oggetti in base alla coordinata X (potresti voler usare un algoritmo di ordinamento più efficiente)
        insertionSort(objectsPiece);

        // Controllo collisioni con bordi del campo
        for (int i = 0; i < objectsPiece.length; i++) {
            borderCollision(objectsPiece[i], gameFieldInterface);

            // Applica attrito agli oggetti
            objectsPiece[i].friction(0.02);

            // Controllo collisione tra oggetti circolari
            for (int j = i + 1; j < objectsPiece.length; j++) {
                // Calcola la distanza tra i centri delle circonferenze
                double dx = objectsPiece[i].getPosition().getX() - objectsPiece[j].getPosition().getX();
                double dy = objectsPiece[i].getPosition().getY() - objectsPiece[j].getPosition().getY();
                double distanceBetweenCenters = Math.sqrt(dx * dx + dy * dy);

                // Calcola la somma dei raggi delle circonferenze
                double sumOfRadii = objectsPiece[i].getRadius() + objectsPiece[j].getRadius();

                // Verifica la collisione solo se le circonferenze si sovrappongono
                if (distanceBetweenCenters < sumOfRadii) {
                    // Risolvi la collisione tra oggetti circolari
                    objectsPiece[i].resolveCollision(objectsPiece[j]);

                    // Applica attrito diverso agli oggetti coinvolti
                    objectsPiece[i].friction(0.035);
                    objectsPiece[j].friction(0.015);

                        SoundClip collision = new SoundClip("collision");
                        collision.play();



                }
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
                SoundClip collision = new SoundClip("collision");
                collision.play();


            } else if (x + radius > MAX_X) {
                StrategyCollision collisionDx = new CollisionBorderRight(gameFieldInterface);
                collisionDx.collisionBoard(object);
                SoundClip collision = new SoundClip("collision");
                collision.play();


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

            //manca tutta la seconda parte


        }

        /**
         * Ordina l'array di oggetti comparabili in modo crescente utilizzando l'algoritmo di ordinamento per inserimento (Insertion Sort).
         * @param a L'array di oggetti comparabili da ordinare.
         */
        public void insertionSort(Comparable[] a) {
            for (int p = 1; p < a.length; p++) {
                Comparable tmp = a[p];
                int left = 0;
                int right = p - 1;

                // Utilizza la ricerca binaria per trovare la posizione corretta per l'inserimento
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (tmp.compareTo(a[mid]) < 0) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }

                // Sposta gli elementi maggiori di tmp in avanti
                for (int j = p - 1; j >= left; j--) {
                    a[j + 1] = a[j];
                }

                // Inserisci l'elemento nella posizione corretta
                a[left] = tmp;
            }
        }


}






