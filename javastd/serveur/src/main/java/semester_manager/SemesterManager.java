package semester_manager;

import files.FileManager;
import metier.semestre.Semestre;

public class 
SemesterManager 
{
	private FileManager fman;
	private Parser parser;
	private Converter converter;
	
	public
	SemesterManager ()
	{
		this.parser = new Parser();
		this.converter = new Converter();
	}
	
	public Semestre
	get (String fileOfSemester)
	{
		if ( fileOfSemester.length() > SemestreConsts.dir.length()
				&& fileOfSemester.subSequence(0, SemestreConsts.dir.length()).equals(SemestreConsts.dir))
			this.fman = new FileManager(fileOfSemester);
		else
			this.fman = new FileManager(SemestreConsts.dir + fileOfSemester);
		String parsed = this.parser.parse(this.fman.getRaw());
		Semestre converted = this.converter.stringToSemestre(parsed);
		return converted;
	}
	
	private long[]
	getLastUpdateFile ()
	{
		long[] res = new long[SemestreConsts.lastUpdate.length];
		for (int i = 0; i < res.length; i++)
		{
			this.fman = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[i]);
			res[i] = this.fman.getFile().lastModified();
		}
		return res;
	}
	
	public void
	updateConsts ()
	{
		long[] arr = this.getLastUpdateFile();
		for (int i = 0; i < arr.length; i++)
		{
			SemestreConsts.lastUpdate[i] = arr[i];
		}
	}
	
	public boolean
	areSemestersUpdated ()
	{
		for (int i = 0; i < SemestreConsts.filenames.length; i++)
		{
			if (this.hasBeenUpdated(i))
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean
	hasBeenUpdated (int i)
	{
		this.fman = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[i]);
		if (this.fman.getFile().lastModified() != SemestreConsts.lastUpdate[i])
		{
			return true;
		}
		return false;
	}
}
