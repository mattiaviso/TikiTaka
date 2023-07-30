package it.unibs.pajc.SinglePlayer.ModalitaAllenamento;

import it.unibs.pajc.SinglePlayer.ModalitaAllenamento.Livelli.ILivelli;
import it.unibs.pajc.SinglePlayer.ModalitaAllenamento.Livelli.LivelloState1;
import it.unibs.pajc.Partita.*;
import it.unibs.pajc.Partita.Collision.Collision;

import javax.swing.*;

/**
 * Model
 */
public class GameFieldTraining extends GameField implements GameFieldInterface {


    private ILivelli posizioneInizialeLivelli;

    static public boolean collision = false;
    public boolean allStop = true;
    public int vita;


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

    /**
     * Metodo costruttore che inizializza gli 11 oggetti del gioco
     * e le relative componenti grafiche
     */
    public GameFieldTraining() {
        setTurno("T1");
        vita = 3;
        setObjectsPiece(new FieldObject[7]);
        posizioneInizialeLivelli = new LivelloState1(this);
        posizioneInizialeLivelli.positionStart();

    }


    /**
     * Metodo che setta le posizioni iniziali delle pedine dopo un gol oppure al calcio d'inizio
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

    @Override
    public void setCollisionForBouard(Boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }


    @Override
    public void setScore(int score) {
        vita++;


    }


    public void setTurnoAlternativo(String turno) {

    }


    /**
     * Metodo che ritorna la pedina premuta  date le coordinate x e y
     *
     * @param x Double x
     * @param y Double y
     * @return pedina seleziononata
     */
    public FieldObject pedinaSelezionata(double x, double y) {
        for (FieldObject f : getObjectsPiece()) {
            if (f instanceof Piece)
                if (Math.pow((x - f.getPosition().getX()), 2) + Math.pow((y - f.getPosition().getY()), 2)
                        < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(getTurno())) {
                    return f;
                }
        }
        return null;
    }

    @Override
    public void checkVincitore() {

    }


    public boolean setLifeIfPlayerDosentScore() {
        vita--;
        if (vita == 0) {
            return false;
        }
        posizioneInizialeLivelli.positionStart();
        return true;

    }


}

