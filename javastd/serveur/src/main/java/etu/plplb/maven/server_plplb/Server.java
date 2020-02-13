package etu.plplb.maven.server_plplb;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;

public class
Server
{
	private SocketIOServer socket;
	private String IP;
	private int port;
	private Configuration config;
	private final Object clientWaiter = new Object();
	
	public
	Server (String IP, int port)
	{
		Debug.log("Creating server..");
		this.IP = IP;
		this.port = port;
		Debug.log("Creating server configuration..");
		this.config = new Configuration();
		this.config.setHostname(this.IP);
		this.config.setPort(this.port);
		Debug.log("Server configuration created.");
		this.socket = new SocketIOServer(this.config);
		Debug.log("Serveur created.");
		
		/* Listener */
		this.socket.addConnectListener(new ConnectListener() 
		{
			/* A la connexion, on envoie un message au client */
			public void 
			onConnect (SocketIOClient client) 
			{
				Debug.log("New client connected: " + client.getRemoteAddress());
				client.sendEvent("Hello Client");
			}
		});
	}
	
	public void
	run ()
	{
		this.socket.start();
		synchronized (this.clientWaiter)
		{
			try
			{
				this.clientWaiter.wait();
			}
			catch (InterruptedException e) 
			{
                e.printStackTrace();
                Debug.error("Client Waiter Error");
            }
		}
		this.socket.stop();
	}
}
