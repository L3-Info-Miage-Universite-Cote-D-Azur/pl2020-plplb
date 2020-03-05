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
	public static Semestre S1(){
		/*TODO : Ajouter les bonus sport et autres..*/
		ArrayList<Categorie> listCategorie = new ArrayList<Categorie>();

		//GEOGRAPHIE
		Categorie geo = new Categorie("GEOGRAPHIE");
		geo.addUe(new UE("Decouverte 1","SPUGDE10"));
		geo.addUe(new UE("Decouverte 2","SPUGDC10"));
		geo.addUe(new UE("Decouverte 1","SPUGDI10"));
		listCategorie.add(geo);

		//INFORMATIQUE
		Categorie info = new Categorie("INFORMATIQUE");
		info.addUe(new UE("Bases de l'informatique","SPUF10"));
		info.addUe(new UE("Introduction a l'informatique par le web","SPUF11"));
		listCategorie.add(info);

		//MATHEMATIQUES (pas toutes dispo selon le choix en math obligatoire)
		Categorie math = new Categorie("MATHEMATIQUES");
		math.addUe(new UE("Fondements 1","SPUM11"));
		math.addUe(new UE("Complements 1","SPUM13"));
		math.addUe(new UE("Methodes - approche continue","SPUM12"));
		listCategorie.add(math);

		//SCIENCES DE LA VIE
		Categorie sv = new Categorie("SCIENCES DE LA VIE");
		sv.addUe(new UE("Genetique. evolution. origine vie & biodiversite","SPUV101"));
		sv.addUe(new UE("Org et mecan. moleculaires - cellules eucaryotes","SPUV100"));
		sv.addUe(new UE("Structure microscopique de la Categorie","SPUC10"));
		listCategorie.add(sv);

		//ELECTRONIQUE
		Categorie elect = new Categorie("ELECTRONIQUE");
		elect.addUe(new UE("Electronique numerique - Bases","SPUE10"));
		listCategorie.add(elect);

		//ECONOMIE - GESTION (choix dans les ecues a voir plus tard)
		Categorie eco = new Categorie("ECONOMIE - GESTION");
		eco.addUe(new UE("Economie-gestion","SPUA10"));
		listCategorie.add(eco);

		//PHYSIQUE
		Categorie phy = new Categorie("PHYSIQUE");
		phy.addUe(new UE("Mecanique 1","SPUP10"));
		listCategorie.add(phy);

		//SCIENCES DE LA TERRE
		Categorie st = new Categorie("SCIENCES DE LA TERRE");
		st.addUe(new UE("Decouverte des sciences de la terre","SPUT10"));
		listCategorie.add(st);

		//MATH ENJEUX (obligatoire)
		Categorie enjeux = new Categorie("MATH ENJEUX");
		enjeux.addUe(new UE("Math enjeux 1","SPUS10"));
		listCategorie.add(enjeux);

		//COMPETENCES TRANSVERSALE (obligatoire)
		Categorie comp = new Categorie("COMPETENCES TRANSVERSALES");
		comp.addUe(new UE("Competences transversales","KCTTS1"));
		listCategorie.add(comp);

		//FABLAB (facultatif)
		Categorie fablab = new Categorie("FABLAB");
		fablab.addUe(new UE("Fablab S1","SPUSF100"));
		listCategorie.add(fablab);

		//RULE
		//obligatoire au choix:
		ArrayList<String> listObligatoire = new ArrayList<String>();
		listObligatoire.add("SPUM11");
		listObligatoire.add("SPUM12");
		//AJOUT AU SEMESTRE 1;
		Semestre S1 = new Semestre(1,listCategorie,listObligatoire);
		return S1;
	}
	
	public static String S1Jsoned = SemestersSample.gson.toJson(SemestersSample.S1());

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private SemestersSample() {}
}
