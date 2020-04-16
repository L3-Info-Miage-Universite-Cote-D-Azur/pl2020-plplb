package file;

import log.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigTest {
    String path = "testConfig";
    File directory;
    FileManager configFile;

    @BeforeEach
    public void init() {
        Logger.verbose = false;
        this.directory = new File(path);
        this.directory.mkdir();
        configFile=new FileManager(new File(directory,"config.txt"));
    }

    @Test
    public void initTest(){
        configFile.write("!une ligne de commentaire\n"+
                "   elt1 := valueElt1    \n"+
                "\n\n\n"+
                "elt2 := valueElt2\n"//des ligne sauté
                );

        Config config = new Config(configFile.getFile());
        //il est pas initialiser au debut
        assertEquals(config.getIsInit(),false);
        config.initConfig();

        //il est initialiser
        assertEquals(config.getIsInit(),true);
        //on a bien que nos deux element
        assertEquals(config.getConfig().size(),2);
        //on a bien les bonne valeur sans les espace en trop
        assertEquals(config.getConfig("elt1"),"valueElt1");
        assertEquals(config.getConfig("elt2"),"valueElt2");

    }

    @Test
    public void initWithNoFileFoundTest(){
        Config config = new Config(configFile.getFile());

        //il est pas initialise
        assertEquals(config.getIsInit(),false);
        config.initConfig();

        //le fichier n'est pas trouver l'initialisation ne se fait pas
        assertEquals(config.getIsInit(),false);

    }



    @AfterEach
    public void clearFile(){
        for(File file :directory.listFiles()){
            file.delete();
        }
        directory.delete();
    }

}
