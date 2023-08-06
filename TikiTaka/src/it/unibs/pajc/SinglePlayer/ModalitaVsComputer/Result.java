package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Result extends JPanel  {

    private  int score1;
    private  int score2;
    private String team1;
    private String team2;
    private  BufferedImage Tabel;


    public Result(String n1 ){
        this.team1 = "computer";
        this.team2 = n1;
    }
/**
     * Imposta i punteggi dei giocatori e richiama repaint() per ridisegnare il pannello con i nuovi punteggi.
     * @param t1 Il punteggio del giocatore 1.
     * @param t2 Il punteggio del giocatore 2.
     */
    public void setTable(int t1, int t2){
        //settare variabili
        this.score1 = t1;
        this.score2 = t2;
        repaint();
    }



    public  void setScore1(int score1) {
        this.score1 = score1;
        repaint();

    }

    public void setScore2(int score2) {
        this.score2 = score2;
        repaint();
    }

    /**
     * Override del metodo paintComponent per disegnare il pannello dei risultati.
     * @param g L'oggetto Graphics utilizzato per disegnare il pannello.
     */

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);
        //g.fillRect(0,0,1300,120);

        try {
            this.Tabel = ImageIO.read(new File("TabelloneHD.png"));
        } catch (IOException var4) {
            System.out.println("immagine non trovata ");
        }

        g2.drawImage(this.Tabel, 0, 0, 1285, 120, null);

        g2.setFont(new Font("Ahoroni",Font.BOLD,30));
        g2.drawString(team1, 230,30);
        g2.drawString(team2, 857,30);
        g2.drawString(score1+"", 505,50);
        g2.drawString(score2+"", 755,50);

        //porterofutbol.gif



    }



}
