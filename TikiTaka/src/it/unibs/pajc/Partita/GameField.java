package it.unibs.pajc.Partita;

import it.unibs.pajc.Partita.Collision.Collision;
import it.unibs.pajc.SinglePlayer.Computer;

import java.lang.reflect.Field;

/**
 * Model
 */
public abstract class GameField implements GameFieldInterface {

    public static final int MAX_X = 566;
    public static final int MIN_X = -566;
    public static final int MIN_Y = -302;
    public static final int MAX_Y = 312;
    public static final float EPSILON = 0.09f;

    private int score1 ;
    private int score2;
    private FieldObject[] objectsPiece;
    private String turno;

    static public boolean collision = false;
    public boolean allStop = true;

    public boolean isAllStop() {
        return allStop;
    }

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

    public void setObjectsPiece(FieldObject[] objectsPiece) {
        this.objectsPiece = objectsPiece;
    }

    public static boolean isCollision() {
        return collision;
    }

    public static void setCollision(boolean collision) {
        GameField.collision = collision;
    }

    /**
     * Metodo costruttore che inizializza gli 11 oggetti del gioco
     * e le relative componenti grafiche
     */
    public GameField() {
        this.score1 = 0;
        this.score2 = 0;
        this.turno = "T1";
        this.objectsPiece = new FieldObject[11];
        positionStart();
    }

    public void setObj(int i, FieldObject object) {
        objectsPiece[i] = object;
    }


    /**
     * Metodo che setta le posizioni iniziali delle pedine dopo un gol oppure al calcio d'inizio
     */
    @Override
    public final void positionStart() {
        setObj(0, new Piece(40, 500, 0, "Pedina1.png", "T1"));
        setObj(1, new Piece(40, 170, 70, "Pedina1.png", "T1"));
        setObj(2, new Piece(40, 170, -70, "Pedina1.png", "T1"));
        setObj(3, new Piece(40, 350, 180, "Pedina1.png", "T1"));
        setObj(4, new Piece(40, 350, -180, "Pedina1.png", "T1"));
        setObj(5, new Ball(18, 0, 0));
        setObj(6, new Piece(40, -520, 0, "Pedina2.png", "T2"));
        setObj(7, new Piece(40, -170, 70, "Pedina2.png", "T2"));
        setObj(8, new Piece(40, -170, -70, "Pedina2.png", "T2"));
        setObj(9, new Piece(40, -350, 180, "Pedina2.png", "T2"));
        setObj(10, new Piece(40, -350, -180, "Pedina2.png", "T2"));
    }

    /**
     * Creazione stringa con posizioni di tutte le pedine, che verra in seguito invitato al server
     *
     * @return String con informazioni sulle coordiante delle pedine
     */
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

    @Override
    public void setCollisionForBouard(Boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
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


    public void setTurno(String turno) {
        this.turno = turno;
    }

    public abstract void setTurnoALternativo(String team) ;

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
     * Metodo che ritorna la pedina premuta  date le coordinate x e y
     *
     * @param x Double x
     * @param y Double y
     * @return pedina seleziononata
     */
    public abstract FieldObject pedinaSelezionata(double x, double y);

    /**
     * metodo per resettare il risulato alla vittoria
     */
    public abstract void checkVincitore();


    public FieldObject selezionaElemento(int i) {
        return objectsPiece[i];
    }


    // da spostare dove servono
    public FieldObject selezionaBall() {
        FieldObject ball = null;
        for (FieldObject o : objectsPiece) {
            if (o instanceof Ball) {
                ball = o;
            }
        }
        return ball;
    }

    public Computer piecePiuVicina(FieldObject ball) {
        FieldObject piece = null;
        double distanzaMinima = Double.MAX_VALUE;
        for (FieldObject o : objectsPiece) {
            if (o instanceof Piece) {
                if (o.getTeam().equalsIgnoreCase("T2")) {
                    double distanza = Math.sqrt(Math.pow(o.getPosition().getX() - ball.getPosition().getX(), 2)
                            + Math.pow(o.getPosition().getY() - ball.getPosition().getY(), 2));
                    if (distanza < distanzaMinima) {
                        distanzaMinima = distanza;
                        piece = o;
                    }
                }
            }


        }
        return new Computer(piece, distanzaMinima);
    }


    public FieldObject findPieceByPosition(FieldObject piece2) {
        for (FieldObject piece : objectsPiece) {
            if (piece.getPosition().getX() == piece2.getPosition().getX() && piece2.getPosition().getY() == piece.getPosition().getY()) {
                return piece; // Restituisci la pedina se la posizione corrisponde
            }
        }
        return null; // Restituisci null se la pedina non è stata trovata
    }




}

