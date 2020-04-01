package metier.parcours.manager;

import java.util.ArrayList;

import metier.parcours.ParcoursType;

/**
 * Class utilitaire, elle contient les differents parcours type avec la liste de leur UEs par defaut
 */
public class
ParcoursSample
{
	/**
	 * Permet d'initialiser les semestres du serveur
	 */
	public static void
	init ()
	{
		ParcoursManager pm = new ParcoursManager();
		ParcoursSample.P1 = pm.get(ParcoursConsts.filenames[0]);
	}

	public static ArrayList<ParcoursType> P1;

	/**
	 * Class non instanciable, c'est une class utilitaire
	 */
	private ParcoursSample() {}
}
