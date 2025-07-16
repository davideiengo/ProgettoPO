package View;

import Entity.Giudice;
import Entity.Team;
import Entity.Utente;
import Entity.HackaThon;
import Models.UtenteModel;
import java.util.List;

import javax.swing.*;
import java.awt.*;
/**
 * Vista per la selezione dei membri di un team per un hackathon.
 * Consente di visualizzare una lista di utenti registrati e di aggiungerli al team selezionato.
 */
public class TeamMembersSelectionView extends JFrame {
    private JPanel panel;
    private JList<String> listaUtenti;
    private JButton btnAggiungi;
    private DefaultListModel<String> modelList;

    /**
     * Costruttore per la visualizzazione della selezione dei membri del team.
     * Carica la lista degli utenti registrati e non ancora assegnati ad altri team,
     * per permettere all'organizzatore di selezionarli e aggiungerli al team.
     *
     * @param team Il team a cui aggiungere i membri.
     * @param hackathon L'hackathon per il quale si stanno selezionando i membri.
     */
    public TeamMembersSelectionView(Team team, HackaThon hackathon) {
        setTitle("Selezione Membri Team");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panel = new JPanel(new BorderLayout());
        modelList = new DefaultListModel<>();

        // Ottieni lista di utenti già in un team
        List<Utente> utentiGiaInTeam = hackathon.getUtentiInTeam();

        // Carica dal DB solo gli utenti registrati a questa hackathon
        List<Utente> tuttiUtenti = UtenteModel.getInstance().trovaPerHackathon(hackathon.getTitoloIdentificativo());

        // Popola solo utenti non ancora assegnati ad altri team, non giudici, e registrati
        for (Utente u : tuttiUtenti) {
            if (!utentiGiaInTeam.stream().anyMatch(t -> t.getNome().equals(u.getNome()))
                    && !(u instanceof Giudice)
                    && u.getRegistrato()) {
                modelList.addElement(u.getNome());
            }
        }


        listaUtenti = new JList<>(modelList);
        panel.add(new JScrollPane(listaUtenti), BorderLayout.CENTER);

        btnAggiungi = new JButton("Aggiungi al Team");
        btnAggiungi.addActionListener(e -> {
            String selected = listaUtenti.getSelectedValue();
            if (selected == null) return;

            // Carica l’utente selezionato dal DB
            Utente u = Models.UtenteModel.getInstance().trova(selected);
            if (u != null) {
                boolean successo = team.aggiungiUtente(u);
                if (successo) {
                    JOptionPane.showMessageDialog(this, "Utente aggiunto!");
                    modelList.removeElement(selected);
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: impossibile aggiungere l'utente.\nMotivi possibili:\n- Team pieno\n- Utente non registrato\n- L'utente è un giudice\n- L'utente è già registrato in un altro team");
                }
            }
        });


        panel.add(btnAggiungi, BorderLayout.SOUTH);
        setContentPane(panel);
        setVisible(true);
    }
}
