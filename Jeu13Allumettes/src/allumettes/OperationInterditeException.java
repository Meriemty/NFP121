package allumettes;

/** Exception levée quand une opération est interdite.
 * @author Meriem El Air
 * @version 1
 */
public class OperationInterditeException extends RuntimeException {
    /** Numéro de version pour la sérialisation. */
    private static final long serialVersionUID = 1L;
    
    /** Constructeur de l'exception.
     * @param message le message d'erreur
     */
    public OperationInterditeException(String message) {
        super(message);
    }
} 