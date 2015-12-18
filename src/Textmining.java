import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Textmining {
	
	
	
	
	

	public static void main(String[] args) throws IOException {

		//TEST SELECTION DE MOTS (à mettre dans une nouvelle classe)
		// 2 phases de sélection : une pré-indexation (on supprime tous les détermimants, prépositions....) et une post-indexation (en fonction des scores)
		
		File file = new File("/home/clement/Documents/webdatamining/webDatamining sources v2/lemmatisation/faitsDivers_texte.96-1");
		
		BufferedReader br = null;
		
		// on prend un fichier
		// si la 2ème colonne = prep, determinant...
		// on fait rien
		// sinon on insère la ligne dans un nouveau fichier "nettoyé"
			br = new BufferedReader(new FileReader(file));
			ArrayList<String> mots = new ArrayList<>();
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				String[] ligne = currentLine.split("\t");
				if (!ligne[ligne.length-2].equals("PRP") && !ligne[ligne.length-2].contains("DET")) {
					// on peut écrire la ligne dans le nouveau fichier
				}

			}
			
			System.out.println(mots.toString());

	}

}
