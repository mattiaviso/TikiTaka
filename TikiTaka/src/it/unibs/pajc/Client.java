package it.unibs.pajc;

import java.net.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

import javax.swing.*;


//The Client that can be run as a console
public class Client  {
	
	// notification
	private String notif = " *** ";

	public static JFrame frame;
	public static ViewClient finestra;

	protected FieldObject[] objectsPiece;
	public static Result  panel;
	// variabili messe dopo il funzionamento
	// da rendere private
	public  static String team;
	public static String turno;



	

	private static boolean close=false;
	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;					// socket object
	
	private String server, username;	// server and username
	private int port;					//port

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 *  Constructor to set below things
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 */
	
	Client(String server, int port, String username) {
		this.server = server;
		this.port = port;
		this.username = username;
	}
	
	/*
	 * To start the chat
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		// exception handler if it failed
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}
		
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the console
	 */
	private void display(String msg) {

		System.out.println(msg);
		
	}
	
	/*
	 * To send a message to the server
	 */
	public void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect
	 */
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {}
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {}
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {}
			
	}
	
	
	public static void main(String[] args) {
		// default values if not entered
		int portNumber = 1500;
		String serverAddress = "localhost";
		String userName = "Anonymous";
		Scanner scan = new Scanner(System.in);
		
		
				
		userName = JOptionPane.showInputDialog(finestra,"Inserisci username");
		
		panel = new Result("","");
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 50, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setTable(0,0);
		panel.setPreferredSize(new Dimension(1300,120));
		
		finestra = new ViewClient();
		finestra.setVisible(true);
		
		frame.add(finestra, BorderLayout.CENTER);

		frame.add(panel, BorderLayout.NORTH);
		
		
		// create the Client object
		Client client = new Client(serverAddress, portNumber, userName);
		// try to connect to the server and return if not connected
		if(!client.start())
			return;

		finestra.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				finestra.xnew = e.getX()-finestra.w/2;
				finestra.ynew = -(e.getY()-finestra.h/2);

				if(finestra.valido!=null)
					finestra.newradius =Math.min((int) (Math.sqrt(((finestra.valido.position.getX()-finestra.xnew)*
							(finestra.valido.position.getX()-finestra.xnew))+((finestra.valido.position.getY()-finestra.ynew)*
							(finestra.valido.position.getY()-finestra.ynew)))),150);

				finestra.repaint();
			}
		});

		// MOUSE LISTENER
		finestra.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseReleased(MouseEvent e) {

				if(finestra.valido!=null){
					//finestra.valido.start(finestra.distance, finestra.angle);
					System.out.println(finestra.valido.position.getX()+finestra.valido.position.getY()+finestra.distance+ finestra.angle);
					//Dare formo al messaggio x@y@distance@angle
					elaboramessaggio(new String( finestra.valido.position.getX()+"@"+finestra.valido.position.getY()+"@"+finestra.distance+"@"+ finestra.angle),client);
					//fieldModel.cambioTurno();
				}
				finestra.valido = null;
				finestra.newradius = 0;
				finestra.repaint();



		    }

			@Override
			public void mousePressed(MouseEvent e) {

				//prendiamo coordinate x e y di dove è stato premuto il mouse
				if(team.equals(turno)) {
					int x = e.getX() - finestra.w / 2;
					int y = -(e.getY() - finestra.h / 2);

					finestra.valido = finestra.checkClickAble(x, y);
					finestra.repaint();
				}
			}

		});
		
		
		if(close == true) {
			// close resource
			scan.close();
			// client completed its job. disconnect client.
			client.disconnect();	
		}
		
		
	}

	private static void elaboramessaggio(String msg,Client client) {
		
		if(msg.equalsIgnoreCase("LOGOUT")) {
			client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
			close=true;;
		}
		// regular text message
		else {
			client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
		}
	
	}

	
	
	
	/*
	 * a class that waits for the message from the server
	 */
	class ListenFromServer extends Thread {

		public void run() {
			String msg = null;


			while(true) {
				try {
					// read the message form the input datastream
					msg = (String) sInput.readObject();
					// print the message
					System.out.println(msg);
					finestra.aggiornaPos(msg);
					String[] parts = msg.split("\n");

					String[] riga11 = parts[11].split("@");
					int nUte = Integer.parseInt(riga11[0]);
					team = riga11[1];
					turno = riga11[2];
					
					if(nUte>=1)
					if(parts[12].equals(username))
						parts[12]+="(you)";
					
					if(nUte>=2)
					if(parts[13].equals(username))
						parts[13]+="(you)";
					
					if(nUte==1) {
						panel.setUsername1(parts[12]);
					}
					else {
						panel.setUsernames(parts[12], parts[13]);
					}
					
					
				}
				catch(IOException e) {
					display(notif + "Server has closed the connection: " + e + notif);
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
			
		}
	}
}

