package it.unibs.pajc;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Piece extends FieldObject{

    protected String team;

    public Piece(double radius, double x, double y,String imagePath, String team ) {
        super(radius, x, y, 60);

        //Caricamento immagine **Metodo da estrarre
        try {
            imageObj = ImageIO.read(new File(imagePath));
        } catch(IOException e) {
            System.out.println("Image not found");
        }

        this.team = team;

    }


}
