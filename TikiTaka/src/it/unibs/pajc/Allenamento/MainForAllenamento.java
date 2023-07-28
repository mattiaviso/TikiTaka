package it.unibs.pajc.Allenamento;

import it.unibs.pajc.ClientServer.SoundClip;
import it.unibs.pajc.SinglePlayer.ControllerGameField;
import it.unibs.pajc.SinglePlayer.Result;

import javax.swing.*;
import java.awt.*;

public class MainForAllenamento extends Canvas {

	private static JLabel textField = new JLabel("");
	private JFrame frame;
	private JTextField txtGioco;
	/*private BufferedImage sfondo = null;*/
	ControllerFieldTraining controllerGameField;
	public static Result panel;



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
					MainForAllenamento window = new MainForAllenamento();
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
	public MainForAllenamento() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		controllerGameField= new ControllerFieldTraining();
		SoundClip sound = new SoundClip("Song");
		sound.start();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 50, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(controllerGameField.getViewGame(), BorderLayout.CENTER);
		frame.getContentPane().add(panel,BorderLayout.NORTH);
		panel.setTable(0,0);
		panel.setPreferredSize(new Dimension(1300,120));


	}
	// metodo per il cambiamento




}




