package it.unibs.pajc.Partita;

public class GameFieldOffline extends GameField{

    public GameFieldOffline() {
        objectsPiece = new FieldObject[6];
        positionStart();
    }


    private void positionStart (){
        int randomBall = (int) (Math.random()*3);
        // TIRO DA DRITTO
        if (randomBall == 0){

            objectsPiece[0] = (new Piece(40, -110, 0, "Pedina1.png", "T1"));
            objectsPiece[1] = (new Ball(18, 0, 0));
            objectsPiece[2] = (new Piece(40, 145, 150, "Pedina2.png", "T2"));
            objectsPiece[3] = (new Piece(40, 175, 0, "Pedina2.png", "T2"));
            objectsPiece[4] = (new Piece(40, 125, -150, "Pedina2.png", "T2"));
            objectsPiece[5] = (new Piece(40, 520, 0, "Pedina2.png", "T2"));
            //TIRO DAL BASSO
        }else if (randomBall ==1 ){
            objectsPiece[0] = (new Piece(40, -120, -180, "Pedina1.png", "T1"));
            objectsPiece[1] = (new Ball(18, -30, -250));
            objectsPiece[2] = (new Piece(40, 145, -150, "Pedina2.png", "T2"));
            objectsPiece[3] = (new Piece(40, 160, -80, "Pedina2.png", "T2"));
            objectsPiece[4] = (new Piece(40, 140, -30, "Pedina2.png", "T2"));
            objectsPiece[5] = (new Piece(40, 520, 0, "Pedina2.png", "T2"));

            //TIRO DAL ALTO
        }else{
            objectsPiece[0] = (new Piece(40, -120, 180, "Pedina1.png", "T1"));
            objectsPiece[1] = (new Ball(18, -30, 250));
            objectsPiece[2] = (new Piece(40, 150, 150, "Pedina2.png", "T2"));
            objectsPiece[3] = (new Piece(40, 160, 80, "Pedina2.png", "T2"));
            objectsPiece[4] = (new Piece(40, 145,30 , "Pedina2.png", "T2"));
            objectsPiece[5] = (new Piece(40, 520, 0, "Pedina2.png", "T2"));
        }


    }

}
