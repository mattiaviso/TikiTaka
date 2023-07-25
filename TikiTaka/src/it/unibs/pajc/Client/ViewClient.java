package it.unibs.pajc.Client;

import it.unibs.pajc.ClientServer.ResultComposite.Result;
import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Piece;
import it.unibs.pajc.Partita.Utility;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe usata per la gestione della parte grafica del gioco
 */
public class ViewClient extends JPanel implements MouseListener, MouseMotionListener {

    public Image field;

    private int w;
    private int h;
    private FieldObject valido;
    // radisu selezionato
    private int radiusPower = 0;
    private int xnew, ynew;
    private int distance;

    private double angle;
   private FieldObject[] objectsPiece;
    // aspettare un giocatore
    private boolean wait = false;
    // colorare bordo del giocatore che tocca
    private String viewBordoPedina;


    private JFrame frame ;





    public void setViewBordoPedina(String viewBordoPedina) {
        this.viewBordoPedina = viewBordoPedina;
    }

    public double getAngle() {
        return angle;
    }

    public int getDistance() {
        return distance;
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

    public int getRadiusPower() {
        return radiusPower;
    }

    public void setRadiusPower(int radiusPower) {
        this.radiusPower = radiusPower;
    }

    public FieldObject getValido() {
        return valido;
    }

    public void setValido(FieldObject valido) {
        this.valido = valido;
    }

    public String getValidoTeam() {
        return valido.getTeam();
    }

    public double getValidoRadius() {
        return valido.getRadius();
    }

    public double getPosValidX() {
        return valido.getPosition().getX();
    }

    public double getPosValidY() {
        return valido.getPosition().getY();
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public BufferedImage imgT1, imgT2, ball;

    public ViewClient() {

        frame = new JFrame();

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

        try {
            field = ImageIO.read(new File("campoHD.png"));
        } catch (IOException var4) {
            System.out.println("Image d'arriere plan non trouvee");
        }
        objectsPiece = new FieldObject[11];

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        repaint();
    }


    /**
     * Creazione del campo come sfondo e del contorno delle pedine quando andiamo a schiacciare per eseguire il tiro
     *
     * @param g
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        w = getWidth();
        h = getHeight();

        g2.translate(0, h);
        g2.scale(1, -1);
        g2.translate(w / 2, h / 2);

        // CREAZIONE CAMPO
        creatingfield(g2);

        if (valido != null) {
            g2.setColor(Color.RED);
            g2.drawOval((int) (valido.getPosition().getX() - valido.getRadius()), (int) (valido.getPosition().getY() - valido.getRadius()), (int) valido.getRadius() * 2, (int) valido.getRadius() * 2);
        }
        if (radiusPower != 0) {
            g2.setColor(new Color(0, 100, 0, 100));
            g2.fillOval((int) (valido.getPosition().getX() - radiusPower), (int) (valido.getPosition().getY() - radiusPower), (int) radiusPower * 2, (int) radiusPower * 2);

            g2.setColor(Color.black);
            int dy = (int) (ynew - valido.getPosition().getY());
            int dx = (int) (xnew - valido.getPosition().getX());
            int xOpposta = (int) valido.getPosition().getX() - dx;
            int yOpposta = (int) valido.getPosition().getY() - dy;

            distance = (int) (Math.min(Math.sqrt(Math.pow(valido.getPosition().getX() - xOpposta, 2) + Math.pow(valido.getPosition().getY() - yOpposta, 2)), 150));
            angle = Math.atan2(yOpposta - valido.getPosition().getY(), xOpposta - valido.getPosition().getX());
            Utility.drawArrow(g2, new Point2D.Double(valido.getPosition().getX(), valido.getPosition().getY()), angle, distance);
        }

        for (FieldObject f : objectsPiece) {
            if (f != null) {
                if (f.getTeam() == null) {
                    g2.drawImage(ball, (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), (int) f.getRadius() * 2, (int) f.getRadius() * 2, null);
                } else {
                    if (f.getTeam().equals("T1"))
                        g2.drawImage(imgT1, (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), (int) f.getRadius() * 2, (int) f.getRadius() * 2, null);
                    else
                        g2.drawImage(imgT2, (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), (int) f.getRadius() * 2, (int) f.getRadius() * 2, null);
                }
            }
        }
        // stampa a video attesa secondo giocatore
        if (wait) {
            g2.setColor(Color.red);
            g2.setFont(new Font("Arial", 0, 100));
            g2.scale(1, -1);
            g2.drawString("Aspetta altro player", -400, -10);
            g2.setColor(Color.BLUE);
        }
    }

    /**
     * creazione del campo da calcio
     *
     * @param g2
     */
    private void creatingfield(Graphics2D g2) {
        g2.drawImage(field, -655, -320, 1300, 645, null);
    }



    /**
     * Dal messaggio ricevuto dal server va ad aggiornare le posizione delle palline
     *
     * @param msg
     */
    public void setPos(String msg) {

        String[] parts = msg.split("\n");

        for (int i = 0; i < 11; i++) {
            // ci permette di andare a identificare l'elemento che viene passato , e spostarlo direttamente
            String[] subpartStrings = parts[i].split("@");
            if (subpartStrings[0].equals("Piece")) {
                objectsPiece[i] = (new Piece(Double.parseDouble(subpartStrings[1]), Double.parseDouble(subpartStrings[2]), Double.parseDouble(subpartStrings[3]), subpartStrings[4], subpartStrings[5]));
            } else if (subpartStrings[0].equals("Ball")) {
                objectsPiece[i] = (new Ball(Double.parseDouble(subpartStrings[1]), Double.parseDouble(subpartStrings[2]), Double.parseDouble(subpartStrings[3])));
            }
        }

        String[] riga11 = parts[11].split("@");
        int nUte = Integer.parseInt(riga11[0]);

        if (nUte == 1) {
            wait = true;
        } else {
            wait = false;
        }
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * ci permette di andare a selezionare una pedina
     *
     * @param xMouse Coordinata x del mouse
     * @param yMouse Coordinata y del mouse
     * @return Ritorna l'oggetto premuto, altrimenti null se non si preme nulla
     */
    public FieldObject checkClickAble(int xMouse, int yMouse) {
        if (!wait)
            for (FieldObject f : objectsPiece) {
                if (f instanceof Piece)
                    if (Math.pow((xMouse - f.getPosition().getX()), 2) + Math.pow((yMouse - f.getPosition().getY()), 2) < Math.pow((f.getRadius()), 2)) {
                        return f;
                    }
            }
        return null;
    }


    // creazione pannello per l'inizzializzazione
    private Result panel;

    public Result getPanel() {
        return panel;
    }

    public void setPanel(Result panel) {
        this.panel = panel;
    }

    // agigunto dopo
    public void createComponents(ModelClientForComunication modelClientForComunication) {
        panel = new Result(modelClientForComunication);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBounds(100, 50, 1300, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setTable(0, 0);
        panel.setPreferredSize(new Dimension(1300, 120));
        setVisible(true);
        frame.add(this, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);
    }

    public void frameDispose(){
        frame.dispose();
    }


}

