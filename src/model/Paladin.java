package model;

public class Paladin extends Guerrier {

	/************************************************************************************************************************************
    Constructeur Paladin.
	 ************************************************************************************************************************************/
    public Paladin(String nom, int pvMax, int pv,
                   int attaque, int defense) {

        super(nom, pvMax, pv, attaque, defense);
    }

    @Override
    public int attaquer(Combattant cible) {

        
        int degats = super.attaquer(cible);

        
        int soin = degats / 10;

        soigner(soin);

        return degats;
    }

    @Override
    public String getClasse() {
        return "Paladin";
    }
}