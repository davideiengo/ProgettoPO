package Entity;

/**
 * La classe {@code Utente} rappresenta un utente con un nome e uno stato di registrazione.
 * Ogni utente ha un nome che può essere impostato e un flag che indica se è registrato.
 */
public class Utente {

    private String nome;
    private boolean registrato = false;


    /**
     * Costruisce un nuovo {@code Utente} con il nome fornito.
     *
     * @param nome Il nome dell'utente.
     */
    public Utente(String nome){
        this.nome = nome;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome Il nome da assegnare all'utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return Il nome dell'utente.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Imposta lo stato di registrazione dell'utente.
     *
     * @param registrato Lo stato di registrazione da assegnare all'utente.
     */
    public void setRegistrato(boolean registrato) {
        this.registrato = registrato;
    }


    /**
     * Restituisce lo stato di registrazione dell'utente.
     *
     * @return {@code true} se l'utente è registrato, {@code false} altrimenti.
     */
    public boolean getRegistrato() {
        return this.registrato;
    }

    /**
     * Confronta questo {@code Utente} con un altro oggetto.
     * Il confronto avviene sulla base del nome dell'utente, ignorando la differenza tra maiuscole e minuscole.
     *
     * @param o L'oggetto da confrontare con questo {@code Utente}.
     * @return {@code true} se i due utenti hanno lo stesso nome, {@code false} altrimenti.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente other = (Utente) o;
        return nome != null && nome.equalsIgnoreCase(other.nome);
    }

    /**
     * Restituisce il codice hash per questo {@code Utente}.
     * Il codice hash viene calcolato in base al nome dell'utente, ignorando la differenza tra maiuscole e minuscole.
     *
     * @return Il codice hash dell'utente.
     */
    @Override
    public int hashCode() {
        return nome == null ? 0 : nome.toLowerCase().hashCode();
    }

}
