package allumettes;

/** Interface qui définit une stratégie de jeu. */
public interface Strategie {
    
    /** Obtenir le nombre d'allumettes que le joueur veut prendre.
     * @param jeu Le jeu en cours
     * @return le nombre d'allumettes que le joueur veut prendre
     */
    int getPrise(Jeu jeu);
}
