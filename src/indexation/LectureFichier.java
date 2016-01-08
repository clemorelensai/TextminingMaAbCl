package indexation;

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

	/**
	 * Cr�e un dictionnaire des fr�quences � partir d'un r�pertoire de fichiers
	 * texte
	 * 
	 * @param adresseRepertoire
	 * @throws IOException
	 */
	public LectureFichier(String adresseRepertoire) throws IOException {
		super();
		this.mots = new ArrayList<String>();
		this.frequences = new ArrayList<ArrayList<Integer>>();
		this.fichiers = new ArrayList<ArrayList<Integer>>();
		File repertoire = new File(adresseRepertoire);
		BufferedReader br = null;
		this.refFichiers = new Hashtable<>();
		int numFichier = 1;
		ArrayList<Integer> temp;
		for (File file : repertoire.listFiles()) {
			br = new BufferedReader(new FileReader(file));
			ArrayList<String> motsDuFichier = this.motsFichier(br);
			for (String mot : motsDuFichier) {
				int emplacement = this.trouvePosition(mot);
				boolean test = (emplacement < mots.size());
				if(test) {
					test = mots.get(emplacement).equals(mot);
				}
				if (test) {
					if (fichiers.get(emplacement).get(fichiers.get(emplacement).size() - 1) != numFichier) {
						// Le mot a d�j� �t� vu, mais pas dans ce document
						temp = frequences.get(emplacement);
						temp.add(1);
						frequences.set(emplacement, temp);
						temp = fichiers.get(emplacement);
						temp.add(numFichier);
						fichiers.set(emplacement, temp);
					} else {
						// Le mot a d�j� �t� vu dans ce document
						temp = frequences.get(emplacement);
						temp.set(temp.size() - 1, temp.get(temp.size() - 1) + 1);
						frequences.set(emplacement, temp);
					}
				} else {
					// Le mot n'a jamais �t� vu
					temp = new ArrayList<>();
					temp.add(1);
					frequences.add(emplacement, temp);
					temp = new ArrayList<>();
					temp.add(numFichier);
					fichiers.add(emplacement, temp);
					mots.add(emplacement, mot);
				}
			}

			refFichiers.put(numFichier++, file);
		}
	}
	
	

	public ArrayList<String> getMots() {
		return mots;
	}



	public ArrayList<ArrayList<Integer>> getFrequences() {
		return frequences;
	}



	public ArrayList<ArrayList<Integer>> getFichiers() {
		return fichiers;
	}



	public Hashtable<Integer, File> getRefFichiers() {
		return refFichiers;
	}



	/**
	 * Donne tous les mots lemmatis�s du fichier pass� en param�tre
	 * 
	 * @param br
	 * @return
	 * @throws IOException
	 */
	private ArrayList<String> motsFichier(BufferedReader br) throws IOException {
		ArrayList<String> mots = new ArrayList<>();
		String currentLine;
		while ((currentLine = br.readLine()) != null) {
			String[] ligne = currentLine.split("\t");
			mots.add(ligne[ligne.length - 1]);
		}
		return mots;
	}

	/**
	 * Donne la position dans le dictionnaire d'un mot qui peut d�j� s'y trouver
	 * ou pas.
	 * 
	 * @param mot
	 * @param dictionnaire
	 * @return
	 */
	private int trouvePosition(String mot) {
		boolean emplacementTrouve = false;
		int res = 0;
		int lower = 0;
		int middle;
		int upper = mots.size() - 1;
		if (mots.size() == 0) {
			emplacementTrouve = true;
		} else if (mot.compareTo(mots.get(upper)) > 0) {
			res = mots.size();
			emplacementTrouve = true;
		}
		while (!emplacementTrouve) {
			middle = (lower + upper) / 2;
			if (middle == lower) {
				emplacementTrouve = true;
				res = upper;
			}
			if (mot.compareTo(mots.get(middle)) > 0) {
				lower = middle;
			} else {
				upper = middle;
			}
		}
		return res;
	}

	/**
	 * Ajoute un fichier texte (et tous ses mots) � la classe actuelle
	 * 
	 * @param adresseFichier
	 */
	private void ajouteFichier(String adresseFichier) {

		File fichier = new File(adresseFichier);
		int numFichier = this.refFichiers.size();
		this.refFichiers.put(numFichier, fichier);
		ArrayList<Integer> temp;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			ArrayList<String> mots = this.motsFichier(br);
			for (String mot : mots) {
				int emplacement = this.trouvePosition(mot);

				if (emplacement < mots.size() & mots.get(emplacement).equals(mot)) {
					if (fichiers.get(emplacement).get(fichiers.get(emplacement).size() - 1) != numFichier) {
						// Le mot a d�j� �t� vu, mais pas dans ce document
						temp = frequences.get(emplacement);
						temp.add(1);
						frequences.set(emplacement, temp);
						temp = fichiers.get(emplacement);
						temp.add(numFichier);
						frequences.set(emplacement, temp);
					} else {
						// Le mot a d�j� �t� vu dans ce document
						temp = frequences.get(emplacement);
						temp.set(temp.size() - 1, temp.get(temp.size() + 1));
						frequences.set(emplacement, temp);
					}
				} else {
					// Le mot n'a jamais �t� vu
					temp = new ArrayList<>();
					temp.add(1);
					frequences.add(emplacement, temp);
					temp = new ArrayList<>();
					temp.add(numFichier);
					frequences.add(emplacement, temp);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
