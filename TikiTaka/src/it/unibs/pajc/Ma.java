package it.unibs.pajc;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import it.unibs.pajc.Partita.FieldObject;

import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.Canvas;
import java.awt.Panel;


public class Ma extends JFrame {

	private JPanel contentPane;
	private JTextField txtIp;
	private JTextField txtUser;
	private JButton btnEsterno;
	private JPanel panel;

	public Ma() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 287);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtIp = new JTextField();
		txtIp.setBounds(208, 186, 207, 44);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JButton btnLocal = new JButton("LocalHost");
		btnLocal.setFont(new Font("Arial", Font.BOLD, 14));
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtUser.getText().equals("")) {
					Client.avvioClient(1500, "localhost", txtUser.getText());
					setVisible(false);
				}
			}
		});
		btnLocal.setBounds(269, 21, 146, 50);
		contentPane.add(btnLocal);
		
		txtUser = new JTextField();
		txtUser.setText("Inserisci qui il tuo username");
		txtUser.setHorizontalAlignment(SwingConstants.RIGHT);
		txtUser.setBounds(6, 21, 224, 26);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		btnEsterno = new JButton("Server Esterno");
		btnEsterno.setFont(new Font("Arial", Font.BOLD, 14));

		btnEsterno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isValidIp(txtIp.getText()) && !txtUser.getText().equals("") ) {
					Client.avvioClient(1500, txtIp.getText(), txtUser.getText());
					setVisible(false);
				}
			}
		});
		btnEsterno.setBounds(270, 112, 146, 50);
		contentPane.add(btnEsterno);
	
	}

	 public static boolean isValidIp(final String ip) {
	        return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
	    }
}
