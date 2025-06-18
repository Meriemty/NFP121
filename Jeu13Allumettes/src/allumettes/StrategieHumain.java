package allumettes;

import java.util.Scanner;

public class StrategieHumain implements Strategie {
    private static final Scanner scanner = new Scanner(System.in);
    
    @Override
    public int getPrise(Jeu jeu) {
        int nbAllumettes = jeu.getNombreAllumettes();
        System.out.print("Combien d'allumettes ? ");
        
        while (true) {
            try {
                String line = scanner.nextLine();
                int prise = Integer.parseInt(line);
                if (prise >= 1 && prise <= Jeu.PRISE_MAX && prise <= nbAllumettes) {
                    return prise;
                } else {
                    System.out.print("Nombre invalide : " + prise + "\nCombien d'allumettes ? ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Vous devez donner un entier.\nCombien d'allumettes ? ");
            }
        }
    }
}
