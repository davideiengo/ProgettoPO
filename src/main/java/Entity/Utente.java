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

    public boolean getRegistrato() {
        return this.registrato;
    }
}
