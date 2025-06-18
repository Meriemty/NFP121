package allumettes;

public class Arbitre {
	/** Le premier joueur. */
	private Joueur j1;
	
	/** Le deuxième joueur. */
	private Joueur j2;
	
	/** Le jeu en cours. */
	private Jeu jeu;
	
	/** Constructeur d'un arbitre.
	 * @param j1 le premier joueur
	 * @param j2 le deuxième joueur
	 */
	public Arbitre(Joueur j1, Joueur j2) {
		this.j1 = j1;
		this.j2 = j2;
		this.jeu = new JeuAllumettes(13);
	}
	
	/** Arbitrer une partie entre les deux joueurs.
	 * @param confiant true si l'arbitre est confiant, false sinon
	 */
	public void arbitrer(boolean confiant) {
		Joueur joueurCourant = this.j1;
		Joueur joueurAdverse = this.j2;
		
		while (true) {
			try {
				// Afficher le nombre d'allumettes restantes
				System.out.println("Allumettes restantes : " + this.jeu.getNombreAllumettes());
				
				// CrÃ©er le proxy si l'arbitre n'est pas confiant
				Jeu jeuJoueur = confiant ? this.jeu : new JeuProxy(this.jeu, joueurCourant);
				
				// Demander au joueur courant de jouer
				int prise = joueurCourant.getPrise(jeuJoueur);
				
				// Afficher le coup jouÃ©
				System.out.println(joueurCourant.getNom() + " prend " + prise + " allumette" + (prise > 1 ? "s" : "") + ".");
				
				// Retirer les allumettes
				this.jeu.retirer(prise);
				
				// VÃ©rifier si le jeu est terminÃ©
				if (this.jeu.getNombreAllumettes() == 0) {
					System.out.println(joueurCourant.getNom() + " perd !");
					System.out.println(joueurAdverse.getNom() + " gagne !");
					return;
				}
				
				// Changer de joueur
				Joueur temp = joueurCourant;
				joueurCourant = joueurAdverse;
				joueurAdverse = temp;
				
			} catch (CoupInvalideException e) {
				System.out.println("Impossible ! " + e.getMessage());
				// Le même joueur rejoue
			} catch (OperationInterditeException e) {
				System.out.println(e.getMessage());
				System.out.println("Abandon de la partie car " + joueurCourant.getNom() + " triche !");
				return;
			}
		}
	}
}
