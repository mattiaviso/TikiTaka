package it.unibs.pajc.singlePlayer;

import it.unibs.pajc.Partita.*;
import it.unibs.pajc.Partita.Collision.Collision;

public abstract class GameFiled {
    public static final float EPSILON = 0.09f;
    private int score1;
    private int score2;
    private FieldObject[] objectsPiece;
    private String turno;


    public GameFiled(int score1, int score2, FieldObject[] objectsPiece, String turno) {
        this.score1 = score1;
        this.score2 = score2;
        this.objectsPiece = objectsPiece;
        this.turno = turno;
    }

    /**
     * Metodo che effettua il cambio del turno dopo il tiro
     */
    public final void cambioTurno() {
        if (turno.equals("T1")) {
            turno = "T2";
        } else if (turno.equals("T2")) {
            turno = "T1";
        }
    }

    public  void setTurno(String team) {
        turno = team;
    }


    public  void setScore(int score) {
        if (score == 1) {
            score1 = score1 + 1;
        }
        if (score == 2) {
            score2 = score2 + 1;
        }
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public FieldObject[] getObjectsPiece() {
        return objectsPiece;
    }

    public void setObjectsPiece(FieldObject[] objectsPiece) {
        this.objectsPiece = objectsPiece;
    }

    public String getTurno() {
        return turno;
    }

    // quando andremo ad aggiungere la modalita' allenamento sara da cambiare questo metodo
    public final void positionStart() {
        objectsPiece[0] = (new Piece(40, 500, 0, "Pedina1.png", "T1"));
        objectsPiece[1] = (new Piece(40, 170, 70, "Pedina1.png", "T1"));
        objectsPiece[2] = (new Piece(40, 170, -70, "Pedina1.png", "T1"));
        objectsPiece[3] = (new Piece(40, 350, 180, "Pedina1.png", "T1"));
        objectsPiece[4] = (new Piece(40, 350, -180, "Pedina1.png", "T1"));
        objectsPiece[5] = (new Ball(18, 0, 0));
        objectsPiece[6] = (new Piece(40, -520, 0, "Pedina2.png", "T2"));
        objectsPiece[7] = (new Piece(40, -170, 70, "Pedina2.png", "T2"));
        objectsPiece[8] = (new Piece(40, -170, -70, "Pedina2.png", "T2"));
        objectsPiece[9] = (new Piece(40, -350, 180, "Pedina2.png", "T2"));
        objectsPiece[10] = (new Piece(40, -350, -180, "Pedina2.png", "T2"));
    }

    /**
     * Metodo permette di vedere se l'utente ha selezionato una pedina oppure il nulla
     *
     * @param xMouse Coordinata x del mouse
     * @param yMouse Coordinata y del mouse
     * @return Ritorna l'oggetto premuto, altrimenti null se non si preme nulla
     */
    public final FieldObject checkClickAble(int xMouse, int yMouse) {
        // if (!allStop()) return null;
        for (FieldObject f : objectsPiece) {
            if (f instanceof Piece)
                if (Math.pow((xMouse - f.getPosition().getX()), 2) + Math.pow((yMouse - f.getPosition().getY()), 2) < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(turno)) {
                    return f;
                }
        }
        return null;
    }

    /**
     * Metodo che se invocato manda avanti di un esecuzione il gioco, si occupa dello spostamento e dei controlli vari
     * Come se fosse lo StepNext
     */
    public final void updateGame() {

        for (int i = 0; i < objectsPiece.length; i++) {
            Vector2d velocity = objectsPiece[i].getVelocita();
            Vector2d position = objectsPiece[i].getPosition();

            velocity.setY(velocity.getY());
            position.setX(position.getX() + (velocity.getX() * 1));
            position.setY(position.getY() + (velocity.getY() * 1));

            if (Math.abs(velocity.getX()) < EPSILON) {
                velocity.setX(0);
            }
            if (Math.abs(velocity.getY()) < EPSILON) {
                velocity.setY(0);
            }
        }

        Collision gestioneCollisioni = new Collision();
        gestioneCollisioni.checkCollision(objectsPiece, this);
    }



    /**
     * ci permette di vedere se tutte le pedine sono ferme
     * @return
     */
    public final boolean allStop() {
        for (FieldObject o : objectsPiece) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }


    public abstract void setCollisionForBouard(Boolean collision);
    public abstract int gool(FieldObject o , double sup , double min);
    public abstract void checkVincitore();

    public  abstract String messaggioPos();



}
