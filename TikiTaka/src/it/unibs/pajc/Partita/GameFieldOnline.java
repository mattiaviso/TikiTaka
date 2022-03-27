package it.unibs.pajc.Partita;

public class GameFieldOnline extends GameField{

    public GameFieldOnline() {
        objectsPiece = new FieldObject[6];
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


    // online
    public void cambioTurno() {
        if (getTurno().equals("T1")) {
            setTurno("T2");
        } else if (getTurno().equals("T2")) {
            setTurno("T1");
        }
    }



    @Override
    public void checkVincitore() {
        super.checkVincitore();
        if (getScore1() == 3 || getScore2()== 3) {
            setScore1(0);
            setScore2(0);
        }
    }





    @Override
    public void ifGoal(String who) {
        super.ifGoal(who);

        if (who == "T2"){
            positionStart();
            setScore2(getScore2()+1);
            setTurno("T2");
        }else if (who == "T1"){
            positionStart();
            setScore2(getScore1()+1);
            setTurno("T1");
        }
    }


    @Override
    public void checkCollisions() {
        super.checkCollisions();
        insertionSort(objectsPiece);


        // Controllo collisioni con bordi del campo
        for (int i = 0; i < objectsPiece.length; i++) {


            if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() < MIN_X) // Bordo Sx
            {
                collision = true ;
                //bordiPorta(objectsPiece[i], 1 );
                if (objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112) {
                    if (objectsPiece[i].position.getX() + objectsPiece[i].getRadius() < MIN_X) {

                        ifGoal("T1");

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

                        ifGoal("T2");
                    }
                } else {

                    objectsPiece[i].position.setX(MAX_X - objectsPiece[i].getRadius());
                    objectsPiece[i].velocita = new Vector2d(-objectsPiece[i].velocita.getX(), objectsPiece[i].velocita.getY());
                }
            }

            checkCollisionUpDown(i);

            // crea una frizione tra i 2 oggetti
            objectsPiece[i].friction(0.02);



            collisionPiece(i);
        }

    }











}



