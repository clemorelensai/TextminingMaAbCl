package indexation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class LectureFichier implements Serializable {

	private ArrayList<String> mots;
	private ArrayList<ArrayList<Integer>> frequences;
	private ArrayList<ArrayList<Integer>> fichiers;
	private Hashtable<Integer, ArrayList<Object>> refFichiers;
	private int tailleRepertoire;

	/**
	 * Crée un dictionnaire des fréquences à partir d'un répertoire de fichiers
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
		tailleRepertoire = repertoire.listFiles().length;
		ArrayList<Integer> temp;
		ArrayList<Object> donneesFichier;
		int tailleFichier = 0;
		for (File file : repertoire.listFiles()) {
			donneesFichier = new ArrayList<>();
			br = new BufferedReader(new FileReader(/*SuppressionTermes.supprimeTermesInutiles(file)*/file));
			ArrayList<String> motsDuFichier = this.motsFichier(br);
			for (String mot : motsDuFichier) {
				int emplacement = this.trouvePosition(mot);
				boolean test = (emplacement < mots.size());
				if (test) {
					test = mots.get(emplacement).equals(mot);
				}
				if (test) {
					if (fichiers.get(emplacement).get(fichiers.get(emplacement).size() - 1) != numFichier) {
						// Le mot a déjà été vu, mais pas dans ce document
						temp = frequences.get(emplacement);
						temp.add(1);
						frequences.set(emplacement, temp);
						temp = fichiers.get(emplacement);
						temp.add(numFichier);
						fichiers.set(emplacement, temp);
					} else {
						// Le mot a déjà été vu dans ce document
						temp = frequences.get(emplacement);
						temp.set(temp.size() - 1, temp.get(temp.size() - 1) + 1);
						frequences.set(emplacement, temp);
					}
				} else {
					// Le mot n'a jamais été vu
					temp = new ArrayList<>();
					temp.add(1);
					frequences.add(emplacement, temp);
					temp = new ArrayList<>();
					temp.add(numFichier);
					fichiers.add(emplacement, temp);
					mots.add(emplacement, mot);
				}
				tailleFichier++;
			}
			donneesFichier.add(file);
			donneesFichier.add(tailleFichier);
			refFichiers.put(numFichier++, donneesFichier);
			tailleFichier = 0;
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

	public Hashtable<Integer, ArrayList<Object>> getRefFichiers() {
		return refFichiers;
	}

	public int getTailleRepertoire() {
		return tailleRepertoire;
	}

	/**
	 * Donne tous les mots lemmatisés du fichier passé en paramètre
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
	 * Donne la position dans le dictionnaire d'un mot qui peut déjà s'y trouver
	 * ou pas.
	 * 
	 * @param mot
	 * @param dictionnaire
	 * @return
	 */
	public int trouvePosition(String mot) {
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
				if (mot.compareTo(mots.get(lower)) <= 0) {
					res = lower;
				} else {
					res = upper;
				}
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
	 * Ajoute un fichier texte (et tous ses mots) à la classe actuelle
	 * 
	 * @param adresseFichier
	 */
	public void ajouteFichier(String adresseFichier) {

		File fichier = new File(adresseFichier);
		int numFichier = this.refFichiers.size();
		ArrayList<Integer> temp;
		int tailleFichier = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			ArrayList<String> mots = this.motsFichier(br);
			for (String mot : mots) {
				tailleFichier++;
				int emplacement = this.trouvePosition(mot);

				if (emplacement < mots.size() & mots.get(emplacement).equals(mot)) {
					if (fichiers.get(emplacement).get(fichiers.get(emplacement).size() - 1) != numFichier) {
						// Le mot a déjà été vu, mais pas dans ce document
						temp = frequences.get(emplacement);
						temp.add(1);
						frequences.set(emplacement, temp);
						temp = fichiers.get(emplacement);
						temp.add(numFichier);
						frequences.set(emplacement, temp);
					} else {
						// Le mot a déjà été vu dans ce document
						temp = frequences.get(emplacement);
						temp.set(temp.size() - 1, temp.get(temp.size() + 1));
						frequences.set(emplacement, temp);
					}
				} else {
					// Le mot n'a jamais été vu
					temp = new ArrayList<>();
					temp.add(1);
					frequences.add(emplacement, temp);
					temp = new ArrayList<>();
					temp.add(numFichier);
					frequences.add(emplacement, temp);
				}
			}
			ArrayList<Object> donneesFichier = new ArrayList<>();
			donneesFichier.add(tailleFichier);
			donneesFichier.add(fichier);
			this.refFichiers.put(numFichier, donneesFichier);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void serialisation(String fichier) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(fichier);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
		System.out.printf("Serialized data is saved in index.ser");
	}

	public static LectureFichier deserialisation(String fichier) throws IOException, ClassNotFoundException {
		LectureFichier res;
		FileInputStream fileIn = new FileInputStream(fichier);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		res = (LectureFichier) in.readObject();
		in.close();
		fileIn.close();
		return res;
	}
	
	public void supprimer(String fichier) {
		File file = new File(fichier);
		boolean trouve=false;
		Iterator<Integer> it = refFichiers.keySet().iterator();
		int numDoc = 0;
		while (it.hasNext() && !trouve) {
			int temp = it.next();
			if(refFichiers.get(temp).get(0).equals(file)) {
				trouve = true;
				numDoc = temp;
			}
		}
		if(!trouve) {
			System.out.println("Le document n'est pas dans le dictionnaire");
		} else {
			for(int i=0; i<fichiers.size(); i++) {
				if(fichiers.get(i).contains(numDoc)) {
					int position = fichiers.get(i).indexOf(numDoc);
					fichiers.get(i).remove(position);
					frequences.get(i).remove(position);
					if(fichiers.get(i).isEmpty()) {
						fichiers.remove(i);
						frequences.remove(i);
						mots.remove(i);
						i--;
					}
				}
				refFichiers.remove(numDoc);
			}
			System.out.println("Le document a été supprimé");
		}
	}

}
