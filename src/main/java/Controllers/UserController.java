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
        HackaThon h = model.getHackathonByTitolo(titoloHackathon);
        if (h != null) {
            Utente u = new Utente(nomeUtente);
            u.effettuaRegistrazione(h);
            JOptionPane.showMessageDialog(view, "Utente registrato all’hackathon: " + h.getTitoloIdentificativo());
        } else {
            JOptionPane.showMessageDialog(view, "Hackathon non trovato");
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


