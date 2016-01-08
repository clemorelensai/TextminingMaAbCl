package indexation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Transformation.SuppressionTermes;

public class Textmining {
	
	
	
	
	

	public static void main(String[] args) throws IOException {
		
		//TEST SELECTION DE MOTS (à mettre dans une nouvelle classe)
		// 2 phases de sélection : une pré-indexation (on supprime tous les détermimants, prépositions....) et une post-indexation (en fonction des scores)
		
		File file = new File("/home/clement/Documents/webdatamining/webDatamining sources v2/lemmatisation/faitsDivers_texte.96-1");
		
		SuppressionTermes.supprimeTermesInutiles(file);
		

	}

}
