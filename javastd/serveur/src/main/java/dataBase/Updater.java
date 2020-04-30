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
    /** Repertoire ou se trouvent les semestres */
    private File dir;

    /**
     * Constructeur de base
     * @param s Le serveur
     */
    public
    Updater (Serveur s) {
        this(s, s.getSemesterDataBase().getDirectory());
    }

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

    /**
     * La fonction body est le corps principal de la fonction run.
     * Elle permet s'executer une fois la boucle principale sans passer par celle-ci.
     * Cette fonction est utile pour les tests unitaires.
     * @param lastModified | Represente la date de la derniere modification des fichiers
     * @return La derniere modification des fichiers
     */
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
