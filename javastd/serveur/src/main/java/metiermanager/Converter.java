package metiermanager;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import metier.parcours.ParcoursType;
import metier.semestre.Semestre;

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
		return gson.fromJson(s, Semestre.class);
	}
	
	/**
	 * Permet de renvoyer une ArrayList&ltPacoursType&gt a partir
	 * d'une String sous format JSON
	 * @param s la String sous format JSON
	 * @return L'ArrayList&ltPacoursType&gt lie a la String
	 */
	public ArrayList<ParcoursType>
	stringToCourse (String s)
	{
		return gson.fromJson(s, new TypeToken<ArrayList<ParcoursType>>(){}.getType());
	}
}
