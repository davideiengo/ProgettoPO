package dao;

import Entity.Utente;
import java.util.List;

public interface UtenteDAO {
    void salvaUtente(Utente utente);
    Utente trovaPerNome(String nome);
    List<Utente> trovaTutti();
    void aggiorna(Utente utente);
    void elimina(String nome);

    // 👉 AGGIUNGI QUESTO
    List<Utente> trovaUtentiPerHackathon(String titoloHackathon);
}


