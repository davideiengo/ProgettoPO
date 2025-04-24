package org.example;

public class Organizzatore {
    private String nomeOrganizzatore;


    public Organizzatore(String nome){
        this.nomeOrganizzatore = nome;
    }

    public String getOrganizzatore(){
        return nomeOrganizzatore;
    }

    public void aperturaRegistrazioni(HackaThon hackaThon){
        hackaThon.permettiIscrizioni(this);
    }

    public void sceltaGiudice(Giudice giudice, HackaThon hackaThon){
        if(giudice.getRegistrato()) {
            hackaThon.setAppartiene();
        }
        else{
            System.out.println("L'utente non è registrato, non puoi decidere un giudice se non sono registrati");
        }
    }
}

