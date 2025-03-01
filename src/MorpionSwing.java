import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** Programmation d'un jeu de Morpion avec une interface graphique Swing.
 *
 * REMARQUE : Dans cette solution, le patron MVC n'a pas été appliqué !
 * On a un modèle (?), une vue et un contrôleur qui sont fortement liés.
 *
 * @author Meriem El Air
 */

public class MorpionSwing {

    // Les images à utiliser en fonction de l'état du jeu.
    private static final Map<ModeleMorpion.Etat, ImageIcon> images = new HashMap<>();
    static {
        images.put(ModeleMorpion.Etat.VIDE, new ImageIcon("blanc.jpg"));
        images.put(ModeleMorpion.Etat.CROIX, new ImageIcon("croix.jpg"));
        images.put(ModeleMorpion.Etat.ROND, new ImageIcon("rond.jpg"));
    }

    private ModeleMorpion modele; // Le modèle du jeu de Morpion

    // Les éléments de la vue (IHM)
    private JFrame fenetre;
    private final JButton boutonQuitter = new JButton("Quitter");
    private final JButton boutonNouvellePartie = new JButton("Nouvelle Partie");
    private final JLabel[][] cases = new JLabel[3][3];

    /** Construire le jeu de morpion */
    public MorpionSwing() {
        this(new ModeleMorpionSimple());
    }

    /** Construire le jeu de morpion */
    public MorpionSwing(ModeleMorpion modele) {
        // Initialiser le modèle
        this.modele = modele;

        // Créer la fenêtre principale
        this.fenetre = new JFrame("Morpion");
        this.fenetre.setLocation(100, 200);
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Construire l'interface utilisateur
        construireInterface();

        // Ajouter les écouteurs d'événements
        ajouterEcouteurs();

        // Ajouter la barre de menu
        ajouterMenu();

        // Afficher la fenêtre
        this.fenetre.pack();
        this.fenetre.setVisible(true);
    }

    /** Construire l'interface graphique */
    private void construireInterface() {
        // Création de la grille du Morpion
        JPanel panelGrille = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cases[i][j] = new JLabel(images.get(ModeleMorpion.Etat.VIDE)); // Case vide au départ
                cases[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                cases[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Ajout de bordures
                panelGrille.add(cases[i][j]);
            }
        }

        // Création du panneau inférieur avec les boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(boutonNouvellePartie);
        panelBoutons.add(boutonQuitter);

        // Ajout des composants à la fenêtre
        fenetre.setLayout(new BorderLayout());
        fenetre.add(panelGrille, BorderLayout.CENTER);
        fenetre.add(panelBoutons, BorderLayout.SOUTH);
    }

    /** Ajouter les écouteurs pour les interactions utilisateur */
    private void ajouterEcouteurs() {
        // Ajout des événements aux boutons
        boutonQuitter.addActionListener(e -> System.exit(0)); // Fermer l'application
        boutonNouvellePartie.addActionListener(e -> recommencer()); // Recommencer la partie

        // Ajout des événements aux cases
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int x = i, y = j; // Variables finales pour éviter les erreurs

                cases[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            modele.cocher(x, y);  // Cocher la case
                            cases[x][y].setIcon(images.get(modele.getValeur(x, y))); // Mise à jour graphique

                            // Vérifier si la partie est finie
                            if (modele.estGagnee()) {
                                JOptionPane.showMessageDialog(fenetre, "Le joueur " + modele.getJoueur() + " a gagné !");
                            } else if (modele.estTerminee()) {
                                JOptionPane.showMessageDialog(fenetre, "Match nul !");
                            }
                        } catch (CaseOccupeeException ex) {
                            JOptionPane.showMessageDialog(fenetre, "Case déjà occupée !");
                        }
                    }
                });
            }
        }
    }

    /** Ajouter une barre de menu */
    private void ajouterMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuJeu = new JMenu("Jeu");
        JMenuItem nouvellePartie = new JMenuItem("Nouvelle Partie");
        JMenuItem quitter = new JMenuItem("Quitter");

        nouvellePartie.addActionListener(e -> recommencer());
        quitter.addActionListener(e -> System.exit(0));

        menuJeu.add(nouvellePartie);
        menuJeu.add(quitter);
        menuBar.add(menuJeu);

        fenetre.setJMenuBar(menuBar);
    }

    /** Recommencer une nouvelle partie */
    public void recommencer() {
        modele.recommencer();

        // Vider les cases
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cases[i][j].setIcon(images.get(ModeleMorpion.Etat.VIDE)); // Remettre les cases vides
            }
        }
    }

    /** Méthode principale */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MorpionSwing());
    }
}
