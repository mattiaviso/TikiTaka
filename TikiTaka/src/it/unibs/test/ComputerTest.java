package it.unibs.test;

import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Piece;
import it.unibs.pajc.Partita.Vector2d;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.Computer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {

    @Test
    void settoAngoloPerAlcuniPuntidellaBallScelgoIlMigliore() {

        FieldObject pedina = new Piece(40, -100, 0, "Pedina1.png", "T2");

        Computer computer = new Computer(pedina, 30);
        Ball ball = new Ball(18, 0, 0);

        computer.settoAngoloPerAlcuniPuntidellaBallScelgoIlMigliore(ball);
        double gradi = computer.getAngle()* 180 / 3.14;
        System.out.println(gradi);

        assertEquals(gradi , 0);

    }


}