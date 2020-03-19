package semester_manager;

import files.FileManager;
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
	 * @return Le semestre represente par le fichier
	 */
	public Semestre
	get (String fileOfSemester)
	{
		// Check si le directory est en parametre ou non
		if ( fileOfSemester.length() > SemestreConsts.dir.length()
				&& fileOfSemester.subSequence(0, SemestreConsts.dir.length()).equals(SemestreConsts.dir))
			this.fman = new FileManager(fileOfSemester);
		else
			this.fman = new FileManager(SemestreConsts.dir + fileOfSemester);
		// Parsing
		String parsed = this.parser.parse(this.fman.getRaw());
		// Converting
		Semestre converted = this.converter.stringToSemestre(parsed);
		return converted;
	}
	
	/**
	 * getLastUpdateFile renvoie un long[number of semesters] qui contient
	 * la valeur a laquelle les fichiers representants les semestres ont ete
	 * modifie pour la derniere fois
	 * @return Un long[] pour chaque element associe chaque fichier
	 */
	private long[]
	getLastUpdateFile ()
	{
		long[] res = new long[SemestreConsts.lastUpdate.length];
		for (int i = 0; i < res.length; i++)
		{
			this.fman = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[i]);
			res[i] = this.fman.getFile().lastModified();
		}
		return res;
	}
	
	/**
	 * Permet de mettre a jour les donnees
	 * statiques de SemestreConsts.lastUpdate
	 */
	public void
	updateConsts ()
	{
		long[] arr = this.getLastUpdateFile();
		for (int i = 0; i < arr.length; i++)
		{
			SemestreConsts.lastUpdate[i] = arr[i];
		}
	}
	
	/**
	 * Verification de si les fichiers ont ete mis a jour ou non
	 * @return true si oui, false sinon
	 */
	public boolean
	areSemestersUpdated ()
	{
		for (int i = 0; i < SemestreConsts.filenames.length; i++)
		{
			if (this.hasBeenUpdated(i))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verification de si le fichier de l'indice i dans SemestreConsts a ete
	 * modifie ou non
	 * @param i l'indice
	 * @return true si oui, false sinon
	 */
	private boolean
	hasBeenUpdated (int i)
	{
		this.fman = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[i]);
		if (this.fman.getFile().lastModified() != SemestreConsts.lastUpdate[i])
		{
			return true;
		}
		return false;
	}
}
