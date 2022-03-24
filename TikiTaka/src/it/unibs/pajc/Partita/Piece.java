package it.unibs.pajc.Partita;

import it.unibs.pajc.Partita.FieldObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Piece extends FieldObject {

    protected String team;
    private String imgPathString;

    public Piece(double radius, double x, double y, String imagePath, String team) {
        super(radius, x, y, 200);

        imgPathString = imagePath;

        //Caricamento immagine **Metodo da estrarre

        /*try {
            imageObj = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Image not found");
        }*/

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

    @Override
    public String toString() {
        return "Piece@" + super.toString() + "@" + imgPathString + "@" + team;
    }


}
