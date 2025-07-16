package Controllers;

import Entity.HackaThon;
import Models.HackathonModel;
import Models.OrganizzatoreModel;
import View.HomeView;
import View.OrganizzatoreView;
import View.SelezioneGiudiceView;

/**
 * La classe {@code OrganizzatoreController} gestisce le operazioni che un organizzatore può effettuare.
 * Permette di creare un {@code HackaThon}, aprire le registrazioni e navigare tra le viste.
 */
public class OrganizzatoreController {

    /** Vista dell'organizzatore */
    OrganizzatoreView organizzatoreView;

    /** Modello dell'organizzatore */
    OrganizzatoreModel model;


    /**
     * Costruttore della classe {@code OrganizzatoreController}.
     * Inizializza la vista dell'organizzatore e il modello.
     *
     * @param organizzatoreView La vista dell'organizzatore.
     * @param organizzatore Il modello dell'organizzatore.
     */
    public OrganizzatoreController(OrganizzatoreView organizzatoreView, OrganizzatoreModel organizzatore) {
        this.organizzatoreView = organizzatoreView;
        this.model = organizzatore;
    }


    /**
     * Crea un {@code Organizzatore} e un {@code HackaThon} con il nome, il titolo e la dimensione massima del team forniti.
     *
     * @param nome Nome dell'organizzatore.
     * @param titolo Titolo identificativo dell'{@code HackaThon}.
     * @param dimMax Dimensione massima dei team nell'{@code HackaThon}.
     * @return Il nuovo {@code HackaThon} creato, o {@code null} se la creazione fallisce.
     */
    public HackaThon creaOrganizzatoreEHackathon(String nome, String titolo, int dimMax) {
        return model.creaOrganizzatoreEHackathon(nome, titolo, dimMax);
    }

    /**
     * Torna alla schermata principale (Home).
     * Chiude la vista dell'organizzatore e riapre la vista home.
     */
    public void ritornaHome(){
        organizzatoreView.setVisible(false);
        new HomeView();
    }

    /**
     * Apre le registrazioni per un {@code HackaThon} se il titolo e il nome dell'organizzatore corrispondono.
     * Se le condizioni sono soddisfatte, consente le iscrizioni e salva lo stato nel database.
     *
     * @param titoloHackathon Il titolo dell'{@code HackaThon}.
     * @param nomeOrganizzatore Il nome dell'organizzatore.
     * @return {@code true} se le registrazioni sono state aperte con successo, {@code false} altrimenti.
     */
    public boolean apriRegistrazioni(String titoloHackathon, String nomeOrganizzatore) {
        HackaThon h = HackathonModel.getInstance().getHackathonByTitolo(titoloHackathon);
        if (h != null && h.getOrganizzatore().getOrganizzatore().equals(nomeOrganizzatore)) {
            h.permettiIscrizioni(h.getOrganizzatore());

            //  Salva il nuovo stato nel database
            HackathonModel.getInstance().aggiornaRegistrazioni(h);

            return true;
        }
        return false;
    }

    /**
     * Apre la vista di selezione dei giudici.
     */
    public void apriVistaSelezioneGiudice() {
        new SelezioneGiudiceView();
    }



}
