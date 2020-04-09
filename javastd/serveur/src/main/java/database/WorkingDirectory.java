package database;

public class
WorkingDirectory
{
    private String workingDirectory;

    public
    WorkingDirectory (String where)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir"));
        sb.append("\\");
        sb.append(where);
        this.workingDirectory = sb.toString();
    }

    public
    WorkingDirectory ()
    {this("");}

    public void
    changeDirectory (String where)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.workingDirectory);
        sb.append("\\");
        sb.append(where);
        sb.append("\\");
        this.workingDirectory = sb.toString();
    }

    public String
    getCurrentDirectory ()
    {return this.workingDirectory; }

    @Override
    public String
    toString ()
    {return this.getCurrentDirectory();}
}
