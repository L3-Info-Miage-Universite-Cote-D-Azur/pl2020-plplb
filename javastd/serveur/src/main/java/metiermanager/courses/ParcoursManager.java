package metier.parcours.manager;

import java.util.ArrayList;

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
	
	public ArrayList<ParcoursType>
	get (String fileOfCourse, boolean needToCheck)
	{
		ArrayList<ParcoursType> courses = new ArrayList<ParcoursType>();
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
		// Parsing
		String parsed = this.parser.parse(this.fman.getRaw());
		// Converting
		courses = this.converter.stringToCourse(parsed);
		return courses;
	}
	
	public ArrayList<ParcoursType>
	get (String fileOfSemester)
	{return this.get(fileOfSemester, true);}
}
