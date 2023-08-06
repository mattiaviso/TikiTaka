package it.unibs.pajc.SinglePlayer.ModalitaAllenamento.Livelli;

public interface ILivelli {

    int GRANDEZZA_ARRAY = 7;

    /**
     * Metodo che posiziona le pedine all'inizio del livello.
     */
    public void positionStart();


    /**
     * Metodo che cambia il livello di gioco.
     */
    public void cambiaLivello();
}
