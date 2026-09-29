package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

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