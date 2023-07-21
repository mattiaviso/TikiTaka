package it.unibs.pajc.ClientServer.ResultComposite;

import java.awt.*;

public class TestoGrafico implements ComponenteGraficoSetColore {

    private String testo;
    private Color colore;
    private int posizioneX;
    private int posizioneY;

    private Font stileTesto;


    public TestoGrafico(String testo, Color colore, int posizioneX, int posizioneY, Font stileTesto) {
        this.testo = testo;
        this.colore = colore;
        this.posizioneX = posizioneX;
        this.posizioneY = posizioneY;
        this.stileTesto = stileTesto;
    }


    @Override
    public void draw(Graphics2D g) {
        g.setFont(stileTesto);
        g.setColor(colore);
        if(testo != null) {
            g.drawString(testo, posizioneX, posizioneY);
        }

    }

    @Override
    public void setColor(Color colore) {
        this.colore = colore;

    }
}
