package Controllers;

import Entity.HackaThon;
import Entity.Utente;
import Models.HackathonModel;
import View.HomeView;
import View.UserRegistrationView;
import View.UserRegistrationView;
import javax.swing.*;
import java.util.ArrayList;

public class UserController {
    private UserRegistrationView view;
    private HackathonModel model;

    public UserController(UserRegistrationView view) {
        this.view = view;
        this.model = HackathonModel.getInstance();
    }

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
            JOptionPane.showMessageDialog(view, "Registrazione avvenuta con successo!");
        } else {
            JOptionPane.showMessageDialog(view, "Errore: nome utente già registrato.");
        }
    }

    public void caricaHackathonDisponibili() {
        ArrayList<HackaThon> lista = model.getTutti();
        ArrayList<String> titoli = new ArrayList<>();
        for (HackaThon h : lista) {
            titoli.add(h.getTitoloIdentificativo());
        }
        view.setHackathonList(titoli);
    }

    public void ritornaHome(){
       view.setVisible(false);
        new HomeView();
    }
}


