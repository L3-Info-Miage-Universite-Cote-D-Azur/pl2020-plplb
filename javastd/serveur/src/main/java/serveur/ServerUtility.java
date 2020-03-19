package serveur;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;

import metier.Etudiant;

public final class 
ServerUtility 
{
	public static Etudiant
	getStudentFromList (SocketIOClient sock, List<Client> l)
	{
		for (Client c : l)
			if (sock.equals(c.getSock()))
				return c.getStudent();
		return null;
	}
	
	public static SocketIOClient
	getSocketFromList (Etudiant etu, List<Client> l)
	{
		for (Client c : l)
			if (etu.getNom().equals(c.getStudent().getNom()))
				return c.getSock();
		return null;
	}
	
	public static Client
	getClientFromSocketOnList (SocketIOClient sock, List<Client> l)
	{
		for (Client c : l)
			if (sock.equals(c.getSock()))
				return c;
		return null;
	}
}
