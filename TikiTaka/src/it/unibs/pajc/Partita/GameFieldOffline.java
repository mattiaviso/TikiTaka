package it.unibs.pajc.Partita;

public class GameFieldOffline extends GameField {

    private boolean tirato;
    private int randomBall;

    public boolean isTirato() {
        return tirato;
    }

    public void setTirato(boolean tirato) {
        this.tirato = tirato;
    }

    public GameFieldOffline() {
        objectsPiece = new FieldObject[6];
        tirato = false;
        positionStart();
    }


    private void positionStart() {
         randomBall = (int) (Math.random() * 3);
        // TIRO DA DRITTO
        if (randomBall == 0) {

            objectsPiece[0] = (new Piece(40, -110, 0, "Pedina1.png", "T1"));
            objectsPiece[1] = (new Ball(18, 0, 0));
            objectsPiece[2] = (new Piece(40, 145, 150, "Pedina2.png", "T2"));
            objectsPiece[3] = (new Piece(40, 175, 0, "Pedina2.png", "T2"));
            objectsPiece[4] = (new Piece(40, 125, -150, "Pedina2.png", "T2"));
            objectsPiece[5] = (new Piece(40, 520, 0, "Pedina2.png", "T2"));
            //TIRO DAL BASSO
        } else if (randomBall == 1) {
            objectsPiece[0] = (new Piece(40, -120, -180, "Pedina1.png", "T1"));
            objectsPiece[1] = (new Ball(18, -30, -250));
            objectsPiece[2] = (new Piece(40, 145, -150, "Pedina2.png", "T2"));
            objectsPiece[3] = (new Piece(40, 160, -80, "Pedina2.png", "T2"));
            objectsPiece[4] = (new Piece(40, 140, -30, "Pedina2.png", "T2"));
            objectsPiece[5] = (new Piece(40, 520, 0, "Pedina2.png", "T2"));

            //TIRO DAL ALTO
        } else {
            objectsPiece[0] = (new Piece(40, -120, 180, "Pedina1.png", "T1"));
            objectsPiece[1] = (new Ball(18, -30, 250));
            objectsPiece[2] = (new Piece(40, 150, 150, "Pedina2.png", "T2"));
            objectsPiece[3] = (new Piece(40, 160, 80, "Pedina2.png", "T2"));
            objectsPiece[4] = (new Piece(40, 145, 30, "Pedina2.png", "T2"));
            objectsPiece[5] = (new Piece(40, 520, 0, "Pedina2.png", "T2"));
        }


    }


    //


    @Override
    public void ifGoal(String who) {
        super.ifGoal(who);
        positionStart();
    }

    // se si avverano delle collisioni
    @Override
    public void checkCollisions() {
        super.checkCollisions();

        insertionSort(objectsPiece);

        if(allStop() && nextStep() && tirato){
            positionStart();
            tirato = false;
        }

        // Controllo collisioni con bordi del campo
        for (int i = 0; i < objectsPiece.length; i++) {



// controllo a sinistra
            if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() < MIN_X) // Bordo Sx
            {
                collision = true;
                objectsPiece[i].position.setX(objectsPiece[i].getRadius() + MIN_X);
                objectsPiece[i].velocita = new Vector2d(-objectsPiece[i].velocita.getX(), +objectsPiece[i].velocita.getY());

            } else if (objectsPiece[i].position.getX() + objectsPiece[i].getRadius() > MAX_X) // Bordo DX
            {
                collision = true;
                if (objectsPiece[i].isBall() && objectsPiece[i].position.getY() + objectsPiece[i].radius > -98 && objectsPiece[i].position.getY() + objectsPiece[i].radius < 112) {

                    if (objectsPiece[i].position.getX() - objectsPiece[i].getRadius() > MAX_X || objectsPiece[i].speedIsZero()) {
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

    private boolean nextStep() {
        if (randomBall == 0 && objectsPiece[0].getPosition().getY()==0 && objectsPiece[0].getPosition().getX()== -110){
            return  false;
        }
        if (randomBall == 1 && objectsPiece[0].getPosition().getY()==-180 && objectsPiece[0].getPosition().getX()== -120){
            return  false;
        }if (randomBall == 2 && objectsPiece[0].getPosition().getY()==180 && objectsPiece[0].getPosition().getX()== -120){
            return  false;
        }
        return true;
    }


    @Override
    public void collisionPiece(int i) {
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
