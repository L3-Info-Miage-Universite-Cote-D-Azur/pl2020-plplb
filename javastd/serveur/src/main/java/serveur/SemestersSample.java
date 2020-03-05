package serveur;

import java.util.ArrayList;

import metier.Categorie;
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
	 * s1 contient tout les Categories du semestre 1 de licence 1.s
	 * @return le semestre 1 sous forme de json.
	 */
	public static String s1(){
		/*TODO : Ajouter les bonus sport et autres..*/
		ArrayList<Categorie> listCategorie = new ArrayList<Categorie>();
		ArrayList<UE> listUE = new ArrayList<UE>();

		//GEOGRAPHIE
		listUE.add(new UE("Decouverte 1","SPUGDE10"));
		listUE.add(new UE("Decouverte 2","SPUGDC10"));
		listUE.add(new UE("Decouverte 1","SPUGDI10"));

		listCategorie.add(new Categorie("GEOGRAPHIE",listUE));

		//INFORMATIQUE
		listUE.clear();
		listUE.add(new UE("Bases de l'informatique","SPUF10"));
		listUE.add(new UE("Introduction a l'informatique par le web","SPUF11"));

		listCategorie.add(new Categorie("INFORMATIQUE",listUE));

		//MATHEMATIQUES (pas toutes dispo selon le choix en math obligatoire)
		listUE.clear();
		listUE.add(new UE("Fondements 1","SPUM11"));
		listUE.add(new UE("Complements 1","SPUM13"));
		listUE.add(new UE("Methodes - approche continue","SPUM12"));

		listCategorie.add(new Categorie("MATHEMATIQUES",listUE));

		//SCIENCES DE LA VIE
		listUE.clear();
		listUE.add(new UE("Genetique. evolution. origine vie & biodiversite","SPUV101"));
		listUE.add(new UE("Org et mecan. moleculaires - cellules eucaryotes","SPUV100"));
		listUE.add(new UE("Structure microscopique de la Categorie","SPUC10"));

		listCategorie.add(new Categorie("SCIENCES DE LA VIE",listUE));

		//ELECTRONIQUE
		listUE.clear();
		listUE.add(new UE("Electronique numerique - Bases","SPUE10"));

		listCategorie.add(new Categorie("ELECTRONIQUE",listUE));

		//ECONOMIE - GESTION (choix dans les ecues a voir plus tard)
		listUE.clear();
		listUE.add(new UE("Economie-gestion","SPUA10"));

		listCategorie.add(new Categorie("ECONOMIE - GESTION",listUE));

		//PHYSIQUE
		listUE.clear();
		listUE.add(new UE("Mecanique 1","SPUP10"));

		listCategorie.add(new Categorie("PHYSIQUE",listUE));

		//SCIENCES DE LA TERRE
		listUE.clear();
		listUE.add(new UE("Decouverte des sciences de la terre","SPUT10"));

		listCategorie.add(new Categorie("SCIENCES DE LA TERRE",listUE));

		//MATH ENJEUX (obligatoire)
		listUE.clear();
		listUE.add(new UE("Math enjeux 1","SPUS10"));

		listCategorie.add(new Categorie("MATH ENJEUX",listUE));

		//COMPETENCES TRANSVERSALE (obligatoire)
		listUE.clear();
		listUE.add(new UE("Competences transversales","KCTTS1"));

		listCategorie.add(new Categorie("COMPETENCES TRANSVERSALE",listUE));

		//FABLAB (facultatif)
		listUE.clear();
		listUE.add(new UE("Fablab S1","SPUSF100"));

		listCategorie.add(new Categorie("FABLAB",listUE));

		//AJOUT AU SEMESTRE 1;
		Semestre S1 = new Semestre(1,listCategorie);
		return gson.toJson(S1);
	}

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private SemestersSample() {}
}
