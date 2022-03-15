package it.unibs.pajc.ClientServer;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import it.unibs.pajc.Client;
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
import java.awt.Color;


public class InterfacciaClient extends JFrame {

	private JPanel contentPane;
	private JTextField txtIp;
	private JTextField txtUser;
	private JButton btnEsterno;
	private JPanel panel;
	

	public InterfacciaClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 287);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtIp = new JTextField();
		txtIp.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtIp.setText(" ip server (no localhost)");
		txtIp.setBounds(10, 76, 342, 36);
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
		btnLocal.setBounds(10, 142, 125, 47);
		contentPane.add(btnLocal);
		
		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUser.setText("Inserisci qui il tuo username");
		txtUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtUser.setBounds(10, 30, 386, 36);
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
		btnEsterno.setBounds(213, 143, 139, 44);
		contentPane.add(btnEsterno);
	
	}

	 public static boolean isValidIp(final String ip) {
	        return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
	    }
}
