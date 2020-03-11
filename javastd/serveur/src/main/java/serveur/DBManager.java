package serveur;

import java.io.*;
import java.util.*;

import metier.Etudiant;
import metier.Parcours;
import metier.Semestre;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.*;

/**
 * DBManager permet de gerer le systeme de base 
 * de donnees de l'application, elle permet de 
 * sauvegarder grace a la fonction save(), de 
 * charger grace a la fonction load(), de charger 
 * un parcours avec la fonction loadCourse()
 * TODO:
 * Faire une fonction saveCourse() qui permet
 * de sauvegarder un parcours, et donc d'en ecraser
 * un si son nom existe deja, avec pour cela
 * l'accord du client en amont
 * @author theoricien
 */
public class
DBManager
{
	/* FIELDS */
	/**
	 * Le FileManager qui permet de gerer le fichier du client
	 */
	private FileManager file;
	
	/* CONSTRUCTORS */
	/**
	 * Constructeur par defaut, construit un FileManager de nom "default.txt"
	 */
	public
	DBManager ()
	{this("default.txt");}
	
	/**
	 * Constructeur, associe un fichier file au FileManager de la class
	 * @param file un File
	 */
	public 
	DBManager (File file)
	{this.file = new FileManager(file.getName());}
	
	/**
	 * Construceur, associe le nom du fichier au FileManager de la class 
	 * et creer le dossier db/ s'il n'existe pas encore
	 * @param file une String
	 */
	public 
	DBManager (String file)
	{
		FileManager dir = new FileManager("db");
		if (!dir.exists())
			dir.getFile().mkdir();
		this.file = new FileManager("db/" + file);
	}
	
	/* GETTERS */
	/**
	 * Getter du FileManager.
	 * Le FileManager qui permet de gerer le fichier du client
	 * @return FileManager
	 */
	public FileManager
	getFile ()
	{return this.file;}
	
	/* SETTERS */
	/**
	 * Setter du FileManager a l'aide d'une String representant le nom du fichier.
	 * Le FileManager qui permet de gerer le fichier du client
	 * @param file une String
	 */
	public void
	setFile (String file)
	{
		/***
		 * TODO:
		 * Checker si file est bien sous la forme d'un numero etudiant
		 */
		
		// On check si les 3 premiers caracteres sont notre dossier
		if (file.subSequence(0, 3).equals("db/"))
			this.file = new FileManager(file);
		else
			this.file = new FileManager("db/" + file);
	}
	
	/**
	 * Setter du FileManager a l'aide d'un File.
	 * Le FileManager qui permet de gerer le fichier du client
	 * @param file un File
	 */
	public void
	setFile (File file)
	{this.setFile(file.getName());}
	
	/* METHODS */
	/**
	 * Permet de d'ecraser la sauvegarde courante du client 
	 * par sa nouvelle sauvegarde dans le fichier db/fileName.txt
	 * <br>
	 * Le fichier se sauvegarde contient la liste des codes des UEs choisies par l'utilisateur
	 * <br><br>
	 * <strong>Exemple de contenu de fichier:</strong>
	 * <pre>{"SLZ51", "SLZI5C", "SLZI8B"}</pre>
	 * @param sem Le semestre voulu
	 * @param als La liste de string qui represente les codes des UEs
	 */
	public void
	save (Semestre sem, ArrayList<String> als)
	{
		DBChecker checker = new DBChecker(sem, als);
		if (!checker.checkSave())
		{
			Debug.error("checker.checkSave() a retourne une erreur.. Sauvegarde non ecrasee");
			return;
		}
		if (!this.file.exists())
			this.file.create();
		final Gson gson = new GsonBuilder().create();
		this.file.write(gson.toJson(als));
	}
	
	/**
	 * Permet de d'ecraser la sauvegarde courante du client 
	 * par sa nouvelle sauvegarde dans le fichier db/fileName.txt
	 * <br>
	 * Le fichier se sauvegarde contient la liste des codes des UEs choisies par l'utilisateur
	 * <br><br>
	 * <strong>Exemple de contenu de fichier:</strong>
	 * <pre>{"SLZ51", "SLZI5C", "SLZI8B"}</pre>
	 * @param als La liste de string qui represente les codes des UEs
	 */
	public void
	save (ArrayList<String> als)
	{
		DBChecker checker = new DBChecker(SemestersSample.S1(), als);
		if (!checker.checkSave())
		{
			Debug.error("checker.checkSave() a retourne une erreur.. Sauvegarde non ecrasee");
			return;
		}
		if (!this.file.exists())
			this.file.create();
		final Gson gson = new GsonBuilder().create();
		this.file.write(gson.toJson(als));
	}
	
	/**
	 * Permet de charger le fichier de l'utilisateur sous un Parcours.
	 * Si le client n a pas de sauvegarde, on revoie <strong>null</strong>
	 * @return Parcours de l'utilisateur au premier semestre
	 */
	public Parcours
	load ()
	{
		if (!this.file.exists())
			return null;
		// Represente le contenu du fichier
		String fcontent = this.file.getFileContent();
		
		/*
		 * Transformation d'une String sous format JSON vers une List<String>
		 * qui contient les keys (ici les codes)
		 */
		final Gson gson = new GsonBuilder().create();
		ArrayList<String> lCodes = gson.fromJson(fcontent, ArrayList.class);
		/**
		 * TODO:
		 * Gestion des 2 semestres
		 */
		ArrayList<Semestre> semestres = new ArrayList<Semestre>();
		semestres.add(SemestersSample.S1());
		semestres.add(SemestersSample.S2());
		return new Parcours(semestres, lCodes);
	}
}