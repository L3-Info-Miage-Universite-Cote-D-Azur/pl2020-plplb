package semester_manager;

import database.FileManager;
import metier.semestre.Semestre;

/**
 * Class qui permet la gestion
 * des semestres au sein du serveur
 */
public class 
SemesterManager 
{
	/** Permet la gestion des fichiers contenant les semestres */
	private FileManager fman;
	/** Permet de parser les donnees des fichiers */
	private Parser parser;
	/** Permet de faire les conversions necessaires sur les donnees */
	private Converter converter;
	
	/** Constructeur de SemesterManager */
	public
	SemesterManager ()
	{
		this.parser = new Parser();
		this.converter = new Converter();
	}
	
	/**
	 * La fonction get permet de renvoyer un Semestre a partir du nom du fichier contenant
	 * le semestre sous format JSON
	 * @param fileOfSemester Le nom du fichier ou se situe le semestre sous JSON
	 * @param needToCheck Si les checks sont necessaires sur fileOfSemesters
	 * @return Le semestre represente par le fichier
	 */
	public Semestre
	get (String fileOfSemester, boolean needToCheck)
	{
		if (needToCheck) 
		{
			// Check si le directory est en parametre ou non
			if ( fileOfSemester.length() > SemestreConsts.dir.length()
					&& fileOfSemester.subSequence(0, SemestreConsts.dir.length()).equals(SemestreConsts.dir))
				this.fman = new FileManager(fileOfSemester);
			else
				this.fman = new FileManager(SemestreConsts.dir + fileOfSemester);
		} else
			this.fman = new FileManager(fileOfSemester);
		// Parsing
		String parsed = this.parser.parse(this.fman.getRaw());
		// Converting
		Semestre converted = this.converter.stringToSemestre(parsed);
		return converted;
	}
	
	/**
	 * La fonction get permet de renvoyer un Semestre a partir du nom du fichier contenant
	 * le semestre sous format JSON
	 * @param fileOfSemester Le nom du fichier ou se situe le semestre sous JSON
	 * @return Le semestre represente par le fichier
	*/
	public Semestre get (String fileOfSemester)
	{return this.get(fileOfSemester, true);}
	
	/**
	 * getLastUpdateFile renvoie un long[number of semesters] qui contient
	 * la valeur a laquelle les fichiers representants les semestres ont ete
	 * modifie pour la derniere fois
	 * @param dir Le dossier ou se trouve les filenames
	 * @param filenames La liste des fichiers
	 * @param updates La liste qui contient le temps auquel les fichiers ont ete
	 * modifie pour la derniere fois
	 * @return Un long[] pour chaque element associe chaque fichier
	 */
	private long[]
	getLastUpdateFile (String dir, String[] filenames, long[] updates)
	{
		long[] res = new long[updates.length];
		for (int i = 0; i < res.length; i++)
		{
			this.fman = new FileManager(dir + filenames[i]);
			res[i] = this.fman.getFile().lastModified();
		}
		return res;
	}
	
	/**
	 * Permet de mettre a jour les donnees
	 * statiques de SemestreConsts.lastUpdate
	 * @param dir Le dossier ou se trouve les filenames
	 * @param filenames La liste des fichiers
	 * @param updates La liste qui contient le temps auquel les fichiers ont ete
	 * modifie pour la derniere fois
	 */
	public void
	updateConsts (String dir, String[] filenames, long[] updates)
	{
		long[] arr = this.getLastUpdateFile(dir, filenames, updates);
		for (int i = 0; i < arr.length; i++)
		{
			updates[i] = arr[i];
		}
	}
	
	/**
	 * Permet de mettre a jour les donnees
	 * statiques de SemestreConsts.lastUpdate
	 * @param dir Le dossier ou se trouve les filenames
	 * @param filenames La liste des fichiers
	 * @param updates La liste qui contient le temps auquel les fichiers ont ete
	 * modifie pour la derniere fois
	 */
	public void updateConsts ()
	{this.updateConsts(SemestreConsts.dir, SemestreConsts.filenames, SemestreConsts.lastUpdate);}
	
	/**
	 * Verification de si les fichiers ont ete mis a jour ou non
	 * @param dir Le dossier ou se trouve les filenames
	 * @param filenames La liste des fichiers
	 * @param updates La liste qui contient le temps auquel les fichiers ont ete
	 * modifie pour la derniere fois
	 * @return true si oui, false sinon
	 */
	public boolean
	areSemestersUpdated (String dir, String[] filenames, long[] updates)
	{
		for (int i = 0; i < filenames.length; i++)
		{
			if (this.hasBeenUpdated(i, dir, filenames, updates))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verification de si les fichiers ont ete mis a jour ou non
	 * @return true si oui, false sinon
	 */
	public boolean areSemestersUpdated ()
	{return this.areSemestersUpdated(SemestreConsts.dir, SemestreConsts.filenames, SemestreConsts.lastUpdate);}
	
	/**
	 * Verification de si le fichier de l'indice i dans SemestreConsts a ete
	 * modifie ou non
	 * @param i l'indice
	 * @param dir Le dossier ou se trouve les filenames
	 * @param filenames La liste des fichiers
	 * @param updates La liste qui contient le temps auquel les fichiers ont ete
	 * modifie pour la derniere fois
	 * @return true si oui, false sinon
	 */
	private boolean
	hasBeenUpdated (int i, String dir, String[] filenames, long[] updates)
	{
		this.fman = new FileManager(dir + filenames[i]);
		if (this.fman.getFile().lastModified() != updates[i])
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Verification de si le fichier de l'indice i dans SemestreConsts a ete
	 * modifie ou non
	 * @param i l'indice
	 * @return true si oui, false sinon
	 */
	public boolean hasBeenUpdated (int i)
	{return this.hasBeenUpdated(i, SemestreConsts.dir, SemestreConsts.filenames, SemestreConsts.lastUpdate);}
}
