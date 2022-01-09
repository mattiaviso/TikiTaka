package it.unibs.pajc;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.AffineTransform;


public class GameFieldView extends JPanel implements MouseListener , MouseMotionListener {


	private  Image field;
 	public static   int w;
	public  static int h;
 	public  FieldObject valido;
 	public  int newradius = 0;
 	public int xnew,ynew;
	 public int distance;
	 public double angle;

	GameField fieldModel = new GameField();

	public GameFieldView() {
		Timer timer = new Timer(10, (e) -> {
			fieldModel.stepNext();

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


		// CREAZIONE CAMPO
		creatingfield(g2);

		for(FieldObject f : fieldModel.objectsPiece){
			g2.drawImage(f.getImageObj(),(int) (f.getX()-(f.getRadius())), (int) (f.getY()-(f.getRadius())),null);
		}
		if(valido!=null){
			g2.setColor(Color.RED);
			g2.drawOval((int)(valido.getX()-valido.radius),(int)(valido.getY()-valido.radius),(int)valido.radius*2,(int)valido.radius*2);
		}
		if (newradius != 0 ){
			g2.setColor(new Color(0, 100, 0, 100));
			g2.fillOval((int)(valido.getX()-newradius),(int)(valido.getY()-newradius),(int)newradius*2,(int)newradius*2);

			g2.setColor(Color.black);
			int dy = (int)(ynew - valido.getY());
			int dx = (int) (xnew- valido.getX());
			int xOpposta = (int) valido.getX() - dx;
			int yOpposta = (int) valido.getY() - dy;

			distance = (int)(Math.min(Math.sqrt(Math.pow(valido.getX()-xOpposta,2)+Math.pow(valido.getY()-yOpposta,2)),150));
			angle = Math.atan2(yOpposta-valido.getY(),xOpposta-valido.getX());
			drawArrow(g2, new Point2D.Double(valido.getX(),valido.getY()),angle,distance);
			//mi danno la direzione se faccio divisione
			//g2.drawLine((int)valido.getX(), (int)valido.getY() , xOpposta,yOpposta);

		}

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
		System.out.println(x+ "  " + y);

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
			newradius =Math.min((int) Math.sqrt((valido.getX()-xnew)*(valido.getX()-xnew)+(valido.getY()-ynew)*(valido.getY()-ynew)),150);

		repaint();
	}


	@Override
	public void mouseMoved(MouseEvent e) {
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
		g.drawLine(0, 0, len-5, 0);
		g.setStroke(new BasicStroke(1f));
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len },
				new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
		g.setTransform(restoreTransform);
	}

	private void creatingfield(Graphics2D g2) {
		try {
			this.field  = ImageIO.read(new File("campogiusto.jpg"));
		} catch (IOException var4) {
			System.out.println("Image d'arriere plan non trouvee");
		}
		g2.drawImage(this.field,-656, -320, 1300,645,null);
	}



}


