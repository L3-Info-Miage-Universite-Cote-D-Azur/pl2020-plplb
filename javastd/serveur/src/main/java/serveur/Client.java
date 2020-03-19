package serveur;

import com.corundumstudio.socketio.SocketIOClient;

import metier.Etudiant;

/**
 * Classe-association pour le client
 */
public class 
Client 
{
	/** L'etudiant represente */
	private Etudiant etu;
	/** La socket du client */
	private SocketIOClient sock;
	
	/**
	 * Constructeur de Client
	 * @param etu L'etudiant represente
	 * @param sock La socket du client
	 */
	public
	Client (Etudiant etu, SocketIOClient sock)
	{
		this.etu = etu;
		this.sock = sock;
	}
	
	@Override
	public String
	toString ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(this.sock.getRemoteAddress().toString());
		sb.append("|");
		sb.append(this.etu.getNom());
		sb.append("]");
		return sb.toString();
	}
	
	/** Getter de Etudiant */
	public Etudiant getStudent ()
	{return this.etu;}
	
	/** Getter de SocketIOCLient */
	public SocketIOClient getSock ()
	{return this.sock;}
}
