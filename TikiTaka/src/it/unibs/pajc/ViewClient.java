package it.unibs.pajc;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.*;


public class ViewClient extends JPanel implements MouseListener, MouseMotionListener {


	private  Image field;
 	public static   int w;
	public  static int h;
 	public  FieldObject valido;
 	public  int newradius = 0;
 	public int xnew,ynew;
 	public int distance;
 	public double angle;
 	protected FieldObject[] objectsPiece;
 	private boolean wait = false;
	 private String team;

	
	
	public ViewClient() {

		objectsPiece = new FieldObject[11];

		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		repaint();
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

		if(valido!=null){
			g2.setColor(Color.RED);
			g2.drawOval((int)(valido.position.getX()-valido.radius),(int)(valido.position.getY()-valido.radius),(int)valido.radius*2,(int)valido.radius*2);
		}
		if (newradius != 0 ){
			g2.setColor(new Color(0, 100, 0, 100));
			g2.fillOval((int)(valido.position.getX()-newradius),(int)(valido.position.getY()-newradius),(int)newradius*2,(int)newradius*2);

			g2.setColor(Color.black);
			int dy = (int)(ynew - valido.position.getY());
			int dx = (int) (xnew- valido.position.getX());
			int xOpposta = (int) valido.position.getX() - dx;
			int yOpposta = (int) valido.position.getY() - dy;

			distance = (int)(Math.min(Math.sqrt(Math.pow(valido.position.getX()-xOpposta,2)+Math.pow(valido.position.getY()-yOpposta,2)),150));
			angle = Math.atan2(yOpposta-valido.position.getY(),xOpposta-valido.position.getX());
			drawArrow(g2, new Point2D.Double(valido.position.getX(),valido.position.getY()),angle,distance);
			//mi danno la direzione se faccio divisione
			//g2.drawLine((int)valido.getX(), (int)valido.getY() , xOpposta,yOpposta);
		}


		for(FieldObject f : objectsPiece) {
			if(f != null)
			g2.drawImage(f.getImageObj(),(int) (f.position.getX()-(f.getRadius())), (int) (f.position.getY()-(f.getRadius())),null);
		}

		if(wait){
			g2.setColor(Color.red);
			g2.setFont(new Font("Arial",0,100));
			g2.scale(1,-1);
			g2.drawString("Aspetta altro player",-400,-10);
			g2.setColor(Color.BLUE);
		}
		

	}
	
	private void creatingfield(Graphics2D g2) {
		try {
			this.field  = ImageIO.read(new File("campogiusto.jpg"));
		} catch (IOException var4) {
			System.out.println("Image d'arriere plan non trouvee");
		}
		g2.drawImage(this.field,-655, -320, 1300,645,null);
	}

	 /*
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


	public FieldObject checkClickAble(int xMouse, int yMouse) {
		if(!wait)
		for (FieldObject f : objectsPiece) {
			if (f instanceof Piece);
				if (Math.pow((xMouse - f.position.getX()), 2) + Math.pow((yMouse - f.position.getY()), 2) < Math.pow((f.radius), 2)) {
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

