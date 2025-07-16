package Entity;

import java.util.HashSet;
/**
 * La classe {@code TeamRegistry} è un registro che gestisce i nomi dei team.
 * Permette di registrare nuovi team e verificare se un team esiste già nel registro.
 */
public class TeamRegistry {
    private static HashSet<String> nomiTeam = new HashSet<>();

    /**
     * Registra un nuovo team nel registro, aggiungendo il nome del team.
     * Il nome del team viene memorizzato in minuscolo per evitare duplicati causati
     * dalla differenza di maiuscole/minuscole.
     *
     * @param nome Il nome del team da registrare.
     * @return {@code true} se il team è stato registrato con successo, {@code false} se esiste già.
     */
    public static boolean registraTeam(String nome) {
        return nomiTeam.add(nome.toLowerCase()); // aggiunge solo se non esiste già
    }

    /**
     * Verifica se un team con il nome specificato esiste già nel registro.
     *
     * @param nome Il nome del team da cercare nel registro.
     * @return {@code true} se il team esiste nel registro, {@code false} altrimenti.
     */
    public static boolean esisteTeam(String nome) {
        return nomiTeam.contains(nome.toLowerCase());
    }
}
