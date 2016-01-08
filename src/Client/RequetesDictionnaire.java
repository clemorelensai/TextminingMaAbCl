package Client;

import java.util.ArrayList;
import java.util.TreeSet;

import indexation.LectureFichier;

public class RequetesDictionnaire implements Requetes {
	
	
	public RequetesDictionnaire() {
	}


	@Override
	public ArrayList<Integer> requeteSimple(String terme, LectureFichier index) {
		// on recherche le nb d'occurrences de t dans chaque document et on calcule un score (tdf/idf p. ex.)
		ArrayList<Integer> documents = new ArrayList<Integer>();
		ArrayList<Double> tfidf = new ArrayList<Double>();
		int position = index.trouvePosition(terme);
		if (index.getMots().get(position).equals(terme)) {

				documents = index.getFichiers().get(position);
				for(int i=0;i<documents.size();i++) {
			//	int tf = 
			//	tfidf.set(i, );
				}
				
		}
		// on retourne la liste des documents les plus pertinents
		return documents;
	}

	@Override
	public TreeSet<String> requeteAnd(ArrayList<String> termes, LectureFichier index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> requeteOr(ArrayList<String> termes, LectureFichier index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> requeteXor(ArrayList<String> termes, LectureFichier index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> requeteNear(ArrayList<String> termes, LectureFichier index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> requeteNot(ArrayList<String> termes, LectureFichier index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> requeteSentence(ArrayList<String> termes, LectureFichier index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> requeteParagraph(ArrayList<String> termes, LectureFichier index) {
		// TODO Auto-generated method stub
		return null;
	}

}
