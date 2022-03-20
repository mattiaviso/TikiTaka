package it.unibs.pajc.Partita;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class GameField {

    public static final int MAX_X = 566;
    public static final int MIN_X = -566;
    public static final int MIN_Y = -302;
    public static final int MAX_Y = 312;
    public static final float EPSILON = 0.09f;

    private int score1 = 0;
    private int score2 = 0;//
    protected FieldObject[] objectsPiece;
    private String turno;

     static public boolean collision = false;

    public String getTurno() {
        return turno;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public FieldObject[] getObjectsPiece() {
        return objectsPiece;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public void setObjectsPiece(FieldObject[] objectsPiece) {
        this.objectsPiece = objectsPiece;
    }
    // partite online
    public GameField() {
        turno = "T1";

        //objectsPiece = new FieldObject[11];
        //positionStart();
    }


    // partite offline
    // non so come risolvere il probelema del secondo costruttore uguale






    // online

    public String messaggioPos() {
        String string = "";
        for (FieldObject f : objectsPiece) {
            string += f.toString() + "\n";
        }
        return string;
    }

    public void setCollision() {
        this.collision = false;
    }

    public boolean getCollision(){
        return collision;
    }
    // online
    public void cambioTurno() {
    }

    public void setTurno(String team) {
        turno = team;
    }

    public boolean allStop() {
        for (FieldObject o : objectsPiece) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }

    public void insertionSort(Comparable[] a) {
        for (int p = 1; p < objectsPiece.length; p++) {
            Comparable tmp = a[p];
            int j = p;

            for (; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];

            a[j] = tmp;
        }
    }

    /**
     * Metodo che se invocato manda avanti il gioco, si occupa di spostamenti ecc
     * Come se fosse lo StepNext
     */
    public void updateGame() {
        for (int i = 0; i < objectsPiece.length; i++) {
            objectsPiece[i].velocita.setY(objectsPiece[i].velocita.getY());
            objectsPiece[i].position.setX(objectsPiece[i].position.getX() + (objectsPiece[i].velocita.getX() * (1)));
            objectsPiece[i].position.setY(objectsPiece[i].position.getY() + (objectsPiece[i].velocita.getY() * (1)));


            if (Math.abs(objectsPiece[i].velocita.getX()) < EPSILON) objectsPiece[i].velocita.setX(0);
            if (Math.abs(objectsPiece[i].velocita.getY()) < EPSILON) objectsPiece[i].velocita.setY(0);
        }

        checkCollisions();

    }
    /**
     * Metodo permette di vedere se l'utente ha selezionato una pedina oppure il nulla
     *
     * @param xMouse Coordinata x del mouse
     * @param yMouse Coordinata y del mouse
     * @return Ritorna l'oggetto premuto, altrimenti null se non si preme nulla
     */
    public FieldObject checkClickAble(int xMouse, int yMouse) {
        if (!allStop()) return null;
        for (FieldObject f : objectsPiece) {
            if (f instanceof Piece)
                if (Math.pow((xMouse - f.position.getX()), 2) + Math.pow((yMouse - f.position.getY()), 2) < Math.pow((f.radius), 2) && ((Piece) f).team.equals(turno)) {
                    return f;
                }
        }
        return null;
    }

    public void checkCollisions() {
        insertionSort(objectsPiece);


        // Controllo collisioni con bordi del campo
        for (int i = 0; i < objectsPiece.length; i++) {


            if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() < MIN_X) // Bordo Sx
            {
                collision = true ;
                //bordiPorta(objectsPiece[i], 1 );
                if (objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112) {
                    if (objectsPiece[i].position.getX() + objectsPiece[i].getRadius() < MIN_X) {

                        ifGoal1();

                    }
                } else {
                    objectsPiece[i].position.setX(objectsPiece[i].getRadius() + MIN_X);
                    objectsPiece[i].velocita = new Vector2d(-objectsPiece[i].velocita.getX(), +objectsPiece[i].velocita.getY());
                }
            } else if (objectsPiece[i].position.getX() + objectsPiece[i].getRadius() > MAX_X) // Bordo DX
            {
                collision = true;
                if (objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112) {

                    if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() > MAX_X) {
                        positionStart();
                        score1++;

                        setTurno("T1");
                        ifGoal("T2");
                    }
                } else {

                    objectsPiece[i].position.setX(MAX_X - objectsPiece[i].getRadius());
                    objectsPiece[i].velocita = new Vector2d(-objectsPiece[i].velocita.getX(), objectsPiece[i].velocita.getY());
                }
            }

            if (objectsPiece[i].position.getY() - objectsPiece[i].getRadius() < MIN_Y)                //Bordo Down
            {
                collision = true;
                objectsPiece[i].position.setY(objectsPiece[i].getRadius() + MIN_Y);
                objectsPiece[i].velocita = new Vector2d(objectsPiece[i].velocita.getX(), -objectsPiece[i].velocita.getY());
            } else if (objectsPiece[i].position.getY() + objectsPiece[i].getRadius() > MAX_Y) //Bordo UP
            {
                collision = true;
                objectsPiece[i].position.setY(MAX_Y - objectsPiece[i].getRadius());
                objectsPiece[i].velocita = new Vector2d(objectsPiece[i].velocita.getX(), -objectsPiece[i].velocita.getY());

            }

            // crea una frizione tra i 2 oggetti
            objectsPiece[i].friction(0.02);


            // Collisione pedina contro pedina
            for (int j = i + 1; j < objectsPiece.length; j++) {
                if ((objectsPiece[i].position.getX() + objectsPiece[i].getRadius()) < (objectsPiece[j].position.getX() - objectsPiece[j].getRadius()))
                    break;

                if ((objectsPiece[i].position.getY() + objectsPiece[i].getRadius()) < (objectsPiece[j].position.getY() - objectsPiece[j].getRadius()) ||
                        (objectsPiece[j].position.getY() + objectsPiece[j].getRadius()) < (objectsPiece[i].position.getY() - objectsPiece[i].getRadius()))
                    continue;

                objectsPiece[i].resolveCollision(objectsPiece[j]);
                objectsPiece[i].friction(0.035);
                objectsPiece[j].friction(0.015);

            }
        }


    }

    public FieldObject pedinaSelezionata(double x, double y) {
        double EPS = 1E-3;
        for (int i = 0; i < objectsPiece.length; i++) {
            if (Math.abs(x - objectsPiece[i].position.getX()) < EPS && Math.abs(y - objectsPiece[i].position.getY()) < EPS) {
                return objectsPiece[i];
            }
        }
        return null;
    }

// solo online
public void checkVincitore() {
}

// sistemato malissimo ricontrollare
public void ifGoal(String who){
    collision = false;


}







}

