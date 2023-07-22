package it.unibs.pajc.ClientServer.HomePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewHomePage extends JFrame {
    private JPanel contentPane;
    private JTextField txtIp;
    private JButton btnLocal;
    private JTextField txtUser;
    private JButton btnEsterno;

    public static final String MSG_NOME = "Inserisci il nome";
    public static final String ERRORE_NOME = "Nome non valido";
    public static final String ERRORE_IP = "IP non valido";

    public ViewHomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 287);
        contentPane = new JPanel();
        ImageIcon img = new ImageIcon("Ball.png");
        this.setIconImage(img.getImage());
        this.setTitle("TikiTaka");

        contentPane.setBackground(Color.GREEN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtIp = new JTextField();
        txtIp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtIp.setBackground(Color.WHITE);
                txtIp.setText("");
            }
        });
        txtIp.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txtIp.setText(" ip server (no localhost)");
        txtIp.setBounds(10, 76, 386, 36);
        contentPane.add(txtIp);
        txtIp.setColumns(10);

        btnLocal = new JButton("LocalHost");
        btnLocal.setFont(new Font("Arial", Font.BOLD, 14));
//        btnLocal.setBounds(10, 142, 174, 48);
//        contentPane.add(btnLocal);

        txtUser = new JTextField();
        txtUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUser.setText("");
                txtUser.setBackground(Color.WHITE);
            }
        });

        txtUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtUser.setText(MSG_NOME);
        txtUser.setHorizontalAlignment(SwingConstants.LEFT);
        txtUser.setBounds(10, 30, 386, 36);
        contentPane.add(txtUser);
        txtUser.setColumns(10);

        btnEsterno = new JButton("Server Esterno");
        btnEsterno.setFont(new Font("Arial", Font.BOLD, 14));

    }

    public JTextField getTxtIp() {
        return txtIp;
    }

    public JButton getBtnLocal() {
        return btnLocal;
    }

    public JTextField getTxtUser() {
        return txtUser;
    }

    public JButton getBtnEsterno() {
        return btnEsterno;
    }
}
