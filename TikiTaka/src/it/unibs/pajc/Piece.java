package it.unibs.pajc;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Piece extends FieldObject{


    public Piece(double radius, double x, double y,String imagePath) {
        super(radius, x, y);

        //Caricamento immagine **Metodo da estrarre
        try {
            imageObj = ImageIO.read(new File(imagePath));
        } catch(IOException e) {
            System.out.println("Image not found");
        }

    }

    @Override
    public boolean isBall() {
        return false;
    }



}
