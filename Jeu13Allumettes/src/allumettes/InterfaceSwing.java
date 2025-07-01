package allumettes;

import java.awt.*;
import javax.swing.*;

public class InterfaceSwing extends JFrame {
    private static final long serialVersionUID = 1L;

    private JButton[] boutons;
    private JButton boutonTricher;
    private JTextField champTriche;
    private JLabel labelAllumettes;
    private final Object verrou;
    private int choix;
    private boolean triche;
    private int nbTriche;
    private boolean choixFait;

    public InterfaceSwing(String nomJoueur, int nbAllumettes, Object verrou) {
        super(nomJoueur + " ?");
        this.verrou = verrou;
        this.choix = 0;
        this.triche = false;
        this.nbTriche = 1;
        this.choixFait = false;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Panel principal vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Label nombre d'allumettes (gros, centré)
        labelAllumettes = new JLabel(String.valueOf(nbAllumettes));
        labelAllumettes.setFont(new Font("Arial", Font.BOLD, 48));
        labelAllumettes.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(labelAllumettes);
        mainPanel.add(Box.createVerticalStrut(10));

        // Panel horizontal pour tricher + champ
        JPanel trichePanel = new JPanel();
        trichePanel.setLayout(new BoxLayout(trichePanel, BoxLayout.X_AXIS));
        boutonTricher = new JButton("tricher");
        boutonTricher.setFocusable(false);
        boutonTricher.addActionListener(e -> {
            synchronized (verrou) {
                triche = true;
                try {
                    nbTriche = Integer.parseInt(champTriche.getText());
                } catch (NumberFormatException ex) {
                    nbTriche = 1;
                }
                choixFait = true;
                verrou.notify();
            }
        });
        champTriche = new JTextField("1", 2);
        trichePanel.add(boutonTricher);
        trichePanel.add(Box.createHorizontalStrut(5));
        trichePanel.add(champTriche);
        trichePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(trichePanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Panel horizontal pour les boutons 1, 2, 3
        JPanel boutonsPanel = new JPanel();
        boutonsPanel.setLayout(new BoxLayout(boutonsPanel, BoxLayout.X_AXIS));
        boutons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            boutons[i] = new JButton(String.valueOf(i + 1));
            boutons[i].setFocusable(false);
            final int nb = i + 1;
            boutons[i].addActionListener(e -> {
                synchronized (verrou) {
                    choix = nb;
                    choixFait = true;
                    verrou.notify();
                }
            });
            boutonsPanel.add(boutons[i]);
            if (i < 2) boutonsPanel.add(Box.createHorizontalStrut(10));
        }
        boutonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(boutonsPanel);

        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void afficher(int nbAllumettes, String message) {
        labelAllumettes.setText(String.valueOf(nbAllumettes));
        for (int i = 0; i < 3; i++) {
            boutons[i].setEnabled((i + 1) <= nbAllumettes);
        }
        boutonTricher.setEnabled(nbAllumettes > 1 || nbAllumettes == 1);
        champTriche.setEnabled(nbAllumettes > 1);
    }

    public int attendreChoix() {
        choix = 0;
        triche = false;
        choixFait = false;
        setVisible(true);
        synchronized (verrou) {
            while (!choixFait) {
                try {
                    verrou.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return choix;
    }

    public boolean aTriche() {
        return triche;
    }

    public int getNbTriche() {
        return nbTriche;
    }

    public void reset() {
        choix = 0;
        triche = false;
        choixFait = false;
    }

    public void fermer() {
        dispose();
    }
}