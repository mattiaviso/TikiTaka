package it.unibs.pajc.ClientServer.ResultComposite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FormaGrafica  implements ComponenteGrafico{

    private BufferedImage Tabel;

    @Override
    public void draw(Graphics2D g) {
        try {
            this.Tabel = ImageIO.read(new File("TabelloneHD.png"));
        } catch (IOException var4) {
            System.out.println("l'immagine non e' stata trovata ");
        }

        g.drawImage(this.Tabel, 0, 0, 1285, 120, null);
    }
}
