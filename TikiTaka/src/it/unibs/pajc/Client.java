package it.unibs.pajc;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


//The Client that can be run as a console
public class Client {
	
	// notification
	private String notif = " *** ";

	public static JFrame frame;
	public static ViewClient finestra;

	protected FieldObject[] objectsPiece;
	public static Result  panel;
	

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
		String serverAddress = "172.28.224.10";
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
					
					int nUte = Integer.parseInt(parts[11]);
					
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

