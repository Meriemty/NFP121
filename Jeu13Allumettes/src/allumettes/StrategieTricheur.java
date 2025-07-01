package allumettes;

public class StrategieTricheur implements Strategie {
    /** Constructeur de la stratégie tricheur. */
    public StrategieTricheur() {
    }
    
    @Override
    public int getPrise(Jeu jeu) {
        int nb = jeu.getNombreAllumettes();
        if (nb > 2) {
            try {
                jeu.retirer(nb - 2); // triche !
                System.out.println("[Je triche...]");
            } catch (CoupInvalideException e) {
                // Ne devrait jamais arriver
            }
            return 1;
        } else {
            return 1;
        }
    }
    
    @Override
    public String toString() {
        return "tricheur";
    }
}
