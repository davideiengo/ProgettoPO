package Controllers;
import View.HomeView;
import View.OrganizzatoreView;

public class HomeController {

    private HomeView homeView;


    public HomeController(HomeView homeView1){
        this.homeView = homeView1;
    }

    public void apriOrganizzatore(){
        homeView.setVisible(false);
        new OrganizzatoreView();
    }

    public void apriRegistrazioneUtente() {
        homeView.setVisible(false);
        new View.UserRegistrationView();
    }

    public void apriLoginGiudice() {
        homeView.setVisible(false);
        new View.LoginGiudiceView();
    }
}

