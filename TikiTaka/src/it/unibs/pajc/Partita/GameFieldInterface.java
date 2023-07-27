package it.unibs.pajc.Partita;
public interface GameFieldInterface {
    // interfaccia che ci permette di andare a utilizzare i metodi del GameFieldInterface nelle collisioni senza creare dipendeze
    void positionStart();
    void setTurnoAlternativo(String turno);
    void setScore(int score);

    void setCollisionForBouard(Boolean collision);

}
