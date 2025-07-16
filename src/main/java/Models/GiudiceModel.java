package Models;

import Entity.Giudice;
import Entity.HackaThon;
import dao.GiudiceDAO;
import PostgresDAO.PostgresGiudiceDAO;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Classe modello che gestisce la logica di business relativa ai giudici.
 * Implementa il pattern Singleton per garantire l'esistenza di una sola istanza del modello.
 */
public class GiudiceModel {

    /** Istanza singleton del modello */
    private static GiudiceModel instance;

    private ArrayList<Giudice> giudici = new ArrayList<>();
    private GiudiceDAO giudiceDAO = new PostgresGiudiceDAO();

    /**
     * Costruttore privato per applicare il pattern Singleton.
     * Evita la creazione di istanze multiple della classe.
     */
    private GiudiceModel() {}

    /**
     * Verifica la password inserita per un dato giudice.
     *
     * @param g Oggetto {@code Giudice} per il quale verificare la password.
     * @param password La password da verificare.
     * @return {@code true} se la password è corretta, {@code false} altrimenti.
     */
    public boolean verificaPassword(Giudice g, String password) {
        return giudiceDAO.verificaPassword(g, password);
    }


    /**
     * Restituisce la lista dei giudici collegati a un {@code HackaThon} specifico.
     *
     * @param titoloHackathon Il titolo dell'{@code HackaThon} per il quale ottenere i giudici.
     * @return Una lista di giudici collegati all'{@code HackaThon}.
     */
    public List<Giudice> trovaPerHackathon(String titoloHackathon) {
        return giudiceDAO.getGiudiciPerHackathon(titoloHackathon);
    }


    /**
     * Restituisce l'istanza singleton della classe {@code GiudiceModel}.
     * Se l'istanza non esiste, ne crea una nuova.
     *
     * @return L'istanza singleton di {@code GiudiceModel}.
     */
    public static GiudiceModel getInstance() {
        if (instance == null) {
            instance = new GiudiceModel();
        }
        return instance;
    }


    /**
     * Aggiunge un giudice a un {@code HackaThon} specifico e imposta la sua password.
     * Se il giudice non è già presente, viene aggiunto alla lista dei giudici.
     *
     * @param g Il giudice da aggiungere.
     * @param h L'{@code HackaThon} a cui associare il giudice.
     * @param password La password da associare al giudice.
     */
    public void aggiungiGiudice(Giudice g, HackaThon h, String password) {
        if (!giudici.contains(g)) {
            giudici.add(g);
            g.setRegistrato(true);
            h.aggiungiGiudice(g, password);
            giudiceDAO.aggiungiGiudice(g, h, password);
        }
    }
}

