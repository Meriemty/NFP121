package allumettes;

public class StrategieExpert implements Strategie {
    
    @Override
    public int getPrise(Jeu jeu) {
        int nbAllumettes = jeu.getNombreAllumettes();
        
        // Stratégie gagnante : laisser un multiple de (PRISE_MAX + 1)
        int reste = nbAllumettes % (Jeu.PRISE_MAX + 1);
        if (reste == 0) {
            return 1; // Si on ne peut pas gagner, on prend 1
        } else {
            return reste;
        }
    }

    @Override
    public String toString() { return "expert"; }
}
