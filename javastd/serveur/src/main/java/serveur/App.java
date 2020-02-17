package serveur;

public class 
App 
{
    public static void 
    main (String[] args)
    {
    	Debug.verbose = true;
        Server serv = new Server("192.168.56.1", 10110);
        serv.run();
        Debug.log("Fin du serveur");
    }
}
