import serveur.Serveur;
import file.Config;


import java.io.File;

/**
 * Classe principale du serveur
 */
public class App {


    public static final void main(String[] args) {

        ClassLoader classLoader = App.class.getClassLoader();
        File configFile = new File(classLoader.getResource("config.txt").getFile());

        //on charge le fichier config:
        Config config = new Config(configFile);
        config.initConfig();


        //on lance le serveur
        Serveur serv = new Serveur(config);
        serv.startServer();

    }
}