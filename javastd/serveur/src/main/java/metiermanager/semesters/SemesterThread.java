package metiermanager.semesters;

import java.util.concurrent.TimeUnit;

import debug.Debug;
import serveur.Serveur;

/**
 * Class qui permet la gestion de la mise
 * a jour des semestres dans le serveur
 */
public class
SemesterThread implements Runnable
{
	/** Objet gerant les semestres */
	private SemesterManager sm;
	/** Objet qui represente le serveur */
	private Serveur serv;
	
	/**
	 * Constructeur de SemesterThread
	 * @param s Le serveur
	 */
	public
	SemesterThread (Serveur s)
	{
		this.sm = new SemesterManager();
		this.serv = s;
	}
	
	@Override
	public void 
	run () 
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
					this.serv.updateSemestersOfClients();
				}
			}
		} catch (Exception E) {
			Debug.error("An error in SemesterThread has occured");
			E.printStackTrace();
			return;
		}
	}
}
