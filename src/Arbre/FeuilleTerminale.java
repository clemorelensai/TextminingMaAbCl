package Arbre;


public class FeuilleTerminale extends ChildNode{

	/** INFORMATION DU NEOUD TERMINAL AVEC TF, IDF*/
	public FeuilleTerminale(Mot mot){super(TypeChile.T); this.mot=new Mot(mot);}
	
	
	public Mot mot;
	

}
