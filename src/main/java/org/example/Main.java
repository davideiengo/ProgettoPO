package org.example;


import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Organizzatore organizzatoreMarco = new Organizzatore("Marco");
        HackaThon hackaThonFrancia = new HackaThon(1, organizzatoreMarco, "ProvaCompetition");
        Giudice giudice1 = new Giudice();
        hackaThonFrancia.permettiIscrizioni(organizzatoreMarco);
        Team tem1 = new Team(1, "Feccia");
        Team tem2 = new Team(1, "Feccia2");
        Team tem3 = new Team(1, "Feccia3");
        hackaThonFrancia.registraTeam(tem1);
        hackaThonFrancia.registraTeam(tem2);
        hackaThonFrancia.registraTeam(tem3);
        Utente utente1 = new Utente();
        Utente utente2 = new Utente();
        Utente utente3 = new Utente();
        Utente utente4 = new Utente();
        utente2.effettuaRegistrazione(hackaThonFrancia);
        utente3.effettuaRegistrazione(hackaThonFrancia);
        utente4.effettuaRegistrazione(hackaThonFrancia);
        utente1.effettuaRegistrazione(hackaThonFrancia);
        organizzatoreMarco.sceltaGiudice(utente1, hackaThonFrancia);
        giudice1.creaProblema(hackaThonFrancia, utente1);
        giudice1.sceltaVoto(tem1, 5, utente1, hackaThonFrancia);
        giudice1.sceltaVoto(tem2, 1, utente1, hackaThonFrancia);
        hackaThonFrancia.pubblicaClassifica();
    }
}
