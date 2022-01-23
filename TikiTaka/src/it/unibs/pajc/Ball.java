package it.unibs.pajc;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Ball extends FieldObject{

    public Ball(double radius,double x,double y){
        super(radius,x,y,2);

        //Caricamento immagine **Metodo da estrarre
        try {
            imageObj = ImageIO.read(new File("Ball.png"));
        } catch(IOException e) {
            System.out.println("Image not found");
        }
    }

    @Override
    public boolean isBall() {
        return true;
    }




}
