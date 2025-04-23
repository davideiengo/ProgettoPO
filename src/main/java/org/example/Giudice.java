package org.example;
public class Giudice extends Utente {

    private int voto;

    public void creaProblema(HackaThon hackaThon, Utente utente) {
        if(utente.getGiudice() && hackaThon.getAppartiene()){
            System.out.println("Ecco il problema per l'hackaThon: " + hackaThon.getTitoloIdentificativo());
        }else{
            System.out.println("Soltanto l'utente Giudice può pubblicare il problema, o non appartiene all'hackhaton");
        }
    }
    public void sceltaVoto(Team team, int voto, Utente utente, HackaThon hackaThon){
        if(utente.getGiudice() && hackaThon.getAppartiene()) {
            this.voto = voto;
            team.setVotoFinale(voto);
        }else{
            System.out.println("Soltanto i giudici posso selezionare i spaccimma di voti froci del cazzo voi non potete");
        }
    }
}
