## Liste des itérations

### Itération 1:
	-Developpement du squelette basique du système client-serveur avec SocketIO sur 1 client
	-Affichage au client d'un message de bienvenue lorsqu'il se connecte. 

### Itération 2
	-Toujours 1 client, qui choisit 1 UE pour le S1.
	-Le serveur retient le choix du client (dans un fichier).
	-Bouton pour quitter. 
	-Le serveur redonne automatiquement au client ses choix "brouillon" lors de sa nouvelle connexion.
	-Le client a une checkbox pour choisir sa seule UE.

### Itération 3:
	-Ajout des UEs des L1 au S1.
	-Ajout des catégories d'UE.
	-Choix des 3 UEs libres + choix de l'UE l'obligatoire par les L1 au S1 (+ autres obligatoires)
	-Conditions de choix pour les UEs libres du s1:
		-1 UE par matière
	-Le client est un L1 au S1 et ses choix sont enregistrés par le serveur.
	-Le client ne peut pas prendre une UE deja choisie.

### Itération 4:
	-Ajout de toutes les UEs. 
	-Choix des UE facultative et UE speciale d'enseignement et professionalisante. 
	-IMH : Menu deroulant pour chaque semestre et chaque matiere. 
	-Developpement pour le S2 jusqu'au S4: 4 UE à choisir
	-Le S2 aura 2 UEs libres possibles par matière, le S3 en aura 3, et le S4 en aura 4 

### Itération 5:
	-Nom, prénom, et l'INE de l'étudiant caractérisera le client, il devra le fournir au login.
	-Le login est stocké de base dans un fichier et est chargé au lancement du serveur.
	-Le serveur reconnais un étudiant déjà dans le fichier et charge ses sauvegardes.
	-Il y a toujours 1 seul client à la fois connecté sur le serveur.

### Itération 6:
	-Developpement du système multi-clients, on peut désomrais avoir un nombre 'infini' de clients connectés en même temps sur le serveur
	-Avancements sur l'IHM:
		-Demange de login personnalisé: Nom, Prenom, INE
		-Toolbar pour les choix:
			-Gestion des semestres
			-Creer un parcours
		-Fond d'accueil qui propose 2 gros choix:
			-Charger le dernier brouillon de parcours
			-Voir l'ensemble des UE disponibles au semestre courant

### Itération 7:
	-Gestion des UEs: UEs "impossibles" grisées en fonction des choix, le client doit décocher son choix d'UE pour la matière pour rendre les autres disponible
	-Le client peut choisir de sauvegarder ses choix, de les charger et de les modifier
	-Les choix de parcours sont enregistrés sur le serveur, le client peut sauvegarder/charger ses choix de parcours
	-Avancements sur l'IHM:
		-Toolbar pour les choix:
			-Charger ses parcours
			-Voir ses parcours

### Itération 8:
	-Implémentation des parcours automatisés, le serveur propose au client des parcours préconçu (modele type de cursus) si celui-ci le demande

### Itération 9:
	-Résolution de conflits: 
		-le client ne pas modifier les semestres antérieurs au semestre courant
		-nombre limite de parcours possibles "sauvegardables"
		-il est impossible d'avoir 1 client connecté en même temps à partir de plusieurs machines (: impossibilité de lire 1 fichier par 2 threads ?)

### Itération 10:
	-Rendu final
