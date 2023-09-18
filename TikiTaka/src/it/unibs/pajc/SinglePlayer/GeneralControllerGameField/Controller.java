package it.unibs.pajc.SinglePlayer.GeneralControllerGameField;

import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.SinglePlayer.View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Controller è una classe astratta che estende MouseAdapter e fornisce metodi comuni per i controller del gioco.
 * Ogni controller specifico del gioco dovrà estendere questa classe per implementare il comportamento specifico del gioco.
 */
public abstract class Controller extends MouseAdapter {

    private View viewGame;
    private GameField modelGameField;


    public void setViewGame(View viewGame) {
        this.viewGame = viewGame;
    }

    public GameField getModelGameField() {
        return modelGameField;
    }

    public void setModelGameField(GameField modelGameField) {
        this.modelGameField = modelGameField;
    }

    public final View getViewGame() {
        return viewGame;
    }

    /**
     * Gestisce l'evento di pressione del mouse.
     * Ottiene le coordinate x e y del punto in cui è stato premuto il mouse e invoca il metodo pedinaSelezionata() del modello per selezionare la pedina corrispondente.
     *
     * @param e L'evento del mouse.
     */
    @Override
    public final void mousePressed(MouseEvent e) {

        //prendiamo coordinate x e y di dove è stato premuto il mouse
        int x = e.getX() - viewGame.getW() / 2;
        int y = -(e.getY() - viewGame.getH() / 2);

        System.out.println("posizione X" + x + "posizione Y " + y);
        if (modelGameField.allStop) {
            viewGame.setValido(modelGameField.pedinaSelezionata(x, y));
        }

        viewGame.repaint();

    }


    @Override
    public abstract void mouseReleased(MouseEvent e);


    /**
     * Gestisce l'evento di trascinamento del mouse.
     * Aggiorna le nuove coordinate x e y del punto in cui è stato trascinato il mouse e calcola il nuovo raggio per la pedina selezionata.
     *
     * @param e L'evento del mouse.
     */

    @Override
    public final void mouseDragged(MouseEvent e) {

        viewGame.setXnew(e.getX() - viewGame.getW() / 2);
        viewGame.setYnew(-(e.getY() - viewGame.getH() / 2));

        if (viewGame.getValido() != null)
            viewGame.setNewradius(Math.min((int) (Math.sqrt(((viewGame.getValido().getPosition().getX() - viewGame.getXnew()) * (viewGame.getValido().getPosition().getX() - viewGame.getXnew())) + ((viewGame.getValido().getPosition().getY() - viewGame.getYnew()) * (viewGame.getValido().getPosition().getY() - viewGame.getYnew())))), 150));

        viewGame.repaint();
    }


}
