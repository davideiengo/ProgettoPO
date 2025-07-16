package Controllers;

import Entity.HackaThon;
import Entity.Utente;
import Models.HackathonModel;
import Models.UtenteModel;
import PostgresDAO.PostgresUtenteDAO;
import View.HomeView;
import View.UserRegistrationView;
import View.UserRegistrationView;
import javax.swing.*;
import java.util.ArrayList;

/**
 * La classe {@code UserController} gestisce la registrazione degli utenti agli hackathon.
 * Fornisce funzionalità per registrare un utente, caricare gli hackathon disponibili e tornare alla home.
 */
public class UserController {

    /** Vista per la registrazione dell'utente */
    private UserRegistrationView view;

    /** Modello per la gestione degli hackathon */
    private HackathonModel model;

    /**
     * Costruttore della classe {@code UserController}.
     * Inizializza la vista e il modello per la gestione degli hackathon.
     *
     * @param view La vista per la registrazione dell'utente.
     */
    public UserController(UserRegistrationView view) {
        this.view = view;
        this.model = HackathonModel.getInstance();
    }

    /**
     * Registra un utente all'interno di un hackathon.
     * Verifica se l'hackathon esiste, se le iscrizioni sono aperte e se il nome utente è già registrato.
     * Se tutto è valido, l'utente viene registrato e associato all'hackathon.
     *
     * @param nomeUtente Il nome dell'utente da registrare.
     * @param titoloHackathon Il titolo dell'hackathon a cui l'utente si registra.
     */
    public void registraUtente(String nomeUtente, String titoloHackathon) {
        HackaThon hackathon = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);
        if (hackathon == null) {
            JOptionPane.showMessageDialog(view, "Hackathon non trovato.");
            return;
        }

        Utente utente = new Utente(nomeUtente);
        if (!hackathon.getAperturaIscrizioni()) {
            JOptionPane.showMessageDialog(view, "Le registrazioni non sono aperte.");
            return;
        }

        boolean successo = hackathon.registraUtente(utente);
        if (successo) {
            utente.setRegistrato(true);
            UtenteModel.getInstance().salva(utente);

            //  salva nella nuova tabella
            PostgresUtenteDAO dao = new PostgresUtenteDAO();
            dao.salvaAssociazioneUtenteHackathon(utente.getNome(), hackathon.getTitoloIdentificativo());

            JOptionPane.showMessageDialog(view, "Registrazione avvenuta con successo!");
        } else {
            JOptionPane.showMessageDialog(view, "Errore: nome utente già registrato.");
        }
    }

    /**
     * Carica la lista degli hackathon disponibili e li mostra nella vista.
     */
    public void caricaHackathonDisponibili() {
        ArrayList<HackaThon> lista = model.getTutti();
        ArrayList<String> titoli = new ArrayList<>();
        for (HackaThon h : lista) {
            titoli.add(h.getTitoloIdentificativo());
        }
        view.setHackathonList(titoli);
    }


    /**
     * Torna alla schermata principale (Home).
     */
    public void ritornaHome(){
       view.setVisible(false);
        new HomeView();
    }
}


