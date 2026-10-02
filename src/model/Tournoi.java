package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Tournoi {

    private ArrayList<Combattant> participants;

    public Tournoi() {
        participants = new ArrayList<>();
    }

    // Inscrire un combattant
    public boolean inscrire(Combattant c) {

        if (participants.size() >= 8) {
            return false;
        }

        for (Combattant participant : participants) {
            if (participant.getNom().equalsIgnoreCase(c.getNom())) {
                return false;
            }
        }

        participants.add(c);
        return true;
    }

    // Désinscrire un combattant
    public boolean desinscrire(String nom) {

        Iterator<Combattant> iterator = participants.iterator();

        while (iterator.hasNext()) {
            Combattant c = iterator.next();

            if (c.getNom().equalsIgnoreCase(nom)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    // Duel entre deux combattants
    public void duel(Combattant a, Combattant b) {

        // Celui qui possède la meilleure attaque commence
        Combattant attaquant;
        Combattant defenseur;

        if (a.getAttaque() >= b.getAttaque()) {
            attaquant = a;
            defenseur = b;
        } else {
            attaquant = b;
            defenseur = a;
        }

        int tour = 1;

        System.out.println("\n===== DUEL =====");
        System.out.println(attaquant.getNom() + " commence !");

        while (!a.estKO() && !b.estKO() && tour <= 50) {

            System.out.println("\n--- Tour " + tour + " ---");

            System.out.println(
                attaquant.getNom() + " attaque " + defenseur.getNom()
            );

            // On utilise la méthode attaquer() de la classe du combattant
            int degats = attaquant.attaquer(defenseur);

            System.out.println(
                defenseur.getNom() + " reçoit " + degats + " dégâts."
            );

            System.out.println(
                defenseur.getNom() + " : "
                + defenseur.getPv() + "/" + defenseur.getPvMax() + " PV"
            );

            // Vérification du K.O.
            if (defenseur.estKO()) {
                System.out.println(
                    "\nK.O. ! " + defenseur.getNom() + " est vaincu."
                );

                System.out.println(
                    "Vainqueur : " + attaquant.getNom()
                );

                return;
            }

            // Inversion des combattants
            Combattant temp = attaquant;
            attaquant = defenseur;
            defenseur = temp;

            tour++;
        }

        // Si le duel dépasse 50 tours
        if (tour > 50) {

            double pourcentageA =
                (double) a.getPv() / a.getPvMax() * 100;

            double pourcentageB =
                (double) b.getPv() / b.getPvMax() * 100;

            System.out.println("\n===== LIMITE DE 50 TOURS =====");

            System.out.printf(
                "%s : %.2f%% de PV%n",
                a.getNom(),
                pourcentageA
            );

            System.out.printf(
                "%s : %.2f%% de PV%n",
                b.getNom(),
                pourcentageB
            );

            if (pourcentageA > pourcentageB) {

                System.out.println(
                    "Vainqueur : " + a.getNom()
                );

            } else if (pourcentageB > pourcentageA) {

                System.out.println(
                    "Vainqueur : " + b.getNom()
                );

            } else {

                System.out.println("Égalité !");
            }
        }
    }
}