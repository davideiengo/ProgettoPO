package Models;

import Entity.HackaThon;
import dao.HackathonDAO;
import PostgresDAO.PostgresHackathonDAO;


import java.util.ArrayList;


/**
 * Classe modello che gestisce la logica di business relativa agli {@code HackaThon}.
 * Implementa il pattern Singleton per garantire l'esistenza di una sola istanza del modello.
 */
public class HackathonModel {
    private static HackathonModel instance = null;
    private ArrayList<HackaThon> hackathonList = new ArrayList<>();
    private HackathonDAO hackathonDAO = new PostgresHackathonDAO();

    /**
     * Costruttore privato per applicare il pattern Singleton.
     * Evita la creazione di istanze multiple della classe.
     */
    private HackathonModel() {}

    /**
     * Restituisce l'istanza singleton della classe {@code HackathonModel}.
     * Se l'istanza non esiste, ne crea una nuova.
     *
     * @return L'istanza singleton di {@code HackathonModel}.
     */
    public static HackathonModel getInstance() {
        if (instance == null) {
            instance = new HackathonModel();
        }
        return instance;
    }

    /**
     * Aggiunge un nuovo {@code HackaThon} alla lista e lo salva nel database.
     *
     * @param h L'{@code HackaThon} da aggiungere.
     */
    public void aggiungiHackathon(HackaThon h) {
        hackathonList.add(h);
        hackathonDAO.salvaHackathon(h); // Salva nel DB
    }

    /**
     * Restituisce un {@code HackaThon} per titolo.
     *
     * @param titolo Il titolo identificativo dell'{@code HackaThon}.
     * @return L'{@code HackaThon} corrispondente al titolo o {@code null} se non trovato.
     */
    public HackaThon getHackathonByTitolo(String titolo) {
        return hackathonDAO.trovaPerTitolo(titolo);
    }

    /**
     * Aggiorna le registrazioni di un {@code HackaThon} nel database.
     *
     * @param h L'{@code HackaThon} da aggiornare.
     */
    public void aggiornaRegistrazioni(HackaThon h) {
        hackathonDAO.aggiorna(h);
    }

    /**
     * Verifica se esiste già un {@code HackaThon} con il titolo specificato.
     *
     * @param titolo Il titolo identificativo da cercare.
     * @return {@code true} se esiste un {@code HackaThon} con il titolo, {@code false} altrimenti.
     */
    public boolean esisteHackathonConTitolo(String titolo) {
        for (HackaThon h : hackathonList) {
            if (h.getTitoloIdentificativo().equalsIgnoreCase(titolo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Restituisce la lista di tutti gli {@code HackaThon}.
     *
     * @return Una lista contenente tutti gli {@code HackaThon}.
     */
    public ArrayList<HackaThon> getTutti() {
        return new ArrayList<>(hackathonDAO.trovaTutti());
    }

}



