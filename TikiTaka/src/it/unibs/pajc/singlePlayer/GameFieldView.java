package it.unibs.pajc.singlePlayer;

import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class GameFieldView extends JPanel implements MouseListener, MouseMotionListener {
    private Image field;
    public static   int w;
    public  static int h;
    public FieldObject valido;
    public  int newradius = 0;
    public int xnew,ynew;
    public int distance;
    public double angle;

    GameFieldSingle fieldModel = new GameFieldSingle();

    public GameFieldView() {
        Timer timer = new Timer(1, (e) -> {
            fieldModel.updateGame();

            repaint();
        });
        timer.start();

        //riceve il focus degli eventi
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        w = getWidth();
        h = getHeight();

        g2.translate(0, h);
        g2.scale(1, -1);
        g2.translate(w / 2, h / 2);

        //g2.setColor(Color.RED);
        //g2.fillRect(-getWidth()/2,-getHeight()/2,1300,240);

        // CREAZIONE CAMPO
        creatingfield(g2);

        for (FieldObject f : fieldModel.objectsPiece) {
            g2.drawImage(f.getImageObj(), (int) (f.getPosition().getX() - f.getRadius()), (int) (f.getPosition().getY() - f.getRadius()), null);
        }

        if (valido != null) {
            g2.setColor(Color.RED);
            g2.drawOval((int) (valido.getPosition().getX() - valido.getRadius()), (int) (valido.getPosition().getY() - valido.getRadius()), (int) valido.getRadius() * 2, (int) valido.getRadius() * 2);
        }

        if (newradius != 0) {
            g2.setColor(new Color(0, 100, 0, 100));
            g2.fillOval((int) (valido.getPosition().getX() - newradius), (int) (valido.getPosition().getY() - newradius), (int) newradius * 2, (int) newradius * 2);

            g2.setColor(Color.black);
            int dy = (int) (ynew - valido.getPosition().getY());
            int dx = (int) (xnew - valido.getPosition().getX());
            int xOpposta = (int) valido.getPosition().getX() - dx;
            int yOpposta = (int) valido.getPosition().getY() - dy;

            distance = (int) (Math.min(Math.sqrt(Math.pow(valido.getPosition().getX() - xOpposta, 2) + Math.pow(valido.getPosition().getY() - yOpposta, 2)), 150));
            angle = Math.atan2(yOpposta - valido.getPosition().getY(), xOpposta - valido.getPosition().getX());
            Utility.drawArrow(g2, new Point2D.Double(valido.getPosition().getX(), valido.getPosition().getY()), angle, distance);
        }
// mi danno la direzione se faccio divisione
// g2.drawLine((int) valido.getPosition().getX(), (int) valido.getPosition().getY(), xOpposta, yOpposta);





        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(1f));
        // LINEA SOPRA
        g2.drawLine(-566,312,566,312);
        //LINEA SOTTO
        g2.drawLine(-566,-302,566,-302);
        // LINEA SX
        g2.drawLine(-566,312,-566,-302);
        // LINEA DX
        g2.drawLine(566,312,566,-302);

        // AMPIEZZA porta sx
        g2.drawLine(-566,108,-566,-98);
        // LINEA PROFONDITA

        // PROFONDITA PORTA SX
        g2.drawLine(-630,108,-630,-98);
        // AMPIEZZA PORTA DESTRA
        g2.drawLine(566,108,566,-98);
        //PROFONDITA DELLA PORTA DX
        g2.drawLine(630,108,630,-98);

        //riga
        g2.drawLine(-630, -98, -566, -98);


        //g2.drawImage(ball[0].getImageObj(),(int) (ball[0].getX()-(ball[0].getRadius()/2)), (int) (ball[0].getY()-(ball[0].getRadius()/2)),null);
        //g2.drawImage(ball[1].getImageObj(),(int) (ball[1].getX()-(ball[1].getRadius()/2)), (int) (ball[1].getY()-(ball[1].getRadius()/2)),null);
		/*g2.setColor(Color.white);
		g2.drawLine(-w/2,0,w/2,0);
		g2.drawLine(0,-h/2,0,h/2);*/

    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        //prendiamo coordinate x e y di dove è stato premuto il mouse
        int x = e.getX()-w/2;
        int y = -(e.getY()-h/2);

        valido = fieldModel.checkClickAble(x,y);

        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // il rilascio lo step next

        if(valido!=null){
            valido.start(distance, angle);

            fieldModel.cambioTurno();
        }
        valido = null;
        newradius = 0;
        repaint();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        xnew = e.getX()-w/2;
        ynew = -(e.getY()-h/2);





        if(valido!=null)

            newradius =Math.min((int) (Math.sqrt(((valido.getPosition().getX()-xnew)*(valido.getPosition().getX()-xnew))+((valido.getPosition().getY()-ynew)*(valido.getPosition().getY()-ynew)))),150);

        repaint();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
    }







    private void creatingfield(Graphics2D g2) {
        try {
            this.field  = ImageIO.read(new File("campogiusto.jpg"));
        } catch (IOException var4) {
            System.out.println("Image d'arriere plan non trouvee");
        }
        g2.drawImage(this.field,-655, -320, 1300,645,null);
    }



}
