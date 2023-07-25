package it.unibs.pajc.singlePlayer;

import it.unibs.pajc.Client.ModelClientForComunication;
import it.unibs.pajc.ClientServer.ResultComposite.ComponenteGraficoSetColore;
import it.unibs.pajc.ClientServer.ResultComposite.FormaGrafica;
import it.unibs.pajc.ClientServer.ResultComposite.GruppoComponent;
import it.unibs.pajc.ClientServer.ResultComposite.TestoGrafico;

import javax.swing.*;
import java.awt.*;

public class ResultSingle extends JPanel {

    private int score1;
    private int score2;
    private String team1;
    private String team2;


    public ResultSingle(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);
        //g.fillRect(0,0,1300,120);


        ComponenteGraficoSetColore testoteam1 = new TestoGrafico(team1, Color.GREEN, 857, 30, new Font("Ahoroni", Font.BOLD, 30));
        ComponenteGraficoSetColore testoteam2 = new TestoGrafico(team2, Color.GREEN, 230, 30, new Font("Ahoroni", Font.BOLD, 30));
        GruppoComponent gruppo = new GruppoComponent();

        gruppo.addComponente(new FormaGrafica());
        gruppo.addComponente(testoteam1);
        gruppo.addComponente(testoteam2);
        gruppo.addComponente(new TestoGrafico(score1 + "", Color.RED, 505, 50, new Font("Ahoroni", Font.BOLD, 30)));
        gruppo.addComponente(new TestoGrafico(score2 + "", Color.BLUE, 755, 50, new Font("Ahoroni", Font.BOLD, 30)));


        gruppo.draw(g2);

    }


}
