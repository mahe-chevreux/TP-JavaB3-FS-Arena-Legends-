package model;

public abstract class  Combattant {
	
	private String nom; 
	private int pvMax;
	private int pv;
	private int attaque;
	private int defense;
	private static int combattant;
	private int[] historiqueDegats = new int[5];
	private int victoires;
	public  Combattant() {
		
	}
	/************************************************************************************************************************************
    Partie création Combattant.
	 ************************************************************************************************************************************/
	public Combattant(String nom, int pvMax, int pv, int attaque, int defense) throws IllegalArgumentException {
		if (nom == null || nom.length() < 3 || nom.length() > 15) 
			{
			throw new IllegalArgumentException("Le nom doit avoir minimum de 3 caractères et maximum 15 caractères ! nom reçu : " + nom + ", nombres de caractères : " + nom.length());
			}
		
		if (pvMax < 50 || pvMax > 300 )
			{
			throw new IllegalArgumentException("Les PV Maximum ne peuvent pas être inférerieur à 50 pv et pas être supérieur à 300 ! Valeurs PV Maximum reçu : " + pvMax);
			}
		
		if (pv < 0 || pv > pvMax)
			{
			throw new IllegalArgumentException("Les PV ne peuvent être inférieur à 0 et ne peuvent pas dépasser les PV Maximum ! Valeurs PV reçu : " + pv);
			}
		
		if (attaque < 5 || attaque > 50)
			{
			throw new IllegalArgumentException("Les attaques ne peuvent pas être inférieurs à 5, ni être supérieur à 50 ! Valeurs attaque reçu : " + attaque);
			}
		
		if (defense < 0 || defense > 30)
			{
			throw new IllegalArgumentException("La défense ne peut pas être inférieur à 0 mais également supérieur à 30 ! Valeurs defense reçu : " + defense);
			}
		
		
		this.nom = nom;
		this.pvMax = pvMax;
		this.pv = pv;
		this.attaque = attaque;
		this.defense = defense;
	}
	/************************************************************************************************************************************
    			Partie Get / Set.
	 ************************************************************************************************************************************/
	public String getNom() {
		return nom;
	}

	public int getPvMax() {
		return pvMax;
	}

	public int getPv() {
		return pv;
	}

	public int getAttaque() {
		return attaque;
	}

	public int getDefense() {
		return defense;
	}

	public static int getCombattant() {
		return combattant;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public static void setCombattant(int combattant) {
		Combattant.combattant = combattant;
	}
	
	public abstract int attaquer(Combattant cible);
	
	/************************************************************************************************************************************
    Méthode Victoires.
	 ************************************************************************************************************************************/
	public int getVictoires() {
	    return victoires;
	}

	public void ajouterVictoire() {
	    victoires++;
	}
	/************************************************************************************************************************************
    Méthode subirDegats.
	 ************************************************************************************************************************************/
	public void subirDegats(int d)
	{
		
		d = d - defense;
		if (d < 1)
		{
			d = 1;
			
		}
		if(pv <= 0)
		{
			pv = pv - d;
			d = 0;
		}
		ajouterHistorique(d);
		
		
	}
	/************************************************************************************************************************************
    Méthode soigner.
	 ************************************************************************************************************************************/
	public void soigner(int s)
	{
		pv = pv + s;
		
		if (pv > pvMax)
			{
			pv = pvMax;
			}
		
		if (pv <= 0 )
			{
			s = 0;
			}
		
			
		
	}
	
	/************************************************************************************************************************************
    Méthode HistoriqueDegats.
	 ************************************************************************************************************************************/
	public int[] getHistoriqueDegats() {
	    return historiqueDegats.clone();
	}
	
	/************************************************************************************************************************************
    Méthode estKO.
	 ************************************************************************************************************************************/
	public boolean estKO() 
		
	
	{
		return pv <= 0;
	}
	
	/************************************************************************************************************************************
    Méthode subirDegatsBruts.
	 ************************************************************************************************************************************/
	protected void subirDegatsBruts(int d) {

	    pv -= d;

	    if (pv < 0) {
	        pv = 0;
	    }
	}
	
	/************************************************************************************************************************************
    Méthode getClasse.
	 ************************************************************************************************************************************/
	public abstract String getClasse();
	
	
		@Override
		public String toString() 
		{ 
			return getClasse()+ " " + nom + " [" + pv + "/" + pvMax + " PV] ATK " + attaque + " DEF " + defense;
	
		}
		
		private void ajouterHistorique(int degats) {

		    for (int i = 0; i < historiqueDegats.length - 1; i++) {
		        historiqueDegats[i] = historiqueDegats[i + 1];
		    }

		    historiqueDegats[historiqueDegats.length - 1] = degats;
		}
		
		/************************************************************************************************************************************
        Exemple TEST. 
		 ************************************************************************************************************************************/
		public static void main(String[] args) 
		{
			Combattant c = new Mage("Veigar", 70, 56 , 20, 3);
			Combattant c2 = new Guerrier("Tryndamere", 80, 24 , 15, 6);
			Combattant c3 = new Voleur("Pyke", 78, 60 , 15, 6, 20);
			System.out.println("Bonjour");
			
			System.out.println(c); c.subirDegats(20); 
			System.out.println(c); c.soigner(10); 
			System.out.println(c); 
			System.out.println("K.O. ? " + c.estKO()); 
			System.out.println(c2); c2.subirDegats(14); 
			System.out.println(c2); c2.soigner(45); 
			System.out.println(c2); 
			System.out.println("K.O. ? " + c2.estKO());
			System.out.println(c3); c3.subirDegats(14); 
			System.out.println(c3); c3.soigner(45); 
			System.out.println(c3); 
			System.out.println("K.O. ? " + c3.estKO());
		}
		
}