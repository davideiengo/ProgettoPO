package Controllers;

import Entity.Giudice;
import Entity.HackaThon;
import Entity.Utente;
import Models.HackathonModel;
import Models.GiudiceModel;
import View.SelezioneGiudiceView;
import Models.UtenteModel;




import javax.swing.*;
/**
 * La classe {@code SelezioneGiudiceController} gestisce la selezione del giudice per un hackathon.
 * Permette di selezionare un utente registrato e associarlo come giudice a un hackathon.
 */
public class SelezioneGiudiceController {

    /** Vista per la selezione del giudice */
    private SelezioneGiudiceView view;

    /**
     * Costruttore della classe {@code SelezioneGiudiceController}.
     * Inizializza la vista e popola la combo box con i titoli degli hackathon.
     *
     * @param view La vista per la selezione del giudice.
     */
    public SelezioneGiudiceController(SelezioneGiudiceView view) {
        this.view = view;
        popolaHackathonCombo();
        view.getComboHackathon().addActionListener(e -> aggiornaUtentiRegistrati());
    }

    /**
     * Popola la combo box con i titoli di tutti gli hackathon registrati.
     */
    private void popolaHackathonCombo() {
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            view.getComboHackathon().addItem(h.getTitoloIdentificativo());
        }
    }

    /**
     * Aggiorna la lista degli utenti registrati per un hackathon selezionato.
     * Mostra solo gli utenti che sono effettivamente registrati.
     */
    private void aggiornaUtentiRegistrati() {
        view.getComboUtenti().removeAllItems();
        String titolo = (String) view.getComboHackathon().getSelectedItem();
        if (titolo == null) return;

        // Legge i registrati dal DB
        for (Utente u : UtenteModel.getInstance().trovaPerHackathon(titolo)) {
            if (u.getRegistrato()) {               // mostro solo chi è realmente registrato
                view.getComboUtenti().addItem(u.getNome());
            }
        }
    }

    /**
     * Conferma la selezione di un utente come giudice per l'hackathon selezionato.
     * Crea un oggetto {@code Giudice} e lo associa all'hackathon.
     *
     * @throws IllegalArgumentException Se uno dei campi è vuoto o non valido.
     */
    public void confermaGiudice() {
        String hackathonTitolo = (String) view.getComboHackathon().getSelectedItem();
        String utenteNome      = (String) view.getComboUtenti().getSelectedItem();
        String password        = view.getTxtPassword().getText();

        if (hackathonTitolo == null || utenteNome == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Errore: seleziona un hackathon, un utente e inserisci la password.");
            return;
        }

        // Recupera l’hackathon (serve per salvare il giudice)
        HackaThon hackathon = HackathonModel.getInstance().getHackathonByTitolo(hackathonTitolo);

        // Recupera l’utente direttamente dal DB
        Utente utente = UtenteModel.getInstance()
                .trovaPerHackathon(hackathonTitolo)    // SELECT sul DB
                .stream()
                .filter(u -> u.getNome().equalsIgnoreCase(utenteNome))
                .findFirst()
                .orElse(null);

        if (utente == null) {
                JOptionPane.showMessageDialog(view, "Errore: utente non trovato tra i registrati.");
            return;
        }

        // Crea il giudice e lo salva
        Giudice giudice = new Giudice(utente);
        GiudiceModel.getInstance().aggiungiGiudice(giudice, hackathon, password);

        JOptionPane.showMessageDialog(view, "Giudice aggiunto con successo all'hackathon!");
        view.dispose();
    }
}

