package it.unibs.pajc.SinglePlayer.ModalitaAllenamento;

import it.unibs.pajc.ClientServer.SoundClip;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.Controller;

import javax.swing.*;
import java.awt.*;

public class MainTraining extends Canvas {

	private static JLabel textField = new JLabel("");
	private JFrame frame;
	private JTextField txtGioco;
	/*private BufferedImage sfondo = null;*/
	Controller controllerGameField;
	public static ResultTraining panel;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		panel = new ResultTraining();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainTraining window = new MainTraining();
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
	public MainTraining() {
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
		panel.setPreferredSize(new Dimension(1300,120));


	}
	// metodo per il cambiamento




}




