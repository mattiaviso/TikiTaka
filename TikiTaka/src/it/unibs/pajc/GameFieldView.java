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


	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		 w = getWidth();
		 h = getHeight();

		g2.translate(0, h);
		g2.scale(1, -1);
		g2.translate(w / 2, h / 2);

		g2.setColor(Color.green);
		g2.fillRect(-w/3, -h/4, w/6, h/2);

		g2.setColor(Color.white);
		// CREAZIONE CAMPO
		creatingfield(g2);
		


		//g2.drawLine(0, h / 4, 0, -h / 4);
		//g2.drawLine(-w / 4, 0, w / 4, 0);



	}




	private void creatingfield(Graphics2D g2) {
		try {
			this.field  = ImageIO.read(new File("campogiusto.jpg"));
		} catch (IOException var4) {
			System.out.println("Image d'arriere plan non trouvee");
		}
		g2.drawImage(this.field,-650, -380, 1300,645,null);
	}


}
