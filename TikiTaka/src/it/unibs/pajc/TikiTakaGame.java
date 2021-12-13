package it.unibs.pajc;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class TikiTakaGame {

	private JFrame frame;
	private JTextField txtGioco;

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
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameFieldView battleFieldView = new GameFieldView();
		frame.getContentPane().add(battleFieldView, BorderLayout.CENTER);
		
		txtGioco = new JTextField();
		txtGioco.setText("GIOCO");
		frame.getContentPane().add(txtGioco, BorderLayout.NORTH);
		txtGioco.setColumns(10);
	}

}
