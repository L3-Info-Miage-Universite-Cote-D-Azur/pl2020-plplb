package database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import debug.Debug;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class
DBCourses
{
    private Directory directory;
    /** Le FileManager qui permet de gerer le parcours du client */
    private FileManager courseFile;

    public
    DBCourses (String whereToWork)
    {this.directory = new Directory("", whereToWork);}

    /**
     * Permet de d'ecraser la sauvegarde courante du client
     * par sa nouvelle sauvegarde dans le fichier db/numEtu/fileName.txt
     * <br>
     * Le fichier se sauvegarde contient la liste des codes des UEs choisies par l'utilisateur
     * <br><br>
     * <strong>Exemple de contenu de fichier:</strong>
     * <pre>{"Informatique", "SLZ51", "SLZI5C", "SLZI8B"}</pre>
     * @param als La liste de string qui represente les codes des UEs
     */
    public void
    save (ArrayList<String> als)
    {
        // Si le dossier de l'etudiant existe
        if (!this.directory.exists())
            this.directory.makeDirectory();
        FileManager file = new FileManager(this.directory.getWorkingDirectory().getCurrentDirectory() + "\\" + als.get(0) + ".txt");
        // Enlever le nom du parcours de la liste
        als.remove(0);
        // Si son parcours existe
        if (!file.exists())
            file.create();
        final Gson gson = new GsonBuilder().create();
        file.write(gson.toJson(als));
    }

    public void
    save (ArrayList<String> als, String dossier)
    {
        // Si le dossier de l'etudiant existe
        if (!this.directory.exists())
            this.directory.makeDirectory();
        FileManager file = new FileManager(this.directory.getWorkingDirectory().getCurrentDirectory() + "\\"  + dossier + "\\" + als.get(0) + ".txt");
        // Enlever le nom du parcours de la liste
        als.remove(0);
        // Si son parcours existe
        if (!file.exists())
            file.create();
        final Gson gson = new GsonBuilder().create();
        file.write(gson.toJson(als));
    }

    /**
     * Permet de charger le fichier de l'utilisateur sous un Parcours.
     * Si le client n a pas de sauvegarde, on revoie <strong>null</strong>
     * @param courseName Le nom du parcours
     * @return Parcours de l'utilisateur au premier semestre
     */
    public ArrayList<String>
    load (String courseName)
    {
        if (!new File(this.directory.getWorkingDirectory().getCurrentDirectory()).exists())
        {
            Debug.error("Directory " + this.directory.getWorkingDirectory().getCurrentDirectory() + " doesnt exist");
            return null;
        }
        // Represente le contenu du fichier
        FileManager file = new FileManager(this.directory.getWorkingDirectory().getCurrentDirectory() + "\\" + courseName + ".txt");
        if (!file.exists())
        {
            Debug.error("File " + this.directory.getWorkingDirectory().getCurrentDirectory() + "\\" + courseName + ".txt" + " doesnt exist");
            return null;
        }
        String fcontent = file.getFileContent();

        /*
         * Transformation d'une String sous format JSON vers une List<String>
         * qui contient les keys (ici les codes)
         */
        final Gson gson = new GsonBuilder().create();
        @SuppressWarnings("unchecked")
        ArrayList<String> content = gson.fromJson(fcontent, ArrayList.class);
        ArrayList<String> res = new ArrayList<String>();
        res.add(courseName);
        res.addAll(content);
        Debug.log("Sending " + res.toString());
        return res; //On renvoie le type de parcours + les ues selectionnee de la sauvegarde.
    }

    public Boolean
    renameCourse (String oldCourseName, String newCourseName)
    {
        Boolean success = false;
        FileManager oldFile = new FileManager(this.getPathTo(oldCourseName));
        /* On ne peut pas renommer un fichier qui n'existe pas */
        if (!oldFile.exists())
            {return success;}
        FileManager newFile = new FileManager(this.getPathTo(newCourseName));
        /* S'il y a deja un fichier existant */
        if (!newFile.exists())
            {success = oldFile.getFile().renameTo(newFile.getFile());}
        return success;
    }

    public Boolean
    deleteCourse (String courseName)
    {
        Boolean success = false;
        FileManager oldFile = new FileManager(this.getPathTo(courseName));
        if (!oldFile.exists())
            {return success;}
        success = oldFile.getFile().delete();
        return success;
    }

    /**
     * Setter du FileManager a l'aide d'une String representant le nom du parcours.
     * @param cf une String
     */
    public void
    setCourseFile (String cf)
    {this.courseFile = new FileManager(this.directory.getWorkingDirectory().getCurrentDirectory() + "\\" + this.directory.getDirectoryName() + "\\" + cf);}

    /**
     * Setter du FileManager a l'aide d'une String representant le nom du parcours.
     * @param cf un File
     */
    public void
    setCourseFile (File cf)
    {this.setCourseFile(cf.getName());}

    /* SETTERS */
    /**
     * Setter du FileManager a l'aide d'une String representant le nom du dossier.
     * @param dir une String
     */
    public void
    setCurrentDir (String dir)
    {this.directory.setDirectory(this.directory.getWorkingDirectory().getCurrentDirectory() + "\\" + dir);}

    /**
     * Setter du FileManager a l'aide d'une String representant le nom du dossier.
     * @param dir un File
     */
    public void
    setCurrentDir (File dir)
    {this.setCurrentDir(dir.getName());}

    /**
     * Getter du parcours courant.
     * Le FileManager qui permet de gerer le parcours du client
     * @return FileManager
     */
    public FileManager
    getCourse ()
    {return this.courseFile;}

    /**
     * Permet d'avoir tous les noms de parcours dans le dossier courant
     * @return un ArrayList&lt;String&gt;
     */
    public ArrayList<String>
    getAllCourses ()
    {
        ArrayList<String> res = new ArrayList<String>();
        if (this.directory.exists())
        {
            File[] list = this.directory.listFiles();
            if(list != null){
                for (File f : list)
                    res.add(f.getName().replaceAll(".txt", ""));
            }
        }
        return res;
    }

    private String
    getPathTo (String theFile)
    {
        return this.directory.getObject().getAbsolutePath() + "\\" + theFile;
    }

    public Directory
    getDir ()
    {return this.directory;}
}
