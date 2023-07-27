package it.unibs.pajc.SinglePlayer;

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


    public Result(String n1, String n2){
        this.team1 = n1;
        this.team2 = n2;
    }

    public void setTable(int t1, int t2){
        //settare variabili
        this.score1 = t1;
        this.score2 = t2;
        repaint();
    }

    public int getScore1() {
        return score1;
    }

    public  void setScore1(int score1) {
        score1 = score1;

    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
        repaint();
    }

    public void setTeam1(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

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
