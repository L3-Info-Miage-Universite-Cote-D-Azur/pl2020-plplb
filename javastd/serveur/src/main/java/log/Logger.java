package log;

import file.Config;
import file.FileManager;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger gere l'affichage des messages du serveur
 */
public class Logger
{
    /** Verbose mode: Permet d'activer ou non les messages de debogugages */
    public static boolean verbose = true;
    /** Represente la totalite des messages de deboguages */
    public static String logs = "";

    /** Aucune instanciation possible */
    private Logger () {}

    /**
     * Permet de sauvegarder Logger.logs dans
     * un fichier conçu pour les logs
     */
    public static void
    saveLogs (Config config)
    {
        File dir = new File(config.getparentPath(),config.getConfig("log_directory"));
        dir.mkdir();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        FileManager fm = new FileManager(dir.getAbsolutePath() + "\\" + dtf.format(now) + ".txt");
        fm.create();
        fm.write(Logger.logs);
        Logger.logs = "";
    }

    /**
     * Affiche un message de facon standard.
     * @param msg le message a afficher
     */
    public static void
    put (String msg)
    {
        if (Logger.verbose)
        {
            System.out.println(msg);
            Logger.logs += msg + System.getProperty("line.separator");
        }
    }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public static void
    log (String msg)
    {
        Logger.put("[*] " + msg);
    }

    /**
     * Error affiche un message d'erreur
     * @param msg le message a afficher
     */
    public static void
    error (String msg)
    {
        Logger.put("[E] " + msg);
    }
}