package it.unibs.pajc.Partita;

/**
 * Model
 */
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

    /**
     * Metodo costruttore che inizializza gli 11 oggetti del gioco
     * e le relative componenti grafiche
     */
    public GameField() {
        String pathT1 = "Pedina1.png";
        String pathT2 = "Pedina2.png";
        turno = "T1";
        objectsPiece = new FieldObject[11];
        positionStart();
    }

    /**
     * Metodo che setta le immagini in base al team di appartenenza delle pedine
     *
     * @param s1 Filepath immagine pedina1
     * @param s2 Filepath immagine pedina1
     */
    public void setImage(String s1, String s2) {
        for (FieldObject f : objectsPiece) {
            if (f instanceof Piece && f.getTeam().equals("T1")) {
                ((Piece) f).setImage(s1);
            }
            if (f instanceof Piece && f.getTeam().equals("T2")) {
                ((Piece) f).setImage(s2);
            }
        }
    }


    /**
     * Metodo che setta le posizioni iniziali delle pedine dopo un gol oppure al calcio d'inizio
     */
    private void positionStart() {
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

    public boolean getCollision() {
        return collision;
    }

    /**
     * Metodo che effettua il cambio del turno dopo il tiro
     */
    public void cambioTurno() {
        if (turno.equals("T1")) {
            turno = "T2";
        } else if (turno.equals("T2")) {
            turno = "T1";
        }
    }

    public void setTurno(String team) {
        turno = team;
    }

    /**
     * Metodo che controlla se tutte le palline sono ferme
     *
     * @return True se tutte le palline sono ferme
     */
    public boolean allStop() {
        for (FieldObject o : objectsPiece) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }

    /**
     * Metodo che mette in ordine le pedine grazie all'interfaccia Comparable
     *
     * @param a
     */
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
     * Metodo che se invocato manda avanti di un esecuzione il gioco, si occupa dello spostamento e dei controlli vari
     * Come se fosse lo StepNext
     */
    public void updateGame() {
        for (int i = 0; i < objectsPiece.length; i++) {
            objectsPiece[i].velocita.setY(objectsPiece[i].velocita.getY());
            objectsPiece[i].position.setX(objectsPiece[i].position.getX() + (objectsPiece[i].velocita.getX() * (1)));
            objectsPiece[i].position.setY(objectsPiece[i].position.getY() + (objectsPiece[i].velocita.getY() * (1)));

            if (Math.abs(objectsPiece[i].velocita.getX()) < EPSILON)
                objectsPiece[i].velocita.setX(0);
            if (Math.abs(objectsPiece[i].velocita.getY()) < EPSILON)
                objectsPiece[i].velocita.setY(0);
        }

        checkCollisions();
    }

    /**
     * Metodo generale che controlla se ci sno collisioni e se si verificano le risolve
     *
     */
    public void checkCollisions() {
        insertionSort(objectsPiece);

        // Controllo collisioni con bordi del campo
        for (int i = 0; i < objectsPiece.length; i++) {
            if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() < MIN_X) // Bordo Sx
            {
                collision = true ;
                //bordiPorta(objectsPiece[i], 1 );
                if (objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112) {
                    collision = false;
                    if (objectsPiece[i].position.getX() + objectsPiece[i].getRadius() < MIN_X) {
                        positionStart();
                        score2++;
                        setTurno("T2");
                    }
                } else {
                    objectsPiece[i].position.setX(objectsPiece[i].getRadius() + MIN_X);
                    objectsPiece[i].velocita = new Vector2d(-objectsPiece[i].velocita.getX(), +objectsPiece[i].velocita.getY());
                }
            } else if (objectsPiece[i].position.getX() + objectsPiece[i].getRadius() > MAX_X) // Bordo DX
            {
                collision = true;
                if (objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112) {

                    collision = false;
                    if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() > MAX_X) {
                        positionStart();
                        score1++;
                        setTurno("T1");
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
            } else if (objectsPiece[i].position.getY() + objectsPiece[i].getRadius() > MAX_Y)       //Bordo UP
            {
                collision = true;
                objectsPiece[i].position.setY(MAX_Y - objectsPiece[i].getRadius());
                objectsPiece[i].velocita = new Vector2d(objectsPiece[i].velocita.getX(), -objectsPiece[i].velocita.getY());
            }

            // crea una attrito tra i 2 oggetti
            objectsPiece[i].friction(0.02);

            // Controllo collisione pedine contro pedina
            for (int j = i + 1; j < objectsPiece.length; j++) {
                if ((objectsPiece[i].position.getX() + objectsPiece[i].getRadius()) <= (objectsPiece[j].position.getX() - objectsPiece[j].getRadius()))
                    break;

                if ((objectsPiece[i].position.getY() + objectsPiece[i].getRadius()) <= (objectsPiece[j].position.getY() - objectsPiece[j].getRadius()) || (objectsPiece[j].position.getY() + objectsPiece[j].getRadius()) <= (objectsPiece[i].position.getY() - objectsPiece[i].getRadius()))
                    continue;

                objectsPiece[i].resolveCollision(objectsPiece[j]);
                objectsPiece[i].friction(0.035);
                objectsPiece[j].friction(0.015);
            }
        }
    }

    /**
     * Metodo che ritorna la pedina premuta  date le coordinate x e y
     * @param x Double x
     * @param y Double y
     * @return pedina seleziononata
     */
    public FieldObject pedinaSelezionata(double x, double y) {
        double EPS = 1E-3;
        for (int i = 0; i < objectsPiece.length; i++) {
            if (Math.abs(x - objectsPiece[i].position.getX()) < EPS && Math.abs(y - objectsPiece[i].position.getY()) < EPS) {
                return objectsPiece[i];
            }
        }
        return null;
    }

    /**
     * metodo per resettare il risulato alla vittoria
     */
    public void checkVincitore() {
        if (score1 == 3 || score2 == 3) {
            score1 = 0;
            score2 = 0;
        }
    }

}

