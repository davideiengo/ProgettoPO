package Controllers;

import View.AreaGiudiceView;

public class AreaGiudiceController {
    private AreaGiudiceView view;

    public AreaGiudiceController(String nomeGiudice, String titoloHackathon) {
        this.view = new AreaGiudiceView(nomeGiudice, titoloHackathon);
        this.view.setVisible(true);
    }
}

