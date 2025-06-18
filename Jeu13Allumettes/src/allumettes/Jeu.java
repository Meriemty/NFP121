package allumettes;

public interface Jeu {

	/** Nombre maximal d'allumettes pouvant être prises. */
	int PRISE_MAX = 3;

	/** Obtenir le nombre d'allumettes encore en jeu.
	 * @return le nombre d'allumettes encore en jeu
	 */
	int getNombreAllumettes();

	/** Retirer des allumettes. Le nombre d'allumettes doit être compris entre 1 et PRISE_MAX,
	 * dans la limite du nombre d'allumettes encore en jeu.
	 * @param prise le nombre d'allumettes prises
	 * @throws CoupInvalideException si le nombre d'allumettes est invalide
	 */
	void retirer(int prise) throws CoupInvalideException;

	/** Obtenir une représentation textuelle du jeu.
	 * @return une représentation textuelle du jeu
	 */
	String toString();

}
