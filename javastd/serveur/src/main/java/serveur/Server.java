package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import org.json.JSONException;
import org.json.JSONObject;

public class
Server
{
	private SocketIOServer socket;
	private String IP;
	private int port;
	private Configuration config;
	//private final Object clientWaiter = new Object();
	
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
			@Override
			public void 
			onConnect (SocketIOClient client) 
			{
				Debug.log("New client connected: " + client.getRemoteAddress());
				/*JSONObject msg = new JSONObject();
				try {
					msg.put("username", "client1");
					msg.append("message", "Hello client");
					client.sendEvent(msg.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}*/
				client.sendEvent("serverMessage", "Hello zizi");
			}
		});
		
		this.socket.addEventListener("clientMessage", String.class, new DataListener<String>() {
			@Override
			public void
			onData (SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				Debug.log("Message du client: " + data);
			}
		});
	}
	
	public void
	run ()
	{
		this.socket.start();
		/*synchronized (this.clientWaiter)
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
		this.socket.stop();*/
	}
}
