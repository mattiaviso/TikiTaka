package it.unibs.pajc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFieldView extends JPanel {

	public GameFieldView() {
		
		Timer timer = new Timer(10, (e)->{
			
			repaint();
		});
		timer.start();
		
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		double s = Math.min(getWidth(), getHeight()) / 1000.;
		double ss = getHeight()/1000.;
		
		g2.scale(s, -s); // passo in un mondo di coordiante con l'asse y che punta verso l'alto 
		g2.translate(500, -500);//traslo verso il centro sposto la x verso dx e abbasso la y
		
		g2.setColor(Color.green);
		g2.fillRect(-500, -500, 1000, 1000);
		
		g2.setColor(Color.white);
		g2.fillOval(0, 0, 20, 20);
		
	}

}
