package dataBase;

import java.io.File;
import java.util.concurrent.TimeUnit;

import file.FileUtility;
import log.Logger;
import serveur.Serveur;

/**
 * Class qui permet la gestion de la mise
 * a jour des semestres dans le serveur
 */
public class
Updater implements Runnable
{
    /** Objet qui represente le serveur */
    private Serveur serv;
    /** Directory */
    private File dir;

    /**
     * Constructeur de SemesterThread
     * @param s Le serveur
     */
    public
    Updater (Serveur s)
    {this(s, new File(s.getServConfig().getparentPath(), s.getServConfig().getConfig("semestre_directory")));}

    /**
     * Constructeur de SemesterThread
     * @param s Le serveur
     * @param f le fichier
     */
    public
    Updater (Serveur s, File f)
    {
        this.serv = s;
        this.dir = f;
    }

    @Override
    public void
    run ()
    {
        long lastModified = FileUtility.getLastModified(this.dir);
        try {
            while (true)
            {
                TimeUnit.SECONDS.sleep(10);
                lastModified = this.body(lastModified);
            }
        } catch (Exception E) {
            Logger.error("An error in Updater has occured");
            E.printStackTrace();
            return;
        }
    }

    public long
    body (long lastModified)
    {
        if (FileUtility.getLastModified(this.dir) != lastModified)
        {
            lastModified = FileUtility.getLastModified(this.dir);
            this.serv.updateSemestersOfClients();
            Logger.log("Semesters updated !");
        }
        return lastModified;
    }
}
