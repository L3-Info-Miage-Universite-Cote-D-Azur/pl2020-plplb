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
    /** Booleen de sortie de thread */
    private boolean __UT;

    /**
     * Constructeur de SemesterThread
     * @param s Le serveur
     */
    public
    Updater (Serveur s)
    {
        this.serv = s;
        this.dir = new File(this.serv.getServConfig().getparentPath(), this.serv.getServConfig().getConfig("semestre_directory"));
        this.__UT = true;
    }

    @Override
    public void
    run ()
    {
        long lastModified = FileUtility.getLastModified(this.dir);
        try {
            while (this.__UT)
            {
                TimeUnit.SECONDS.sleep(10);
                if (FileUtility.getLastModified(this.dir) != lastModified)
                {
                    lastModified = FileUtility.getLastModified(this.dir);
                    this.serv.updateSemestersOfClients();
                    Logger.log("Semesters updated !");
                }
            }
        } catch (Exception E) {
            Logger.error("An error in SemesterThread has occured");
            E.printStackTrace();
            return;
        }
    }

    public void
    swapBool ()
    {
        if (this.__UT == true)
            this.__UT = false;
        else
            this.__UT = true;
    }
}
