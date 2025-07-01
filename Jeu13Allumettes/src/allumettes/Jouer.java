package allumettes;

/**
 * Lance une partie des 13 allumettes en fonction des arguments fournis sur la
 * ligne de commande.
 * 
 * @author Meriem
 * @version 1
 */
public class Jouer {

	/**
	 * Lancer une partie. En argument sont donnés les deux joueurs sous la forme
	 * nomstratégie.
	 * 
	 * @param args la description des deux joueurs
	 */
	public static void main(String[] args) {
		try {
			boolean confiant = false;
			int debutArgs = 0;
			
			// Vérifier si l'option -confiant est présente
			if (args.length > 0 && args[0].equals("-confiant")) {
				confiant = true;
				debutArgs = 1;
			}
			
			// Vérifier le nombre d'arguments restants
			if (args.length - debutArgs != 2) {
				throw new ConfigurationException("Nombre incorrect d'arguments : " + (args.length - debutArgs));
			}
			
			// Création des joueurs
			String[] joueur1Info = args[debutArgs].split("@");
			String[] joueur2Info = args[debutArgs + 1].split("@");
			
			if (joueur1Info.length != 2 || joueur2Info.length != 2) {
				throw new ConfigurationException("Format invalide pour les joueurs");
			}
			
			Joueur joueur1 = new Joueur(joueur1Info[0], creerStrategie(joueur1Info[1]));
			Joueur joueur2 = new Joueur(joueur2Info[0], creerStrategie(joueur2Info[1]));
			
			// Création de l'arbitre
			Arbitre arbitre = new Arbitre(joueur1, joueur2);
			
			// Lancement du jeu
			arbitre.arbitrer(confiant);
			
		} catch (ConfigurationException e) {
			System.out.println("Erreur : " + e.getMessage());
			afficherUsage();
		}
	}

	private static Strategie creerStrategie(String nom) throws ConfigurationException {
		switch (nom.toLowerCase()) {
			case "humain":
				return new StrategieHumain();
			case "naif":
				return new StrategieNaif();
			case "rapide":
				return new StrategieRapide();
			case "expert":
				return new StrategieExpert();
			case "tricheur":
				return new StrategieTricheur();
			case "supertricheur":
				return new StartegieSuperTricheur();
			case "swing":
				return new StrategieHumainSwing();
			default:
				throw new ConfigurationException("Strategie inconnue : " + nom);
		}
	}

	/** Afficher des indications sur la manière d'exécuter cette classe. */
	public static void afficherUsage() {
		System.out.println("Usage : java allumettes.Jouer [-confiant] joueur1@strategie1 joueur2@strategie2");
		System.out.println("Strategies disponibles : humain, naif, rapide, expert, tricheur, supertricheur, swing");
	}

}

