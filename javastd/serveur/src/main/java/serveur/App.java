package serveur;

public class 
App 
{
    public static void 
    main (String[] args)
    {
    	Debug.verbose = true;
        Server serv = new Server("127.0.0.1", 10111);
        serv.run();
        Debug.log("Serveur en cours d\'execution");
    }
}
