package it.unibs.pajc.SinglePlayer.ModalitaVsComputer;

import it.unibs.pajc.ClientServer.SoundClip;
import it.unibs.pajc.SinglePlayer.GeneralControllerGameField.Controller;

import javax.swing.*;
import java.awt.*;

public class MainSinglePlayer extends Canvas {

    public static Result panel;
    private static final JLabel textField = new JLabel("");
    /*private BufferedImage sfondo = null;*/
    Controller controllerGameField = new ControllerSinglePlayer();
    private JFrame frame;
    private JTextField txtGioco;


    /**
     * Create the application.
     */
    public MainSinglePlayer() {
        initialize();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {


        String team2 = JOptionPane.showInputDialog("Dammi il nome del team");

        panel = new Result(team2);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainSinglePlayer window = new MainSinglePlayer();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        SoundClip sound = new SoundClip("Song");
        sound.start();
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 50, 1300, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(controllerGameField.getViewGame(), BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        panel.setTable(0, 0);
        panel.setPreferredSize(new Dimension(1300, 120));


    }
    // metodo per il cambiamento


}




