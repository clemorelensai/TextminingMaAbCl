package Arbre;

import java.util.TreeMap;


public class Feuille extends ChildNode{
	
	public TreeMap<Mot, Noeud> liste;

	public Feuille() {
		super(TypeChile.F);
		liste = new TreeMap<Mot, Noeud>(Noeud.comp);
	}

	public Feuille(TreeMap<Mot, Noeud> liste) {
		super(TypeChile.F);
		this.liste = liste;	
	}
	
	

}
