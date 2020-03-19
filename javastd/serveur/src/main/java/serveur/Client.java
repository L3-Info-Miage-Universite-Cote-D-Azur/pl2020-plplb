package serveur;

import com.corundumstudio.socketio.SocketIOClient;

import metier.Etudiant;

public class 
Client 
{
	private Etudiant etu;
	private SocketIOClient sock;
	
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
	
	public Etudiant getStudent ()
	{return this.etu;}
	
	public SocketIOClient getSock ()
	{return this.sock;}
}
