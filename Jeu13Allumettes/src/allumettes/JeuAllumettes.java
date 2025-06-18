package allumettes;

public class JeuAllumettes implements Jeu {
    
    /** Nombre d'allumettes restantes. */
    private int nbAllumettes;
    
    /** Créer un jeu avec nb allumettes.
     * @param nb nombre d'allumettes initial
     */
    public JeuAllumettes(int nb) {
        if (nb < 0) {
            throw new IllegalArgumentException("Nombre d'allumettes initial invalide : " + nb);
        }
        this.nbAllumettes = nb;
    }
    
    @Override
    public int getNombreAllumettes() {
        return this.nbAllumettes;
    }
    
    @Override
    public void retirer(int nbPrises) throws CoupInvalideException {
        if (nbPrises < 1) {
            throw new CoupInvalideException(nbPrises, "< 1");
        }
        if (nbPrises > PRISE_MAX) {
            throw new CoupInvalideException(nbPrises, "> " + PRISE_MAX);
        }
        if (nbPrises > this.nbAllumettes) {
            throw new CoupInvalideException(nbPrises, "> " + this.nbAllumettes);
        }
        this.nbAllumettes -= nbPrises;
    }
    
    @Override
    public String toString() {
        return "Allumettes restantes : " + this.nbAllumettes;
    }
} 