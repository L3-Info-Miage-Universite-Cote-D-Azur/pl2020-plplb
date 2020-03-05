package serveur;

import java.io.*;

/**
 * Permet de gerer avec plus de facilite les fichiers de l'application
 * @author theoricien
 */
public class 
FileManager 
{
	/* FIELDS */
	/**
	 * file represente un fichier sous un File
	 */
	private File file;
	
	/* CONSTRUCTORS */
	/**
	 * Constructeur a l'aide d'une String
	 * @param file une String representant le nom du fichier voulu
	 */
	public
	FileManager (String file)
	{this.file = new File(file);}
	
	/**
	 * Constructeur avec un File
	 * @param file un File
	 */
	public
	FileManager (File file)
	{this.file = file;}
	
	/* METHODS */
	/**
	 * Permet de vider totalement un fichier
	 */
	public void
	clearFile ()
	{this.write("");}
	
	/**
	 * Permet de creer le fichier
	 */
	public void
	create ()
	{
		try {
			this.file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de savoir si le fichier existe ou non
	 * @return Un boolean
	 */
	public boolean
	exists ()
	{return this.file.exists();}
	
	/**
	 * Permet de remplacer le contenu d'un fichier par content
	 * @param content Le futur contenu du fichier
	 */
	public void
    write (String content)
	{
		// Si on a les droits pour ecrire
		if (this.file.canWrite())
		{
			PrintWriter pw;
			// On essaye d'ecrire
			try {
				pw = new PrintWriter(this.file);
				// On essaye d'ecrire ici
				pw.write(content);
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Debug.error("File " + this.file.getName() + " does not exist !");
			}
		} else {
			Debug.error(this.file.getName() + " isn\'t writable !");
		}

	}
	
	/**
	 * Permet de recuperer la premiere ligne de notre fichier, puisque dans notre
	 * application, il n'y a qu'1 seule ligne par fichier
	 * @return Une String
	 */
	public String
	getFileContent ()
	{
		// Si on a les droits pour lire le fichier
		if (this.file.canRead())
		{
			// On essaye de lire la premiere ligne
			try
			{
				/* INITIALISATION */
				FileReader r = new FileReader(this.file);
				BufferedReader br = new BufferedReader(r);
				/* GET THE CONTENT */
				String tmp = br.readLine();
				/* CLOSE FILESTREAMS */
				br.close();
				r.close();
				
				// On renvoie la premier ligne
				return tmp;
			} catch (Exception e) {
				e.printStackTrace();
				Debug.error(this.file.getName() + " doesn\'t exist !");
			}
		} else {
			Debug.error(this.file.getName() + " isn\'t readable !");
		}
		return "";
	}
	
	/* GETTER */
	/**
	 * Getter qui renvoie le File file pour des manipulations plus precises.
	 * file represente un fichier sous un File
	 * @return un File
	 */
	public File
	getFile ()
	{return this.file;}
}
