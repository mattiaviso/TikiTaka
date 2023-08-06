package it.unibs.pajc.SinglePlayer;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.Partita.Utility;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.ControllerGeneral;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * La classe ViewGameField rappresenta la vista del campo di gioco in modalità giocatore singolo o contro il computer.
 * Estende JPanel e implementa MouseListener e MouseMotionListener per gestire gli eventi del mouse.
 */
public class ViewGameField extends JPanel implements MouseListener, MouseMotionListener {

    private int w;
    private int h;
    private FieldObject valido;
    private int newradius = 0;
    private int xnew, ynew;
    private int distance;
    private double angle;
    private BufferedImage ball, imgT1, imgT2;
    private BufferedImage background;

    private Map<String, BufferedImage> imageCache = new HashMap<>();


    GameField fieldModel;
    ControllerGeneral controllerGameField;

    public ViewGameField(ControllerGeneral controllerGameField) {
        this.controllerGameField = controllerGameField;
        fieldModel = controllerGameField.getModelGameField();


        this.setDoubleBuffered(true);

        //riceve il focus degli eventi
        this.setFocusable(true);
        this.requestFocusInWindow();


        try {
            imgT1 = ImageIO.read(new File("Pedina1HD.png"));
        } catch (IOException e) {
        }
        try {
            imgT2 = ImageIO.read(new File("Pedina2HD.png"));
        } catch (IOException e) {
        }
        try {
            ball = ImageIO.read(new File("BallHD.png"));
        } catch (IOException e) {
        }
        // creazione in background dell'immagine
        SwingWorker<Void, Void> imageLoader = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    background = ImageIO.read(new File("campoHD.png"));
                } catch (IOException e) {
                    System.out.println("non trovata ");
                }
                return null;
            }

            @Override
            protected void done() {
                // Aggiorna l'interfaccia quando il caricamento è completato
                repaint();
            }
        };
        imageLoader.execute();
    }


    public int getW() {
        return w;
    }


    public int getH() {
        return h;
    }


    public FieldObject getValido() {
        return valido;
    }

    public void setValido(FieldObject valido) {
        this.valido = valido;
    }


    public void setNewradius(int newradius) {
        this.newradius = newradius;
    }

    public int getXnew() {
        return xnew;
    }

    public void setXnew(int xnew) {
        this.xnew = xnew;
    }

    public int getYnew() {
        return ynew;
    }

    public void setYnew(int ynew) {
        this.ynew = ynew;
    }

    public int getDistance() {
        return distance;
    }


    public double getAngle() {
        return angle;
    }


    public BufferedImage getBall() {
        return ball;
    }

    public void setBall(BufferedImage ball) {
        this.ball = ball;
    }



    /**
     * Carica un'immagine dal percorso specificato e la memorizza nella cache per utilizzi futuri.
     *
     * @param filename Il percorso del file dell'immagine.
     * @return L'immagine BufferedImage caricata o null se si verifica un errore durante il caricamento.
     */
    public BufferedImage loadImage(String filename) {
        if (imageCache.containsKey(filename)) {
            return imageCache.get(filename);
        } else {
            try {
                BufferedImage image = ImageIO.read(new File(filename));
                imageCache.put(filename, image); // Aggiungiamo l'immagine alla cache
                return image;
            } catch (IOException e) {
                return null;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Copia il codice originale del metodo paintComponent qui
        Graphics2D g2 = (Graphics2D) g;

        w = getWidth();
        h = getHeight();

        g2.translate(0, h);
        g2.scale(1, -1);
        g2.translate(w / 2, h / 2);
        // andiamo a caricare in background l'immagine
        if (background != null) {
            g2.drawImage(background, -655, -320, 1300, 645, null);
        }

        for (FieldObject f : fieldModel.getObjectsPiece()) {
            if (f != null) {
                int x = (int) (f.getPosition().getX() - f.getRadius());
                int y = (int) (f.getPosition().getY() - f.getRadius());
                int diameter = (int) (f.getRadius() * 2);

                BufferedImage image;
                if (f.getTeam() == null) {
                    image = loadImage("BallHD.png");
                } else if (f.getTeam().equals("T1")) {
                    image = loadImage("Pedina1HD.png");
                } else {
                    image = loadImage("Pedina2HD.png");
                }

                if (image != null) {
                    g2.drawImage(image, x, y, diameter, diameter, null);
                }
            }
        }


        for (FieldObject f : fieldModel.getObjectsPiece()) {
            g2.drawImage(f.getImageObj(), (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), null);
        }
        if (newradius != 0) {
            g2.setColor(Color.black);
            posizioneFreccia(g2);
            g2.dispose();
        }


    }

    /**
     * Calcola la posizione e l'angolo per disegnare una freccia e quindi la disegna.
     *
     * @param g2 L'oggetto Graphics2D su cui disegnare la freccia.
     */
    public void posizioneFreccia(Graphics2D g2) {

        int dy = (int) (ynew - valido.getPosition().getY());
        int dx = (int) (xnew - valido.getPosition().getX());
        int xOpposta = (int) valido.getPosition().getX() - dx;
        int yOpposta = (int) valido.getPosition().getY() - dy;

        distance = (int) (Math.min(Math.sqrt(Math.pow(valido.getPosition().getX() - xOpposta, 2) + Math.pow(valido.getPosition().getY() - yOpposta, 2)), 150));
        angle = Math.atan2(yOpposta - valido.getPosition().getY(), xOpposta - valido.getPosition().getX());
        Utility.drawArrow(g2, new Point2D.Double(valido.getPosition().getX(), valido.getPosition().getY()), angle, distance);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // il rilascio lo step next


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {


    }


    @Override
    public void mouseMoved(MouseEvent e) {
    }


}



