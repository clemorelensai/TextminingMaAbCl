package Client;

import java.util.ArrayList;
import java.util.TreeSet;

import indexation.LectureFichier;

public class RequetesArbre implements Requetes {
	LectureFichier index;

	@Override
	public ArrayList<Integer> requeteSimple(String terme) {

		return null;
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
