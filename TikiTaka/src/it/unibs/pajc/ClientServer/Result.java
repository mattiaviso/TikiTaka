package it.unibs.pajc.ClientServer;

import it.unibs.pajc.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class Result extends JPanel {

    private int score1;
    private int score2;
    private String team1;
    private String team2;
    private BufferedImage Tabel;
    private Client client;

    public Result(Client t) {
        this.client = t;
    }

    public void setUsername1(String t1) {
        this.team1 = t1;
        repaint();
    }

    public void setScore(int s1, int s2) {
        score1 = s1;
        score2 = s2;
        repaint();
    }

    public void setUsernames(String t1, String t2) {
        this.team1 = t1;
        this.team2 = t2;
        repaint();
    }


    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTable(int t1, int t2) {
        //settare variabili
        this.score1 = t1;
        this.score2 = t2;

        repaint();
    }

    /**
     * creazione del panello per il risultato
     *
     * @param g
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
            System.out.println("Image d'arriere plan non trouvee");
        }

        g2.drawImage(this.Tabel, 0, 0, 1285, 120, null);

        g2.setFont(new Font("Ahoroni", Font.BOLD, 30));
        if (client.getTurno() != null) {
            if (client.getTurno().equals("T1")) {
                if(team1.contains("(you)")){
                    g2.setColor(Color.GREEN);
                }else{
                    g2.setColor(Color.BLUE);
                }
                g2.drawString(team1, 857, 30);
                g2.setColor(Color.RED);
                if (team2 != null)
                    g2.drawString(team2, 230, 30);
            } else if (client.getTurno().equals("T2")) {
                if(team2.contains("(you)")){
                    g2.setColor(Color.GREEN);
                }else{
                    g2.setColor(Color.RED);
                }
                if (team2 != null)
                    g2.drawString(team2, 230, 30);
                g2.setColor(Color.BLUE);
                g2.drawString(team1, 857, 30);
            } else {
                if (team2 != null){
                    g2.setColor(Color.RED);
                    g2.drawString(team2, 230, 30);
                }
                g.setColor(Color.BLUE);
                g2.drawString(team1, 857, 30);
            }
        }

        g2.setColor(Color.RED);
        g2.drawString(score1 + "", 505, 50);
        g2.setColor(Color.BLUE);
        g2.drawString(score2 + "", 755, 50);

    }

}
