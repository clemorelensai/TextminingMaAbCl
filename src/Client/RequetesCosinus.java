package Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import indexation.LectureFichier;

public class RequetesCosinus {
	
	private LectureFichier index;

	public RequetesCosinus(LectureFichier index) {
		super();
		this.index = index;
	}
	
	public double calculSimilarite(ArrayList<Integer> doc1, ArrayList<Integer> doc2, int tailleDoc) {
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
		
		res /=tailleDoc;
		
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
		int tailleDoc;
		
		for(int i=0; i<index.getFrequences().get(numMot).size(); i++) {
			doc1.add(1);
			doc2.add(index.getFrequences().get(numMot).get(i));
			tailleDoc = (int) index.getRefFichiers().get(index.getFichiers().get(numMot).get(i)).get(1);
			cos = calculSimilarite(doc1, doc2, tailleDoc);
			temp = placeSimilarite(similarites, cos);
			
			similarites.add(temp, cos);
			documents.add(temp, index.getFichiers().get(numMot).get(i));
			
			doc1.clear();
			doc2.clear();
		}
		
		return documents;
	}

	public ArrayList<Integer> requeteOr(ArrayList<String> termes) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		ArrayList<Double> similarites = new ArrayList<Double>();
		HashMap<Integer, ArrayList<Integer>> documents = new HashMap<>();
		ArrayList<Integer> tailles = new ArrayList<>();
		int emplacement;
		//docsMot : documents contenant le mot
		ArrayList<Integer> docsMot;
		ArrayList<Integer> temp;
		double similarite;
		for(int i=0; i<termes.size(); i++) {
			emplacement = index.trouvePosition(termes.get(i));
			if(index.getMots().size()>emplacement) {
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
		}
		
		Iterator<Integer> it = documents.keySet().iterator();
		while (it.hasNext()){
		   int cle = it.next();
		   ArrayList<Integer> doc1 = documents.get(cle);
		   for(int i=doc1.size(); i<termes.size(); i++) {
			   doc1.add(0);
		   }

		   ArrayList<Integer> doc2 = new ArrayList();
		   for(int j=0; j<termes.size(); j++) {
			   doc2.add(1);
		   }
		   similarite = this.calculSimilarite(doc1, doc2, (int) index.getRefFichiers().get(cle).get(1));
		   int place = this.placeSimilarite(similarites, similarite);
		   res.add(place, cle);
		   similarites.add(place, similarite);
		}
		
		for (int i = 0; i < res.size(); i++) {
			System.out.println(index.getRefFichiers().get(res.get(i)));
			System.out.println(similarites.get(i));
		}
		
		return res;
	}

	public TreeSet<String> requeteAnd(ArrayList<String> termes) {
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
