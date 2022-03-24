package it.unibs.pajc.ClientServer;

import it.unibs.pajc.Client;
import it.unibs.pajc.Partita.Ball;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Piece;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;



public class ViewClient extends JPanel implements MouseListener, MouseMotionListener {

    //Immagine del campo
    public Image field;

    private int w;
    private int h;

    private FieldObject valido;
    // getRadius() selezionato
    private int radiusPower = 0;
    private int xnew, ynew;
    private int distance;
    private double angle;
    protected FieldObject[] objectsPiece;
    // aspettare un giocatore
    private boolean wait = false;
    // colorare bordo del giocatore che tocca
    private String viewBordoPedina;

    public void setViewBordoPedina(String viewBordoPedina) {
        this.viewBordoPedina = viewBordoPedina;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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

    public String getValidoTeam(){
        return valido.getTeam();
    }

    public double getValidoRadius(){
        return valido.getRadius();
    }

    public double getPosValidX(){
        return valido.getPosition().getX();
    }
    public double getPosValidY(){
        return valido.getPosition().getY();
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public BufferedImage imgT1,imgT2,ball;
    public ViewClient() {

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
     * creazione del gamefield e del contorno delle pedine quando andiamo a schiacciare
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
            g2.drawOval((int) (valido.getPosition().getX() - valido.getRadius()), (int) (valido.getPosition().getY() - valido.getRadius()), (int) valido.getRadius() * 2, (int) valido.getRadius()* 2);
        }
        if (radiusPower != 0) {
            g2.setColor(new Color(0, 100, 0, 100));
            g2.fillOval((int) (valido.getPosition().getX() - radiusPower), (int) (valido.getPosition().getY() - radiusPower ), (int) radiusPower * 2, (int) radiusPower * 2);

            g2.setColor(Color.black);
            int dy = (int) (ynew - valido.getPosition().getY());
            int dx = (int) (xnew - valido.getPosition().getX());
            int xOpposta = (int) valido.getPosition().getX() - dx;
            int yOpposta = (int) valido.getPosition().getY() - dy;

            distance = (int) (Math.min(Math.sqrt(Math.pow(valido.getPosition().getX() - xOpposta, 2) + Math.pow(valido.getPosition().getY() - yOpposta, 2)), 150));
            angle = Math.atan2(yOpposta - valido.getPosition().getY(), xOpposta - valido.getPosition().getX());
            drawArrow(g2, new Point2D.Double(valido.getPosition().getX(), valido.getPosition().getY()), angle, distance);
            //mi danno la direzione se faccio divisione
            //g2.drawLine((int)valido.getX(), (int)valido.getY() , xOpposta,yOpposta);
        }


        /*for (FieldObject f : objectsPiece) {
            if (f != null)
                g2.drawImage(f.getImageObj(), (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), null);
        }*/
        for (FieldObject f : objectsPiece) {
            if (f != null) {
                if(f.getTeam() == null) {
                    g2.drawImage(ball, (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), (int) f.getRadius() * 2, (int) f.getRadius() * 2, null);
                } else {
                    if(f.getTeam().equals("T1"))
                        g2.drawImage(imgT1, (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), (int) f.getRadius() * 2, (int) f.getRadius() * 2, null);
                    else
                        g2.drawImage(imgT2, (int) (f.getPosition().getX() - (f.getRadius())), (int) (f.getPosition().getY() - (f.getRadius())), (int) f.getRadius() * 2, (int) f.getRadius() * 2, null);
                }
            }
        }


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
     * @param g2
     */
    private void creatingfield(Graphics2D g2) {
        /*try {
            this.field = ImageIO.read(new File("campoHD.png"));
        } catch (IOException var4) {
            System.out.println("Image d'arriere plan non trouvee");
        }*/
        g2.drawImage(field, -655, -320, 1300, 645, null);
    }

    /**
     * Metodo che permette di disegnare a video una linea con una freccia
     * @param g Graphics2d g2
     * @param point Punto di partenza della freccia
     * @param angle Angolo formato dalla freccia
     * @param len lunghezza della retta
     */
    private static void drawArrow(Graphics2D g, Point2D point, double angle, int len) {
        AffineTransform at = AffineTransform.getTranslateInstance(point.getX(), point.getY());
        at.concatenate(AffineTransform.getRotateInstance(angle));
        AffineTransform restoreTransform = g.getTransform();
        g.transform(at);

        int ARR_SIZE = 10;

        // Draw horizontal arrow starting in (0, 0)
        g.setStroke(new BasicStroke(2.5f));
        g.drawLine(0, 0, len - 5, 0);
        g.setStroke(new BasicStroke(1f));
        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        g.setTransform(restoreTransform);
    }

    /**
     * dal messaggio ricevuto dal server va ad aggiornare le posizione delle palline
     * @param msg
     */

    public void aggiornaPos(String msg) {

        String[] parts = msg.split("\n");


        for (int i = 0; i < 11; i++) {
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

    /**
     * Metodo permette di vedere se l'utente ha selezionato una pedina oppure il nulla
     *
     * @param xMouse Coordinata x del mouse
     * @param yMouse Coordinata y del mouse
     * @return Ritorna l'oggetto premuto, altrimenti null se non si preme nulla
     */
    public FieldObject checkClickAble(int xMouse, int yMouse) {
        if (!wait)
            for (FieldObject f : objectsPiece) {
                if (f instanceof Piece )
                if (Math.pow((xMouse - f.getPosition().getX()), 2) + Math.pow((yMouse - f.getPosition().getY()), 2) < Math.pow((f.getRadius()), 2)) {
                    return f;
                }
            }
        return null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

