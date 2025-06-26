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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente other = (Utente) o;
        return nome != null && nome.equalsIgnoreCase(other.nome);
    }

    @Override
    public int hashCode() {
        return nome == null ? 0 : nome.toLowerCase().hashCode();
    }

}
