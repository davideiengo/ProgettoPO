package Entity;

import View.HomeView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Imposta il look and feel di sistema (opzionale)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Errore nel settaggio del look and feel: " + e.getMessage());
        }

        // Avvia l'interfaccia grafica nella thread grafico
        SwingUtilities.invokeLater(() -> {
            HomeView homeView = new HomeView();
            homeView.setVisible(true);  // Assicurati che HomeView estenda JFrame o simile
        });
    }
}

