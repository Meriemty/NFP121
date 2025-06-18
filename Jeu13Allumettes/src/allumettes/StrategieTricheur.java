package allumettes;

public class StrategieTricheur implements Strategie {
    /** Constructeur de la stratégie tricheur. */
    public StrategieTricheur() {
    }
    
    @Override
    public int getPrise(Jeu jeu) {
        // Le tricheur retire toutes les allumettes sauf 2
        int nbAllumettes = jeu.getNombreAllumettes();
        if (nbAllumettes > 2) {
            return nbAllumettes - 2;
        } else {
            return 1;
        }
    }
    
    @Override
    public String toString() {
        return "tricheur";
    }
}
