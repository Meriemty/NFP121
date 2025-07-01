package allumettes;

public class StrategieHumainSwing implements Strategie {
    private static InterfaceSwing interfaceSwing;
    private static final Object verrou = new Object();
    private static boolean initialisee = false;
    private String nomJoueur;

    public StrategieHumainSwing() {
        this.nomJoueur = "Joueur";
        initialiserInterface();
    }
    public StrategieHumainSwing(String nom) {
        this.nomJoueur = nom;
        initialiserInterface();
    }
    private void initialiserInterface() {
        if (!initialisee) {
            interfaceSwing = new InterfaceSwing(nomJoueur, 13, verrou);
            initialisee = true;
        }
    }
    @Override
    public int getPrise(Jeu jeu) {
        int nbAllumettes = jeu.getNombreAllumettes();
        interfaceSwing.afficher(nbAllumettes, nomJoueur + ", combien d'allumettes ?");
        int prise = interfaceSwing.attendreChoix();
        if (interfaceSwing.aTriche()) {
            int nbTriche = interfaceSwing.getNbTriche();
            if (nbAllumettes == 1) {
                // Ajout d'une allumette (cas spécial)
                System.out.println("[Je triche... 1 allumette en plus]");
            } else {
                int retire = Math.min(nbTriche, nbAllumettes - 1);
                try {
                    jeu.retirer(retire);
                } catch (Exception e) {}
                System.out.println("[Je triche... " + retire + (retire > 1 ? " allumettes en moins]" : " allumette en moins]") );
            }
            interfaceSwing.reset();
            return interfaceSwing.attendreChoix();
        }
        interfaceSwing.reset();
        return prise;
    }
} 