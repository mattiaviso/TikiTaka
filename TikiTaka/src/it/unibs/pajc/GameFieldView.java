package it.unibs.pajc;

import java.awt.*;
import javax.swing.JPanel;

public class GameFieldView extends JPanel {

	public GameFieldView() {
		
		
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int w = getWidth();
		int h = getHeight();
		
		g2.translate(0, h);
		g2.scale(1, -1);
		g2.translate(w/2, h/2);
		
		g2.setColor(Color.green);
		g2.fillRect(-w/3, -h/4, w/6, h/2);
		
		g2.setColor(Color.white);
		g2.drawLine(0, h/4, 0, -h/4);
		g2.drawLine(-w/4, 0, w/4, 0);
		
		
	
	}

}
