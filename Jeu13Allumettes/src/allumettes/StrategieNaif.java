package allumettes;

import java.util.Random;

public class StrategieNaif implements Strategie {
    private Random random = new Random();
    
    @Override
    public int getPrise(Jeu jeu) {
        int nbAllumettes = jeu.getNombreAllumettes();
        int max = Math.min(Jeu.PRISE_MAX, nbAllumettes);
        return random.nextInt(max) + 1;
    }
} 