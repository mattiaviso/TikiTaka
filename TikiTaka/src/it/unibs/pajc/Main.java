package it.unibs.pajc;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.ServerSocket;
import javax.swing.SwingConstants;

public class Main {

    private JFrame frame;
    private JTextField ip;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
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
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton single = new JButton("PARTITA OFFLINE");
        single.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mouse cliccato single player
                TikiTakaGame game = new TikiTakaGame();
            }
        });
        single.setBounds(213, 40, 194, 55);
        frame.getContentPane().add(single);

        JButton server = new JButton("CREA SERVER");
        server.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //partita server


            }
        });
        server.setBounds(213, 107, 194, 55);
        frame.getContentPane().add(server);

        JButton online = new JButton("PARTITA ONLINE");
        online.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //partita offline
            }
        });
        online.setBounds(213, 172, 194, 55);
        frame.getContentPane().add(online);

        ip = new JTextField();
        ip.setHorizontalAlignment(SwingConstants.RIGHT);
        ip.setText("Inserisci ip del server qui");
        ip.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ip.setText("");
            }
        });
        ip.setToolTipText("");
        ip.setBounds(213, 228, 194, 37);
        frame.getContentPane().add(ip);
        ip.setColumns(10);
    }
}
