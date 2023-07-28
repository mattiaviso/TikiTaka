package it.unibs.pajc.Allenamento;

import it.unibs.pajc.Allenamento.Livelli.ILivelli;
import it.unibs.pajc.Allenamento.Livelli.LivelloState1;
import it.unibs.pajc.Allenamento.Livelli.livelloState2;
import it.unibs.pajc.Partita.*;
import it.unibs.pajc.Partita.Collision.Collision;
import it.unibs.pajc.SinglePlayer.Computer;

import javax.swing.*;
import java.util.Arrays;

/**
 * Model
 */
public class GameFieldTraining implements GameFieldInterface {

    public static final int MAX_X = 566;
    public static final int MIN_X = -566;
    public static final int MIN_Y = -302;
    public static final int MAX_Y = 312;
    public static final float EPSILON = 0.09f;


    private FieldObject[] objectsPiece;
    private String turno;
    private ILivelli posizioneInizialeLivelli ;

    static public boolean collision = false;
    public boolean allStop = true;
    public int vita;


    public boolean isAllStop() {
        return allStop;
    }

    public void setAllStop(boolean allStop) {
        this.allStop = allStop;
    }

    public int getVita() {
        return vita;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }

    public String getTurno() {
        return turno;
    }

    public FieldObject[] getObjectsPiece() {
        return objectsPiece;
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
        this.turno = "T1";
        vita = 3;
        posizioneInizialeLivelli = new LivelloState1();
        objectsPiece = Arrays.copyOf(posizioneInizialeLivelli.getFieldObject(), posizioneInizialeLivelli.getFieldObject().length);
    }





    /**
     * Metodo che setta le posizioni iniziali delle pedine dopo un gol oppure al calcio d'inizio
     */
    @Override
    public  void positionStart() {
        posizioneInizialeLivelli=posizioneInizialeLivelli.cambiaLivello();
        // prendo il livello
        if(posizioneInizialeLivelli == null){
            JOptionPane.showConfirmDialog(null , "hai vinto ");
        }
        posizioneInizialeLivelli.positionStart();
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



    }



    public void setTurnoAlternativo(String turno) {

    }


    /**
     * Metodo che se invocato manda avanti di un esecuzione il gioco, si occupa dello spostamento e dei controlli vari
     * Come se fosse lo StepNext
     */
    public  void updateGame() {

        for (int i = 0; i < objectsPiece.length; i++) {
            Vector2d velocity = objectsPiece[i].getVelocita();
            Vector2d position = objectsPiece[i].getPosition();

            velocity.setY(velocity.getY());
            position.setX(position.getX() + (velocity.getX() * 1));
            position.setY(position.getY() + (velocity.getY() * 1));

            if (Math.abs(velocity.getX()) < EPSILON) {
                velocity.setX(0);
            }
            if (Math.abs(velocity.getY()) < EPSILON) {
                velocity.setY(0);
            }
        }

        Collision gestioneCollisioni = new Collision();
        gestioneCollisioni.checkCollision(objectsPiece, this);
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
            if (f instanceof Piece )
                if (Math.pow((x - f.getPosition().getX()), 2) + Math.pow((y - f.getPosition().getY()), 2)
                        < Math.pow((f.getRadius()), 2) && ((Piece) f).getTeam().equals(getTurno())) {
                    return f;
                }
        }
        return null;
    }

    /**
     * Metodo che controlla se tutte le palline sono ferme
     *
     * @return True se tutte le palline sono ferme
     */
    public  boolean allStop() {
        for (FieldObject o : objectsPiece) {
            if (!o.speedIsZero()) return false;
        }
        return true;
    }


    public void setLifeIfPlayerDosentScore(){
            vita--;
            System.out.println(vita);
            posizioneInizialeLivelli.positionStart();


    }














}

