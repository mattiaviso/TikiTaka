package it.unibs.test;

import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Piece;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.GameFieldSinglePlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameFieldSinglePlayerTest {

    // metodo che verifica che la pedina presa sia quella che sta sulla sinistra della palla
    @Test
    void piecePiuVicina() {

        FieldObject[] fieldObjects = new FieldObject[2];
        fieldObjects[0] = new Piece(40, 100, 0, "Pedina1.png", "T2");
        fieldObjects[1] = new Piece(40, -170, 70, "Pedina1.png", "T2");

        String turno = "T1";

        GameFieldSinglePlayer gameFieldSinglePlayer = new GameFieldSinglePlayer(fieldObjects, turno);

        Ball ball = new Ball(18, 0, 0);
        double distanza = Math.sqrt(Math.pow((-170) - 0, 2) + Math.pow(70 - 0, 2));

        assertEquals(gameFieldSinglePlayer.piecePiuVicina(ball).getDistance(), distanza);
    }


    @Test
    void pedinaVicina() {
        FieldObject[] fieldObjects = new FieldObject[2];
        fieldObjects[0] = new Piece(40, 500, 0, "Pedina1.png", "T2");
        fieldObjects[1] = new Piece(40, -170, 70, "Pedina1.png", "T2");

        String turno = "T1";

        GameFieldSinglePlayer gameFieldSinglePlayer = new GameFieldSinglePlayer(fieldObjects, turno);

        Ball ball = new Ball(18, 0, 0);
        double distanza = Double.MAX_VALUE;
        double distanza0 = Math.sqrt(Math.pow(fieldObjects[0].getPosition().getX() - ball.getPosition().getX(), 2)
                + Math.pow(fieldObjects[0].getPosition().getY() - ball.getPosition().getY(), 2));
        double distanza1 = Math.sqrt(Math.pow(fieldObjects[1].getPosition().getX() - ball.getPosition().getX(), 2)
                + Math.pow(fieldObjects[1].getPosition().getY() - ball.getPosition().getY(), 2));


        if (fieldObjects[1].getPosition().getX() < ball.getPosition().getX() && fieldObjects[0].getPosition().getX() > ball.getPosition().getX()) {
            distanza = distanza1;
        } else if (fieldObjects[0].getPosition().getX() < ball.getPosition().getX() && fieldObjects[1].getPosition().getX() > ball.getPosition().getX()) {
            distanza = distanza0;
        } else {
            distanza = Math.min(distanza1, distanza0);
        }

        assertEquals(gameFieldSinglePlayer.piecePiuVicina(ball).getDistance(), distanza);

    }


}








 /* double distanza=Double.MAX_VALUE;
        double distanza0 = Math.sqrt(Math.pow(fieldObjects[0].getPosition().getX() - ball.getPosition().getX(), 2)
                + Math.pow(fieldObjects[0].getPosition().getY() - ball.getPosition().getY(), 2));
        double distanza1 = Math.sqrt(Math.pow(fieldObjects[1].getPosition().getX() - ball.getPosition().getX(), 2)
                + Math.pow(fieldObjects[1].getPosition().getY() - ball.getPosition().getY(), 2));



       if (fieldObjects[1].getPosition().getX() < ball.getPosition().getX() && fieldObjects[0].getPosition().getX() > ball.getPosition().getX()){
          distanza = distanza1;
      }else if ( fieldObjects[0].getPosition().getX() < ball.getPosition().getX() && fieldObjects[1].getPosition().getX() >ball.getPosition().getX()){
          distanza= distanza0;
      }else{
          distanza = Math.min(distanza1,distanza0);
      }*/