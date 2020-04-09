package database;

import java.io.File;

import debug.Debug;

public class
Directory
{
    private File directory;
    private WorkingDirectory workingDirectory;

    public
    Directory (String directoryName, String workingDirectory)
    {
        this.directory = new File(workingDirectory + directoryName);
        this.workingDirectory = new WorkingDirectory(workingDirectory);
    }

    public
    Directory (String directoryName)
    {this(directoryName, "");}

    public void
    makeDirectory ()
    {this.directory.mkdir();}

    public boolean
    exists ()
    {return this.directory.exists();}

    public boolean
    delete ()
    {return this.directory.delete();}

    public String
    getDirectoryName ()
    {return this.directory.getName();}

    public File[]
    listFiles ()
    {return this.directory.listFiles();}
    
    public void
    setDirectory (String path)
    {this.directory = new File(path);}
    
    public File
    getObject ()
    {return this.directory;}

    public WorkingDirectory
    getWorkingDirectory ()
    {return this.workingDirectory;}
}
