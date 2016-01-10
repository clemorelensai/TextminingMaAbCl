package Client;

import java.util.ArrayList;
import java.util.TreeSet;

import indexation.LectureFichier;

public class RequetesCosinus implements Requetes {
	
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

	@Override
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
