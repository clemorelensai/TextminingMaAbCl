package Client;

import java.util.ArrayList;
import java.util.TreeSet;

import indexation.LectureFichier;

public interface Requetes {
	

	
	public ArrayList<Integer> requeteSimple(String terme);
	// retourne les documents les plus pertinents dans la recherche de "terme"

	
	public ArrayList<Integer> requeteAnd(ArrayList<String> termes);
	// retourne les documents les plus pertinents contenant les termes "termes"
	
	public ArrayList<Integer> requeteOr(ArrayList<String> termes);
	// retourne les documents les plus pertinents contenant au moins l'un des termes parmi "termes"
	
	public TreeSet<String> requeteXor(ArrayList<String> termes);
	// retourne les documents les plus pertinents contenant un seul des termes parmi "termes"
	
	public TreeSet<String> requeteNear(ArrayList<String> termes);
	// retourne les documents les plus pertinents contenant les termes "termes" collés entre eux
	
	public TreeSet<String> requeteNot(ArrayList<String> termes);
	// retourne les documents les plus pertinents ne contenant PAS les termes "termes"
	
	public TreeSet<String> requeteSentence(ArrayList<String> termes);
	// retourne les documents les plus pertinents contenant les termes "termes" dans une même phrase
	
	public TreeSet<String> requeteParagraph(ArrayList<String> termes);
	// retourne les documents les plus pertinents contenant les termes "termes" dans un même paragraphe
	

}
