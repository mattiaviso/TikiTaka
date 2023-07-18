package it.unibs.pajc.Partita;

public class Piece extends FieldObject {

    protected String team;
    private String imgPathString;

    public Piece(double radius, double x, double y, String imagePath, String team) {
        super(radius, x, y, 200);
        imgPathString = imagePath;
        this.team = team;
    }

    public void setImage(String imgPathString) {
        this.imgPathString = imgPathString;

    }

    public String getTeam() {
        return team;
    }

    public boolean isBall() {
        return false;
    }
// e' da togliere
    @Override
    public String toString() {
        return "Piece@" + super.toString() + "@" + imgPathString + "@" + team;
    }


}
