package semester_manager;

import java.util.concurrent.TimeUnit;

import debug.Debug;
import serveur.Serveur;

public class
SemesterThread implements Runnable
{
	private SemesterManager sm;
	private Serveur serv;
	
	public
	SemesterThread (Serveur s)
	{
		this.sm = new SemesterManager();
		this.serv = s;
	}
	
	@Override
	public void run() 
	{
		try {
			this.sm.updateConsts();
			while (true)
			{
				TimeUnit.SECONDS.sleep(10);
				if (sm.areSemestersUpdated())
				{
					Debug.log("Semesters updated !");
					this.sm.updateConsts();
				}
			}
		} catch (Exception E) {
			Debug.error("An error in SemesterThread has occured");
			E.printStackTrace();
			return;
		}
	}
}
