package Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import indexation.LectureFichier;

public class RequetesCosinus {
	
	private LectureFichier index;

	public RequetesCosinus(LectureFichier index) {
		super();
		this.index = index;
	}
	
	public double calculSimilarite(ArrayList<Integer> doc1, ArrayList<Integer> doc2) {
		double res = 0;
		for(int i=0; i<doc1.size(); i++) {
			res += doc1.get(i)*doc2.get(i);
		}
		double diviseur = 0;
		for(int i=0; i<doc1.size(); i++) {
			diviseur += Math.pow(doc1.get(i), 2);
		}
		diviseur = Math.sqrt(diviseur);
		res /= diviseur;
		
		diviseur = 0;
		for(int i=0; i<doc2.size(); i++) {
			diviseur += Math.pow(doc2.get(i), 2);
		}
		diviseur = Math.sqrt(diviseur);
		res /= diviseur;
		
		return res;
	}
	
	public int placeSimilarite(ArrayList<Double> similarites, double similarite) {
		int res = 0;
		boolean emplacementTrouve = false;
		int lower = 0;
		int middle;
		int upper = similarites.size() - 1;
		if (similarites.size() == 0) {
			emplacementTrouve = true;
		} else if (similarite>similarites.get(upper)) {
			res = similarites.size();
			emplacementTrouve = true;
		}
		while (!emplacementTrouve) {
			middle = (lower + upper) / 2;
			if (middle == lower) {
				emplacementTrouve = true;
				if (similarite <= similarites.get(upper)) {
					res = lower;
				} else {
					res = upper;
				}
			}
			if (similarite > similarites.get(middle)) {
				lower = middle;
			} else {
				upper = middle;
			}
		}
		
		return res;
	}

	public ArrayList<Integer> requeteSimple(String terme) {
		ArrayList<Integer> documents = new ArrayList<Integer>();
		ArrayList<Double> similarites = new ArrayList<Double>();
		
		int numMot = index.trouvePosition(terme);
		int temp;
		double cos;
		ArrayList<Integer> doc1 = new ArrayList<>();
		ArrayList<Integer> doc2 = new ArrayList<>();
		
		for(int i=0; i<index.getFrequences().get(numMot).size(); i++) {
			doc1.add(1);
			doc2.add(index.getFrequences().get(numMot).get(i));
			cos = calculSimilarite(doc1, doc2);
			temp = placeSimilarite(similarites, cos);
			
			similarites.add(temp, cos);
			documents.add(temp, index.getFichiers().get(numMot).get(i));
			
			doc1.clear();
			doc2.clear();
		}
		
		return documents;
	}

	public ArrayList<Integer> requeteAnd(ArrayList<String> termes) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		ArrayList<Double> similarites = new ArrayList<Double>();
		HashMap<Integer, ArrayList<Integer>> documents = new HashMap<>();
		int emplacement;
		//docsMot : documents contenant le mot
		ArrayList<Integer> docsMot;
		ArrayList<Integer> temp;
		for(int i=0; i<termes.size(); i++) {
			emplacement = index.trouvePosition(termes.get(i));
			if(index.getMots().get(emplacement).equals(termes.get(i))) {
				docsMot = index.getFichiers().get(emplacement);
				for(int j=0; j<docsMot.size(); j++) {
					if(documents.containsKey(docsMot.get(j))) {
						temp = documents.get(docsMot.get(j));
					} else {
						temp = new ArrayList<Integer>();
						documents.put(docsMot.get(j), temp);
					}
					for(int k=temp.size(); k<i; k++) {
						temp.add(0);
					}
					temp.add(index.getFrequences().get(emplacement).get(j));
					documents.put(docsMot.get(j), temp);
				}
			}
		}
		
		for(int i=0; i<documents.size(); i++) {
			//TODO : a partir de la list des frequences par document, calculer la similarité
		}
		return res;
	}

	public TreeSet<String> requeteOr(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<String> requeteXor(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<String> requeteNear(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<String> requeteNot(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<String> requeteSentence(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<String> requeteParagraph(ArrayList<String> termes) {
		// TODO Auto-generated method stub
		return null;
	}

}
