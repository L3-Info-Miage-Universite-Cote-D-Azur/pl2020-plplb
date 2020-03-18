package debug;

/**
 * Debug gere l'affichage des messages du serveur
 */
public class Debug
{
    /* Set this value to true if you want to get verbose mode */
    public static boolean verbose = true;
    private Debug () {}

    /**
     * affiche un message de facon standard.
     * @param msg le message a afficher
     */
    public static void
    debug (String msg)
    {
        if (Debug.verbose)
            System.out.println(msg);
    }

    /**
     * log affiche un message de log
     * @param msg le message a afficher
     */
    public static void
    log (String msg)
    {
        Debug.debug("[*] " + msg);
    }

    /**
     * error affiche un message d'erreur
     * @param msg le message a afficher
     */
    public static void
    error (String msg)
    {
        Debug.debug("[E] " + msg);
    }
}