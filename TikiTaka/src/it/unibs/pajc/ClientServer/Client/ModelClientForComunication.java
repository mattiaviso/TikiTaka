package it.unibs.pajc.ClientServer.Client;

import it.unibs.pajc.ClientServer.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ModelClientForComunication {

    //componenti grafiche


    // variabili gioco
    private   String turno;
    private int score1, score2;
    private String team = null;


    // variabili client
    private boolean close = false;
    private ObjectInputStream sInput;        // to read from the socket
    private ObjectOutputStream sOutput;        // to write on the socket
    private Socket socket;                    // socket object
    private String server, username;    // server and username
    private int port;

    public String getUsername() {
        return username;
    }

    public ObjectInputStream getsInput(){
        return sInput;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public ObjectOutputStream getsOutput() {
        return sOutput;
    }

    public void setsOutput(ObjectOutputStream sOutput) {
        this.sOutput = sOutput;
    }

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
     *  costruttore
     *  server: Ip del server
     *  port: Numero di porta per la connessione
     *  username: Username
     */

    ModelClientForComunication(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    /**
     * conessione al server
     *
     * @return true se la connessione va a buon fine
     */

    public boolean start() {
        try {
            socket = new Socket(server, port);
        } catch (Exception ec) {
            //display("CONNESIONE NON RIUSCITA, SERVER PIENO");

            return false;
        }

        //Creazione Data Stream ( per scambiare messaggi)
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            //display("CONNESIONE NON RIUSCITA, SERVER PIENO");
            System.exit(0);
            return false;
        }
        return true;
    }



    /**
     * Manda un messaggio msg al server
     *
     * @param msg
     */
    public void sendMessage(Message msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
           // display("Eccezione scrittura su server: " + e);

            System.exit(0);
        }
    }

    /**
     * Quando la connessione salta
     * Vengono chiusi tutti gli stream di dati
     */
    public void disconnect() {
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







}

