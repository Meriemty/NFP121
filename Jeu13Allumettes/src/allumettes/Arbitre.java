package allumettes;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Arbitre {
	/** Le premier joueur. */
	private Joueur j1;
	
	/** Le deuxi�me joueur. */
	private Joueur j2;
	
	/** Le jeu en cours. */
	private Jeu jeu;
	
	private Document xmlDoc;
	private Element racine;
	private int coupNum = 1;
	private boolean partieTerminee = false;
	
	/** Constructeur d'un arbitre.
	 * @param j1 le premier joueur
	 * @param j2 le deuxi�me joueur
	 */
	public Arbitre(Joueur j1, Joueur j2) {
		this.j1 = j1;
		this.j2 = j2;
		this.jeu = new JeuAllumettes(13);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			xmlDoc = db.newDocument();
			racine = xmlDoc.createElement("deroulement");
			xmlDoc.appendChild(racine);
		} catch (ParserConfigurationException e) {
			xmlDoc = null;
		}
	}
	
	/** Arbitrer une partie entre les deux joueurs.
	 * @param confiant true si l'arbitre est confiant, false sinon
	 */
	public void arbitrer(boolean confiant) {
		Joueur courant = j1, autre = j2;
		while (!partieTerminee) {
			try {
				// Afficher le nombre d'allumettes restantes
				System.out.println("Allumettes restantes : " + this.jeu.getNombreAllumettes());
				
				// Créer le proxy si l'arbitre n'est pas confiant
				Jeu jeuPourJoueur = confiant ? this.jeu : new JeuProxy(this.jeu, courant);
				
				// Demander au joueur courant de jouer
				int prise;
				if (courant.getStrategie() instanceof StrategieHumainSwing) {
					// Passer le nom du joueur à la stratégie swing si besoin
					StrategieHumainSwing strat = (StrategieHumainSwing) courant.getStrategie();
					prise = strat.getPrise(jeuPourJoueur);
				} else {
					prise = courant.getPrise(jeuPourJoueur);
				}
				
				// Afficher le coup joué
				System.out.println(courant.getNom() + " prend " + prise + " allumette" + (prise > 1 ? "s" : "") + ".");
				
				// Retirer les allumettes
				this.jeu.retirer(prise);
				
				// Enregistrer le coup
				enregistrerCoup(coupNum++, courant.getNom(), prise);
				
				// Vérifier si le jeu est terminé
				if (this.jeu.getNombreAllumettes() == 0) {
					System.out.println(courant.getNom() + " perd !");
					System.out.println(autre.getNom() + " gagne !");
					enregistrerFin(autre.getNom(), null);
					partieTerminee = true;
					break;
				}
				
				// Changer de joueur
				Joueur tmp = courant;
				courant = autre;
				autre = tmp;
				
			} catch (CoupInvalideException e) {
				System.out.println("Impossible ! " + e.getMessage());
				// Le mme joueur rejoue
			} catch (OperationInterditeException e) {
				System.out.println(e.getMessage());
				System.out.println("Abandon de la partie car " + courant.getNom() + " triche !");
				enregistrerFin(null, courant.getNom());
				partieTerminee = true;
				break;
			}
		}
		ecrireXML();
	}
	
	private void enregistrerCoup(int num, String nom, int prise) {
		if (xmlDoc == null) return;
		Element coup = xmlDoc.createElement("coup");
		coup.setAttribute("numero", String.valueOf(num));
		coup.setAttribute("joueur", nom);
		coup.setAttribute("prise", String.valueOf(prise));
		racine.appendChild(coup);
	}
	
	private void enregistrerFin(String gagnant, String tricheur) {
		if (xmlDoc == null) return;
		Element fin = xmlDoc.createElement("fin");
		if (gagnant != null) fin.setAttribute("gagnant", gagnant);
		if (tricheur != null) fin.setAttribute("tricheur", tricheur);
		racine.appendChild(fin);
	}
	
	private void ecrireXML() {
		if (xmlDoc == null) return;
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(xmlDoc), new StreamResult(new File("deroulement.xml")));
			System.out.println("[INFO] Fichier deroulement.xml généré.");
		} catch (TransformerException e) {
			System.out.println("Erreur lors de l'écriture du fichier XML : " + e.getMessage());
		}
	}
}
