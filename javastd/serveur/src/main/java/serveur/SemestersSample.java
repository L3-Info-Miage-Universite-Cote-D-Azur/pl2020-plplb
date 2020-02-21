package serveur;

import java.util.ArrayList;

import org.json.*;

/**
 * Class utilitaire, elle contient les differents semestres avec la liste de leur UEs par defaut
 * TODO:
 * Mettre toutes les UE du S1,
 * faire la meme chose pour les autres semestres.
 * @author theoricien
 */
public class 
SemestersSample 
{
	/**
	 * Represente le Semestre S1 par defaut sous un JSONObject
	 */
	public static final JSONObject S1 = new JSONObject(
											new Semestre(
												1,
												new ArrayList<UE>(
													new UE("Introduction à l\'informatique par le web", "SPUF11"))));
	
	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private SemestersSample() {}
}
