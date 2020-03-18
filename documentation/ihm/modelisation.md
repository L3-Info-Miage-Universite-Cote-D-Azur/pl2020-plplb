# Modélisation
![Model](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplb/blob/master/documentation/ihm/modelisation.png)

### Itération 3 :
- Lorsque l'utilisateur se connecte (automatiquement) au serveur, celui-ci lui envoie un message.
Il lui envoie éventuellement ses sauvegardes si elles existent.
- L'utilisateur arrive sur la page décrite comme sur l'image à l'exception que chaque catégories
n'est pas déroulées. (on ne voit que le titre)
- Lorsque l'utilisateur clique sur une des catégories, elle se déroule et affiche toutes les Ues
qu'elle contient (comme sur l'image).
- L'utilisateur peut cocher ou décocher les cases des ues. Un bouton sauvegarde apparait alors en bas
de l'écran. Si l'utilisateur clique, il envoie alors une sauvegarde de l'état de son parcours au serveur.

### Itération 4 :
- La modélisation se base sur celle de l'itération 3 mais à présent la checkbox n'existe plus. L'utilisateur
peut constater que son ue est selectionnée grâce au code couleur.
- L'utilisateur peut changer de semestre en cliquant en haut à droite (voir l'image). Si il le fait, une
nouvelle activité s'affiche avec les catégories du semestre choisies (non déroulée).
- L'utilisateur peut appuyer sur le bouton sauvegarde en bas de l'ecran:
	- si il a un parcours valide, la sauvegarde est envoyée et stockée par le serveur.
	- si il est incomplet, un message d'erreur est envoyé.
- Les ues ont maintenant un code couleur :
    - Verte : l'ue est disponible.
    - Rouge : l'ue ne peux être choisies (pas encore d'explication)
    - Bleu : l'ue est selectionnée.

### Itération 5 :
- L'utilisateur arrive sur la page de login (1) où il doit rentrer son INE (Identification Nationale des Etudiants)
puis valider avec le bouton "Connexion", un message d'erreur apparaît si l'INE est mauvais (2) et peut réessayer.
- L'utilisateur passe ensuite de la page de login (1 ou 2) à la page du parcours (3) si son INE est valide.

### Itération 6:
- Maintenant l'utilisateur passe à un menu contenant ses parcours (4) après s'être identifié (1 ou 2).
- Sur le menu, l'utilisateur créer un nouveau parcours sur la case du même nom, qui va le mener à l'image 3.
- S'il appuie sur une case parcours déja créé, il va arriver sur une page de visualisation de celui-ci (5).
- Avec les flèches il peut dérouler les UEs choisies pour les semestres correspondants.
- Il peut modifier son parcours en appuyant sur l'icône du stylo, ce qui l'amène à l'image 3.
- S'il appuie sur l'icône poubelle, il va supprimer son parcours et revenir au menu (4).
