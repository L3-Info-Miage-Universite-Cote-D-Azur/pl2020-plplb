package metiermanager.courses;

import java.util.ArrayList;

import metier.parcours.ParcoursType;
import metiermanager.Converter;

/**
 * Class utilitaire, elle contient les differents parcours type avec la liste de leur UEs par defaut
 */
public class
ParcoursSample
{
	/**
	 * Permet d'initialiser les semestres du serveur
	 */
	public static void
	init ()
	{		
		ParcoursManager pm = new ParcoursManager();
		ParcoursSample.JPI = pm.getJson(ParcoursConsts.filenames[0]);
		ParcoursSample.JPM = pm.getJson(ParcoursConsts.filenames[1]);
		ParcoursSample.JPMM = pm.getJson(ParcoursConsts.filenames[2]);
		ParcoursSample.JPE = pm.getJson(ParcoursConsts.filenames[3]);
		ParcoursSample.JPF = pm.getJson(ParcoursConsts.filenames[4]);
		
		ParcoursSample.PI = pm.get(ParcoursConsts.filenames[0]);
		ParcoursSample.PM = pm.get(ParcoursConsts.filenames[1]);
		ParcoursSample.PMM = pm.get(ParcoursConsts.filenames[2]);
		ParcoursSample.PE = pm.get(ParcoursConsts.filenames[3]);
		ParcoursSample.PF = pm.get(ParcoursConsts.filenames[4]);
	}

	public static ParcoursType PI;
	public static ParcoursType PM;
	public static ParcoursType PMM;
	public static ParcoursType PE;
	public static ParcoursType PF;
	
	public static String JPI;
	public static String JPM;
	public static String JPMM;
	public static String JPE;
	public static String JPF;

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private ParcoursSample() {}
}
