import serveur.Serveur;
import window.GUI;
import file.Config;

import java.awt.EventQueue;
import java.io.File;

/**
 * Classe principale de l'application
 */
public class App {
    public static final void main(String[] args) {

        ClassLoader classLoader = App.class.getClassLoader();
        File configFile = new File(classLoader.getResource("config.txt").getFile());

        //on charge le fichier config:
        Config config = new Config(configFile);
        config.initConfig();

        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI(config);
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}