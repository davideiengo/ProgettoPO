package View;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Controllers.HomeController;

public class HomeView extends JFrame{
    private JPanel homeView;
    private JButton btnRegistraUtente;
    private JButton btnLoginOrganizzatore;
    private JButton btnGestioneTeam;
    private JButton btnAreaGiudice;
    private JButton diventaOrganizzatoreButton;
    private JButton organizzatore;
    HomeController controller = new HomeController(this);


    public HomeView() {
        setContentPane(homeView);
        setName("HomeView");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

        btnLoginOrganizzatore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.apriOrganizzatore();
            }
        });

        btnRegistraUtente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.apriRegistrazioneUtente();
            }

        });


        btnGestioneTeam.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new TeamCreationView();
            }
        });
        btnAreaGiudice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.apriLoginGiudice();
            }
        });
    }

    public static class Main {
        public static void main(String[] args) {
            new HomeView();
        }
    }
}


