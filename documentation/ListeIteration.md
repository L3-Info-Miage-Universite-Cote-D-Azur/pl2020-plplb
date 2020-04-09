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
	-Separation des UEs par matieres/catégories. 
	-Ajout d'un bouton dans la toolbar pour choisir le semestre à afficher (pour l'instant semestre 1 uniquement)
	-IHM : Les UEs sont regroupés par catégories. Cliquer sur une catégorie déroule les UEs correspondantes.
	-Choix des 3 UEs libres + choix de l'UE l'obligatoire par les L1 au S1 (+ autres obligatoires)
	-Mise en place des conditions de choix pour les UEs libres du s1:
		-1 UE par catégories.
		-Le client ne peut pas prendre une UE deja choisie.
	
### Itération 4:
	-Ajout des UEs du s2. 
	-Ajout du s2.
	-Supression de la checkbox.
	-Code couleur des UE (vert pour disponible, rouge pour indisponible, bleu pour choisie). 
	-Le passage d'un semestre à l'autre se fait par Intent (une activité par semestre)
	-Developpement pour le S2 jusqu'au S4: 4 UE à choisir
	-Mise en place des conditions de choix pour les UEs libres:
		-le S2 aura 2 UEs libres possibles par catégories, 
		-le S3 en aura 3,
		-le S4 en aura 4.

### Itération 5:
	-Ajout des UEs du s3.
	-Ajout du s3.
	-Ajout des UEs du s4.
	-Ajout du s4.
	-Ajout d'une activité Login. Le numéro de l'étudiant caractérisera le client, qu'il devra fournir au login pour 	récupérer ses UEs.
	-Le login est stocké de base dans un fichier.
	-Le serveur reconnais un étudiant déjà dans le fichier et charge ses sauvegardes.
	-IHM : page de login a l'ouverture de l'application.
	- demande du client : intégrer des pré-requis aux Ues. (modifié donc la validation de certain parcours en conséquences)
	- demande du client : pouvoir changer les données coté serveur (listes des cours, description des cours, « pré-requis », etc.), 
		en notifier le client et ré-évaluer ses parcours et données en conséquences.
	

### Itération 6:
	-Le client peut choisir de sauvegarder un ou plusieurs parcours et les charger/modifier. Ces sauvegardes envoyées sont 
	gérées par le serveur
	-Les choix de parcours sont enregistrés sur le serveur, le client peut sauvegarder/charger ses choix de parcours
	- Le client peut choisir un parcours prédefinie quand il en créer un nouveau (parcours de math, d'info...)
	-Avancements sur l'IHM:
		-Toolbar pour les choix:
			-Charger ses parcours
			-Visualiser et modifier ses parcours
			-Creer un nouveau parcours


### Itération 7:
	-Refonte du code pour optimisation.
	-ajout de l'option : filtrer / rechercher par discipline quand le client creer/modifie un parcours (coté client)
	-Explication de l'indisponibilité d'une UE en rouge. 
	

### Itération 8:
	- Le client peut partager son parcours (par mail, fichier txt voir reseaux sociaux).
	- intégrer un parcours en partie fait (par exemple le S1 ou le S2, il reste les autres semestres/cours à choisir)
	- Le client peut voir le parcours des autres utilisateurs qui ont partagé.

### Itération 9:
	-Résolution de conflits
	-nombre limite de parcours possibles "sauvegardables"
	-Amélioration de l'IHM pour un rendu plus esthétique (fond,icones etc)
	-Reorganisation du serveur pour maintenabilité
	-Marge d'erreur pour retard.

### Itération 10:
	-Rendu final
