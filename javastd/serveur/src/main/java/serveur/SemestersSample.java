package serveur;

import java.util.ArrayList;

import metier.Semestre;
import metier.UE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.*;

/**
 * Class utilitaire, elle contient les differents semestres avec la liste de leur UEs par defaut
 * TODO:
 * Mettre toutes les UE du S1,
 * faire la meme chose pour les autres semestres.
 */
public class 
SemestersSample 
{
	static final Gson gson = new GsonBuilder().create();

	/**
	 * s1 contient tout les ues du semestre 1 de licence 1.s
	 * @return le semestre 1 sous forme de json.
	 */
	public static String s1(){
		UE ue = new UE("Introduction à l\'informatique par le web", "SPUF11");
		ArrayList<UE> a = new ArrayList<UE>();
		a.add(ue);
		return gson.toJson(new Semestre(1,a));
	}

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private SemestersSample() {}
}
