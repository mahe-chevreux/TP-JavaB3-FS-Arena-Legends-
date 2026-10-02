package model;

public class Guerrier extends Combattant {
	private	int rage;
	
	/************************************************************************************************************************************
    Constructeur Guerrier.
	 ************************************************************************************************************************************/
	public Guerrier (String nom, int pvMax, int pv, int attaque, int defense ) 
	{
		super(nom,pvMax,pv,attaque,defense);
		
		rage = 0;
	}
	

	public int getRage() {
		return rage;
	}
	
	@Override
	public String getClasse() {
	    return "Guerrier";
	}
	
	
	@Override
	public int attaquer(Combattant cible) {
		// TODO Auto-generated method stub
		
		rage += 20;
		
		int degats = getAttaque();
		
		if (rage >= 100)
		{
			degats = degats * 2;
			rage = 0;
		}
		
		cible.subirDegats(degats);
		return degats;
	}
	
}
