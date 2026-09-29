package model;
import java.util.Random;
import java.util.Scanner;



public class Main {

    Scanner sc = new Scanner(System.in);
    int dé;
    int point;
    int coup;
    int crit;
    int total;
    int chance;
    int pourcentage;
    int résult;
    /************************************************************************************************************************************
    Menu
    ************************************************************************************************************************************/
    public void menu(int choix) {

        do {
        	
        	System.out.println("=== ARENA LEGENDS ===");
            System.out.println("1 - Lancé un dé");
            System.out.println("2 - Calculer un rang");
            System.out.println("3 - Test de coup critique");
            System.out.println("0 - Quitter");
            System.out.print("Votre choix : ");

            choix = sc.nextInt();

            switch (choix) {

            
      /*****************************************************************************************************************************
                    Lancé de dé
      *****************************************************************************************************************************/
                case 1:
                	while (dé > 20 || dé < 4)
                	{
                		if (dé > 20 || dé < 4)
                		{
                			System.out.println("Erreur veuillez séléctionner un chiffre paire entre 4 et 20 pour le dé");
                			
                		}
                		else
                		{
                			
                		}
                    System.out.println("Veuillez sélectionner un dé qui comporte entre 4 et 20 face :   ");
                    
                    dé = sc.nextInt();
                	}
                	
                    Random random = new Random();
                    résult = random.nextInt(dé) +1 ;
                    System.out.println("Le résultat du dé est : " + résult);
                        break;
                    
                    
                    
           /************************************************************************************************************************************
                        Calcul de rang.
           ************************************************************************************************************************************/
                case 2:
                    System.out.println("Veuillez saisir vos points :  ");
                    point = sc.nextInt();
                    
                    if (point > 0 && point <= 99) {
                    	System.out.println("Vous êtes de rang bronze.");
                    }
                    else if (point >99 && point <= 499)
                    {
                    	System.out.println("Vous êtes de rang argent.");
                    }
                    else if (point > 499 && point <= 1499)
                    {
                    	System.out.println("Vous êtes de rang Or.");
                    }
                    else if (point > 1499) 
                    {
                    	System.out.println("Vous êtes une Légende !!!");
                    }
                    else
                    {
                    	System.out.println("Erreur les nombres négatifs ne sont pas autorisés.");
                    }
                    break;


                    /************************************************************************************************************************************
                    Test coups critiques
                    ************************************************************************************************************************************/            
                case 3:
                    System.out.println("Test coup critique");
                   
                    total = 10000;
                     for (int i = 0; i < 10001; i++) {
                    	Random random2 = new Random();
                    	chance = random2.nextInt(100);
                    	 if (chance <= 15) 
                    	 {
                    		 crit = crit +1;
                    	 } 
                    	 else
                    	 {
                    		 
                    	 }
                     }
                     pourcentage = 100 * crit / total; 
                     System.out.println("Vous avez fait " + crit + " soit un pourcentage de " + pourcentage + " % de taux critique.3");
                     break;
                
                case 0:
                	System.out.println("Au revoir !");
                	break;
                	
                default :
                    System.out.println("Choix incorrect");
            }

        } while (choix != 0);
    }

    public static void main(String[] arg) {

        Main m = new Main();

        m.menu(0);
    }
}
