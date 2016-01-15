package Arbre;

import org.apache.commons.lang3.StringUtils;

public class Mot implements Comparable<Mot> {
	public String nom;

	public String getNom() {	return nom;}

	public void setNom(String nom) {	this.nom = nom;}

	public Mot(String nom) {this.nom = nom;}
	
	public Mot(Mot mot ) {this.nom = mot.nom.toLowerCase();}
	
	public Mot(char nom) {this.nom = String.valueOf(nom);}
	
	
	/** On suppose  que les deux mots on forcement un truc en commun */
	public String  extraire( Mot nouveau ){
		
		int posIndFinCommun=  StringUtils.indexOfDifference(this.nom, nouveau.nom);
    	
    	String  parent =nom.substring(0, posIndFinCommun);
    	
    	nom=nom.substring(posIndFinCommun, this.nom.length());
    	
    	nouveau.nom = nouveau.nom.substring(posIndFinCommun, nouveau.nom.length());
	   
		return parent;
	 
	}
	
	public boolean  extraireRecherche( Mot nouveau ){
		
		int posIndFinCommun=  StringUtils.indexOfDifference(this.nom, nouveau.nom);
    	
		if(posIndFinCommun==-1) {return false;}
		
    	nouveau.nom= nouveau.nom.substring(posIndFinCommun, nouveau.nom.length());
    	
    	return true;
	  
	}
	
	
	public static void main(String args[]){
		Mot romane=new Mot("r");
		
		Mot search=new Mot("romane");
		
		romane.extraireRecherche(search);
		
		System.out.println(search);
		
		romane.extraireRecherche(search);
		
		System.out.println(search);
	}
	
	

	@Override
	public int compareTo(Mot autre) {
		return  (StringUtils.indexOfDifference(this.nom.toLowerCase(), autre.nom.toLowerCase())==0) ? -1 :0;
	}

	@Override
	public String toString() {return nom;}	
	
}
