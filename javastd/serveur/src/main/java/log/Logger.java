package log;

/**
 * Logger gere l'affichage des messages du serveur
 */
public class Logger
{
    /* Set this value to true if you want to get verbose mode */
    public static boolean verbose = true;
    private Logger () {}

    /**
     * affiche un message de facon standard.
     * @param msg le message a afficher
     */
    public static void
    info (String msg)
    {
        if (Logger.verbose)
            System.out.println(msg);
    }

    /**
     * log affiche un message de log
     * @param msg le message a afficher
     */
    public static void
    log (String msg)
    {Logger.info("[*] " + msg);}

    /**
     * error affiche un message d'erreur
     * @param msg le message a afficher
     */
    public static void
    error (String msg)
    {Logger.info("[E] " + msg);}
}