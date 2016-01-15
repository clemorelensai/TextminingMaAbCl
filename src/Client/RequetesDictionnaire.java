package Client;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import indexation.LectureFichier;

public class RequetesDictionnaire implements Requetes {
	
	private LectureFichier index;
	

	
	
	public RequetesDictionnaire(LectureFichier ind) {
		this.index = ind;
	}
	
	
	
	
	public ArrayList<Integer> echangeInt(ArrayList<Integer> list, int i, int j) {
		ArrayList<Integer> res = list;
		Integer obji = list.get(i);
		res.set(i, res.get(j));
		res.set(j, obji);
		return res;
	}
	
	public ArrayList<Double> echangeDouble(ArrayList<Double> list, int i, int j) {
		ArrayList<Double> res = list;
		Double obji = list.get(i);
		res.set(i, res.get(j));
		res.set(j, obji);
		return res;
	}
	
	public ArrayList<Integer> triRespectif(ArrayList<Integer> list1, ArrayList<Integer> list2) {
		if ((list1.size()==list2.size())) {	
			for (int j = 0;j<list1.size();j++) {
				double maxLocal = 0;
				int indexMax = -1;
				for (int i = j; i<list1.size();i++) {
				if (list1.get(i) > maxLocal) {
					maxLocal = list1.get(i);
					indexMax = i;
						}	
					}
						list1 = echangeInt(list1,indexMax,j);
						list2 = echangeInt(list2,indexMax,j);
				}
			}
			
		
		else {
			System.out.println("TAILLES NON COMPATIBLES (triRespectif)");
		}
		return list2;
	}
	
	
	public ArrayList<Integer> tfidf(String terme, ArrayList<Integer> documents) { // calcul le tfidf d'un terme au sein d'une liste de documents fournie
		int position = index.trouvePosition(terme);
		ArrayList<Integer> tfidf = new ArrayList<Integer>();
		
		for(int i=0;i<documents.size();i++) {
			double tf = index.getFrequences().get(position).get(i);
			System.out.println("tf du document " + (toRef(documents).get(i)) + " : " + tf);
			double df = documents.size();
			double idf = index.getTailleRepertoire()/Math.log10(df); 
			System.out.println("idf du document " + (toRef(documents).get(i)) + " : " + idf);
			tfidf.add((int) (tf*idf));
			System.out.println("tfidf du document " + (toRef(documents).get(i)) + "pour le mot " + terme + " : "+ tfidf.get(i));
		}
		System.out.println("documents respectifs : " + toRef(documents).toString());
		System.out.println("tf/idf respectifs : " + tfidf.toString());
		return tfidf;
	}
	
	
	public ArrayList<String> toRef(ArrayList<Integer> listeFichiers) {
		ArrayList<String> liste = new ArrayList<String>();
		for (int i=0;i<listeFichiers.size();i++) {
			liste.add(((File)index.getRefFichiers().get(listeFichiers.get(i)).get(0)).getName());
		}
		
		return liste;
	}
	


	@Override
	public ArrayList<Integer> requeteSimple(String terme) {//on a fait le choix d'utiliser deux listes différentes pour regrouper les fichiers et leur fréquence respective, on aurait peut-être pu utiliser un Treeset...
		// on recherche le nb d'occurrences de t dans chaque document et on calcule un score (tdf/idf p. ex.)
		ArrayList<Integer> documents = new ArrayList<Integer>();
		
		int position = index.trouvePosition(terme);

		if (index.getMots().get(position).equals(terme)) { // on vérifie que le mot figure bien dans l'index
			System.out.println("Position du terme '" + terme + "' dans l'index : " + position);
			
			documents = index.getFichiers().get(position);
			
			System.out.println("Documents comportant le terme '" + terme + "' dans l'index : \n" + documents.toString());

				
			ArrayList<Integer> tfidf = tfidf(terme,documents);
				
				documents = triRespectif(tfidf, documents); //réordonnement des deux listes par ordre croissant du tfidf
				
				System.out.println("Documents triés par pertinence : \n" + toRef(documents));
				System.out.println("tf/idf associés : \n" + tfidf.toString());
				
				
		}
		// on retourne la liste des documents les plus pertinents
		return documents;
	}

	@Override
	public ArrayList<Integer> requeteAnd(ArrayList<String> termes) {
		ArrayList<Integer> documents = new ArrayList<Integer>();
		
		boolean presenceDansIndex = true;
		
		
		for (int t=0;t<termes.size();t++) { //on vérifie que tous les mots figurent dans l'index
			
			int position = index.trouvePosition(termes.get(t));
			if (index.getMots().get(position).equals(termes.get(t))) {
				System.out.println("Position du terme '" + termes.get(t) + "' dans l'index : " + position);				
				documents = index.getFichiers().get(position);

				}
			else {
				presenceDansIndex = false;
			
			}
		}
		if (presenceDansIndex) {
			// documents contient actuellement la liste des documents qui contiennent le dernier terme
			for (int t=0;t<termes.size()-1;t++) { // on raccourcit la liste au fur et à mesure (et on exclut le dernier terme)
				int position = index.trouvePosition(termes.get(t));
				ArrayList<Integer> documentsTerme = index.getFichiers().get(position);
				for (int d=0;d<documents.size();d++) {
						if (!documentsTerme.contains(documents.get(d))) {
							documents.remove(d);
						}
				}
			}
			
		}
		ArrayList<Integer> tfidf = new ArrayList<Integer>();
		for (int d=0;d<documents.size();d++) { // initialisation de la liste des tfidf à 0
			tfidf.add(0);
			
		}
		// Calcul du tf/idf par sommation des tfidf de chaque document
		for (int t=0;t<termes.size();t++) {
			
			for(int d=0;d<tfidf.size();d++) {
			System.out.println(toRef(documents).get(d));
			System.out.println(tfidf(termes.get(t),documents).get(d));
			tfidf.set(d, tfidf.get(d)+tfidf(termes.get(t),documents).get(d)); // sommation des listes (vérifier que c'est bien respectif)
			}
		//	System.out.println("boucle " + (t+1) +  " : ");
		//	System.out.println("Documents : \n" + toRef(documents));
		//	System.out.println("tf/idf associés : \n" + tfidf(termes.get(t),documents));
		//	System.out.println("tf/idf associés : \n" + tfidf.toString());
			
		}
		
		
		documents = triRespectif(tfidf, documents); //réordonnement des deux listes par ordre croissant du tfidf
		
		System.out.println("Documents triés par pertinence : \n" + toRef(documents));
		System.out.println("tf/idf associés : \n" + tfidf.toString());
		
		
		return documents; 
	}



	@Override
	public ArrayList<Integer> requeteOr(ArrayList<String> termes) {
		ArrayList<Integer> documents = new ArrayList<Integer>();
		
		boolean presenceDansIndex = false;
		
		
		for (int t=0;t<termes.size();t++) { //on vérifie que tous les mots figurent dans l'index
			
			int position = index.trouvePosition(termes.get(t));
			if (index.getMots().get(position).equals(termes.get(t))) {
				System.out.println("Position du terme '" + termes.get(t) + "' dans l'index : " + position);				
				documents = index.getFichiers().get(position);
				presenceDansIndex = true;

				}
		}
		if (presenceDansIndex) {
			// documents contient actuellement la liste des documents qui contiennent le dernier terme
			for (int t=0;t<termes.size()-1;t++) { // on agrandit la liste au fur et à mesure (et on exclut le dernier terme)

				int position = index.trouvePosition(termes.get(t));
				ArrayList<Integer> documentsTerme = index.getFichiers().get(position);
				for (int d=0;d<documents.size();d++) {
					if (!documents.contains(documentsTerme.get(d))); // on vérifie que documents ne contient pas déjà d
						if (!documentsTerme.contains(documents.get(d))) {
							documents.add(documentsTerme.get(d));
						}
				}
			}
			
		}
		ArrayList<Integer> tfidf = new ArrayList<Integer>();
		for (int d=0;d<documents.size();d++) { // initialisation de la liste des tfidf à 0
			tfidf.add(0);
		}
		// Calcul du tf/idf par sommation des tfidf de chaque document
		for (int t=0;t<termes.size();t++) {
			for(int d=0;d<tfidf.size();d++) {
			tfidf.set(d, tfidf.get(d)+tfidf(termes.get(t),documents).get(d)); // sommation des listes (vérifier que c'est bien respectif)
			}
		}
		// ATTENTION si le document ne contient PAS le terme ça risque de buger
		
		
		documents = triRespectif(tfidf, documents); //réordonnement des deux listes par ordre croissant du tfidf
		
		System.out.println("Documents triés par pertinence : \n" + toRef(documents));
		System.out.println("tf/idf associés : \n" + tfidf.toString());
		
		
		return documents; 
	}



	@Override
	public TreeSet<String> requeteXor(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public TreeSet<String> requeteNear(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public TreeSet<String> requeteNot(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public TreeSet<String> requeteSentence(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public TreeSet<String> requeteParagraph(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}

}
