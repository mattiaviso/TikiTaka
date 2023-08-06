package it.unibs.pajc.Partita;
public interface GameFieldInterface {
    /**
     * Metodo per impostare le posizioni iniziali delle pedine dopo un gol o al calcio d'inizio.
     */
    void positionStart();
    /**
     * Metodo per impostare il turno alternativo dopo un tiro.
     *
     * @param turno Il turno da impostare.
     */
    void setTurno(String turno);

    /**
     * Metodo per impostare il punteggio attuale del gioco.
     *
     * @param score Il punteggio da impostare.
     */
    void setScore(int score);

    /**
     * Metodo per impostare la flag di collisione del campo di gioco.
     *
     * @param collision True se è avvenuta una collisione, False altrimenti.
     */
    void setCollisionForBouard(Boolean collision);

}
