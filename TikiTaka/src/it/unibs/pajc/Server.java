package it.unibs.pajc;


import it.unibs.pajc.ClientServer.ChatMessage;
import it.unibs.pajc.ClientServer.ViewServer;
import it.unibs.pajc.Partita.FieldObject;
import it.unibs.pajc.Partita.GameField;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * creazione del server
 */
public class Server {
    // a unique ID for each connection
    private static int uniqueId;
    // an ArrayList to keep the list of the Client
    private ArrayList<ClientThread> al;
    // to display time
    private SimpleDateFormat sdf;
    // the port number to listen for connection
    private int port;
    // to check if server is running
    private boolean keepGoing;
    // notification
    private String notif = " *** ";

    public static GameField modelField;

    public static ViewServer frame;


    //constructor that receive the port to listen to for connection as parameter
    public Server(int port) {
        // the port
        this.port = port;
        // to display hh:mm:ss
        sdf = new SimpleDateFormat("HH:mm:ss");
        // an ArrayList to keep the list of the Client
        al = new ArrayList<ClientThread>();

    }

    public void start() {
        keepGoing = true;
        //create socket server and wait for connection requests
        try {
            // the socket used by the server
            ServerSocket serverSocket = new ServerSocket(port);

            // infinite loop to wait for connections ( till server is active )
            while (keepGoing) {
                display("Server waiting for Clients on port " + port + ".");

                // break if server stoped
                if (!keepGoing)
                    break;
                // accept connection if requested from client
                Socket socket = serverSocket.accept();
                if (al.size() < 2) {

                    // if client is connected, create its thread
                    ClientThread t = new ClientThread(socket);
                    //add this client to arraylist

                    al.add(t);
                    frame.repaintPeople(al);
                    broadcastFerme(al.size());
                    t.start();
                } else {
                    socket.close();
                    serverSocket.close();
                }



            }
            // try to stop the server
            try {
                serverSocket.close();
                for (int i = 0; i < al.size(); ++i) {
                    ClientThread tc = al.get(i);
                    try {
                        // close all data streams and socket
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    } catch (IOException ioE) {
                    }
                }
            } catch (Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        } catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            display(msg);
        }
    }

    /*// to stop the server
    protected void stop() {
        keepGoing = false;
        try {
            new Socket("localhost", port);
        } catch (Exception e) {
        }
    }*/

    /**
     * manda dei messaggi alla console in un tempo definito
     *
     * @param msg
     */
    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);
    }

    /**
     * messaggio per il cambio turno (quando tutte le palline sono ferme
     *
     * @param m
     * @return
     */

    private boolean broadcastFerme(int m) {

        for (int i = al.size(); --i >= 0; ) {
            ClientThread ct = al.get(i);
            String team;
            if (i % 2 == 0) {
                team = "T1";
            } else {
                team = "T2";
            }

            String messageLf = modelField.messaggioPos() + m + "@" + team + "@" + modelField.getTurno() + "@" + modelField.getScore1() + "@" + modelField.getScore2() + "@" + modelField.getCollision() + "\n";
            for (int j = 0; j < m; j++) {
                messageLf += al.get(j).username + "\n";
            }


            // try to write to the Client if it fails remove it from the list
            if (!ct.writeMsg(messageLf)) {
                al.remove(i);
                display("Disconnected Client " + ct.username + " removed from list.");
            }
        }
        modelField.setCollision();
        return true;
    }


    /**
     * messaggio che inviamo a tutti i client conessi in cui aggiorna la posizione
     *
     * @param m
     * @return
     */
    private boolean broadcast(int m) {
        // because it has disconnected

        for (int i = al.size(); --i >= 0; ) {
            ClientThread ct = al.get(i);
            String team;
            if (i % 2 == 0) {
                team = "T1";
            } else {
                team = "T2";
            }
            String messageLf = modelField.messaggioPos() + m + "@" + team + "@null" + "@" + modelField.getScore1() + "@" + modelField.getScore2() + "@" + modelField.getCollision() + "\n";
            for (int j = 0; j < m; j++) {
                messageLf += al.get(j).username + "\n";
            }

            // try to write to the Client if it fails remove it from the list
            if (!ct.writeMsg(messageLf)) {
                al.remove(i);
                display("Disconnected Client " + ct.username + " removed from list.");
            }

        }
        modelField.checkVincitore();
        modelField.setCollision();
        return true;


    }

    // if client sent LOGOUT message to exit
    synchronized void remove(int id) {

        String disconnectedClient = "";
        // scan the array list until we found the Id
        for (int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);
            // if found remove it
            if (ct.id == id) {
                disconnectedClient = ct.getUsername();
                al.remove(i);
                break;
            }
        }
        broadcast(al.size());
    }


    public static void main(String[] args) throws UnknownHostException {
        // start server on port 1500 unless a PortNumber is specified
    	int portNumber = 1500;
        frame = new ViewServer(Inet4Address.getLocalHost().getHostAddress(),portNumber);
        frame.setVisible(true);

        
        modelField = new GameField();
        //System.out.println(modelField.messaggioPos());

        // create a server object and start it
        Server server = new Server(portNumber);
        server.start();
    }

    // One instance of this thread will run for each client
    public class ClientThread extends Thread {
        // the socket to get messages from client
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        // my unique id (easier for deconnection)
        int id;
        // the Username of the Client
        public String username;
        // message object to recieve message and its type
        ChatMessage cm;
        String date;
        Timer timer;

        // Constructor
        ClientThread(Socket socket) {
            // a unique id
            id = ++uniqueId;
            this.socket = socket;
            //Creating both Data Str
            //System.out.println("ip clinet " + socket.getInetAddress() + "Port " + socket.getPort());
            //System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                // Legge il primo messaggio inviato
                username = (String) sInput.readObject();
                broadcast(al.size());

            } catch (IOException e) {
                display("Exception creating new Input/output Streams: " + e);
                return;
            } catch (ClassNotFoundException e) {
            }
            date = new Date().toString() + "\n";
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        // infinite loop to read and forward message
        public void run() {
            // to loop until LOGOUT
            boolean keepGoing = true;
            while (keepGoing) {
                // read a String (which is an object)
                try {
                    cm = (ChatMessage) sInput.readObject();
                } catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                    break;
                }
                // get the message from the ChatMessage object received
                String message = cm.getMessage();

                // different actions based on type message
                switch (cm.getType()) {
                    case ChatMessage.MESSAGE:
                        //formato messaggio x@y@distance@angle
                        //dove x e y sono le coordinate del primo oggetto che si muove
                        if (!message.isEmpty()) {
                            modelField.cambioTurno();
                            System.out.println("MESSAGGIO INVIATO" + message);
                            String part[] = message.split("@");
                            FieldObject selezionata = modelField.pedinaSelezionata(Double.parseDouble(part[0]), Double.parseDouble(part[1]));
                            if (selezionata != null)
                                selezionata.start(Integer.parseInt(part[2]), Double.parseDouble(part[3]));
                        }
                        timer = new Timer(9, (e) -> {
                            if (!modelField.allStop()) {
                                modelField.updateGame();
                                broadcast(al.size());
                            } else {
                                timer.stop();
                                broadcastFerme(al.size());
                            }
                        });
                        timer.start();
                        break;

                }
            }
            // if out of the loop then disconnected and remove from client list
            remove(id);

            close();
            frame.repaintPeople(al);
        }

        // close everything
        private void close() {
            try {
                if (sOutput != null) sOutput.close();
            } catch (Exception e) {
            }
            try {
                if (sInput != null) sInput.close();
            } catch (Exception e) {
            }
            ;
            try {
                if (socket != null) socket.close();
            } catch (Exception e) {
            }
        }

        // write a String to the Client output stream
        private boolean writeMsg(String msg) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {
                sOutput.writeObject(msg);
            }
            // if an error occurs, do not abort just inform the user
            catch (IOException e) {
                display(notif + "Error sending message to " + username + notif);
                display(e.toString());
            }
            return true;
        }
    }


}

