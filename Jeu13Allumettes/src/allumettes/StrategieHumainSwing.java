package allumettes;

public class StrategieHumainSwing implements Strategie {
    private int nombreChoisi = -1;
    private boolean choixFait = false;
    
    @Override
    public int getPrise(Jeu jeu) {
        // Cette méthode ne sera pas utilisée directement
        // car les choix sont gérés par l'interface graphique
        return nombreChoisi;
    }
    
    public void setChoix(int nombre) {
        this.nombreChoisi = nombre;
        this.choixFait = true;
    }
    
    public boolean isChoixFait() {
        return choixFait;
    }
    
    public void resetChoix() {
        this.choixFait = false;
        this.nombreChoisi = -1;
    }
} 