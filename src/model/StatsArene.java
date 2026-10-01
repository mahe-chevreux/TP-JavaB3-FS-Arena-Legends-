package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatsArene {

    int[] scores = {42, 87, 15, 99, 63, 87, 5, 71, 99, 34, 50, 28};

    public static void main(String[] args) {

        StatsArene stats = new StatsArene();

        int maximum = stats.scores[0];
        int minimum = stats.scores[0];
        int somme = 0;

        for (int i = 0; i < stats.scores.length; i++) {
        	/************************************************************************************************************************************
            Partie Maximum
            ************************************************************************************************************************************/
            
            if (stats.scores[i] > maximum) {
                maximum = stats.scores[i];
            }
            /************************************************************************************************************************************
            Partie Minimum
            ************************************************************************************************************************************/
            
            if (stats.scores[i] < minimum) {
                minimum = stats.scores[i];
            }
            /************************************************************************************************************************************
            Partie moyenne
            ************************************************************************************************************************************/
            
            somme += stats.scores[i];
        }

        double moyenne = (double) somme / stats.scores.length;

        System.out.println("Score maximum : " + maximum);
        System.out.println("Score minimum : " + minimum);
        System.out.println("Moyenne : " + moyenne);

        /************************************************************************************************************************************
        Partie affichage Trier décroissant
        ************************************************************************************************************************************/
        int nombre = stats.trierDecroissant(stats.scores);
        
        System.out.print("Tableau trié de manière décroissante : ");
        //tsansdoublons = tsansdoublons.sansDoublons;
        for (int num : stats.scores) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println("Total d'échanges : " + nombre);
        
        /************************************************************************************************************************************
        Partie affichage Sans doublons
        ************************************************************************************************************************************/
        List<Integer> resultat = stats.sansDoublons(stats.scores);

        System.out.print("Tableau sans doublons : ");

        for (int num : resultat) {
            System.out.print(num + " ");
        }
        
        
        /************************************************************************************************************************************
        Partie affichage arene
        ************************************************************************************************************************************/
        
        char[][] arene = new char[8][8];
        Random random = new Random();

        
        for (int i = 0; i < arene.length; i++) {
            for (int j = 0; j < arene[i].length; j++) {
                arene[i][j] = '.';
            }
        }

        
        int obstacles = 0;

        while (obstacles < 6) {
            int ligne = random.nextInt(8);
            int colonne = random.nextInt(8);

            
            if (arene[ligne][colonne] == '.') {
                arene[ligne][colonne] = '#';
                obstacles++;
            }
        }

        
        boolean playerA = false;

        while (!playerA) {
            int ligne = random.nextInt(8);
            int colonne = random.nextInt(8);

            if (arene[ligne][colonne] == '.') {
                arene[ligne][colonne] = 'A';
                playerA = true;
            }
        }

        
        boolean playerB = false;

        while (!playerB) {
            int ligne = random.nextInt(8);
            int colonne = random.nextInt(8);

            if (arene[ligne][colonne] == '.') {
                arene[ligne][colonne] = 'B';
                playerB = true;
            }
        }

        
        System.out.println("      ");
        System.out.println("=== ARÈNE ===" );

        for (int i = 0; i < arene.length; i++) {
            for (int j = 0; j < arene[i].length; j++) {
                System.out.print(arene[i][j] + " ");
            }
            System.out.println();
        }
        int distanceAB = stats.distance(arene);

        System.out.println("Distance entre A et B : " + distanceAB);
    }
    /************************************************************************************************************************************
    Partie Trier décroissant
    ************************************************************************************************************************************/
    public int trierDecroissant(int[] t) {

        int nombre = 0;
        boolean echange;

        for (int i = 0; i < scores.length - 1; i++) {

            echange = false;

            for (int j = 0; j < scores.length - i - 1; j++) {

                if (t[j] < t[j + 1]) {

                    int temp = t[j];
                    t[j] = t[j + 1];
                    t[j + 1] = temp;

                    echange = true;
                    nombre++;
                }
            }

            if (!echange) {
                break;
            }
        }

        return nombre;
    }
    
    public int distance(char[][] grille) {

        int ligneA = -1;
        int colonneA = -1;

        int ligneB = -1;
        int colonneB = -1;

        // Une seule passe dans la grille
        for (int i = 0; i < grille.length; i++) {

            for (int j = 0; j < grille[i].length; j++) {

                if (grille[i][j] == 'A') {
                    ligneA = i;
                    colonneA = j;
                }

                if (grille[i][j] == 'B') {
                    ligneB = i;
                    colonneB = j;
                }
            }
        }

        // Distance de Manhattan
        return Math.abs(ligneA - ligneB) + Math.abs(colonneA - colonneB);
    }
    /************************************************************************************************************************************
    Partie sansDoublons
    ************************************************************************************************************************************/
    public List<Integer> sansDoublons(int[] t) {

        List<Integer> sansDoubles = new ArrayList<>();

        for (int num : t) {

            if (!sansDoubles.contains(num)) {
                sansDoubles.add(num);
            }
        }

        return sansDoubles;
    }
    
   
    
    
}