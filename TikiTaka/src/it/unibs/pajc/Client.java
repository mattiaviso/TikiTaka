package it.unibs.pajc;

import java.net.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

import javax.swing.*;


//The Client that can be run as a console
public class Client {

    //componenti grafiche
    public static JFrame frame;
    public static ViewClient finestra;
    public static Result panel;

    // variabili gioco
    protected String turno;
    private int score1, score2;
    private String team = null;

    // variabili client
    private static boolean close = false;
    private ObjectInputStream sInput;        // to read from the socket
    private ObjectOutputStream sOutput;        // to write on the socket
    private Socket socket;                    // socket object
    private String server, username;    // server and username
    private int port;


    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
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
        catch (Exception ec) {
            display("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        /* Creating both Data Stream */
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try {
            sOutput.writeObject(username);
        } catch (IOException eIO) {
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
        JOptionPane.showMessageDialog(finestra, msg);

    }

    /*
     * To send a message to the server
     */
    public void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    /*
     * When something goes wrong
     * Close the Input/Output streams and disconnect
     */
    private void disconnect() {
        try {
            if (sInput != null) sInput.close();
        } catch (Exception e) {
        }
        try {
            if (sOutput != null) sOutput.close();
        } catch (Exception e) {
        }
        try {
            if (socket != null) socket.close();
        } catch (Exception e) {
        }

    }

    public static boolean isValidIp(final String ip) {
        return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
    }

    public static void main(String[] args) {
        //Musica
        SoundClip sound;

        // default values if not entered
        int portNumber = 1500;
        // immette ip
        String serverAddress;
        String ipServer = JOptionPane.showInputDialog("inserire ipServer (premere invio per il localhost)");
        if (ipServer != null && isValidIp(ipServer)) {
            serverAddress = ipServer;
        } else {
            serverAddress = "localhost";
        }


        String userName = "Anonymous";
        Scanner scan = new Scanner(System.in);


        userName = JOptionPane.showInputDialog(finestra, "Inserisci username");

        panel = new Result("", "",client);
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBounds(100, 50, 1300, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setTable(0, 0);
        panel.setPreferredSize(new Dimension(1300, 120));


        finestra = new ViewClient();
        finestra.setVisible(true);

        frame.add(finestra, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);

        sound = new SoundClip("Song");
        sound.start();


        // create the Client object
        Client client = new Client(serverAddress, portNumber, userName);
        // try to connect to the server and return if not connected
        if (!client.start())
            return;

        finestra.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                finestra.xnew = e.getX() - finestra.w / 2;
                finestra.ynew = -(e.getY() - finestra.h / 2);

                if (finestra.valido != null)
                    finestra.newradius = Math.min((int) (Math.sqrt(((finestra.valido.position.getX() - finestra.xnew) *
                            (finestra.valido.position.getX() - finestra.xnew)) + ((finestra.valido.position.getY() - finestra.ynew) *
                            (finestra.valido.position.getY() - finestra.ynew)))), 150);

                finestra.repaint();
            }
        });

        // MOUSE LISTENER
        finestra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (finestra.valido != null) {
                    if (!(finestra.distance <= finestra.valido.getRadius())) {
                        //finestra.valido.start(finestra.distance, finestra.angle);
                        System.out.println(finestra.valido.position.getX() + finestra.valido.position.getY() + finestra.distance + finestra.angle);
                        //Dare formo al messaggio x@y@distance@angle
                        elaboramessaggio(new String(finestra.valido.position.getX() + "@" + finestra.valido.position.getY() + "@" + finestra.distance + "@" + finestra.angle), client);
                    }
                }
                finestra.valido = null;
                finestra.newradius = 0;
                finestra.repaint();

            }

            @Override
            public void mousePressed(MouseEvent e) {
                //prendiamo coordinate x e y di dove è stato premuto il mouse
                if (client.getTeam().equals(client.getTurno())) {
                    int x = e.getX() - finestra.w / 2;
                    int y = -(e.getY() - finestra.h / 2);
                    finestra.valido = finestra.checkClickAble(x, y);
                    if (finestra.valido != null) {
                        if (finestra.valido.getTeam().equals(client.getTeam())) {
                            finestra.repaint();
                        } else {
                            finestra.valido = null;
                        }
                    }
                }
            }

        });


        if (close == true) {
            // chiude le risorse dal server
            scan.close();
            // Client ha finito il suo lavoro, disconnessione client
            client.disconnect();
        }


    }

    private static void elaboramessaggio(String msg, Client client) {

        if (msg.equalsIgnoreCase("LOGOUT")) {
            client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
            close = true;
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

            while (true) {
                try {
                    // read the message form the input datastream
                    msg = (String) sInput.readObject();
                    // print the message
                    System.out.println(msg);
                    finestra.aggiornaPos(msg);
                    String[] parts = msg.split("\n");

                    String[] riga11 = parts[11].split("@");

                    int nUsers = Integer.parseInt(riga11[0]);

                    setTeam(riga11[1]);
                    setTurno(riga11[2]);
                    setScore1(Integer.parseInt(riga11[3]));
                    setScore2(Integer.parseInt(riga11[4]));

                    panel.setScore(score1, score2);

                    if (nUsers >= 1)
                        if (parts[12].equals(username))
                            parts[12] += "(you)";

                    if (nUsers >= 2)
                        if (parts[13].equals(username))
                            parts[13] += "(you)";

                    if (nUsers == 1) {
                        panel.setUsername1(parts[12]);
                    } else {
                        panel.setUsernames(parts[12], parts[13]);
                    }

                    checkVincitore();


                } catch (IOException e) {

                    display("Server has closed the connection: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }

        }
    }

    /**
     * Metodo che controlla se qualcuno ha vinto
     */
    public void checkVincitore() {
        ImageIcon winner = new ImageIcon("winner.gif");
        ImageIcon loser = new ImageIcon("loser.gif");
        if (score1 == 3) {
            if (panel.getTeam2().equals(username + "(you)")) {
                JOptionPane.showMessageDialog(null, null, "Hai vinto", JOptionPane.INFORMATION_MESSAGE, winner);
            } else {
                JOptionPane.showMessageDialog(null, null, "Hai perso", JOptionPane.INFORMATION_MESSAGE, loser);
            }
        } else if (score2 == 3) {
            if (panel.getTeam1().equals(username + "(you)")) {
                JOptionPane.showMessageDialog(null, null, "Hai vinto", JOptionPane.INFORMATION_MESSAGE, winner);
            } else {
                JOptionPane.showMessageDialog(null, null, "Hai perso", JOptionPane.INFORMATION_MESSAGE, loser);

            }
        }
    }
}

