package it.unibs.pajc.Partita;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Utility {
    /**
     * Metodo che permette di disegnare a video una linea con una freccia
     *
     * @param g     Graphics2d g2
     * @param point Punto di partenza della freccia
     * @param angle Angolo formato dalla freccia
     * @param len   lunghezza della retta
     */
    public static void  drawArrow(Graphics2D g, Point2D point, double angle, int len) {
        AffineTransform at = AffineTransform.getTranslateInstance(point.getX(), point.getY());
        at.concatenate(AffineTransform.getRotateInstance(angle));
        AffineTransform restoreTransform = g.getTransform();
        g.transform(at);

        int ARR_SIZE = 10;

        g.setStroke(new BasicStroke(2.5f));
        g.drawLine(0, 0, len - 5, 0);
        g.setStroke(new BasicStroke(1f));
        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        g.setTransform(restoreTransform);
    }
}
