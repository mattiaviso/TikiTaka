package it.unibs.pajc.ClientServer.Client;

import it.unibs.pajc.ClientServer.Message;
import it.unibs.pajc.ClientServer.SoundClip;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ControllerClient {

    private ViewClient viewClient;
    private ModelClientForComunication modelClientForComunication;

    public ControllerClient(int portNumber, String serverAddress, String userName) {
        modelClientForComunication = new ModelClientForComunication(serverAddress, portNumber, userName);
        viewClient = new ViewClient();
        viewClient.createComponents(modelClientForComunication);

        SoundClip sound = new SoundClip("Song");
        sound.start();

        if( !startClientListener())
            return;

        viewClient.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                viewClient.setXnew(e.getX() - viewClient.getW() / 2);
                viewClient.setYnew(-(e.getY() - viewClient.getH() / 2));

                if (viewClient.getValido() != null)
                    viewClient.setRadiusPower(
                            Math.min((int) (Math.sqrt(((viewClient.getPosValidX() - viewClient.getXnew()) *
                                    (viewClient.getPosValidX() - viewClient.getXnew())) + ((viewClient.getPosValidY() - viewClient.getYnew()) *
                                    (viewClient.getPosValidY() - viewClient.getYnew())))), 150));

                viewClient.repaint();
            }
        });


        viewClient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (viewClient.getValido() != null) {
                    if (!(viewClient.getDistance() <= viewClient.getValidoRadius())) {

                        //Dare forma al messaggio x@y@distance@angle che verrà in seguito inviato al server
                        buildMessage(viewClient.getPosValidX() + "@" + viewClient.getPosValidY() + "@" + viewClient.getDistance() + "@" + viewClient.getAngle());
                    }
                }
                viewClient.setValido(null);
                viewClient.setRadiusPower(0);
                viewClient.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //prendiamo coordinate x e y di dove è stato premuto il mouse

                if (modelClientForComunication.getTeam().equals(modelClientForComunication.getTurno())) {
                    int x = e.getX() - viewClient.getW() / 2;
                    int y = -(e.getY() - viewClient.getH() / 2);
                    viewClient.setValido(viewClient.checkClickAble(x, y));
                    if (viewClient.getValido() != null) {
                        if (viewClient.getValidoTeam().equals(modelClientForComunication.getTeam())) {
                            viewClient.repaint();
                        } else {
                            viewClient.setValido(null);
                        }
                    }
                }
            }

        });

        if (modelClientForComunication.getClose() == true) {
            // ModelClientForComunication ha finito il suo lavoro, disconnessione modelClientForComunication
            modelClientForComunication.disconnect();
        }

    }
    /**
     * Metodo che controlla se il vincitore
     * E stampa a video gif vincitore e perdente
     */
    public void checkWinner() {
        ImageIcon winner = new ImageIcon("winner.gif");
        ImageIcon loser = new ImageIcon("loser.gif");
        if (modelClientForComunication.getScore1() == 3) {
            if (viewClient.getPanel().getTeam2().equals(modelClientForComunication.getUsername()+ "(you)")) {
                SoundClip win = new SoundClip("win");
                win.startSound();
                JOptionPane.showMessageDialog(null, null, "Hai vinto", JOptionPane.INFORMATION_MESSAGE, winner);
            } else {
                SoundClip los = new SoundClip("Loser");
                los.startSound();
                JOptionPane.showMessageDialog(null, null, "Hai perso", JOptionPane.INFORMATION_MESSAGE, loser);
            }
        } else if (modelClientForComunication.getScore2() == 3) {
            if (viewClient.getPanel().getTeam1().equals(modelClientForComunication.getUsername() + "(you)")) {
                SoundClip win = new SoundClip("win");
                win.startSound();
                JOptionPane.showMessageDialog(null, null, "Hai vinto", JOptionPane.INFORMATION_MESSAGE, winner);
            } else {
                SoundClip los = new SoundClip("Loser");
                los.startSound();
                JOptionPane.showMessageDialog(null, null, "Hai perso", JOptionPane.INFORMATION_MESSAGE, loser);

            }
        }
    }

    /**
     * creazione del messaggio da mandare
     * @param msg
     */
    private void buildMessage(String msg) {

        if (msg.equalsIgnoreCase("LOGOUT")) {
            modelClientForComunication.sendMessage(new Message(Message.LOGOUT, ""));
            modelClientForComunication.setClose(true);
        } else {
            modelClientForComunication.sendMessage(new Message(Message.MESSAGE, msg));
        }
    }

    /**
     * Stampa a video in un pop-up una certa stringa msg
     *
     * @param msg
     */
    public void display(String msg) {
        JOptionPane.showMessageDialog(viewClient, msg);
    }

    public boolean startClientListener (){
        if (!modelClientForComunication.start())
            return false;
        // creazione thread per ascoltare dal server
        new ListenFromServer().start();
        try {
            //invio username per giocare
            modelClientForComunication.getsOutput().writeObject(modelClientForComunication.getUsername());
        } catch (IOException eIO) {
            //display("Exception doing login : " + eIO);
            modelClientForComunication.disconnect();
            System.exit(0);
            return false;
        }
        return true;
    }



    /**
     * Classe che resta in ascolto dei messaggi dal server
     * Ed elabora le informazioni ricevute
     */
    class ListenFromServer extends Thread {

        // sistemare questa classe di messaggi utilizzando qualcosa di asincrono
        public void run() {
            String msg = null;



            while (true) {
                try {
                    //Legge il messaggio inviato dal dataStream
                    msg = (String) modelClientForComunication.getsInput().readObject();

                    viewClient.setPos(msg);
                    String[] parts = msg.split("\n");

                    String[] riga11 = parts[11].split("@");

                    Runnable task = ()->{
                        if(riga11[5].equals("true")){
                            SoundClip collision = new SoundClip("collision");
                            collision.startSound();
                        }
                    };
                    task.run();

                    Thread thread = new Thread(task);
                    thread.start();

                    int nUsers = Integer.parseInt(riga11[0]);
                    int newScore1 = Integer.parseInt(riga11[3]);
                    int newScore2 = Integer.parseInt(riga11[4]);

                    modelClientForComunication.setTeam(riga11[1]);
                    modelClientForComunication.setTurno(riga11[2]);

                    if ((newScore1 != modelClientForComunication.getScore1() && newScore1 < 3) || (newScore2 != modelClientForComunication.getScore2() && newScore2 < 3)) {
                        SoundClip gol = new SoundClip("Goal");
                        gol.startSound();
                    }

                    modelClientForComunication.setScore1(newScore1);
                    modelClientForComunication.setScore2(newScore2);

                    viewClient.getPanel().setScore(modelClientForComunication.getScore1(), modelClientForComunication.getScore2());

                    if (nUsers >= 1)
                        if (parts[12].equals(modelClientForComunication.getUsername()))
                            parts[12] += "(you)";

                    if (nUsers >= 2)
                        if (parts[13].equals(modelClientForComunication.getUsername()))
                            parts[13] += "(you)";

                    if (nUsers == 1) {
                        viewClient.getPanel().setUsername1(parts[12]);
                    } else {
                        viewClient.getPanel().setUsernames(parts[12], parts[13]);
                    }

                    checkWinner();

                } catch (IOException e) {
                    display("La connessione al server è stata interrotta");
                    System.exit(0);
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }

        }
    }




    // GESTIONE DELLE PEDINE DA PARTE DEL CLIENT SECONDO ME E' DA CREARE UN ALTRA CLASSE








}




