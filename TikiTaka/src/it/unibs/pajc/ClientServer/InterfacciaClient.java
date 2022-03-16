package it.unibs.pajc.ClientServer;


import it.unibs.pajc.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class InterfacciaClient extends JFrame {

	public static final String MSG_NOME = "Inserisci qui il tuo username";
	public static final String ERRORE_NOME = "INSERIRE IL NOME";
	public static final String ERRORE_IP = "IP SERVER NON CORRETTO";
	private JPanel contentPane;
	private JTextField txtIp;
	private JTextField txtUser;
	private JButton btnEsterno;
	JComboBox comboBox;

	public InterfacciaClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 287);
		contentPane = new JPanel();
		ImageIcon img = new ImageIcon("Ball.png");
		this.setIconImage(img.getImage());
		this.setTitle("TikiTaka");

		Object[] items =
				{
						new ImageIcon("Pedina1.png"),
						new ImageIcon("Pedina2.png"),
						new ImageIcon("Pedina2.png"),

				};
		contentPane.setLayout(null);
		comboBox = new JComboBox( items );
		comboBox.setBounds(180, 150, 100, 100);
		contentPane.add(comboBox);
		comboBox.setSelectedIndex(1);
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		comboBox.setMaximumRowCount(3);
		comboBox.setPreferredSize(new Dimension(100, 100));



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

		JButton btnLocal = new JButton("LocalHost");
		btnLocal.setFont(new Font("Arial", Font.BOLD, 14));
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(txtUser.getText().equals("") || txtUser.getText().equals(MSG_NOME) || txtUser.getText().equals(ERRORE_NOME))) {
					Client.avvioClient(1500, "localhost", txtUser.getText(),comboBox.getSelectedItem().toString());
					setVisible(false);
				} else {
					txtUser.setText(ERRORE_NOME);
					txtUser.setBackground(Color.RED);
				}
			}
		});
		btnLocal.setBounds(10, 142, 174, 48);
		contentPane.add(btnLocal);

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

		btnEsterno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isValidIp(txtIp.getText()) && !txtUser.getText().equals("") && !txtUser.getText().equals(MSG_NOME) && !txtUser.getText().equals(ERRORE_NOME)) {
					Client.avvioClient(1500, txtIp.getText(), txtUser.getText(),comboBox.getSelectedItem().toString());
					setVisible(false);
				} else {
					if(!isValidIp(txtIp.getText())){
						txtIp.setText(ERRORE_IP);
						txtIp.setBackground(Color.RED);
					}
					else{
						txtUser.setText(ERRORE_NOME);
						txtUser.setBackground(Color.RED);
					}

				}
			}
		});
		btnEsterno.setBounds(235, 143, 180, 47);
		contentPane.add(btnEsterno);
	
	}

	 public static boolean isValidIp(final String ip) {
	        return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
	    }
}

