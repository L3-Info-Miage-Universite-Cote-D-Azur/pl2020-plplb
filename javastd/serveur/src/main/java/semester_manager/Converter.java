package semester_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
}
