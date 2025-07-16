package Entity;

import View.HomeView;

/**
 * La classe <code>Main</code> è il punto di ingresso dell'applicazione. Contiene il metodo <code>main</code>,
 * che avvia l'interfaccia utente principale creando una nuova vista per la home.
 */
public class Main{
    /**
     * Il metodo <code>main</code> è il punto di partenza dell'applicazione. Crea un'istanza della vista principale dell'applicazione.
     */
    public static void main(String[] args){
        // Creazione della vista principale dell'applicazione
        HomeView homeView = new HomeView();
    }
}

