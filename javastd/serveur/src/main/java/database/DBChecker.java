package database;

import java.util.*;

import metier.Categorie;
import metier.semestre.Semestre;

/**
 * DBChecker permet de verifier les sauvegardes
 * emises par l utilisateur au cas ou cet utilisateur
 * aurait une application personnalisee et essayerait
 * d envoyer des fausses donnees 
 * @author theoricien
 */
public class 
DBChecker 
{
	private Semestre semestre;
	private ArrayList<String> codes;
	
	/**
	 * Constructeur de DBChecker, affectation de reference
	 * @param seum le semestre de reference
	 * @param lCodes la liste des codes d UE
	 */
	public
	DBChecker (Semestre seum, ArrayList<String> lCodes)
	{
		this.semestre = seum;
		this.codes = lCodes;
	}
	
	/**
	 * Permet de verifier 2 cas: <br>
	 * <pre>-Si on a une UE obligatoire presente</pre>
	 * <pre>-Si les 3 UE restantes sont de categories differentes</pre>
	 * Ne marche uniquement sur le S1<br>
	 * TODO:
	 * Porter cette fonction pour les futurs 4 semestres
	 * @return true si il n y a pas d erreurs, false sinon
	 */
	public boolean
	checkSave ()
	{
		String oldcode = null;
		/* Check si on a bien une UE obligatoire de categories Mathematiques */
		boolean hasObligatory = false;
		for (String code : this.codes)
		{
			/* Check si le code existe */
			if (this.semestre.findUE(code) == null)
				return false;
			/*
			if (this.semestre.isObligatoryUE(this.semestre.findUE(code)))
			{
				/* On le remove pour nos futurs checks
				this.codes.remove(code);
				oldcode = code;
				hasObligatory = true;
				break;
			}
			*/
		}
		if (!hasObligatory) return false;
		/* Check si les 3 autres UE ont une categorie differente */
		// {Categorie: NbOcurrence}
		HashMap<Categorie, Integer> hm = new HashMap<Categorie, Integer>();
		for (String code : this.codes)
		{
			/* On cherche la categorie associee au code de l UE */
			ArrayList<Categorie> keys = this.semestre.getListCategorie();
			Categorie key = null;
			for (Categorie k : keys)
			{				
				if (this.semestre.findUE(code).getCategorie().equals(k.getName()))
				{
					key = k;
					break;
				}
			}
			/* Si le code de l UE ne correspond a aucune categorie */
			if (key == null)
				return false;
			/* On ajoute l occurence a la categorie associee */
			if (hm.containsKey(key)) {
				/* Si hm contient la key, ca veut dire qu on a 2
				 * UE de la meme categorie */
				return false;}
			else
				/* On ajoute 1 occurence */
				hm.put(key, 1);
		}
		this.codes.add(oldcode);
		return true;
	}
}
