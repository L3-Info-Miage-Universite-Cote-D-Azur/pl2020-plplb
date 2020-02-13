package serveur;

public class 
App 
{
    public static void 
    main (String[] args)
    {
    	Debug.verbose = true;
        Server serv = new Server("127.0.0.1", 10101);
        serv.run();
        Debug.log("Fin du serveur");
    }
}
