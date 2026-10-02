package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Tournoi {

    private ArrayList<Combattant> participants;

    /************************************************************************************************************************************
   Méthode Tournoi.
	 ************************************************************************************************************************************/
    public Tournoi() {
        participants = new ArrayList<>();
    }

   
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

    /************************************************************************************************************************************
    Méthode duel entre combattant.
	 ************************************************************************************************************************************/
    public void duel(Combattant a, Combattant b) {

        
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

            
            int degats = attaquant.attaquer(defenseur);

            System.out.println(
                defenseur.getNom() + " reçoit " + degats + " dégâts."
            );

            System.out.println(
                defenseur.getNom() + " : "
                + defenseur.getPv() + "/" + defenseur.getPvMax() + " PV"
            );

            
            if (defenseur.estKO()) {

                attaquant.ajouterVictoire();

                System.out.println(
                    "\nK.O. ! " + defenseur.getNom() + " est vaincu."
                );

                System.out.println(
                    "Vainqueur : " + attaquant.getNom()
                );

                return;
            }

            
            Combattant temp = attaquant;
            attaquant = defenseur;
            defenseur = temp;

            tour++;
        }

        
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
    /************************************************************************************************************************************
    Méthode lancer.
	 ************************************************************************************************************************************/
    public Combattant lancer() {

        
        if (participants.size() < 2) {
            return null;
        }

     
        ArrayList<Combattant> tour = new ArrayList<>(participants);

        
        java.util.Collections.shuffle(tour);

        while (tour.size() > 1) {

            System.out.println("\n===== NOUVEAU TOUR =====");

            ArrayList<Combattant> vainqueurs = new ArrayList<>();

            
            for (int i = 0; i < tour.size(); i += 2) {

                Combattant a = tour.get(i);
                Combattant b = tour.get(i + 1);

                System.out.println(
                    "\nDuel : " + a.getNom() + " VS " + b.getNom()
                );

                
                duel(a, b);

                
                Combattant vainqueur;

                if (a.estKO()) {
                    vainqueur = b;
                } else {
                    vainqueur = a;
                }

                System.out.println(
                    "Vainqueur du duel : " + vainqueur.getNom()
                );

               
                vainqueur.soigner(vainqueur.getPvMax());

                System.out.println(
                    vainqueur.getNom() + " est entièrement soigné : "
                    + vainqueur.getPv() + "/" + vainqueur.getPvMax() + " PV"
                );

                
                vainqueurs.add(vainqueur);
            }

            
            tour = vainqueurs;
        }

        
        Combattant champion = tour.get(0);

        System.out.println("\n===== CHAMPION DU TOURNOI =====");
        System.out.println(champion);

        return champion;
    }
    
    /************************************************************************************************************************************
    Méthode classement
	 ************************************************************************************************************************************/
    
    public ArrayList<Combattant> classement() {

        ArrayList<Combattant> classement =
            new ArrayList<>(participants);

        
        for (int i = 0; i < classement.size() - 1; i++) {

            int indiceMax = i;

            for (int j = i + 1; j < classement.size(); j++) {

                if (classement.get(j).getVictoires()
                        > classement.get(indiceMax).getVictoires()) {

                    indiceMax = j;
                }
            }

            
            if (indiceMax != i) {

                Combattant temp = classement.get(i);

                classement.set(i, classement.get(indiceMax));

                classement.set(indiceMax, temp);
            }
        }

        return classement;
    }
    
    /************************************************************************************************************************************
    Méthode statsParClasse.
	 ************************************************************************************************************************************/
    public void statsParClasse() {

        int victoiresGuerrier = 0;
        int victoiresMage = 0;
        int victoiresVoleur = 0;
        int victoiresPaladin = 0;

        for (Combattant c : participants) {

            switch (c.getClasse()) {

                case "Guerrier":
                    victoiresGuerrier += c.getVictoires();
                    break;

                case "Mage":
                    victoiresMage += c.getVictoires();
                    break;

                case "Voleur":
                    victoiresVoleur += c.getVictoires();
                    break;

                case "Paladin":
                    victoiresPaladin += c.getVictoires();
                    break;
            }
        }

        System.out.println("\n===== STATISTIQUES PAR CLASSE =====");
        System.out.println("Guerrier : " + victoiresGuerrier + " victoire(s)");
        System.out.println("Mage     : " + victoiresMage + " victoire(s)");
        System.out.println("Voleur   : " + victoiresVoleur + " victoire(s)");
        System.out.println("Paladin  : " + victoiresPaladin + " victoire(s)");
    }
    
    public ArrayList<Combattant> getParticipants() {
        return new ArrayList<>(participants);
    }
}