package allumettes;

/** Exception qui indique que la configuration d'une partie est incorrecte.
 * @author Meriem El Air
 * @version 1
 */
public class ConfigurationException extends RuntimeException {

	/** Numéro de version pour la sérialisation. */
	private static final long serialVersionUID = 1L;

	/** Initialiser une ConfigurationException avec le message précisé.
	 * @param message le message explicatif
	 */
	public ConfigurationException(String message) {
		super(message);
	}

}
