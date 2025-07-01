package allumettes;

public class Joueur {
	/** Le nom du joueur. */
	private String nom;
	
	/** La stratégie du joueur. */
	private Strategie strategie;
	
	/** Constructeur d'un joueur.
	 * @param nom le nom du joueur
	 * @param strategie la stratégie du joueur
	 */
	public Joueur(String nom, Strategie strategie) {
		this.nom = nom;
		this.strategie = strategie;
	}
	
	/** Obtenir le nom du joueur.
	 * @return le nom du joueur
	 */
	public String getNom() {
		return nom;
	}
	
	/** Obtenir le nombre d'allumettes que le joueur veut prendre.
	 * @param jeu le jeu en cours
	 * @return le nombre d'allumettes à prendre
	 */
	public int getPrise(Jeu jeu) {
		return strategie.getPrise(jeu);
	}
	
	/** Obtenir la stratégie du joueur.
	 * @return la stratégie du joueur
	 */
	public Strategie getStrategie() {
		return strategie;
	}
}
