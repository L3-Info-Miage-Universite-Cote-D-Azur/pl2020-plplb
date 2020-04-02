package metiermanager.courses;

/**
 * Class utilitaire qui regroupe les donnee
 * importante sur la gestion des semestres
 * par l'application
 */
public final class 
ParcoursConsts 
{
	/** Le repertoire ou sont les semestres */
	public static String dir = "parcoursType/";
	/** Le nom des fichiers representant les semestres<br>
	 *  Ils doivent etre dans l'ordre croissant ! */
	public static String[] filenames = new String[] {"pinfo.txt",
													 "pmaths.txt",
													 "pmmaths.txt",
													 "pelec.txt",
													 "pfree.txt"};
}