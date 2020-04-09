package metiermanager.semesters;

import metier.Categorie;
import metier.UE;
import metier.semestre.Semestre;

import java.util.ArrayList;

/**
 * Class utilitaire, elle contient les differents semestres avec la liste de leur UEs par defaut
 * TODO:
 * Mettre toutes les UE du S1,
 * faire la meme chose pour les autres semestres.
 */
public class
SemestersSample
{
	/**
	 * Permet d'initialiser les semestres du serveur
	 */
	public static void
	init ()
	{
		SemesterManager sm = new SemesterManager();
		//TODO ajouter tout les ue
		SemestersSample.S1 = sm.get(SemestreConsts.filenames[0]);
		SemestersSample.S2 = sm.get(SemestreConsts.filenames[1]);
		SemestersSample.S3 = sm.get(SemestreConsts.filenames[2]);
		SemestersSample.S4 = sm.get(SemestreConsts.filenames[3]);

		//rajoute les regle de semestre
		SemestersSample.S1.setRules(sm.getRule(SemestreConsts.ruleFilenames[0]));
		SemestersSample.S2.setRules(sm.getRule(SemestreConsts.ruleFilenames[1]));
		SemestersSample.S3.setRules(sm.getRule(SemestreConsts.ruleFilenames[2]));
		SemestersSample.S4.setRules(sm.getRule(SemestreConsts.ruleFilenames[3]));

		SemestersSample.S1Jsoned = SemestersSample.S1.getJson();
		SemestersSample.S2Jsoned = SemestersSample.S2.getJson();
		SemestersSample.S3Jsoned = SemestersSample.S3.getJson();
		SemestersSample.S4Jsoned = SemestersSample.S4.getJson();
	}

	/**
	 * s1 contient tout les Categories du semestre 1 de licence 1.s
	 */
	public static Semestre S1;

	/**
	 * s2 contient tout les Categories du semestre 2 de licence 1.s
	 */
	public static Semestre S2;

	/**
	 * s3 contient tout les Categories du semestre 3 de licence 2.s
	 */
	public static Semestre S3;

	/**
	 * s3 contient tout les Categories du semestre 4 de licence 2.s
	 */
	public static Semestre S4;

	public static String S1Jsoned;
	public static String S2Jsoned;
	public static String S3Jsoned;
	public static String S4Jsoned;

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private SemestersSample() {}
}
