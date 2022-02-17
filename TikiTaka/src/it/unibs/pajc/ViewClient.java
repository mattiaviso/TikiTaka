package it.unibs.pajc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ViewClient extends JPanel  {


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
	
	
	public ViewClient() {
				
		repaint();
		objectsPiece = new FieldObject[11];
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
		
		for(FieldObject f : objectsPiece) {
			g2.drawImage(f.getImageObj(),(int) (f.position.getX()-(f.getRadius())), (int) (f.position.getY()-(f.getRadius())),null);
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



	public void aggiornaPos(String msg) {

		String[] parts = msg.split("\n");
		for(int i=0;i<11;i++) {
			String []subpartStrings = parts[i].split("@");
			if(subpartStrings[0].equals("Piece")) {
				objectsPiece[i] = (new Piece(Double.parseDouble(subpartStrings[1]), Double.parseDouble(subpartStrings[2]), Double.parseDouble(subpartStrings[3]), subpartStrings[4], subpartStrings[5]));
			}
			else if(subpartStrings[0].equals("Ball")) {
				objectsPiece[i] = (new Ball(Double.parseDouble(subpartStrings[1]), Double.parseDouble(subpartStrings[2]), Double.parseDouble(subpartStrings[3])));
			}
		}
		
		if(Integer.parseInt(parts[11]) == 1 ) {
			wait = true;
		}
		else {
			wait=false;
		}
		
		repaint();
	}
	
	


}

