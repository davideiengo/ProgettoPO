package View;

import Entity.Team;
import Entity.Utente;
import Entity.HackaThon;

import javax.swing.*;
import java.awt.*;

public class TeamMembersSelectionView extends JFrame {
    private JPanel panel;
    private JList<String> listaUtenti;
    private JButton btnAggiungi;
    private DefaultListModel<String> modelList;

    public TeamMembersSelectionView(Team team, HackaThon hackathon) {
        setTitle("Selezione Membri Team");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panel = new JPanel(new BorderLayout());
        modelList = new DefaultListModel<>();

        for (Utente u : hackathon.getUtentiRegistrati()) {
            modelList.addElement(u.getNome());
        }

        listaUtenti = new JList<>(modelList);
        panel.add(new JScrollPane(listaUtenti), BorderLayout.CENTER);

        btnAggiungi = new JButton("Aggiungi al Team");
        btnAggiungi.addActionListener(e -> {
            String selected = listaUtenti.getSelectedValue();
            for (Utente u : hackathon.getUtentiRegistrati()) {
                if (u.getNome().equals(selected)) {
                    boolean successo = team.aggiungiUtente(u);
                    if (successo) {
                        JOptionPane.showMessageDialog(this, "Utente aggiunto!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Errore: impossibile aggiungere l'utente.\nMotivi possibili:\n- Team pieno\n- Utente non registrato\n- L'utente è un giudice\n- L'utente è già registrato in un altro team");
                    }
                    break;
                }
            }
        });


        panel.add(btnAggiungi, BorderLayout.SOUTH);
        setContentPane(panel);
        setVisible(true);
    }
}
