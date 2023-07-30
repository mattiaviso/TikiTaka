package it.unibs.pajc.SinglePlayer.GeneralControllerGameField;

import it.unibs.pajc.Partita.GameField;
import it.unibs.pajc.SinglePlayer.ModalitaVsComputer.GameFieldSingol;
import it.unibs.pajc.SinglePlayer.ViewGameField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ControllerGeneral extends MouseAdapter {

    private ViewGameField viewGame;
    private GameField modelGameField;


    public void setViewGame(ViewGameField viewGame) {
        this.viewGame = viewGame;
    }

    public GameField getModelGameField() {
        return modelGameField;
    }

    public void setModelGameField(GameField modelGameField) {
        this.modelGameField = modelGameField;
    }

    public final ViewGameField getViewGame() {
        return viewGame;
    }

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
    public abstract   void mouseReleased(MouseEvent e) ;

    @Override
    public final void mouseDragged(MouseEvent e) {

        viewGame.setXnew(e.getX() - viewGame.getW() / 2);

        viewGame.setYnew(-(e.getY() - viewGame.getH() / 2));


        if (viewGame.getValido() != null)
            viewGame.setNewradius(Math.min((int) (Math.sqrt(((viewGame.getValido().getPosition().getX() - viewGame.getXnew()) * (viewGame.getValido().getPosition().getX() - viewGame.getXnew())) + ((viewGame.getValido().getPosition().getY() - viewGame.getYnew()) * (viewGame.getValido().getPosition().getY() - viewGame.getYnew())))), 150));

        viewGame.repaint();
    }


}
