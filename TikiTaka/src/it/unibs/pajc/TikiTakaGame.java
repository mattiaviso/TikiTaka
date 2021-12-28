package it.unibs.pajc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class TikiTakaGame extends Canvas {

	private JFrame frame;
	private JTextField txtGioco;
	/*private BufferedImage sfondo = null;*/


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TikiTakaGame window = new TikiTakaGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TikiTakaGame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 50, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameFieldView battleFieldView = new GameFieldView();
		frame.getContentPane().add(battleFieldView, BorderLayout.CENTER);
		/*disegna();*/
		
	
	}



	}




