package Arbre;

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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


public class LectureFichierArbre implements Serializable {

	private ArrayList<String> mots;
	private ArrayList<ArrayList<Integer>> frequences;
	private ArrayList<ArrayList<Integer>> fichiers;
	private Hashtable<Integer, ArrayList<Object>> refFichiers;
	private int tailleRepertoire;

	/**
	 * Cr�e un dictionnaire des fr�quences � partir d'un r�pertoire de fichiers
	 * texte
	 * 
	 * @param adresseRepertoire
	 * @throws IOException
	 */
	public LectureFichierArbre(String adresseRepertoire) throws IOException {
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
			
			for (String mot : motsDuFichier) { Noeud.ajouter(mot);}
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
	
	
	public void ajouteFichier(String adresseFichier) {

		File fichier = new File(adresseFichier);
		int numFichier = this.refFichiers.size();
		ArrayList<Integer> temp;
		int tailleFichier = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			ArrayList<String> mots = this.motsFichier(br);
			for (String mot : mots) { Noeud.ajouter(mot);}
				
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

	public static LectureFichierArbre deserialisation(String fichier) throws IOException, ClassNotFoundException {
		LectureFichierArbre res;
		FileInputStream fileIn = new FileInputStream(fichier);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		res = (LectureFichierArbre) in.readObject();
		in.close();
		fileIn.close();
		return res;
	}


}
