package it.unibs.pajc.SinglePlayer.ModalitaAllenamento;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ResultAllenamento extends JPanel  {


    private static int scoreMancanti;
    private static int livelli =1 ;

    private  BufferedImage Tabel;

    public static int getLivelli() {
        return livelli;
    }

    public static void setLivelli() {
        livelli++;
    }

    public int getScoreMancanti() {
        return scoreMancanti;
    }

    public  static void setScoreMancanti(int scoreMancanti) {
        ResultAllenamento.scoreMancanti = scoreMancanti;
    }

    public BufferedImage getTabel() {
        return Tabel;
    }

    public void setTabel(BufferedImage tabel) {
        Tabel = tabel;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);
        //g.fillRect(0,0,1300,120);



        g2.drawImage(this.Tabel, 0, 0, 1285, 120, null);

        g2.setFont(new Font("Ahoroni",Font.BOLD,30));
        g2.drawString( "livello " +" "+  livelli +"/4", 438,30);
        g2.drawString(" vita " + scoreMancanti +"", 857,30);

        repaint();
        //porterofutbol.gif


    }



}
