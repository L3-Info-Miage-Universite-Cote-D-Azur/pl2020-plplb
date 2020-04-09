package database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import debug.Debug;
import serveur.Client;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class
DBSharedCourses
{
    private Directory directory;
    private FileManager currentFile;

    public
    DBSharedCourses (String whereToWork)
    {
        this.directory = new Directory("\\sharedCourses", whereToWork);
        if (!this.directory.exists())
            this.directory.makeDirectory();
    }

    public void
    saveSharedCourse (String code, ArrayList<String> toSave)
    {
        Gson gson = new GsonBuilder().create();
        this.currentFile = new FileManager(this.directory.getObject().getAbsolutePath() + "\\" + code + ".txt");
        toSave.remove(toSave.get(0));
        if (!this.currentFile.exists())
            this.currentFile.create();
        this.currentFile.write(gson.toJson(toSave));
    }

    /**
     * Fonction qui permet de renvoyer la liste
     * des codes des parcours enregistres dans
     * la base de donnees
     * @return
     */
    public ArrayList<String>
    loadSharedCourseNames ()
    {
        File[] files = this.directory.listFiles();
        ArrayList<String> filenames = new ArrayList<String>();
        for (File f : files)
            filenames.add(f.getName());
        return filenames;
    }

    public Boolean
    loadSharedCourseFile (String sharedCourseCode, Client client, DBCourses privateClientDatabase)
    {
        Gson gson = new GsonBuilder().create();
        FileManager file = new FileManager(this.directory.getObject().getAbsolutePath() + "\\" + sharedCourseCode + ".txt");
        Boolean res = false;
        if (file.exists())
        {
            /* Creation de l'arraylist [name][type][codes] */
            ArrayList<String> contentToSave = new ArrayList<String>();
            ArrayList<String> contentOfFile = gson.fromJson(file.getFileContent(), ArrayList.class);
            contentToSave.add(sharedCourseCode);
            contentToSave.addAll(contentOfFile);
            /* Sauvegarde dans les fichiers du client */
            privateClientDatabase.setCourseFile(client.getStudent().getNom());
            privateClientDatabase.save(contentToSave);
            res = true;
        }
        return res;
    }
}
