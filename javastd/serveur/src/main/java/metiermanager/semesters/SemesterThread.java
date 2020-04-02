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
	/** Sert uniquement aux tests unitaires */
	private boolean __UT;
	
	/**
	 * Constructeur de SemesterThread
	 * @param s Le serveur
	 * @param sm Le semesterManager
	 */
	public
	SemesterThread (Serveur s, SemesterManager sm)
	{this(s, sm, false);}
	
	public
	SemesterThread (Serveur s, SemesterManager sm, boolean ut)
	{
		this.sm = sm;
		this.serv = s;
		this.__UT = ut;
	}
	
	/**
	 * Constructeur de SemesterThread
	 * @param s Le serveur
	 */
	public
	SemesterThread (Serveur s)
	{this(s, new SemesterManager());}
	
	@Override
	public void 
	run () 
	{
		try {
			this.sm.updateConsts();
			while (true)
			{
				if (!this.__UT)
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
