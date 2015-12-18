import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

public class LectureFichier {

	private ArrayList<String> mots;
	private ArrayList<ArrayList<Integer>> frequences;
	private ArrayList<ArrayList<Integer>> fichiers;
	private Hashtable<Integer, File> refFichiers;
	

	public LectureFichier(String adresseRepertoire) throws IOException {
		super();
		this.mots = new ArrayList<String>();
		this.frequences = new ArrayList<ArrayList<Integer>>();
		this.fichiers = new ArrayList<ArrayList<Integer>>();
		File repertoire = new File(adresseRepertoire);
		BufferedReader br = null;
		this.refFichiers = new Hashtable<>();
		int numFichier = 1;
		for(File file : repertoire.listFiles()) {
			br = new BufferedReader(new FileReader(file));
			ArrayList<String> mots = this.motsFichier(br);
			for(String mot : mots) {
				int emplacement = this.trouvePosition(mot, mots);
				if(emplacement < mots.size() & mots.get(emplacement).equals(mot)) {
					if(fichiers.get(emplacement).get(fichiers.get(emplacement).size()-1) != numFichier) {
						//frequences.set(emplacement, frequences.get(emplacement)+1);
						
					}
				} else {
					
				}
			}
			
			refFichiers.put(numFichier++, file);
		}
	}
	
	private ArrayList<String> motsFichier(BufferedReader br) throws IOException {
		ArrayList<String> mots = new ArrayList<>();
		String currentLine;
		while ((currentLine = br.readLine()) != null) {
			String[] ligne = currentLine.split("\t");
			mots.add(ligne[ligne.length-1]);
		}
		return mots;
	}
	
	private int trouvePosition(String mot, ArrayList<String> dictionnaire) {
		return 0;
	}

}