package allumettes;

import java.awt.*;
import javax.swing.*;

public class InterfaceSwing extends JFrame {
    /** Numèro de version pour la sérialisation. */
    private static final long serialVersionUID = 1L;

    private JButton[] boutons;
    private JTextArea messageArea;
    private JLabel labelAllumettes;
    private Jeu jeu;
    private Joueur joueurOrdinateur;
    private String nomJoueur;
    
    public InterfaceSwing() {
        super("Jeu des 13 allumettes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        // Demander le nom du joueur
        this.nomJoueur = JOptionPane.showInputDialog(this,
            "Entrez votre nom :",
            "Nom du joueur",
            JOptionPane.QUESTION_MESSAGE);
        if (this.nomJoueur == null || this.nomJoueur.trim().isEmpty()) {
            this.nomJoueur = "Joueur";
        }
        
        // Création du jeu
        this.jeu = new JeuAllumettes(13);
        
        // Sélection de la stratégie de l'ordinateur
        Strategie strategieOrdi = choisirStrategie();
        this.joueurOrdinateur = new Joueur("Ordinateur", strategieOrdi);
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Label pour afficher le nombre d'allumettes
        labelAllumettes = new JLabel("Allumettes restantes : 13");
        labelAllumettes.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelAllumettes.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(labelAllumettes);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Zone de texte pour les messages
        messageArea = new JTextArea(10, 30);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Panel pour les boutons
        JPanel buttonPanel = new JPanel();
        boutons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            boutons[i] = new JButton(String.valueOf(i + 1));
            final int nbAllumettes = i + 1;
            boutons[i].addActionListener(e -> jouerCoup(nbAllumettes));
            buttonPanel.add(boutons[i]);
        }
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel);
        
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        
        // Message initial
        afficherMessage("Allumettes restantes : 13");
        afficherMessage(nomJoueur + ", combien d'allumettes ?");
    }
    
    private void afficherMessage(String message) {
        messageArea.append(message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }
    
    private Strategie choisirStrategie() {
        String[] options = {"Rapide", "Naif", "Expert"};
        int choix = JOptionPane.showOptionDialog(this,
            "Choisissez le niveau de l'ordinateur :",
            "Niveau de l'ordinateur",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
            
        switch (choix) {
            case 0:
                return new StrategieRapide();
            case 1:
                return new StrategieNaif();
            case 2:
                return new StrategieExpert();
            default:
                return new StrategieExpert(); // Par défaut, on utilise la stratégie expert
        }
    }
    
    private void jouerCoup(int nbAllumettes) {
        try {
            // Tour du joueur humain
            afficherMessage(nomJoueur + " prend " + nbAllumettes + " allumette" + (nbAllumettes > 1 ? "s" : "") + ".");
            jeu.retirer(nbAllumettes);
            updateInterface();
            
            if (jeu.getNombreAllumettes() == 0) {
                finDePartie(nomJoueur + " perd !");
                return;
            }
            
            // Tour de l'ordinateur
            desactiverBoutons();
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(1000); // Pause pour effet visuel
                    int priseOrdi = joueurOrdinateur.getPrise(jeu);
                    afficherMessage("Ordinateur prend " + priseOrdi + " allumette" + (priseOrdi > 1 ? "s" : "") + ".");
                    jeu.retirer(priseOrdi);
                    updateInterface();
                    
                    if (jeu.getNombreAllumettes() == 0) {
                        finDePartie("Ordinateur perd !");
                        afficherMessage(nomJoueur + " gagne !");
                    } else {
                        activerBoutons();
                        afficherMessage(nomJoueur + ", combien d'allumettes ?");
                    }
                } catch (Exception e) {
                    afficherMessage("Impossible ! " + e.getMessage());
                    activerBoutons();
                }
            });
        } catch (CoupInvalideException e) {
            afficherMessage("Impossible ! " + e.getMessage());
        }
    }
    
    private void updateInterface() {
        String message = "Allumettes restantes : " + jeu.getNombreAllumettes();
        labelAllumettes.setText(message);
        afficherMessage(message);
        for (JButton bouton : boutons) {
            int nb = Integer.parseInt(bouton.getText());
            bouton.setEnabled(nb <= jeu.getNombreAllumettes());
        }
    }
    
    private void desactiverBoutons() {
        for (JButton bouton : boutons) {
            bouton.setEnabled(false);
        }
    }
    
    private void activerBoutons() {
        updateInterface();
    }
    
    private void finDePartie(String message) {
        afficherMessage(message);
        desactiverBoutons();
        
        // Proposer une nouvelle partie
        SwingUtilities.invokeLater(() -> {
            int choix = JOptionPane.showConfirmDialog(this,
                "Voulez-vous faire une nouvelle partie ?",
                "Fin de partie",
                JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                nouvellePartie();
            } else {
                dispose();
            }
        });
    }
    
    private void nouvellePartie() {
        this.jeu = new JeuAllumettes(13);
        // Permettre de changer la stratégie de l'ordinateur
        Strategie strategieOrdi = choisirStrategie();
        this.joueurOrdinateur = new Joueur("Ordinateur", strategieOrdi);
        messageArea.setText(""); // Effacer les messages précédents
        updateInterface();
        activerBoutons();
        afficherMessage(nomJoueur + ", combien d'allumettes ?");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfaceSwing().setVisible(true);
        });
    }
} 