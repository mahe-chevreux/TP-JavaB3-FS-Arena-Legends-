
package model;

import java.util.Random;

public class Voleur extends Combattant {

    private int esquive;
    private Random random = new Random();

    /************************************************************************************************************************************
    Constructeur Voleur.
	 ************************************************************************************************************************************/
    public Voleur(String nom, int pvMax, int pv,
                  int attaque, int defense, int esquive) {

        super(nom, pvMax, pv, attaque, defense);

        if (esquive < 10 || esquive > 40) {
            throw new IllegalArgumentException(
                "esquive doit être entre 10 et 40, reçu : " + esquive
            );
        }

        this.esquive = esquive;
    }

    public int getEsquive() {
        return esquive;
    }

    
    @Override
	public String getClasse() {
	    return "Voleur";
	}
    
    @Override
    public int attaquer(Combattant cible) {

        int degats = getAttaque();

        
        if (random.nextInt(100) < 25) {
            degats = degats * 2;
        }

        cible.subirDegats(degats);

        return degats;
    }

    @Override
    public void subirDegats(int d) {

       
        if (random.nextInt(100) < esquive) {

            System.out.println(getNom() + " esquive complètement l'attaque !");

            return;
        }

        
        super.subirDegats(d);
    }
}
