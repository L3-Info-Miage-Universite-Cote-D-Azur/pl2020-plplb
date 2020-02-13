package etu.plplb.maven.server_plplb;

public class 
App 
{
    public static void 
    main (String[] args)
    {
    	Debug.verbose = true;
        Server serv = new Server("127.0.0.1", 6969);
        serv.run();
        Debug.log("Fin du serveur");
    }
}
