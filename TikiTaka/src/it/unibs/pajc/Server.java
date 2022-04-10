package it.unibs.pajc;


import it.unibs.pajc.ClientServer.Message;
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
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe Server
 */
public class Server {
    private static int uniqueId;
    // Lista client connessi
    private ArrayList<ClientThread> al;
    // numero porta connessione
    private int port;
    // attributo che indica se il server è in esecuzione
    private boolean keepGoing;
    private String notif = " *** ";
    //Model del gioco
    public static GameField modelField;
    //Frame che ci permette di vedere cosa succede sul server
    public static ViewServer frame;

    /**
     * Costruttore classe Server
     *
     * @param port Porta di connessione
     */
    public Server(int port) {
        this.port = port;
        al = new ArrayList<ClientThread>();
    }

    /**
     * Metodo che avvia il server
     */
    public void start() {
        keepGoing = true;
        //crea il socket e aspetta connessioni dai client
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            // Loop infinito per aspettare connessioni
            while (keepGoing) {
                display("Server waiting for Clients on port " + port + ".");

                if (!keepGoing) break;

                //Accetto connessione al server se gli utenti connessi sono meno di 2
                //altrimenti la richiesta di connessione viene rifuitata
                Socket socket = serverSocket.accept();
                if (al.size() < 2) {

                    // thread client
                    ClientThread t = new ClientThread(socket);
                    al.add(t);

                    frame.repaintPeople(al);
                    broadcastFerme(al.size());
                    t.start();
                } else {
                    socket.close();
                    serverSocket.close();
                }
            }

            // chiusura del server
            try {
                serverSocket.close();
                for (int i = 0; i < al.size(); ++i) {
                    ClientThread tc = al.get(i);
                    try {
                        // Chiusura di DataStream
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
            String msg = " Exception on new ServerSocket: " + e + "\n";
            display(msg);
        }
    }


    /**
     * Stampa in console messaggi per controllare stato server
     *
     * @param msg
     */
    private void display(String msg) {
        String time = msg;
        System.out.println(time);
    }

    /**
     * messaggio per il cambio turno (quando tutte le palline sono ferme)
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


            // Provo a scrivere al Client, se la procedura fallisce lo elimino dall'array al
            if (!ct.writeMsg(messageLf)) {
                al.remove(i);
                display("Disconnected Client " + ct.username + " removed from list.");
            }
        }
        modelField.setCollision();

        return true;
    }


    /**
     * messaggio che inviamo a tutti i client conessi in cui aggiorna la posizione, quando sono in movimento
     *
     * @param m
     * @return
     */
    private boolean broadcast(int m) {

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

            if (!ct.writeMsg(messageLf)) {
                al.remove(i);
                display("Disconnected Client " + ct.username + " removed from list.");
            }
        }

        modelField.checkVincitore();
        modelField.setCollision();

        return true;
    }

    /**
     * Se il client invia un messaggio di tipo LOGOUT
     * il client viene elimintao dalla lista al
     *
     * @param id
     */
    synchronized void remove(int id) {

        String disconnectedClient = "";
        for (int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);
            if (ct.id == id) {
                disconnectedClient = ct.getUsername();
                al.remove(i);
                break;
            }
        }
        broadcast(al.size());
    }


    public static void main(String[] args) throws UnknownHostException {

    	int portNumber = 1500;
        frame = new ViewServer(Inet4Address.getLocalHost().getHostAddress(), portNumber);
        frame.setVisible(true);

        modelField = new GameField();

        //Crea l'oggetto server e lo esegue
        Server server = new Server(portNumber);
        server.start();
    }

    /**
     * Un instanza di questo thread sarà eseguita per ogni client
     */
    public class ClientThread extends Thread {
        // socket ricezione messaggio client
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        int id;
        public String username;
        Message cm;
        String date;
        Timer timer;

        //Costruttore
        ClientThread(Socket socket) {
            id = ++uniqueId;
            this.socket = socket;

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

        /**
         * Metodo che esegue un loop infinito e prende i messaggi del client
         * finchè non viene eseguito il messaggio di LOGOUT
         */
        public void run() {
            boolean keepGoing = true;
            while (keepGoing) {
                try {
                    cm = (Message) sInput.readObject();
                } catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                    break;
                }
                // Prende il messaggio ricevuto dal client
                String message = cm.getMessage();

                // Azioni differenti in base all'azione scelta
                switch (cm.getType()) {
                    case Message.MESSAGE:
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
            remove(id);

            close();
            frame.repaintPeople(al);
        }

        /**
         * Chiude tutto
         */
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

        /**
         * Metodo che ci permette di scrivere il messaggio al client
         * @param msg
         * @return
         */
        private boolean writeMsg(String msg) {
            //invia il messaggio se il client è ancora connesso
            if (!socket.isConnected()) {
                close();
                return false;
            }
            try {
                sOutput.writeObject(msg);

            } catch (IOException e) {
                display(notif + "Error sending message to " + username + notif);
                display(e.toString());
            }
            return true;
        }
    }

}

