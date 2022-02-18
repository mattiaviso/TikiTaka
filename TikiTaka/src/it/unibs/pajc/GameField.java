package it.unibs.pajc;

public class GameField {

    public static final int MAX_X = 566;
    public static final int MIN_X = -566;
    public static final int MIN_Y = -302;
    public static final int MAX_Y = 312;
    public static final float EPSILON = 0.09f;
    public static final float RESTITUTION = 0.85f; //potenza che la pedina perde quando va contro un ostacolo
    protected int score1=0;
    protected int score2=0;


    protected FieldObject[] objectsPiece;
    protected String turno;

    public GameField() {
        turno = "T1";
        objectsPiece = new FieldObject[11];
        positionStart();
    }

    
    
    /**
     * Posizione iniziale delle pedine
     */
    private void positionStart() {
        objectsPiece[0] = (new Piece(40, 520, 0, "Pedina1.png", "T1"));
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

    
    public String messaggioPos() {
    	String string = "";
    	for(FieldObject f: objectsPiece) {
    		string+=f.toString()+"\n";
    	}
    	return string;
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

    public void cambioTurno() {
        if (turno.equals("T1")) {
            turno = "T2";
        } else if (turno.equals("T2")) {
            turno = "T1";
        }
    }

    public void setTurno(String team){
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

    public void checkCollisions() {
        insertionSort(objectsPiece);



        // Controllo collisioni con bordi del campo
        for (int i = 0; i < objectsPiece.length; i++) {



            if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() < MIN_X) // Bordo Sx
            {
                    //bordiPorta(objectsPiece[i], 1 );
                if(objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius  > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112)
                {
                    if(objectsPiece[i].position.getX() + objectsPiece[i].getRadius() < MIN_X ) {
                        positionStart();
                        score2++;
                        //TikiTakaGame.panel.setTable(score1,score2);
                        setTurno("T2");
                    }
                }
                else{
                    objectsPiece[i].position.setX(objectsPiece[i].getRadius() + MIN_X);
                    objectsPiece[i].velocita = new Vector2d(-objectsPiece[i].velocita.getX(), +objectsPiece[i].velocita.getY());
                }
            } else if (objectsPiece[i].position.getX() + objectsPiece[i].getRadius() > MAX_X) // Bordo DX
            {
                if(objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius  > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112)
                {

                    if(objectsPiece[i].position.getX() - objectsPiece[i].getRadius() > MAX_X ) {
                        positionStart();
                        score1++;
                        //TikiTakaGame.panel.setTable(score1,score2);
                        setTurno("T1");
                    }
                }
                else {

                    objectsPiece[i].position.setX(MAX_X - objectsPiece[i].getRadius());
                    objectsPiece[i].velocita = new Vector2d(-objectsPiece[i].velocita.getX(), objectsPiece[i].velocita.getY());
                }
            }

            if (objectsPiece[i].position.getY() - objectsPiece[i].getRadius() < MIN_Y)                //Bordo Down
            {
                objectsPiece[i].position.setY(objectsPiece[i].getRadius() + MIN_Y);
                objectsPiece[i].velocita = new Vector2d(objectsPiece[i].velocita.getX(), -objectsPiece[i].velocita.getY());
            } else if (objectsPiece[i].position.getY() + objectsPiece[i].getRadius() > MAX_Y) //Bordo UP
            {
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



// PROVATO A IMPLEMENTARE I BORDI DELLA PORTA
    public void bordiPorta ( FieldObject o ,int  direzione){

        if ( o.position.getY() + o.getRadius() <106 &&  o.position.getY() - o.getRadius() >-96 ){
            // bordo interno
            if(o.position.getX()-o.radius < -630 ){
                o.position.setX(o.getRadius() + -630);
                o.velocita = new Vector2d(-o.velocita.getX(), +o.velocita.getY());
            // sopra
            } if(o.position.getY()+ o.radius>106 && o.position.getX()<-566 && o.position.getX()>= -630){
                o.position.setY(106 - o.getRadius());
                o.velocita = new Vector2d(o.velocita.getX(), -o.velocita.getY());
            // sotto
            } if (o.position.getY()- o.radius<-96&& o.position.getX()<-566 && o.position.getX()>= -630) {
                o.position.setY(o.getRadius() -96);
                o.velocita = new Vector2d(o.velocita.getX(), -o.velocita.getY());

            }
        }else{
            if (direzione ==1 ){ // sinistra
                o.position.setX(o.getRadius() + MIN_X);
                o.velocita = new Vector2d(-o.velocita.getX(), o.velocita.getY());
            }else{
                o.position.setX(MAX_X - o.getRadius());
                o.velocita = new Vector2d(-o.velocita.getX(), o.velocita.getY());

                }
        }
    }


    public int gool(FieldObject o , double sup , double min ){


        if ( o.position.getY() + o.getRadius() <108 &&  o.position.getY() - o.getRadius() >-98 && o.isBall){
            return 1;

        }
        else if (o.position.getY() + o.getRadius() <108 &&  o.position.getY() - o.getRadius() >min && o.isBall){
            return 2;
        }else
            return 0;
    }


     public FieldObject pedinaSelezionata(double x, double y){
        double EPS = 1E-3;
         for (int i = 0; i< objectsPiece.length ; i++) {
             if ( Math.abs(x-objectsPiece[i].position.getX()) < EPS &&  Math.abs(y-objectsPiece[i].position.getY())< EPS ){
                 return objectsPiece[i];
             }
         }
        return null;
     }









}

