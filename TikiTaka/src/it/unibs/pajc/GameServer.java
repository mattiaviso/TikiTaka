package it.unibs.pajc;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Calendar;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;


    public GameServer() throws IOException {
        System.out.println("GameServer");
        numPlayers = 0 ;
        ss= new ServerSocket();
    }


    public void acceptConnecrions (){
        try {
            System.out.println("Waiting for connection");
            while (numPlayers<2){

            }
        }catch ()
    }
}
