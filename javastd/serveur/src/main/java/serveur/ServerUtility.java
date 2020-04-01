package serveur;

import java.util.ArrayList;
import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.Student;
import metiermanager.semesters.SemestersSample;

/**
 * Class utilitaire pour une meilleure
 * gestion cote serveur
 */
public final class 
ServerUtility 
{
	/**
	 * Permet de recuperer un Etudiant depuis une List de Client
	 * @param sock La socket du client
	 * @param l La liste des clients
	 * @return L'Etudiant voulu
	 */
	public static Student
	getStudentFromList (SocketIOClient sock, List<Client> l)
	{
		for (Client c : l)
			if (sock.equals(c.getSock()))
				return c.getStudent();
		return null;
	}
	
	/**
	 * Permet de recuperer un SocketIOClient depuis une List de Client
	 * @param etu l'Etudiant du client
	 * @param l La liste des clients
	 * @return La SocketIOCLient voulue
	 */
	public static SocketIOClient
	getSocketFromList (Student etu, List<Client> l)
	{
		for (Client c : l)
			if (etu.getNom().equals(c.getStudent().getNom()))
				return c.getSock();
		return null;
	}
	
	/**
	 * Permet de recuperer le Client a partir de la Socket du client
	 * depuis la List des Client
	 * @param sock la SocketIOClient du client
	 * @param l La liste des clients
	 * @return Le Client voulu
	 */
	public static Client
	getClientFromSocketOnList (SocketIOClient sock, List<Client> l)
	{
		for (Client c : l)
			if (sock.equals(c.getSock()))
				return c;
		return null;
	}
	
	/**
	 * Renvoie une String contenant la liste des
	 * semestres sous format JSON
	 * @return une String
	 */
	public static String
	getListOfSemestersJSONed ()
	{
		Gson gson = new GsonBuilder().create();
		ArrayList<String> semestresJsonned = new ArrayList<String>();
	    semestresJsonned.add(SemestersSample.S1Jsoned);
	    semestresJsonned.add(SemestersSample.S2Jsoned);
	    semestresJsonned.add(SemestersSample.S3Jsoned);
		semestresJsonned.add(SemestersSample.S4Jsoned);
	    return gson.toJson(semestresJsonned);
	}
	
}
