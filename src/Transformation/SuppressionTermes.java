package Transformation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SuppressionTermes {
	
	
	public static void parcourtRepertoire(File repertoire) throws IOException {
    	File[] files=repertoire.listFiles();
   	
        for (File file : files) { // on parcourt tous les fichiers du répertoire
        	
        	supprimeTermesInutiles(file);
        	
        }
	}
	
	public static File supprimeTermesInutiles(File file) throws IOException { // cette fonction crée un nouveau fichier nettoyé (temp) qu'elle renvoie à la fin. A TESTER
		
		File newFile = new File("temp");
		BufferedReader br = null;
		BufferedWriter bw = null;
		int fileSize = 0;
		int newFileSize = 0;
		
		
		// on prend un fichier
		// si la 2ème colonne = prep, determinant...
		// on fait rien
		// sinon on insère la ligne dans un nouveau fichier "nettoyé"
			br = new BufferedReader(new FileReader(file));
			FileWriter fw = new FileWriter(newFile);
			bw = new BufferedWriter(fw);
			ArrayList<String> mots = new ArrayList<>();
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				fileSize++;
				String[] ligne = currentLine.split("\t");
				if (!ligne[ligne.length-2].contains("PRP") && 
					!ligne[ligne.length-2].contains("DET") && 
					!ligne[ligne.length-2].contains("PRO") &&
					!ligne[ligne.length-2].contains("SEN") &&
					!ligne[ligne.length-2].contains("KON") &&
					!ligne[ligne.length-2].contains("ADV") &&
					!ligne[ligne.length-2].contains("NUM") &&
					!ligne[ligne.length-2].contains("PUN")) {
					bw.write(currentLine); // on garde les noms, les adjectifs, les verbes
					bw.newLine();
					newFileSize++;
					
					
				}
				
			}
			bw.close();
			br.close();
			
			//System.out.println("Nombre de mots fichier initial : "+fileSize);
			//System.out.println("Nombre de mots fichier final : "+newFileSize);
			//System.out.println("Nombre de mots ignorés : "+(fileSize-newFileSize));
			
			return newFile;
		
	}

}
