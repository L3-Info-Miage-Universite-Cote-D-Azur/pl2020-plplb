package metiermanager.courses;


import database.FileManager;
import metier.parcours.ParcoursType;
import metiermanager.Converter;
import metiermanager.Parser;

public class
ParcoursManager 
{
	/** Permet la gestion des fichiers contenant les parcours */
	private FileManager fman;
	/** Permet de parser les donnees des fichiers */
	private Parser parser;
	/** Permet de faire les conversions necessaires sur les donnees */
	private Converter converter;
	
	public
	ParcoursManager ()
	{
		this.parser = new Parser();
		this.converter = new Converter();
	}
	
	public String
	getJson (String fileOfCourse, boolean needToCheck)
	{
		if (needToCheck) 
		{
			// Check si le directory est en parametre ou non
			if ( fileOfCourse.length() > ParcoursConsts.dir.length()
					&& fileOfCourse.subSequence(0, ParcoursConsts.dir.length()).equals(ParcoursConsts.dir))
				this.fman = new FileManager(fileOfCourse);
			else
				this.fman = new FileManager(ParcoursConsts.dir + fileOfCourse);
		} else
			this.fman = new FileManager(fileOfCourse);
		String parsed = this.parser.parse(this.fman.getRaw());
		return parsed;
	}
	
	public String
	getJson (String fileOfCourse)
	{return this.getJson(fileOfCourse, true);}
	
	public ParcoursType
	get (String fileOfCourse, boolean needToCheck)
	{
		// Parsing
		String parsed = this.getJson(fileOfCourse, needToCheck);
		ParcoursType courses = this.converter.stringToCourse(parsed);
		return courses;
	}
	
	public ParcoursType
	get (String fileOfSemester)
	{return this.get(fileOfSemester, true);}
}
