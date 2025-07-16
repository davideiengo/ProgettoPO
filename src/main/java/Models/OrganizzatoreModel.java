package Models;

import Entity.HackaThon;
import Entity.Organizzatore;

import java.util.ArrayList;

/**
 * Classe modello che gestisce la logica di business relativa agli {@code Organizzatore} e {@code HackaThon}.
 */
public class OrganizzatoreModel {
    public static ArrayList<Organizzatore> organizzatori = new ArrayList<>();

    /**
     * Crea un nuovo {@code Organizzatore} e un {@code HackaThon} con le informazioni fornite.
     * Se esiste già un {@code HackaThon} con lo stesso titolo, la creazione fallisce e restituisce {@code null}.
     *
     * @param nome Il nome dell'organizzatore.
     * @param titolo Il titolo dell'{@code HackaThon}.
     * @param dimensioneMaxTeam La dimensione massima per i team nell'{@code HackaThon}.
     * @return Un nuovo {@code HackaThon} con l'organizzatore, oppure {@code null} se il titolo è duplicato.
     */
    public HackaThon creaOrganizzatoreEHackathon(String nome, String titolo, int dimensioneMaxTeam) {
        if (HackathonModel.getInstance().esisteHackathonConTitolo(titolo)) {
            return null;  // titolo duplicato, segnala fallimento
        }
        Organizzatore o = new Organizzatore(nome);
        organizzatori.add(o);
        return new HackaThon(dimensioneMaxTeam, o, titolo);
    }
}

