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
	private FileManager dir;
	/** Le FileManager qui permet de gerer le parcours du client */
	private FileManager courseFile;
	
	/* CONSTRUCTORS */
	/**
	 * Constructeur par defaut, construit un FileManager de nom "default.txt"
	 */
	public
	DBManager ()
	{this("defaultStudent","defaultCourse");}
	
	/**
	 * Constructeur, associe un fichier file au FileManager de la class
	 * @param dir un File contenant le dossier
	 * @param course une File contenant le parcours
	 */
	public 
	DBManager (File dir, File course)
	{
		this.dir = new FileManager(dir.getName());
		this.courseFile = new FileManager(course.getName());
	}
	
	/**
	 * Construceur, associe le nom du fichier au FileManager de la class 
	 * et creer le dossier db/ s'il n'existe pas encore
	 * @param dir un File contenant le dossier
	 * @param course une File contenant le parcours
	 */
	public 
	DBManager (String directory, String course)
	{
		FileManager dir = new FileManager("db");
		if (!dir.exists())
			dir.getFile().mkdir();
		this.dir = new FileManager("db/" + directory);
		this.courseFile = new FileManager("db/" + directory + "/" + course + ".txt");
	}
	
	/* GETTERS */
	/**
	 * Getter du dossier courant.
	 * Le FileManager qui permet de gerer le dossier du client
	 * @return FileManager
	 */
	public FileManager
	getDir ()
	{return this.dir;}
	
	/**
	 * Getter du parcours courant.
	 * Le FileManager qui permet de gerer le parcours du client
	 * @return FileManager
	 */
	public FileManager
	getCourse ()
	{return this.courseFile;}
	
	/**
	 * Permet d'avoir tous les noms de parcours dans le dossier courant
	 * @return un ArrayList&lt;String&gt;
	 */
	public ArrayList<String>
	getAllCourses ()
	{
		ArrayList<String> res = new ArrayList<String>();
		if (this.dir.exists())
		{
			File[] list = this.dir.getFile().listFiles();
			for (File f : list)
				res.add(f.getName().replaceAll(".txt", ""));
		}
		return res;
	}
	
	/* SETTERS */
	/**
	 * Setter du FileManager a l'aide d'une String representant le nom du dossier.
	 * @param dir une String
	 */
	public void
	setCurrentDir (String dir)
	{
		/***
		 * TODO:
		 * Checker si file est bien sous la forme d'un numero etudiant
		 */
		
		// On check si les 3 premiers caracteres sont notre dossier
		if (dir.length() > 3 && dir.subSequence(0, 3).equals("db/"))
			this.dir = new FileManager(dir);
		else
			this.dir = new FileManager("db/" + dir);
	}

	/**
	 * Setter du FileManager a l'aide d'une String representant le nom du dossier.
	 * @param dir un File
	 */
	public void
	setCurrentDir (File dir)
	{this.setCurrentDir(dir.getName());}
	
	/**
	 * Setter du FileManager a l'aide d'une String representant le nom du parcours.
	 * @param dir une String
	 */
	public void
	setCourseFile (String cf)
	{this.courseFile = new FileManager("db/" + this.dir.getFile().getName() + "/" + cf);}
	
	/**
	 * Setter du FileManager a l'aide d'une String representant le nom du parcours.
	 * @param dir un File
	 */
	public void
	setCourseFile (File cf)
	{this.setCourseFile(cf.getName());}
	
	/* METHODS */
	/**
	 * Permet de d'ecraser la sauvegarde courante du client 
	 * par sa nouvelle sauvegarde dans le fichier db/numEtu/fileName.txt
	 * <br>
	 * Le fichier se sauvegarde contient la liste des codes des UEs choisies par l'utilisateur
	 * <br><br>
	 * <strong>Exemple de contenu de fichier:</strong>
	 * <pre>{"Informatique", "SLZ51", "SLZI5C", "SLZI8B"}</pre>
	 * @param als La liste de string qui represente les codes des UEs
	 */
	public void
	save (ArrayList<String> als)
	{
		// Si le dossier de l'etudiant existe
		if (!this.dir.exists())
			this.dir.getFile().mkdir();
		FileManager file = new FileManager("db/" + this.dir.getFile().getName() + "/" + als.get(0) + ".txt");
		// Enlever le nom du parcours de la liste
		als.remove(0);
		// Si son parcours existe
		if (!file.exists())
			file.create();
		final Gson gson = new GsonBuilder().create();
		file.write(gson.toJson(als));
	}
	
	/**
	 * Permet de charger le fichier de l'utilisateur sous un Parcours.
	 * Si le client n a pas de sauvegarde, on revoie <strong>null</strong>
	 * @param courseName Le nom du parcours
	 * @return Parcours de l'utilisateur au premier semestre
	 */
	public ArrayList<String>
	load (String courseName)
	{
		if (!this.dir.exists())
			return null;
		// Represente le contenu du fichier
		FileManager file = new FileManager("db/" + this.dir.getFile().getName() + "/" + courseName + ".txt");
		if (!file.exists())
			return null;
		String fcontent = file.getFileContent();
		
		/*
		 * Transformation d'une String sous format JSON vers une List<String>
		 * qui contient les keys (ici les codes)
		 */
		final Gson gson = new GsonBuilder().create();
		@SuppressWarnings("unchecked")
		ArrayList<String> content = gson.fromJson(fcontent, ArrayList.class);

		return content; //On renvoie le type de parcours + les ues selectionnee de la sauvegarde.
	}
}