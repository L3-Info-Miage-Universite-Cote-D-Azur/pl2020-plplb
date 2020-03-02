# pl2020-plplb

## Liste des itérations

![Model](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplb/blob/master/documentation/ihm/modelisation.png)

### Itération 1:
	-Developpement du squelette basique du système client-serveur avec SocketIO sur 1 client
	-Affichage au client d'un message de bienvenue lorsqu'il se connecte. 

### Itération 2
	-Toujours 1 client, qui choisit 1 UE pour le S1.
	-Le serveur retient le choix du client (dans un fichier).
	-Creation de la classe UE (libelle,code,description). 
	-Le client a une checkbox pour choisir son UE.
	-L'UE s'affiche sur la page principale.
	-Le serveur redonne automatiquement au client ses choix "brouillon" lors de sa nouvelle connexion.

### Itération 3:
	-Ajout des UEs du S1.
	-IHM : Menu deroulant pour les UEs.
	-Separation des UEs par matieres/categories. 
	-Choix des 3 UEs libres + choix de l'UE l'obligatoire par les L1 au S1 (+ autres obligatoires)
	-Mise en place des conditions de choix pour les UEs libres du s1:
		-1 UE par matière
		-Le client ne peut pas prendre une UE deja choisie.
	
### Itération 4:
	-Ajout des UEs du s2. 
	-Ajout du s2.
	-Code couleur des UE (gris pour disponible, rouge pour indisponible, bleu pour choisie). 
	-IHM : Menu deroulant pour chaque semestre et chaque matiere. 
	-Developpement pour le S2 jusqu'au S4: 4 UE à choisir
	-Mise en place des conditions de choix pour les UEs libres:
		-le S2 aura 2 UEs libres possibles par matière, 
		-le S3 en aura 3,
		-le S4 en aura 4.

### Itération 5:
	-Ajout des UEs du s3.
	-Ajout du s3.
	-Nom, prénom, et l'INE de l'étudiant caractérisera le client, il devra le fournir au login.
	-Le login est stocké de base dans un fichier et est chargé au lancement du serveur.
	-Le serveur reconnais un étudiant déjà dans le fichier et charge ses sauvegardes.
	-IHM : page de login a l'ouverture de l'application.
	

### Itération 6:
	-Ajout des UEs du s4.
	-Ajout du s4.
	-Le client peut choisir de sauvegarder ses choix, de les charger et de les modifier
	-Les choix de parcours sont enregistrés sur le serveur, le client peut sauvegarder/charger ses choix de parcours
	-Avancements sur l'IHM:
		-Toolbar pour les choix:
			-Charger ses parcours
			-Voir ses parcours
	-Avancements sur l'IHM:
		-Toolbar pour les choix:
			-Gestion des semestres
			-Creer un parcours
		-Fond d'accueil qui propose 2 gros choix:
			-Charger le dernier brouillon de parcours
			-Voir l'ensemble des UE disponibles au semestre courant

### Itération 7:
	-Gestion des UEs: UEs "impossibles" grisées en fonction des choix, le client doit décocher son choix d'UE pour la matière pour 		rendre les autres disponible
	-Explication de l'indisponibilité d'une UE en rouge. 
	-Choix des UE facultative et UE speciale d'enseignement et professionalisante. 
	

### Itération 8:
	-Implémentation des parcours automatisés, le serveur propose au client des parcours préconçu (modele type de cursus) si celui-ci 	le demande
	- Mise en place d'une liste de parcours préconcu en fonction des matières.
	- Le client peut voir le parcours des autres utilisateurs.

### Itération 9:
	-Résolution de conflits: 
		-le client ne peut pas modifier les semestres antérieurs au semestre courant
		-nombre limite de parcours possibles "sauvegardables"
		-il est impossible d'avoir 1 client connecté en même temps à partir de plusieurs machines (: impossibilité de lire 1 fichier par 2 threads ?)

### Itération 10:
	-Rendu final
