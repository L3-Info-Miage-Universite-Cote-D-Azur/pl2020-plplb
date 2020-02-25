Bilan de l'iteration : 
	Nous avons realiser l'IHM avec succes.
	
	Le libelle de l'ue ainsi que son code apparait au client. 
	Le client peut cocher la case de l'ue (il choisit alors l'ue).
	
	Un bouton sauvegarde apparait lorsque le client coche une UE (car il creer alors une modification).
	
	Le serveur retient le client dans la database.
	Le serveur et le client communique:
		-Le serveur envoie le semestre 1 (avec une seule ue) au client;
		-Le client la recoit et l'affiche.
		-Le client envoie sa sauvegarde au serveur. (click sur le bouton sauvegarder)
		-Le serveur rend les modification au client lorsqu'il se reconnecte.
	

Bilan de l'iteration sur les tests :

	Nous avons effectuer tout les test cote serveur. Tous les tests fonctionnent. (test java)
	Nous avons pu tester le modele cote client. (test)
	Nous n'arrivons pas a effectuer des tests android sur le client a cause d'un manque de connaissance 
	et une complexité du fonctionnent des tests. (test android)

Prévision pour la prochaine itération : 

	Essaye de mieux comprendre les test android pour la prochaine iteration.
	
	Effectuer si possible les Users story de l'iteration 3 :
		-Ajout des UEs des L1 au S1.
		-IHM : Menu deroulant pour les UEs.
		-Separation des UEs par matieres/categories. 
		-Choix des 3 UEs libres + choix de l'UE l'obligatoire par les L1 au S1 (+ autres obligatoires)
		-Conditions de choix pour les UEs libres du s1:
			-1 UE par matière
			-Le client ne peut pas prendre une UE deja choisie.
		-Le client est un L1 au S1 et ses choix sont enregistrés par le serveur.