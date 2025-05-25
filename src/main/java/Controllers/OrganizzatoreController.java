package Controllers;

import Entity.HackaThon;
import Models.HackathonModel;
import Models.OrganizzatoreModel;
import View.HomeView;
import View.OrganizzatoreView;
import View.SelezioneGiudiceView;

public class OrganizzatoreController {

    OrganizzatoreView organizzatoreView;
    OrganizzatoreModel model;

    public OrganizzatoreController(OrganizzatoreView organizzatoreView, OrganizzatoreModel organizzatore) {
        this.organizzatoreView = organizzatoreView;
        this.model = organizzatore;
    }

    public HackaThon creaOrganizzatoreEHackathon(String nome, String titolo, int dimMax) {
        return model.creaOrganizzatoreEHackathon(nome, titolo, dimMax);
    }

    public void ritornaHome(){
        organizzatoreView.setVisible(false);
        new HomeView();
    }

    public boolean apriRegistrazioni(String titoloHackathon, String nomeOrganizzatore) {
        HackaThon h = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);
        if (h != null && h.getOrganizzatore().getOrganizzatore().equals(nomeOrganizzatore)) {
            h.permettiIscrizioni(h.getOrganizzatore());
            return true;
        }
        return false;
    }

    public void apriVistaSelezioneGiudice() {
        new SelezioneGiudiceView();
    }



}
