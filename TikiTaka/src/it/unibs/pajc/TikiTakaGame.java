package it.unibs.pajc;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TikiTakaGame extends Canvas {

	private static JLabel textField = new JLabel("");
	private JFrame frame;
	private JTextField txtGioco;
	/*private BufferedImage sfondo = null;*/
	GameFieldView battleFieldView = new GameFieldView();
	 private static JPanel  panel = new JPanel();



	 
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


		frame.getContentPane().add(battleFieldView, BorderLayout.CENTER);
		panel.setBackground(Color.RED);
		panel.setPreferredSize(new Dimension(1300,120));
		frame.getContentPane().add(panel,BorderLayout.NORTH);
		rep(0,0, "RedMax", "Cigno" );

	}
	// metodo per il cambiamento

	public static void rep(int t1,int t2,String n1,String n2){
		//textField.setText(n1 +t1 +  n2 + t2);
		//panel.add(textField);
		panel.add(new JLabel(new ImageIcon("Tabellone.png")));
		panel.isOptimizedDrawingEnabled()
		panel.revalidate();
	}


}




