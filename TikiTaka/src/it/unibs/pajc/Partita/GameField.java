package it.unibs.pajc.Partita;


import it.unibs.pajc.Partita.Collision.CollisionThread;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.Computer;

/**
 * Model
 */
public abstract class GameField implements GameFieldInterface {

    public static final int MAX_X = 566;
    public static final int MIN_X = -566;
    public static final int MIN_Y = -302;
    public static final int MAX_Y = 312;
    public static final float EPSILON = 0.09f;

    private int score1;
    private int score2;
    private FieldObject[] objectsPiece;
    private String turno;

    static public boolean collision = false;
    public boolean allStop = true;
    

    public void setAllStop(boolean allStop) {
        this.allStop = allStop;
    }

    public String getTurno() {
        return turno;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public FieldObject[] getObjectsPiece() {
        return objectsPiece;
    }

    public void setCollision() {
        this.collision = false;
    }

    @Override
    public void setCollisionForBouard(Boolean collision) {
        this.collision = collision;

    }

    public boolean getCollision() {
        return collision;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setObjectsPiece(FieldObject[] objectsPiece) {
        this.objectsPiece = objectsPiece;
    }

    public static boolean isCollision() {
        return collision;
    }

    public static void setCollision(boolean collision) {
        GameField.collision = collision;
    }

    public FieldObject selezionaElemento(int i) {
        return objectsPiece[i];
    }

    public void setObj(int i, FieldObject object) {
        objectsPiece[i] = object;
    }


    /**
     * Imposta le posizioni iniziali delle pedine dopo un gol o al calcio d'inizio.
     */
    @Override
    public abstract void positionStart();

    /**
     * Crea una stringa con le posizioni di tutte le pedine per inviarla al server.
     *
     * @return La stringa con le informazioni sulle coordinate delle pedine.
     */
    public String messaggioPos() {
        String string = "";
        for (FieldObject f : objectsPiece) {
            string += f.toString() + "\n";
        }
        return string;
    }


    /**
     * Cambia il turno tra i due team dopo il tiro.
     */
    public final void cambioTurno() {
        if (turno.equals("T1")) {
            turno = "T2";
        } else if (turno.equals("T2")) {
            turno = "T1";
        }
    }


    @Override
    public abstract void setScore(int score);

    /**
     * Metodo che controlla se tutte le palline sono ferme
     *
     * @return True se tutte le palline sono ferme
     */
    public final boolean allStop() {
        for (FieldObject o : objectsPiece) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }



    /**
     * Metodo che esegue un'iterazione del gioco, si occupa dello spostamento e dei controlli vari.
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

        /*Collision gestioneCollisioni = new Collision();
        gestioneCollisioni.checkCollision(objectsPiece, this);*/
        CollisionThread collisionThread = new CollisionThread(objectsPiece, this);
        collisionThread.start();


    }


    /**
     * Trova la pedina premuta date le coordinate x e y.
     *
     * @param x Coordinata x.
     * @param y Coordinata y.
     * @return La pedina selezionata.
     */
    public abstract FieldObject pedinaSelezionata(double x, double y);

    /**
     * Verifica se c'è un vincitore e aggiorna il punteggio.
     */
    public abstract void checkVincitore();


    /**
     * Seleziona la palla presente nell'array di pedine.
     *
     * @return La Ball se presente nell'array, altrimenti null.
     */
    public FieldObject selezionaBall() {
        FieldObject ball = null;
        for (FieldObject o : objectsPiece) {
            if (o instanceof Ball) {
                ball = o;
            }
        }
        return ball;
    }

    /**
     * Trova la pedina del team "T2" più vicina alla posizione della palla prima controllando se ci sono sul retro poi controllandole tuttr .
     *
     * @param ball La palla di riferimento per il calcolo della distanza.
     * @return Un oggetto Computer contenente la pedina più vicina e la sua distanza dalla palla.
     */
    public  abstract Computer piecePiuVicina(FieldObject ball);


    /**
     * Trova la pedina nell'array con la stessa posizione specificata.
     *
     * @param piece2 L'oggetto FieldObject con la posizione da cercare.
     * @return La pedina trovata con la stessa posizione, se presente nell'array, altrimenti null.
     */
    public FieldObject findPieceByPosition(FieldObject piece2) {
        for (FieldObject piece : objectsPiece) {
            if (piece.getPosition().getX() == piece2.getPosition().getX() && piece2.getPosition().getY() == piece.getPosition().getY()) {
                return piece;
            }
        }
        return null;

    }
}

