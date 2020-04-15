package dataBase;

import debug.Debug;
import metier.parcours.Parcours;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class qui permet de gerer le fichier config
 */
public class Config {

    private FileManager configFile;
    private boolean isInit = false;
    private HashMap<String,String> config;


    public Config(File configFile){
        this.configFile = new FileManager(configFile);
    }

    /**
     * Permet de charger le fichier confi
     * cela permet d'acceder au different element qui le compose
     */
    public void initConfig(){

        List<String> configArray = new Parser().parseLine(configFile.getRaw());
        if(configArray.size()==0){
            Debug.error("Impossible de charger le fichier config");
            return;
        }
        isInit = true;
        config = new HashMap<String,String>();
        for(String elt : configArray){
            if(elt.trim().charAt(0) != '!'&& elt.trim().length()>0) { //si ce n'est pas un commentaire ou ligne vide
                Debug.log(elt);
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
     * Permet de recuperer le chemin du directory
     * @return le chemin du directory
     */
    public String getparentPath(){
        return configFile.getParentPath();
    }

}