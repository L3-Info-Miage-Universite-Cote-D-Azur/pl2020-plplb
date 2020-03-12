package serveur;

import java.util.ArrayList;

import metier.Categorie;
import metier.UE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import metier.semestre.Semestre;
import metier.semestre.rules.BasicSemestreRules;
import metier.semestre.rules.SemestreRulesWithChoose;

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
		geo.addUe(new UE("Disciplinaire 1","SPUGDI10"));
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
		listCategorie.add(sv);

		// CHIMIE
		Categorie chimie = new Categorie("CHIMIE");
		chimie.addUe(new UE("Structure microscopique de la matiere","SPUC10"));
		listCategorie.add(chimie);

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
		ArrayList<String> listChoixUE = new ArrayList<String>();
		listChoixUE.add("SPUM11");
		listChoixUE.add("SPUM12");

		//ue check automatiquement et non modifiable
		ArrayList<String> listObligatoire = new ArrayList<String>();
		listObligatoire.add("KCTTS1");
		listObligatoire.add("SPUS10");

		BasicSemestreRules rules = new SemestreRulesWithChoose(3,1,listObligatoire,listChoixUE,1);

		//AJOUT AU SEMESTRE 1;
		Semestre S1 = new Semestre(1,listCategorie,rules);
		return S1;
	}

	/**
	 * s1 contient tout les Categories du semestre 2 de licence 1.s
	 * @return le semestre 2 sous forme de json.
	 */
	public static Semestre S2(){
		/*TODO : Ajouter les bonus sport et autres..*/
		ArrayList<Categorie> listCategorie = new ArrayList<Categorie>();

		//GEOGRAPHIE
		Categorie geo = new Categorie("GEOGRAPHIE");
		geo.addUe(new UE("Decouverte 3","SPUGDE20"));
		geo.addUe(new UE("Decouverte 4","SPUGDC20"));
		geo.addUe(new UE("Decouverte 2","SPUGDI20"));
		listCategorie.add(geo);

		//INFORMATIQUE
		Categorie info = new Categorie("INFORMATIQUE");
		info.addUe(new UE("Programmation imperative","SPUF21"));
		info.addUe(new UE("Systeme 1 unix et programmation shell","SPUF20"));
		listCategorie.add(info);

		//MATHEMATIQUES (pas toutes dispo selon le choix en math obligatoire)
		Categorie math = new Categorie("MATHEMATIQUES");
		math.addUe(new UE("Fondements 2","SPUM21"));
		math.addUe(new UE("Complements 2","SPUM23"));
		math.addUe(new UE("Methodes Maths-Approche discrete","SPUM22"));
		listCategorie.add(math);

		//SCIENCES DE LA VIE
		Categorie sv = new Categorie("SCIENCES DE LA VIE");
		sv.addUe(new UE("Diversite du vivant","SPUV201"));
		sv.addUe(new UE("Physiologie - neurologie - enzymologie","SPUV200"));
		listCategorie.add(sv);

		//CHIMIE
		Categorie chimie = new Categorie("CHIMIE");
		chimie.addUe(new UE("Reactions et reactivites chimiques","SPUC20"));
		chimie.addUe(new UE("Thermodynamique chimique / Options","SPUC21"));
		listCategorie.add(chimie);

		//ELECTRONIQUE
		Categorie elect = new Categorie("ELECTRONIQUE");
		elect.addUe(new UE("Communication sans fil","SPUE21"));
		elect.addUe(new UE("Electronique analogique","SPUE20"));
		listCategorie.add(elect);

		//ECONOMIE - GESTION (choix dans les ecues a voir plus tard)
		Categorie eco = new Categorie("ECONOMIE - GESTION");
		eco.addUe(new UE("Economie-gestion S2","SPUA20"));
		listCategorie.add(eco);

		//PHYSIQUE
		Categorie phy = new Categorie("PHYSIQUE");
		phy.addUe(new UE("Mecanique - complements","SPUP21"));
		phy.addUe(new UE("Optique","SPUP20"));
		listCategorie.add(phy);

		//SCIENCES DE LA TERRE
		Categorie st = new Categorie("SCIENCES DE LA TERRE");
		st.addUe(new UE("Atmosphere. ocean. climats","SPUT22"));
		st.addUe(new UE("Structure et dynamique de la terre","SPUT20"));
		listCategorie.add(st);

		//MATH ENJEUX (obligatoire)
		Categorie enjeux = new Categorie("MATH ENJEUX");
		enjeux.addUe(new UE("Math enjeux 2","SPUS20"));
		listCategorie.add(enjeux);

		//COMPETENCES TRANSVERSALE (obligatoire)
		Categorie comp = new Categorie("COMPETENCES TRANSVERSALES");
		comp.addUe(new UE("Competences transversales - S2","KCTTS2"));
		listCategorie.add(comp);

		//FABLAB (facultatif)
		Categorie fablab = new Categorie("FABLAB");
		fablab.addUe(new UE("Fablab S2","SPOSF200"));
		listCategorie.add(fablab);

		//RULE
		//ue au choix:
		ArrayList<String> listChoixUE = new ArrayList<String>();
		listChoixUE.add("SPUM21");
		listChoixUE.add("SPUM22");

		//ue obligatoire
		ArrayList<String> listObligatoire = new ArrayList<String>();
		listObligatoire.add("KCTTS2");
		listObligatoire.add("SPUS20");

		BasicSemestreRules rule = new SemestreRulesWithChoose(4,2,listObligatoire,listChoixUE,1);

		//AJOUT AU SEMESTRE 1;
		Semestre S2 = new Semestre(2,listCategorie,rule);
		return S2;
	}


	public static String S1Jsoned = SemestersSample.gson.toJson(SemestersSample.S1());
	public static String S2Jsoned = SemestersSample.gson.toJson(SemestersSample.S2());

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private SemestersSample() {}
}
