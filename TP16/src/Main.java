import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.util.zip.*;
import java.time.LocalDateTime;

public class Main {
    private static void repondreQuestions(Reader in) {
        Iterable<PointDeVente> iterable = PointDeVenteUtils.fromXML(in);
        Map<Long, PointDeVente> pdvs = new HashMap<>();

        for (PointDeVente pdv : iterable) {
            pdvs.put(pdv.getIdentifiant(), pdv);
        }

        // 1. Nombre de points de vente
        System.out.println("Nombre de points de vente : " + pdvs.size());

        // 2. Nombre de services existants
        Set<String> allServices = pdvs.values().stream()
                .flatMap(p -> p.getServices().stream())
                .collect(Collectors.toSet());
        System.out.println("Nombre de services existants : " + allServices.size());

        // 3. Liste de tous les services
        System.out.println("Tous les services : " + allServices);

        // 4. Prix du gazole pour le PDV 31075001 le 25 janvier 2017 à 10h
        LocalDateTime date = LocalDateTime.of(2017, 1, 25, 10, 0);
        PointDeVente pdv31075001 = pdvs.get(31075001L);
        if (pdv31075001 != null) {
            int prixGazole = pdv31075001.getPrix(Carburant.GAZOLE, date);
            System.out.println("Prix du Gazole pour PDV 31075001 le 25/01/2017 à 10h : " + prixGazole + " millièmes d'euro");
        }

        // 5. Nombre de villes avec au moins un point de vente
        long nbVilles = pdvs.values().stream()
                .map(PointDeVente::getVille)
                .distinct()
                .count();
        System.out.println("Nombre de villes offrant au moins un point de vente : " + nbVilles);

        // 6. Nombre moyen de points de vente par ville
        double moyennePDVParVille = (double) pdvs.size() / nbVilles;
        System.out.println("Nombre moyen de points de vente par ville : " + moyennePDVParVille);

        // 7. Nombre de PDV par nombre de carburants proposés
        for (int nbCarburants = 1; nbCarburants < 7; nbCarburants++) {
            final int nb = nbCarburants;
            long nbPdvNbCarburantsEtPlus = pdvs.values().stream()
                    .filter(p -> p.getPrix().keySet().size() >= nb)
                    .count();
            System.out.printf("PDV proposant au moins %d carburant(s) : %d%n", nbCarburants, nbPdvNbCarburantsEtPlus);
        }

        // 8. Nombre et détails des PDV dont le code postal est 31200
        List<PointDeVente> pdvs31200 = pdvs.values().stream()
                .filter(p -> "31200".equals(p.getCodePostal()))
                .collect(Collectors.toList());
        System.out.println("Nombre de PDV en 31200 : " + pdvs31200.size());
        pdvs31200.forEach(System.out::println);

        // 9. Nombre de PDV à Toulouse proposant Gazole et GPLc
        long pdvsToulouseGazoleGPLc = pdvs.values().stream()
                .filter(p -> "TOULOUSE".equals(p.getVille())
                        && p.getPrix().containsKey(Carburant.GAZOLE)
                        && p.getPrix().containsKey(Carburant.GPLc))
                .count();
        System.out.println("Nombre de PDV à Toulouse proposant Gazole et GPLc : " + pdvsToulouseGazoleGPLc);

        // 10. Nombre et liste des villes avec au moins 20 points de vente
        Map<String, Long> villesAvecPDVs = pdvs.values().stream()
                .collect(Collectors.groupingBy(PointDeVente::getVille, Collectors.counting()));
        villesAvecPDVs.entrySet().stream()
                .filter(entry -> entry.getValue() >= 20)
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));
    }

    private static Reader ouvrir(String nomFichier) throws FileNotFoundException, IOException {
        if (nomFichier.endsWith(".zip")) {
            ZipFile zfile = new ZipFile(nomFichier);
            ZipEntry premiere = zfile.entries().nextElement();
            return new InputStreamReader(zfile.getInputStream(premiere));
        } else {
            return new FileReader(nomFichier);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage : java Main <fichier.xml ou fichier.zip>");
        } else {
            try (Reader in = ouvrir(args[0])) {
                repondreQuestions(in);
            } catch (FileNotFoundException e) {
                System.out.println("Fichier non trouvé : " + args[0]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
