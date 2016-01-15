package Arbre;

import java.util.Comparator;

public class Comparateur implements Comparator<Mot> {

	@Override
	public int compare(Mot a, Mot b) { return a.compareTo(b);}

}