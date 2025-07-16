package Controllers;

import Models.GiudiceModel;
import Models.HackathonModel;
import View.HomeView;
import View.LoginGiudiceView;
import Entity.Giudice;
import Entity.HackaThon;
import java.util.List;


import javax.swing.*;

/**
 * La classe {@code LoginGiudiceController} gestisce la logica di accesso dei giudici alla piattaforma.
 * Permette di eseguire il login e di navigare verso la vista di valutazione team una volta autenticato.
 */

public class LoginGiudiceController {


    private LoginGiudiceView view;

    /**
     * Costruttore della classe {@code LoginGiudiceController}.
     * Inizializza la vista e imposta gli ascoltatori per gli eventi.
     *
     * @param view Vista di login del giudice.
     */
    public LoginGiudiceController(LoginGiudiceView view) {
        this.view = view;
        inizializza();
    }

    /**
     * Esegue il login del giudice.
     * Verifica che tutti i campi siano compilati, che il giudice esista per l'{@code HackaThon} selezionato
     * e che la password sia corretta. In caso di successo, apre la vista di valutazione.
     */
    public void eseguiLogin() {
        String titoloHackathon = (String) view.getComboHackathon().getSelectedItem();
        String nomeGiudice     = (String) view.getComboGiudici().getSelectedItem();
        String password        = new String(view.getTxtPassword().getPassword());

        if (titoloHackathon == null || nomeGiudice == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Compila tutti i campi.");
            return;
        }

        //  Recupero giudici dal DB
        List<Giudice> lista = GiudiceModel.getInstance().trovaPerHackathon(titoloHackathon);
        Giudice giudiceSel = lista.stream()
                .filter(g -> g.getNome().equalsIgnoreCase(nomeGiudice))
                .findFirst()
                .orElse(null);

        if (giudiceSel == null) {
            JOptionPane.showMessageDialog(view, "Errore: Giudice non trovato per questa hackathon.");
            return;
        }

        //  Verifica password
        if (!GiudiceModel.getInstance().verificaPassword(giudiceSel, password)) {
            JOptionPane.showMessageDialog(view, "Password errata.");
            return;
        }

        //  Se il login è ok apro la schermata di valutazione
        HackaThon hackathon = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);
        JOptionPane.showMessageDialog(view, "Accesso effettuato con successo!");
        new ValutazioneTeamController(hackathon.getTitoloIdentificativo(), giudiceSel);
        view.dispose();
    }

    /**
     * Inizializza i dati necessari per il login e configura gli ascoltatori di eventi.
     */
    private void inizializza() {
        caricaHackathon();

        view.getComboHackathon().addActionListener(e -> {
            String selezionato = (String) view.getComboHackathon().getSelectedItem();
            if (selezionato != null) {
                caricaGiudiciPerHackathon(selezionato);
            }
        });

        view.getBtnAccedi().addActionListener(e -> {
            String titoloHackathon = (String) view.getComboHackathon().getSelectedItem();
            String nomeGiudice = (String) view.getComboGiudici().getSelectedItem();
            String password = new String(view.getTxtPassword().getPassword());

            if (titoloHackathon == null || nomeGiudice == null || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Compila tutti i campi.");
                return;
            }

            HackaThon hackathon = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);
            if (hackathon != null) {
                for (Giudice g : hackathon.getGiudici()) {
                    if (g.getNome().equalsIgnoreCase(nomeGiudice)) {
                        if (GiudiceModel.getInstance().verificaPassword(g, password)) {
                            JOptionPane.showMessageDialog(view, "Accesso effettuato con successo!");
                            new ValutazioneTeamController(hackathon.getTitoloIdentificativo(), g);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(view, "Password errata.");
                            return;
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(view, "Errore: Giudice non trovato per questa hackathon.");
        });
    }



    /**
     * Carica tutti gli {@code HackaThon} nella combo box per la selezione.
     */
    public void caricaHackathon() {
        view.getComboHackathon().removeAllItems();
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            view.getComboHackathon().addItem(h.getTitoloIdentificativo());
        }
    }


    /**
     * Carica tutti i giudici associati a un {@code HackaThon} selezionato nella combo box.
     *
     * @param titoloHackathon Il titolo dell'{@code HackaThon} per cui caricare i giudici.
     */
    public void caricaGiudiciPerHackathon(String titoloHackathon) {
        view.getComboGiudici().removeAllItems();
        for (Giudice g : GiudiceModel.getInstance().trovaPerHackathon(titoloHackathon)) {
            view.getComboGiudici().addItem(g.getNome());
        }
    }

    /**
     * Torna alla schermata principale (Home).
     * Chiude la vista di login e riapre la vista home.
     */
    public void tornaHome() {
        view.dispose();
        new HomeView(); // Riapre la home
    }
}
