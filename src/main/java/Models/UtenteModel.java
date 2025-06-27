package Models;

import dao.UtenteDAO;
import PostgresDAO.PostgresUtenteDAO;
import Entity.Utente;
import java.util.List;

public class UtenteModel {
    private static UtenteModel instance;
    private UtenteDAO utenteDAO = new PostgresUtenteDAO();

    private UtenteModel() {}

    public static UtenteModel getInstance() {
        if (instance == null) {
            instance = new UtenteModel();
        }
        return instance;
    }

    public void salva(Utente utente) {
        utenteDAO.salvaUtente(utente);
    }

    public Utente trova(String nome) {
        return utenteDAO.trovaPerNome(nome);
    }

    public List<Utente> trovaPerHackathon(String titoloHackathon) {
        return utenteDAO.trovaUtentiPerHackathon(titoloHackathon);
    }

    public boolean isGiudice(String nomeUtente) {
        return ((PostgresUtenteDAO) utenteDAO).isGiudice(nomeUtente);
    }

    public boolean haGiaUnTeamNellHackathon(String nomeUtente, String titoloHackathon) {
        return ((PostgresUtenteDAO) utenteDAO).utenteHaGiaUnTeamNellHackathon(nomeUtente, titoloHackathon);
    }




}

