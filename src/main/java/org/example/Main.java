package org.example;


import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Organizzatore organizzatoreMarco = new Organizzatore("Marco");
        Organizzatore organizzatoreSergio = new Organizzatore("Marco");

        HackaThon hackaThonFrancia = new HackaThon(5, organizzatoreMarco, "Test1");
        HackaThon hackaThonBelgio = new HackaThon(5, organizzatoreSergio, "Test1");

        organizzatoreSergio.aperturaRegistrazioni(hackaThonBelgio);
        organizzatoreMarco.aperturaRegistrazioni(hackaThonFrancia);

        Team tem1 = new Team(hackaThonFrancia, "Tem1");

        Utente utente4 = new Utente("Marco");
        Utente utente3 = new Utente("Marco");
        Utente utente1 = new Utente("Marco");
        Utente utente2 = new Utente("Francesco");

        Giudice giudice1 = new Giudice("Marco");
        Giudice giudice2 = new Giudice("Ferdinando");

        giudice1.effettuaRegistrazione(hackaThonFrancia);
        giudice2.effettuaRegistrazione(hackaThonFrancia);

        organizzatoreMarco.sceltaGiudice(giudice1, hackaThonFrancia);
        organizzatoreMarco.sceltaGiudice(giudice2, hackaThonFrancia);

        utente2.effettuaRegistrazione(hackaThonFrancia);
        utente1.effettuaRegistrazione(hackaThonFrancia);

        tem1.aggiungiUtente(utente1);
        tem1.aggiungiUtente(utente2);

        hackaThonFrancia.registraTeam(tem1);
        giudice1.sceltaVoto(tem1, 5, hackaThonFrancia);
        giudice2.sceltaVoto(tem1, 5, hackaThonFrancia);

        hackaThonFrancia.pubblicaClassifica();
    }
}
