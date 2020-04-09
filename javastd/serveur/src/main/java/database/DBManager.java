package database;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * DBManager permet de gerer le systeme de base 
 * de donnees de l'application, elle permet de 
 * sauvegarder grace a la fonction save(), de 
 * charger grace a la fonction load()
 */
public class
DBManager
{
	/* FIELDS */
	/** Le FileManager qui permet de gerer le dossier du client */
	private Directory dbDir;
	private DBCourses dbCourses;
	private DBSharedCourses dbSharedCourses;
	
	/* CONSTRUCTORS */
	/**
	 * Constructeur par defaut
	 */
	public
	DBManager ()
	{this("db/");}
	
	/**
	 * Constructeur, associe un fichier file au FileManager de la class
	 * @param dir un File contenant le dossier
	 */
	public 
	DBManager (File dir)
	{
		this.dbDir = new Directory(dir.getName());
	}
	
	/**
	 * Construceur, associe le nom du fichier au FileManager de la class 
	 * et creer le dossier db/ s'il n'existe pas encore
	 * @param directory un File contenant le dossier
	 */
	public 
	DBManager (String directory)
	{
		this.dbDir = new Directory("db");
		if (!dbDir.exists())
			dbDir.makeDirectory();
		// Creation du dossier pour les parcours partages
		this.dbSharedCourses = new DBSharedCourses(this.dbDir.getDirectoryName());
		this.dbCourses = new DBCourses(this.dbDir.getDirectoryName());
	}
	
	/* GETTERS */
	/**
	 * Getter du dossier courant.
	 * Le Directory qui permet de gerer le dossier du client
	 * @return Directory
	 */
	public Directory
	getDir ()
	{return this.dbDir;}

	public DBCourses
	getDBCourse ()
	{return this.dbCourses;}

	public DBSharedCourses
	getDbSharedCourses ()
	{return this.dbSharedCourses;}
}