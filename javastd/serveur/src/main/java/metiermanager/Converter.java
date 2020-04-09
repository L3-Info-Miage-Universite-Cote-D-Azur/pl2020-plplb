package metiermanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import metier.UE;
import metier.parcours.ParcoursType;
import metier.semestre.Semestre;
import metier.semestre.SemestreRules;

/**
 * Class qui permet la conversion simplifiee
 * de types
 */
public class 
Converter 
{
	/** Objet qui traite la conversion */
	private Gson gson;
	
	/**
	 * Constructeur de Converter
	 */
	public
	Converter ()
	{
		this.gson = new GsonBuilder().create();
	}
	
	/**
	 * Permet de renvoyer un Semestre a partir
	 * d'une String sous format JSON
	 * @param s la String sous format JSON
	 * @return Le semestre lie a la String
	 */
	public Semestre
	stringToSemestre (String s)
	{

		ArrayList<UE> allUe = stringToUe(s);

		Semestre semestre = new Semestre(allUe);
		return semestre;
	}

	/**
	 * Convertie le fichier csv en list d'ue
	 * @param s le string contenant le csv
	 * @return l'array list d'ue des ue du semestre
	 */
	public ArrayList<UE> stringToUe(String s){
		List<String> allUeString;
		allUeString = Arrays.asList(s.split("\n"));
		if(allUeString.size()<=0) return null;

		List<List<String>> allUeStringSplit = new ArrayList<List<String>>();
		for(int i = 1 ; i<allUeString.size() ; i++){
			allUeStringSplit.add(Arrays.asList(allUeString.get(i).split(";")));
		}

		ArrayList<UE> ueList =new ArrayList<UE>();
		for(List<String> stringUe: allUeStringSplit){
			//si la ligne est bien complete
			if(stringUe.size() >= 4) {
				ueList.add(new UE(stringUe));
			}
		}
		return ueList;
	}


	/**
	 * Permet de renvoyer un semestreRules a partir
	 * d'une String sous format JSON
	 * @param s la String sous format JSON
	 * @return le semestreRules lie a la String
	 */
	public SemestreRules stringToSemestreRule(String s){
		return gson.fromJson(s, SemestreRules.class);
	}
	
	/**
	 * Permet de renvoyer une ArrayList&ltPacoursType&gt a partir
	 * d'une String sous format JSON
	 * @param s la String sous format JSON
	 * @return L'ArrayList&ltPacoursType&gt lie a la String
	 */
	public ParcoursType
	stringToCourse (String s)
	{
		return gson.fromJson(s, ParcoursType.class);
	}
}
