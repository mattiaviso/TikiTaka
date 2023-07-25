package it.unibs.pajc.ClientServer.ResultComposite;

import it.unibs.pajc.Client.ModelClientForComunication;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello usato per la visualizzazione del risultato
 */
public class ResultClientServer extends JPanel {

    private int score1;
    private int score2;
    private String team1;
    private String team2;

    private ModelClientForComunication modelClientForComunication ;

    public ResultClientServer(ModelClientForComunication t) {
        this.modelClientForComunication = t;
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


        colorareScritta(testoteam1, testoteam2);

        gruppo.draw(g2);

    }

    public void colorareScritta(ComponenteGraficoSetColore testoteam1, ComponenteGraficoSetColore testoteam2) {
        if (modelClientForComunication.getTurno() != null) {
            if (modelClientForComunication.getTurno().equals("T1")) {
                    if (team1.contains("(you)")) {

                        testoteam1.setColor(Color.GREEN);
                    } else {
                        testoteam1.setColor(Color.BLUE);
                    }
                    testoteam2.setColor(Color.RED);
            } else if (modelClientForComunication.getTurno().equals("T2")) {
                    if (team2.contains("(you)")) {
                        testoteam2.setColor(Color.GREEN);
                    } else {
                        testoteam2.setColor(Color.RED);

                    }
                    testoteam1.setColor(Color.BLUE);
            } else {
                if (team2 != null) {
                    testoteam2.setColor(Color.RED);
                }
                testoteam1.setColor(Color.BLUE);

            }
        }
    }

}
