package it.unibs.pajc.ClientServer;

import java.io.Serializable;

/*
classe utilizzata per inviare oggetti server client ( serializable
 */
public class Message implements Serializable {
    // MESSAGE indica messaggio normale
    // LOGOUT messaggio di disconnessione dal server
    public static final int MESSAGE = 1,LOGOUT=2;
    private int type;
    private String message;

    public Message(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
