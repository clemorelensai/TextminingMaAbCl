package Client;

import java.util.ArrayList;
import java.util.TreeSet;

import indexation.LectureFichier;

public class RequetesDictionnaire implements Requetes {
	
	private LectureFichier index;
	

	
	
	public RequetesDictionnaire(LectureFichier ind) {
		this.index = ind;
	}
	
	
	
	public double max(ArrayList<Double> list) {
		double max=0;
		for (int i=0;i<list.size();i++) {
			if (max < i) {
				max = i;
			}
		}
		return max;
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
	
	public ArrayList<Integer> triRespectif(ArrayList<Double> list1, ArrayList<Integer> list2) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if ((list1.size()==list2.size())) {
			
			for (int j = 0;j<list1.size();j++) {
				int i = j;
				while(i!=max(list1)) {
					i++;
				}
						list1 = echangeDouble(list1,i,j);
						list2 = echangeInt(list2,i,j);
				
			}
			
		}
		else {
			System.out.println("TAILLES NON COMPATIBLES (triRespectif)");
		}
		return res;
	}


	@Override
	public ArrayList<Integer> requeteSimple(String terme) {
		// on recherche le nb d'occurrences de t dans chaque document et on calcule un score (tdf/idf p. ex.)
		ArrayList<Integer> documents = new ArrayList<Integer>();
		ArrayList<Double> tfidf = new ArrayList<Double>();
		int position = index.trouvePosition(terme);

		if (index.getMots().get(position).equals(terme)) {
			System.out.println("Position du terme '" + terme + "' dans l'index : " + position);
			
			documents = index.getFichiers().get(position);
			
			System.out.println("Documents comportant le terme '" + terme + "' dans l'index : " + documents.toString());


				for(int i=0;i<documents.size();i++) {
				double tf = index.getFrequences().get(position).get(i);
				//System.out.println(tf);
				double df = documents.size();
				double idf = index.getTailleRepertoire()/Math.log10(df); 
				tfidf.add(tf*idf);
				}
				System.out.println("tf/idf respectifs : " + tfidf.toString());
				
				documents = triRespectif(tfidf, documents);
				
				System.out.println("Documents triés par pertinence : " + documents.toString());
				
				
		}
		// on retourne la liste des documents les plus pertinents
		return documents;
	}

	@Override
	public TreeSet<String> requeteAnd(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public TreeSet<String> requeteOr(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
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
