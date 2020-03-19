package semester_manager;

import metier.semestre.Semestre;

/**
 * Class utilitaire, elle contient les differents semestres avec la liste de leur UEs par defaut
 * TODO:
 * Mettre toutes les UE du S1,
 * faire la meme chose pour les autres semestres.
 */
public class 
SemestersSample 
{
	
	public static void
	init ()
	{
		SemesterManager sm = new SemesterManager();
		SemestersSample.S1 = sm.get(SemestreConsts.filenames[0]);
		SemestersSample.S2 = sm.get(SemestreConsts.filenames[1]);
		
		SemestersSample.S1Jsoned = SemestersSample.S1.getJson();
		SemestersSample.S2Jsoned = SemestersSample.S2.getJson();
	}

	/**
	 * s1 contient tout les Categories du semestre 1 de licence 1.s
	 */
	public static Semestre S1;

	/**
	 * s1 contient tout les Categories du semestre 2 de licence 1.s
	 */
	public static Semestre S2;


	public static String S1Jsoned;
	public static String S2Jsoned;

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private SemestersSample() {}
}
