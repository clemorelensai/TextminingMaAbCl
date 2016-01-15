package Arbre;

import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;



public class Noeud implements Comparable<Noeud>{
	
public Mot value;
	
	public ChildNode child;
	
	public static Comparateur comp = new Comparateur();
	
	public static TreeMap<Mot, Noeud> arbre = new TreeMap<Mot,Noeud>();

// ==================================== MAIN ============================================



	public static void main(String args[]){
		
		String romane = "romane";
		
		Noeud.ajouter(romane);

		Noeud.ajouter("romanus");
		
		Noeud.ajouter("romulus");
		Noeud.ajouter("Rubens");
		
		Noeud.ajouter("ruber");
		Noeud.ajouter("Rubicon");
		Noeud.ajouter("Rubicundus");
		Noeud.ajouter("Rub");
		
		Noeud.ajouter("web");
		
		Noeud.affichageArbre();
		
		System.out.println(Noeud.recherche("romane"));
		
		
		
     	System.out.println("\n======================= FIN MAIN =======================");
	}

public static Mot recherche(String search){ 
	Mot motSearch= new Mot(search);
	
	Noeud racineSelectionne = arbre.get(motSearch);
	
	System.out.println(" Racine :"+racineSelectionne);
	
	return racineSelectionne.recherche(motSearch);

}
		
public  Mot recherche(Mot motSearch){
	
	System.out.println(" search :"+motSearch);
	
	if(value.extraireRecherche(motSearch)){
	
		Noeud filsSelection = ((Feuille)child).liste.get(motSearch);
		
		System.out.println(" Noeud selectionne :"+filsSelection.value);
		
		return filsSelection.recherche(motSearch);
	}
	
	else {return ((FeuilleTerminale) child).mot;}	
	
}

public static void ajouter(String nouveau){ajouter(new Noeud(new Mot(nouveau.toLowerCase())));	}

public static void ajouter(Noeud nouveau){
	
	Noeud racineSelectionne = arbre.remove(nouveau.value);
	
	if( racineSelectionne != null){
		
		System.out.println(" nouveau :"+nouveau.value+" racine "+racineSelectionne.value);
		
		arbre.remove(racineSelectionne.value);
		
		Noeud nouveauRacine=racineSelectionne.ajoutUnNoeud(nouveau); 
		
		((Feuille) nouveauRacine.child ).liste.put(racineSelectionne.value,racineSelectionne);
		
		arbre.put(nouveauRacine.value, nouveauRacine);
	}
	
	else { arbre.put(nouveau.value, nouveau);}
}

public Noeud  ajoutUnNoeud(Noeud nouveau){
	
	String motParent = value.extraire(nouveau.value);
	
	Noeud  noeudParent = new Noeud(motParent,nouveau);

	return noeudParent;
	
}


public static void affichageArbre(){
	
	for(Noeud racine : arbre.values()) {
		
		System.out.println(racine);
		
		if(racine.child.type==TypeChile.F ) {racine.affichage(racine.value.nom.length());}

	}
	
}

/** On suppose que le child est non vide */
public void affichage(int debut){
  if(child.type==TypeChile.F){
	  for(Noeud unNoeud : ((Feuille)child).liste.values()){
			  String resultat="";
			  
			  for(int vide=0;vide<debut;vide++) { resultat="  "+resultat;}
			
			  resultat+=unNoeud; 
		      
			  System.out.println(resultat);
	
			  if(unNoeud.child.type==TypeChile.F)
				  unNoeud.affichage(debut+unNoeud.value.nom.length());
			  
//			  else  System.out.println(((FeuilleTerminale)unNoeud.child).mot);
	}
  }
}



// ==================================== AFFICHAGE ============================================

// ==================================== UTILITAIRE ============================================


public Noeud(String mot){this(new Mot(mot));  }

public Noeud(String  mot,Noeud fils){
	this.value =new Mot(mot);
	this.child = new Feuille();
	((Feuille)this.child).liste.put(fils.value, fils);
}

/** PAR DEFEAUT UN NOEUD EST UNIQUE**/
public Noeud(Mot value) {this.value = value;child =new FeuilleTerminale(value);	}



@Override
public int compareTo(Noeud autre) {return value.compareTo(autre.value);}

@Override
public String toString() {   if(StringUtils.isBlank(value.nom)) return ""; else return "|"+value+"|";}
	






}
