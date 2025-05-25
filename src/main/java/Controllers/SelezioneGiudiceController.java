package Controllers;

import Entity.Giudice;
import Entity.HackaThon;
import Entity.Utente;
import Models.HackathonModel;
import Models.GiudiceModel;
import View.SelezioneGiudiceView;

import javax.swing.*;

public class SelezioneGiudiceController {
    private SelezioneGiudiceView view;

    public SelezioneGiudiceController(SelezioneGiudiceView view) {
        this.view = view;
        popolaHackathonCombo();
        view.getComboHackathon().addActionListener(e -> aggiornaUtentiRegistrati());
    }

    private void popolaHackathonCombo() {
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            view.getComboHackathon().addItem(h.getTitoloIdentificativo());
        }
    }

    private void aggiornaUtentiRegistrati() {
        view.getComboUtenti().removeAllItems();
        String titolo = (String) view.getComboHackathon().getSelectedItem();
        HackaThon h = HackathonModel.getInstance().getHackathonByTitolo(titolo);
        if (h != null) {
            for (Utente u : h.getUtentiRegistrati()) {
                view.getComboUtenti().addItem(u.getNome());
            }
        }
    }

    public void confermaGiudice() {
        String hackathonTitolo = (String) view.getComboHackathon().getSelectedItem();
        String utenteNome = (String) view.getComboUtenti().getSelectedItem();
        String password = view.getTxtPassword().getText();

        HackaThon h = HackathonModel.getInstance().getHackathonByTitolo(hackathonTitolo);
        if (h == null || utenteNome == null || utenteNome.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Errore: seleziona un hackathon e un utente valido.");
            return;
        }

        // Cerca l'oggetto Utente reale dalla lista di registrati
        Utente utente = null;
        for (Utente u : h.getUtentiRegistrati()) {
            if (u.getNome().equalsIgnoreCase(utenteNome)) {
                utente = u;
                break;
            }
        }

        if (utente == null) {
            JOptionPane.showMessageDialog(view, "Errore: utente non trovato tra i registrati.");
            return;
        }

        Giudice g = new Giudice(utente);
        GiudiceModel.getInstance().aggiungiGiudice(g, h, password);

        JOptionPane.showMessageDialog(view, "Giudice aggiunto con successo all'hackathon!");
        view.dispose();
    }
}

