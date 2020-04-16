## Bilan de l'iteration 8 : 
	- correction du bug du filtre de recherche
	- mise a jour du serveur. Il est maintenant plus facilement maintenable et plus facile d'utilisation.
	- Ajout de renommage et de supression des parcours.
	- Ajout du partage par mail ou autres....
	- Rencontre d'un bug : quand on rename une fois un parcours, puis on change d'activité , qu'on reviens et qu'on rename a nouveau un parcours il y a une duplication. 
	Cela se produit car les anciens listeners sont encore là auquel il s'ajoute les nouveaus listeners. 
	L'effet escompté se produit donc deux fois (il s'agit d'un bug visuel si on change d'intent ou on se deconnecte, tout redeviens normal).
	Le meme bug se produit par l'ajout de parcours par code.

	- La config du serveur se trouve maintenant dans pl2020-plplb\javastd\serveur\ressource\config.txt 
	- Les sauvegardes se situe dans pl2020-plplb\javastd\serveur\target\classes\sauvegarde\
		
## Bilan de l'iteration sur les tests :

	- Les tests du serveur ont été mis a jour. 


## Prévision pour la prochaine itération : 

	- Regler le probleme d'affichage du renommage et du l'ajout par code.
	- Finaliser l'application, regler les derniers bug mineurs
	- Ameliorer l'ihm et son coté visuel. 
	- Gérer des cas frauduleux coté serveur et client. (nom de fichier vide, connexion inexistante....)
