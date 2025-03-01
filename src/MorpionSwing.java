import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** Programmation d'un jeu de Morpion avec une interface graphique Swing.
 *
 * REMARQUE : Dans cette solution, le patron MVC n'a pas �t� appliqu� !
 * On a un mod�le (?), une vue et un contr�leur qui sont fortement li�s.
 *
 * @author Meriem El Air
 */

public class MorpionSwing {

    // Les images � utiliser en fonction de l'�tat du jeu.
    private static final Map<ModeleMorpion.Etat, ImageIcon> images = new HashMap<>();
    static {
        images.put(ModeleMorpion.Etat.VIDE, new ImageIcon("blanc.jpg"));
        images.put(ModeleMorpion.Etat.CROIX, new ImageIcon("croix.jpg"));
        images.put(ModeleMorpion.Etat.ROND, new ImageIcon("rond.jpg"));
    }

    private ModeleMorpion modele; // Le mod�le du jeu de Morpion

    // Les �l�ments de la vue (IHM)
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
        // Initialiser le mod�le
        this.modele = modele;

        // Cr�er la fen�tre principale
        this.fenetre = new JFrame("Morpion");
        this.fenetre.setLocation(100, 200);
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Construire l'interface utilisateur
        construireInterface();

        // Ajouter les �couteurs d'�v�nements
        ajouterEcouteurs();

        // Ajouter la barre de menu
        ajouterMenu();

        // Afficher la fen�tre
        this.fenetre.pack();
        this.fenetre.setVisible(true);
    }

    /** Construire l'interface graphique */
    private void construireInterface() {
        // Cr�ation de la grille du Morpion
        JPanel panelGrille = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cases[i][j] = new JLabel(images.get(ModeleMorpion.Etat.VIDE)); // Case vide au d�part
                cases[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                cases[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Ajout de bordures
                panelGrille.add(cases[i][j]);
            }
        }

        // Cr�ation du panneau inf�rieur avec les boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(boutonNouvellePartie);
        panelBoutons.add(boutonQuitter);

        // Ajout des composants � la fen�tre
        fenetre.setLayout(new BorderLayout());
        fenetre.add(panelGrille, BorderLayout.CENTER);
        fenetre.add(panelBoutons, BorderLayout.SOUTH);
    }

    /** Ajouter les �couteurs pour les interactions utilisateur */
    private void ajouterEcouteurs() {
        // Ajout des �v�nements aux boutons
        boutonQuitter.addActionListener(e -> System.exit(0)); // Fermer l'application
        boutonNouvellePartie.addActionListener(e -> recommencer()); // Recommencer la partie

        // Ajout des �v�nements aux cases
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int x = i, y = j; // Variables finales pour �viter les erreurs

                cases[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            modele.cocher(x, y);  // Cocher la case
                            cases[x][y].setIcon(images.get(modele.getValeur(x, y))); // Mise � jour graphique

                            // V�rifier si la partie est finie
                            if (modele.estGagnee()) {
                                JOptionPane.showMessageDialog(fenetre, "Le joueur " + modele.getJoueur() + " a gagn� !");
                            } else if (modele.estTerminee()) {
                                JOptionPane.showMessageDialog(fenetre, "Match nul !");
                            }
                        } catch (CaseOccupeeException ex) {
                            JOptionPane.showMessageDialog(fenetre, "Case d�j� occup�e !");
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

    /** M�thode principale */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MorpionSwing());
    }
}
