package it.unibs.pajc.Partita;

public class Piece extends FieldObject {

    protected String team;
    private String imgPathString;

    public Piece(double radius, double x, double y, String imagePath, String team) {
        super(radius, x, y, 200);
        this.team = team;
    }


    public String getTeam() {
        return team;
    }


    @Override
    public String toString() {
        return "Piece@" + super.toString() + "@" + imgPathString + "@" + team;
    }


}
