package it.unibs.pajc.Partita;

public class Ball extends FieldObject {

    public Ball(double radius, double x, double y) {
        super(radius, x, y, 100);
    }


    @Override
    public String toString() {
        return "Ball@" + super.toString() + "@Ball.png" + "@null";
    }
}
