package Controllers;
import View.HomeView;
import View.OrganizzatoreView;

/**
 * La classe {@code HomeController} gestisce la logica di navigazione per la schermata principale.
 * Permette di aprire diverse viste, come quella dell'organizzatore, della registrazione utente e del login del giudice.
 */

public class HomeController {


    private HomeView homeView;

    /**
     * Costruttore della classe {@code HomeController}.
     *
     * @param homeView1 Vista principale da associare al controller.
     */
    public HomeController(HomeView homeView1){
        this.homeView = homeView1;
    }


    /**
     * Apre la vista dell'organizzatore, nascondendo la vista principale.
     */
    public void apriOrganizzatore(){
        homeView.setVisible(false);
        new OrganizzatoreView();
    }

    /**
     * Apre la vista di registrazione utente, nascondendo la vista principale.
     */
    public void apriRegistrazioneUtente() {
        homeView.setVisible(false);
        new View.UserRegistrationView();
    }

    /**
     * Apre la vista di login del giudice, nascondendo la vista principale.
     */
    public void apriLoginGiudice() {
        homeView.setVisible(false);
        new View.LoginGiudiceView();
    }
}

