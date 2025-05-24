package Controllers;

import Models.GiudiceModel;
import Models.HackathonModel;
import View.HomeView;
import View.LoginGiudiceView;
import View.AreaGiudiceView;
import Entity.Giudice;
import Entity.HackaThon;

import javax.swing.*;

public class LoginGiudiceController {
    private LoginGiudiceView view;

    public LoginGiudiceController(LoginGiudiceView view) {
        this.view = view;
        inizializza();
    }

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
                            new AreaGiudiceView(g.getNome(), hackathon.getTitoloIdentificativo());
                            view.dispose();  // Chiudi il login
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

    public void caricaHackathon() {
        view.getComboHackathon().removeAllItems();
        for (HackaThon h : HackathonModel.getInstance().getTutti()) {
            view.getComboHackathon().addItem(h.getTitoloIdentificativo());
        }
    }

    public void caricaGiudiciPerHackathon(String titoloHackathon) {
        view.getComboGiudici().removeAllItems();
        HackaThon h = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);
        if (h != null) {
            for (Giudice g : h.getGiudici()) {
                view.getComboGiudici().addItem(g.getNome());
            }
        }
    }

    public void tornaHome() {
        view.dispose();
        new HomeView(); // Riapre la home
    }
}
