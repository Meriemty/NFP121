package allumettes;

public class StrategieRapide implements Strategie {
    
    @Override
    public int getPrise(Jeu jeu) {
        int nbAllumettes = jeu.getNombreAllumettes();
        return Math.min(Jeu.PRISE_MAX, nbAllumettes);
    }
}
