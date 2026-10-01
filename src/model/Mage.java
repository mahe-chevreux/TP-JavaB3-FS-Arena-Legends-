package model;

public class Mage extends Combattant {
	
	private int mana;
	public Mage (String nom, int pvMax, int pv, int attaque, int defense ) 
	{
		super(nom,pvMax,pv,attaque,defense);
		
		mana = 100;
	}
	
	public int getMana() {
		return mana;
	}

	@Override
	public String getClasse() {
	    return "Mage";
	}


	@Override
	public int attaquer(Combattant cible) {
		
		int degats = getAttaque();
		
		if (mana >= 30 )
		{
			degats = degats * 2;
			mana = mana - 30;
			cible.subirDegatsBruts(degats);
		}
		else
		{
			
			degats = degats / 2;
			mana += 15;
		}
		
		if (mana >100 ) 
		{
			mana = 100;
		}
		// TODO Auto-generated method stub
		return degats;
	}

}
