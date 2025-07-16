package Entity;

import java.util.HashMap;
import java.util.HashSet;
/**
 * La classe <code>HackathonRegistry</code> gestisce la registrazione e la verifica degli hackathon nel sistema.
 * Mantiene un registro dei titoli degli hackathon per evitare duplicati e un map per associare il titolo a un oggetto <code>HackaThon</code>.
 */
public class HackathonRegistry {
    private static HashSet<String> titoliHackathon = new HashSet<>();
    private static HashMap<String, HackaThon> hackathonMap = new HashMap<>();

    /**
     * Registra un nuovo hackathon con il titolo specificato.
     * Il titolo dell'hackathon viene aggiunto al set per garantire che non ci siano duplicati.
     *
     * @param titolo Il titolo identificativo dell'hackathon da registrare.
     * @return <code>true</code> se il titolo è stato aggiunto con successo, <code>false</code> se il titolo era già presente.
     */
    public static boolean registraHackathon(String titolo) {
        if (titolo == null) return false;
        return titoliHackathon.add(titolo.toLowerCase());
    }

    /**
     * Verifica se esiste già un hackathon con il titolo specificato.
     *
     * @param titolo Il titolo identificativo dell'hackathon da verificare.
     * @return <code>true</code> se esiste un hackathon con quel titolo, <code>false</code> altrimenti.
     */
    public static boolean esisteHackathon(String titolo) {
        return titoliHackathon.contains(titolo.toLowerCase());
    }

    /**
     * Aggiunge un oggetto <code>HackaThon</code> al registro, associandolo al suo titolo identificativo.
     *
     * @param hackathon L'oggetto <code>HackaThon</code> da aggiungere al registro.
     */
    public static void aggiungiHackathon(HackaThon hackathon) {
        if (hackathon != null) {
            String titolo = hackathon.getTitoloIdentificativo().toLowerCase();
            hackathonMap.put(titolo, hackathon);
        }
    }
}

