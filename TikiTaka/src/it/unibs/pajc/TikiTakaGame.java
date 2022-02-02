package it.unibs.pajc;

import java.awt.*;

import javax.swing.*;

public class TikiTakaGame extends Canvas {

	private static JLabel textField = new JLabel("");
	private JFrame frame;
	private JTextField txtGioco;
	/*private BufferedImage sfondo = null;*/
	GameFieldView battleFieldView = new GameFieldView();
	public static Result  panel;


/**
 * Suono al gol
 * Quando si vince? e immagine
 * Server Client
 * Musica in gioco
 *
 */


	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		String team1 = JOptionPane.showInputDialog("Dammi il nome del team1");
		String team2 = JOptionPane.showInputDialog("Dammi il nome del team2");

		panel = new Result(team1,team2);

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
		frame.getContentPane().add(panel,BorderLayout.NORTH);
		panel.setTable(0,0);
		panel.setPreferredSize(new Dimension(1300,120));

	}
	// metodo per il cambiamento




}




