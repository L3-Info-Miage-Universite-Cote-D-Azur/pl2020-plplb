package file;

import log.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class qui permet de gerer le fichier config.
 * Le fichier config est un fichier de configuration qui permet
 * la gestion de differents parametres persistant de l'application
 */
public class Config {
    /** Represente le fichier de configuration de l'application */
    private FileManager configFile;
    /** Boolean qui represente si oui ou non le fichier a deja ete initialise */
    private boolean isInit = false;
    /** Representation de la configuration de l'application sous une table de hachage */
    private HashMap<String,String> config;

    /**
     * Constructeur de base
     * @param configFile | Le fichier de configuration
     */
    public Config(File configFile){
        this.configFile = new FileManager(configFile);
    }

    /**
     * Permet de charger le fichier config,
     * cela permet d'acceder aux differents elements qui le compose
     */
    public void initConfig(){
        if(!configFile.exists()){
            Logger.error("Impossible de trouver le fichier config");
            return;
        }
        List<String> configArray = new Parser().parseLine(configFile.getRaw());
        if(configArray.size()==0){
            Logger.error("Impossible de charger le fichier config");
            return;
        }
        isInit = true;
        config = new HashMap<String,String>();
        for(String elt : configArray){
            if(elt.trim().length()>0 && elt.trim().charAt(0) != '!') { //si ce n'est pas un commentaire ou ligne vide
                List<String> tuple = Arrays.asList(elt.split(":="));
                if (tuple.size() == 2) {
                    config.put(tuple.get(0).trim(), tuple.get(1).trim());
                }
            }
        }

    }

    /**
     * Permet de recuperer un element du fichier config
     * @param id l'element que l'on veut verifier
     * @return l'element que l'on veut
     */
    public String getConfig(String id) {
        if (isInit) {
            return config.getOrDefault(id, "");
        }
        return null;
    }

    /**
     * Permet de recuperer la hashmap generer a l'aide du fichier (pour les test)
     * @return la hashmap
     */
    protected  HashMap<String,String> getConfig(){
        return config;
    }

    /**
     * Permet de recuperer le chemin du directory
     * @return le chemin du directory
     */
    public String getparentPath(){
        return configFile.getParentPath();
    }

    /** Getter de isInit */
    protected boolean getIsInit(){
        return  isInit;
    }

}
