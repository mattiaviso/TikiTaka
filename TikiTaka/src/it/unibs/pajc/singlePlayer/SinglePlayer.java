//package it.unibs.pajc.singlePlayer;
//
//
//import it.unibs.pajc.ClientServer.ResultComposite.Result;
//
//import java.awt.*;
//
//import javax.swing.*;
//
//public class SinglePlayer extends Canvas{
//    private static JLabel textField = new JLabel("");
//    private JFrame frame;
//    private JTextField txtGioco;
//    /*private BufferedImage sfondo = null;*/
//    GameFiledView battleFieldView = new GameFiledView();
//    public static Result panel;
//
//
///**
// * Suono al gol
// * Quando si vince? e immagine
// * Server ModelClientForComunication
// * Musica in gioco
// *
// */
//
//
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//
//        String team1 = JOptionPane.showInputDialog("Dammi il nome del team1");
//        String team2 = JOptionPane.showInputDialog("Dammi il nome del team2");
//        // ci servira' una classe apposita , secondo me dovremmo andare a utilizzare qualche pattern
//        panel = new Result(team1,team2);
//
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    SinglePlayer window = new SinglePlayer();
//                    window.frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * Create the application.
//     */
//    public SinglePlayer() {
//        initialize();
//    }
//
//    /**
//     * Initialize the contents of the frame.
//     */
//    private void initialize() {
//
//        frame = new JFrame();
//        frame.setResizable(false);
//        frame.setBounds(100, 50, 1300, 800);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//
//        frame.getContentPane().add(battleFieldView, BorderLayout.CENTER);
//        frame.getContentPane().add(panel,BorderLayout.NORTH);
//        panel.setTable(0,0);
//        panel.setPreferredSize(new Dimension(1300,120));
//
//    }
//    // metodo per il cambiamento
//
//
//
//}
