package it.unibs.pajc.SinglePlayer.ModalitaAllenamento;

import it.unibs.pajc.SinglePlayer.ModalitaAllenamento.Livelli.ILivelli;
import it.unibs.pajc.SinglePlayer.ModalitaAllenamento.Livelli.LivelloState1;
import it.unibs.pajc.Partita.*;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.Computer;

import javax.swing.*;

/**
 * Model
 */
public class GameFieldTraining extends GameField {


    private ILivelli posizioneInizialeLivelli;

    static public boolean collision = false;
    private boolean allStop = true;
    private int vita;



    public boolean isAllStop() {
        return allStop;
    }

    public void setAllStop(boolean allStop) {
        this.allStop = allStop;
    }


    public static boolean isCollision() {
        return collision;
    }

    public static void setCollision(boolean collision) {
        GameFieldTraining.collision = collision;
    }


    public GameFieldTraining() {
        setTurno("T1");
        vita = 3;
        ResultTraining.setScoreMancanti(vita);
        setObjectsPiece(new FieldObject[7]);
        posizioneInizialeLivelli = new LivelloState1(this);
        posizioneInizialeLivelli.positionStart();

    }


    /**
     * setta le posizioni iniziali delle pedine dopo un gol oppure al calcio d'inizio
     */
    @Override
    public void positionStart() {
        posizioneInizialeLivelli.cambiaLivello();
        // prendo il livello
        if (posizioneInizialeLivelli == null) {
            JOptionPane.showMessageDialog(null, "hai vinto ");
            System.exit(0);
        }
        posizioneInizialeLivelli.positionStart();
    }

    public void impostaStato(ILivelli nuovoStato) {
        posizioneInizialeLivelli = nuovoStato;
    }

    public void setCollision() {
        this.collision = false;
    }




    public boolean getCollision() {
        return collision;
    }


    @Override
    public void setScore(int score) {
        vita++;
        ResultTraining.setScoreMancanti(vita);
        ResultTraining.setLivelli();
    }





    /**
     * Metodo che ritorna la pedina premuta date le coordinate x e y.
     * Controlla se il punto (x, y) è all'interno del raggio della pedina e se la pedina appartiene al turno corrente.
     *
     * @param x Double x, coordinata x del punto premuto.
     * @param y Double y, coordinata y del punto premuto.
     * @return La pedina selezionata, null se non è stata premuta alcuna pedina valida.
     */
    public FieldObject pedinaSelezionata(double x, double y) {
        for (FieldObject f : getObjectsPiece()) {
            if (f instanceof Piece)
                if (Math.pow((x - f.getPosition().getX()), 2) + Math.pow((y - f.getPosition().getY()), 2) < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(getTurno())) {
                    return f;
                }
        }
        return null;
    }

    @Override
    public void checkVincitore() {

    }

    @Override
    public Computer piecePiuVicina(FieldObject ball) {
        return null;
    }

    /**
     * Metodo che diminuisce la vita del giocatore se non riesce a segnare un gol.
     * Se la vita del giocatore arriva a 0, mostra un messaggio di sconfitta e chiude il gioco.
     * Inoltre, imposta la posizione iniziale del livello di allenamento corrente per consentire un nuovo tentativo.
     *
     * @return true se il giocatore ha ancora vite disponibili, false se il giocatore ha perso tutte le vite.
     */
    public boolean setLifeIfPlayerDosentScore() {
        vita--;
        ResultTraining.setScoreMancanti(vita);
        if (vita == 0) {
            return false;
        }
        posizioneInizialeLivelli.positionStart();
        return true;
    }


}

