package metiermanager.semesters;

/**
 * Class utilitaire qui regroupe les donnee
 * importante sur la gestion des semestres
 * par l'application
 */
public final class 
SemestreConsts 
{
	/** Le repertoire ou sont les semestres */
	public static String dir = "semestres/";
	/** Le nom des fichiers representant les semestres<br>
	 *  Ils doivent etre dans l'ordre croissant ! */
	public static String[] filenames = new String[] {"s1.txt", "s2.txt","s3.txt","s4.txt"};
	/** Tableau qui represente quand les fichiers ont ete modifie pour la derniere fois */
	public static long[] lastUpdate = new long[filenames.length];
}
