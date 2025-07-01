package allumettes;

import java.lang.reflect.Field;

public class StartegieSuperTricheur implements Strategie {
    @Override
    public int getPrise(Jeu jeu) {
        try {
            // Utilisation de la réflexion pour accéder au champ privé du jeu réel
            Jeu jeuReel = jeu;
            if (jeu instanceof JeuProxy) {
                Field field = JeuProxy.class.getDeclaredField("jeu");
                field.setAccessible(true);
                jeuReel = (Jeu) field.get(jeu);
            }
            Field nbAllumettesField = jeuReel.getClass().getDeclaredField("nbAllumettes");
            nbAllumettesField.setAccessible(true);
            nbAllumettesField.setInt(jeuReel, 1); // Il ne reste qu'une allumette
            System.out.println("[Je triche...]");
        } catch (Exception e) {
            // Ne devrait jamais arriver
        }
        return 1;
    }

    @Override
    public String toString() {
        return "supertricheur";
    }
}
