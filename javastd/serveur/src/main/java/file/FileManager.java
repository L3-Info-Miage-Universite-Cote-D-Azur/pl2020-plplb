package file;

import log.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    /* FIELDS */
    /**
     * file represente un fichier que le fileManager s'occupe
     */
    private File file;

    /**
     * Constructeur a l'aide d'une String
     * @param path une String representant le chemin du fichier voulue
     */
    public FileManager (String path)
    {this.file = new File(path);}

    /**
     * Constructeur a l'aide d'un file
     * @param file le file qui vas etre utiliser
     */
    public FileManager (File file)
    {this.file = file;}


    /**
     * Permet de créer plusieur plusieur fileManager a
     * l'aide d'un chemin de de nom de fichier
     * @param path le repertoir a partir du quel sont stocker/créer les fichiers
     * @param filesName la liste des nom de fichier
     * @return une list de fileManager
     */
    public static List<FileManager> ListToFileManager(String path , List<String> filesName) {
        ArrayList<FileManager> filesManager = new ArrayList<FileManager>();
        for (String name : filesName) {
            filesManager.add(new FileManager(new File(path,name)));
        }
        return filesManager;
    }


    /**
     * Permet de creer le fichier
     */
    public boolean create()
    {
        try {
            this.file.createNewFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error(this.file.getName()+ "can\'t be create");
        }
        return false;
    }

    /**
     * Permet de savoir si le fichier existe ou non
     * @return Un boolean
     */
    public boolean exists ()
    {return this.file.exists();}

    /**
     * Permet de remplacer le contenu d'un fichier par content
     * @param content Le futur contenu du fichier
     */
    public void write (String content)
    {
        //si le fichier n'est pas encore créer on le crée
        if (!file.exists()) {
            boolean isCreate = create();
            if(!isCreate) return;
        }

        // Si on a les droits pour ecrire
        if(file.canWrite()){
            PrintWriter pw;
            // On essaye d'ecrire
            try {
                pw = new PrintWriter(this.file);
                // On essaye d'ecrire ici
                pw.write(content);
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Logger.error("File " + this.file.getName() + " does not exist !");
            }

        } else {
            Logger.error(this.file.getName() + " isn\'t writable !");
        }

    }

    /**
     * Permet de renvoyer tout le contenu d'un fichier
     * sous forme brut
     * @return un String
     */
    public String getRaw()
    {

        //si le fichier n'est pas créer
        if (!file.exists()) return null;

        // Si on a les droits pour lire le fichier
        if (this.file.canRead())
        {
            // On essaye de lire la premiere ligne
            try
            {
                /* INITIALISATION */
                FileReader r = new FileReader(this.file);
                BufferedReader br = new BufferedReader(r);
                StringBuilder resultString = new StringBuilder();
                /* GET THE CONTENT */
                String tmp;
                //On remet le \n sauf la premiere fois que l'on lit une ligne
                if((tmp = br.readLine()) != null){
                    resultString.append(tmp);
                }
                while ((tmp = br.readLine()) != null)
                {
                    resultString.append("\n"+tmp);
                }
                /* CLOSE FILESTREAMS */
                br.close();
                r.close();

                // On renvoie la premier ligne
                return resultString.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Logger.error(this.file.getName() + " doesn\'t exist !");
            }
        } else {
            Logger.error(this.file.getName() + " isn\'t readable !");
        }
        return "";
    }

    /**
     * Permet de suprimer un fichier
     * @return true ou false selon la reussite de la suppression
     */
    public boolean deleteFile(){
        if(exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Permet de recueperer le chemin du repertoire parent
     * @return le chemin du repertoire parent
     */
    public String getParentPath(){
        return file.getParent();
    }


    /**
     * Permet de recuperer le File du file manager
     */
    public File getFile() {
        return file;
    }

    /**
     * Permet de vider totalement un fichier
     */
    public void clearFile ()
    {write("");}
}
