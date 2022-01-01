package it.unibs.pajc;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class GameFieldView extends JPanel {

	 private  Image field;
	 public static   int w;
	 public  static int h;

	private FieldObject[] ball;

	public GameFieldView() {
		ball = new FieldObject[11];

		ball[0] = new Ball(36,0,0);
		ball[1] = new Piece(80,520,0,"Pedina1.png");
		ball[2] = new Piece(80,170,70,"Pedina1.png");
		ball[3] = new Piece(80,170,-70,"Pedina1.png");
		ball[4] = new Piece(80,350,180,"Pedina1.png");
		ball[5] = new Piece(80,350,-180,"Pedina1.png");
		ball[6] = new Piece(80,-520,0,"Pedina2.png");
		ball[7] = new Piece(80,-170,70,"Pedina2.png");
		ball[8] = new Piece(80,-170,-70,"Pedina2.png");
		ball[9] = new Piece(80,-350,180,"Pedina2.png");
		ball[10] = new Piece(80,-350,-180,"Pedina2.png");
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

		for(int i=0;i < ball.length; i++){
			g2.drawImage(ball[i].getImageObj(),(int) (ball[i].getX()-(ball[i].getRadius()/2)), (int) (ball[i].getY()-(ball[i].getRadius()/2)),null);
		}
		//g2.drawImage(ball[0].getImageObj(),(int) (ball[0].getX()-(ball[0].getRadius()/2)), (int) (ball[0].getY()-(ball[0].getRadius()/2)),null);
		//g2.drawImage(ball[1].getImageObj(),(int) (ball[1].getX()-(ball[1].getRadius()/2)), (int) (ball[1].getY()-(ball[1].getRadius()/2)),null);
		/*g2.setColor(Color.white);
		g2.drawLine(-w/2,0,w/2,0);
		g2.drawLine(0,-h/2,0,h/2);*/


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
