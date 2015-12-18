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
	private ArrayList<Integer> frequences;
	private ArrayList<Integer> fichiers;
	private Hashtable<Integer, File> refFichiers;
	

	public LectureFichier(String adresseRepertoire) throws IOException {
		super();
		this.mots = new ArrayList<String>();
		this.frequences = new ArrayList<Integer>();
		this.fichiers = new ArrayList<Integer>();
		File repertoire = new File(adresseRepertoire);
		BufferedReader br = null;
		this.refFichiers = new Hashtable<>();
		int numFichier = 1;
		for(File file : repertoire.listFiles()) {
			br = new BufferedReader(new FileReader(file));
			ArrayList<String> mots = this.motsFichier(br);
			for(String mot : mots) {
				int emplacement = this.trouvePosition(mot, mots);
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
