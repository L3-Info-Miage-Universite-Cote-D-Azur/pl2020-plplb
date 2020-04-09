package serveur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.Student;
import metiermanager.courses.ParcoursSample;
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
	 * Permet d'ajouter correctement un client
	 * sans qu'il y ait duplication de sockets
	 * @param list la liste des clients
	 * @param client le client a ajouter
	 */
	public static void
	addClientToList (ArrayList<Client> list, Client client)
	{
		SocketIOClient clientSocket = client.getSock();
		/* Recherche des clients de list qui contiennent la meme socket
		* que le client qu'on veut ajouter */
		for (int i = 0; i < list.size(); i++)
		{
			/* Pour chaque client qui a la meme socket */
			if (list.get(i).getSock() == clientSocket)
			{
				/* Le supprimer de la liste */
				list.remove(list.get(i));
				break;
			}
		}
		/* Ajouter le client a la liste */
		list.add(client);
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
	
	/**
	 * Renvoie une String contenant la liste des
	 * semestres sous format JSON
	 * @return une String
	 */
	public static String
	getListOfCourseTypeJSONed()
	{
		Gson gson = new GsonBuilder().create();
		ArrayList<String> coursesJsonned = new ArrayList<String>();
		coursesJsonned.add(ParcoursSample.JPI);
		coursesJsonned.add(ParcoursSample.JPM);
		coursesJsonned.add(ParcoursSample.JPMM);
		coursesJsonned.add(ParcoursSample.JPE);
		coursesJsonned.add(ParcoursSample.JPF);
	    return gson.toJson(coursesJsonned);
	}

	/**
	 * Renvoie le code (string) unique généré.
	 * @param existingCode : la liste des codes qui existent déjà
	 * @return le code unique.
	 */
	public static String
	generateCourseCode(ArrayList<String> existingCode){
		Random random = new Random();
		String code = "todo";

		//Si l'on reçoit un null, on creer une liste vide.
		if(existingCode == null){
			existingCode = new ArrayList<String>();
		}

		//Tant que le code n'a pas été créer ou qu'il existe déjà.
		while(code == "todo" || existingCode.contains(code)){
			//On reinitialise le code.
			code = "";

			//On ajoute 5 fois un chiffre aléatoire entre 0 et 9
			for(int i = 0; i < 5;i++){
				code  += ""+random.nextInt(10);
			}
		}
		//On retourne le code.
		return code;
	}
}
