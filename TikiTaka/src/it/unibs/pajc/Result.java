package it.unibs.pajc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Result extends JPanel  {

    private int score1;
    private int score2;
    private String team1;
    private String team2;
    private  BufferedImage Tabel;


    public Result(String n1, String n2){
        this.team1 = n1;
        this.team2 = n2;
    }
    
    public void setUsername1(String t1) {
    	this.team1 = t1;
    	repaint();
    }
    public void setScore(int s1,int s2){
        score1 = s1;
        score2 = s2;
        repaint();
    }

    public void setUsername2(String t2) {
    	this.team2 = t2;
    	repaint();
    }
    public void setUsernames(String t1,String t2) {
    	this.team1 = t1;
        this.team2 = t2;
    }

    public void setTable(int t1, int t2){
        //settare variabili
        this.score1 = t1;
        this.score2 = t2;

        repaint();
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);
        //g.fillRect(0,0,1300,120);

        try {
            this.Tabel = ImageIO.read(new File("Tabellone.png"));
        } catch (IOException var4) {
            System.out.println("Image d'arriere plan non trouvee");
        }

        g2.drawImage(this.Tabel, 0, 0, 1285, 120, null);

        g2.setFont(new Font("Ahoroni",Font.BOLD,30));
        if(Client.turno.equals("T1")){
            g2.setColor(Color.RED);
            g2.drawString(team1, 230,30);
            g2.setColor(Color.BLUE);
        }
        else if(Client.turno.equals("T2")){
            g2.setColor(Color.RED);
            g2.drawString(team2, 857,30);
            g2.setColor(Color.BLUE);
        }


        g2.drawString(score1+"", 505,50);
        g2.drawString(score2+"", 755,50);

        //porterofutbol.gif



    }



}
