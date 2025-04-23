package org.example;

public class Utente {

    private String nome;
    private boolean registrato = false;
    private boolean eGiudice = false;

    public void setGiudice(){
        this.eGiudice = true;
    }

    public boolean getGiudice(){
        return this.eGiudice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void effettuaRegistrazione(HackaThon hackaThon){
        if(hackaThon.getAperturaIscrizioni()) {
            hackaThon.registraUtente(this);
            this.registrato = true;
        }
        else{
            System.out.println("Le registrazioni non sono aperte, attendi che l'organizzatore le apra");
        }
    }

    public boolean getRegistrato() {
        return this.registrato;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Utente utente)) return false;
        return eGiudice == utente.eGiudice && this.nome.equals(utente.nome);
    }
}
