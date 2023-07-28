package it.unibs.pajc.Allenamento;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ResultAllenamento extends JPanel  {

    private  int scoreMancanti;

    private  BufferedImage Tabel;












    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);
        //g.fillRect(0,0,1300,120);

        String textToDraw = ""+scoreMancanti;

        // Ottieni le dimensioni del pannello
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Ottieni le dimensioni della scritta
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(textToDraw);
        int textHeight = fontMetrics.getHeight();

        // Calcola la posizione per centrare la scritta nel pannello
        int x = (panelWidth - textWidth) / 2;
        int y = (panelHeight - textHeight) / 2 + fontMetrics.getAscent();

        // Disegna la scritta
        g.drawString(textToDraw, x, y);



    }



}
