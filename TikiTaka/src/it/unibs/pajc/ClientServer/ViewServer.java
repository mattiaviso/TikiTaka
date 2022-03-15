package it.unibs.pajc.ClientServer;

import it.unibs.pajc.Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ViewServer extends JFrame {

    private JPanel contentPane;
    private JTextArea testo;
    public JTextArea people;
    private JScrollPane scrollPane;

    /**
     * Create the frame.
     */
    public ViewServer(String ip,int pNumber) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 501, 363);
        contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(19, 49, 267, 242);
        contentPane.add(scrollPane);

        testo = new JTextArea();
        scrollPane.setViewportView(testo);
        testo.setEditable(false);
        testo.setBackground(Color.RED);
        testo.setColumns(10);

        JLabel title = new JLabel("Console Server");
        title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(127, 6, 224, 33);
        contentPane.add(title);

        people = new JTextArea("Partecipanti:\n");
        people.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        people.setEditable(false);
        people.setColumns(10);
        people.setBackground(Color.WHITE);
        people.setBounds(322, 49, 151, 242);
        contentPane.add(people);
        
        JTextPane info = new JTextPane();
        info.setEditable(false);
        info.setText("Ip server: " + ip + " Porta: "+pNumber);
        info.setBounds(103, 302, 254, 16);
        contentPane.add(info);
    }




    public void repaintPeople(ArrayList<Server.ClientThread> al) {
        String string = "Partecipanti:\n";
        for (Server.ClientThread c : al) {
            string += c.username + "\n";
        }
        people.setText(string);
    }
}
