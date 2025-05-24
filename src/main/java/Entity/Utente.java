package Entity;

public class Utente {

    private String nome;
    private boolean registrato = false;

    public Utente(String nome){
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setRegistrato(boolean registrato) {
        this.registrato = registrato;
    }

    public void effettuaRegistrazione(HackaThon hackaThon) {
        if (hackaThon.getAperturaIscrizioni()) {
            boolean successo = hackaThon.registraUtente(this);
            if (successo) {
                this.registrato = true;
            } else {
                System.out.println("Registrazione fallita: nome utente già esistente.");
            }
        } else {
            System.out.println("Le registrazioni non sono aperte, attendi che l'organizzatore le apra");
        }
    }


    public boolean getRegistrato() {
        return this.registrato;
    }
}
