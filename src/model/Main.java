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
            System.out.println("4 - Lancer un tournoi");
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


                       
                case 3:
                    System.out.println("Test coup critique");
                    int serie = 0;
                    int maxSerie = 0;
                   
                     for (int i = 0; i < 10000; i++) {
                    	Random random2 = new Random();
                    	chance = random2.nextInt(100);
                    	 if (chance <= 15) 
                    	 {
                    		 crit++;
                    		 serie ++;
                    	 } 
                    	 
                    	 if (serie > maxSerie)
                    	 {
                    		 maxSerie = serie;
                    	 }
                    	 else
                    	 {
                    		 serie = 0;
                    	 }
                 
                     }
                     pourcentage = 100 * crit / 10000; 
                     System.out.println("Vous avez fait une série maximum de critique de : " + maxSerie);
                     System.out.println("Vous avez fait " + crit + " soit un pourcentage de " + pourcentage + " % de taux critique.");
                     
                     
                     break;
                     /************************************************************************************************************************************
                     		Affichage Tournoi
                      ************************************************************************************************************************************/

					case 4: {
					
					    System.out.println("\n===== ARENA LEGENDS : TOURNOI =====");
					
					    
					    Tournoi tournoi = new Tournoi();
					
					    
					    Combattant c1 = new Guerrier("Tryndamere", 100, 100, 25, 10);
					    Combattant c2 = new Mage("Veigar", 80, 80, 30, 5);
					    Combattant c3 = new Voleur("Pyke", 90, 90, 20, 8, 20);
					    Combattant c4 = new Paladin("Leona", 120, 120, 18, 15);
					    Combattant c5 = new Guerrier("Garen", 110, 110, 22, 12);
					    Combattant c6 = new Mage("Ahri", 75, 75, 28, 4);
					    Combattant c7 = new Voleur("Talon", 85, 85, 24, 6, 20);
					    Combattant c8 = new Paladin("Taric", 130, 130, 15, 18);
					
					    
					    tournoi.inscrire(c1);
					    tournoi.inscrire(c2);
					    tournoi.inscrire(c3);
					    tournoi.inscrire(c4);
					    tournoi.inscrire(c5);
					    tournoi.inscrire(c6);
					    tournoi.inscrire(c7);
					    tournoi.inscrire(c8);
					
					  
					    System.out.println("\n===== PARTICIPANTS =====");
					
					    for (Combattant c : tournoi.getParticipants()) {
					        System.out.println(c);
					    }
					
					    
					    System.out.println("\n===== TEST COPIE DEFENSIVE =====");
					
					    System.out.println("Participants avant clear : "
					            + tournoi.getParticipants().size());
					
					    tournoi.getParticipants().clear();
					
					    System.out.println("Participants après clear : "
					            + tournoi.getParticipants().size());
					
					    
					    System.out.println("\n===== DEBUT DU TOURNOI =====");
					
					    tournoi.lancer();
					
					    
					    System.out.println("\n===== CLASSEMENT FINAL =====");
					
					    int rang = 1;
					
					    for (Combattant c : tournoi.classement()) {
					        System.out.println(rang + ". " + c.getNom()
					                + " - " + c.getVictoires() + " victoire(s)");
					        rang++;
					    }
					
					    
					    System.out.println("\n===== STATISTIQUES PAR CLASSE =====");
					
					    tournoi.statsParClasse();
					
					    System.out.println("\n===== FIN DU TOURNOI =====");
					
					    break;
					} 
                     
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
