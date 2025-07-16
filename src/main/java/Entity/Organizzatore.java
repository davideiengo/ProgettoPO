package Entity;


/**
 * La classe {@code Organizzatore} rappresenta un organizzatore con un nome.
 * Questa classe permette di gestire il nome dell'organizzatore
 * e fornisce un metodo per accedere al suo nome.
 */
public class Organizzatore {
    private String nomeOrganizzatore;

    /**
     * Costruisce un nuovo oggetto {@code Organizzatore} con il nome fornito.
     *
     * @param nome Il nome dell'organizzatore.
     */
    public Organizzatore(String nome){
            this.nomeOrganizzatore = nome;
        }

    /**
     * Restituisce il nome dell'organizzatore.
     *
     * @return Il nome dell'organizzatore.
     */
    public String getOrganizzatore(){
        return nomeOrganizzatore;
    }
}

