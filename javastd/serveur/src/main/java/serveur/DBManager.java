package serveur;

import java.io.*;

import metier.Etudiant;
import metier.Semestre;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	 * par sa nouvelle sauvegarde dans le fichier db/file.getName().txt pour son seul parcours
	 * TODO:
	 * Porter cette fonction a quelque chose de plus general, par exemple avec une class
 	 * Client qui contient une liste de Parcours, qui contiennent chacun 4 semestres de
	 * type Semestre, qui contiennent chacun une liste d'UE
	 * @param semestre le Semestre courant
	 */
	public void
	save (Semestre semestre)
	{
		if (!this.file.getFile().exists()) {
			try {
				this.file.getFile().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		final Gson gson = new GsonBuilder().create();
		this.file.write(gson.toJson(semestre));
	}
	
	/**
	 * Permet de charger le fichier courant sous un JSONObject, 
	 * si le client n a pas de sauvegarde, on revoie le premier semestre
	 * @return JSONObject
	 */
	public String
	load ()
	{
		if (!this.file.getFile().exists())
			return SemestersSample.s1();
		return this.file.getFileContent();
	}

	/*
	/**
	 * Permet de charger le parcours du client courant sous un JSONObject, 
	 * si le parcours ou le fichier n existe pas, on renvoie null
	 * @param key La String representant le nom du parcours
	 * @return JSONObject
	public JSONObject
	loadCourse (String key)
	{
		// Check si le fichier existe
		if (!this.file.getFile().exists())
			return null;
		JSONObject json = new JSONObject(this.file.getFileContent());
		// Check si la clef key existe dans notre fichier
		if (!json.has(key))
			return null;
		return new JSONObject(this.load().getJSONObject(key));
	}
	*/
}