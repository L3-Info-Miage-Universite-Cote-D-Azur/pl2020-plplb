package file;

import java.io.File;

/**
 * FileUtility est une classe utilitaire qui permet
 * l'utilisation de fonctions utilitaires sur les fichiers
 * sans instanciation
 */
public class FileUtility {

    /**
     * Cette fonction permet d'avoir le dernier
     * element modifie d'un repertoire
     * @param directory | Repertoire a analyser
     * @return | Long : Dernier element modifie
     */
    public static Long getLastModified(File directory) {
        File[] files = directory.listFiles();
        if (files.length == 0) return directory.lastModified();
        long max = -1;
        for(File file : files){
            long lastModif;
            //si c'est un repertoire on regarde a l'interieur le dernier fichier modifier
            if(file.isDirectory()){
                lastModif = getLastModified(directory);
            }
            else{ //si c'est un fichier on recuprer juste la valeur
                lastModif = file.lastModified();
            }
            if(lastModif>max){
                max = lastModif;
            }
        }
        return max;
    }
}
