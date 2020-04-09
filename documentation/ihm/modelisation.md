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
- L'utilisateur arrive sur la page de login (1) où il doit rentrer son numéro étudiant (Identification Nationale des Etudiants)
puis valider avec le bouton "Connexion", un message d'erreur apparaît si le numéro étudiant est mauvais (2) et peut réessayer.
- L'utilisateur passe ensuite de la page de login (1 ou 2) à la page du parcours (3) si son numéro étudiant est valide.
- La page de parcours affiche de la même manière que la page pendant l'itération 4, toutefois elle possède
en plus deux nouveaux boutons: le bouton (4) permet de se déconnecter et ramène à la page de login (1), et le
bouton (5) permet de passer au semestre suivant.
- Lorsque l'utilisateur passe sur semestre autre que le premier, le bouton (4) devient "précédent" et permet
de revenir au semestre précédent.
- Lorsque l'utilisateur est sur la page du semestre 4, le bouton (5) devient "finaliser" et permet de confirmer
notre parcours, et s'il n'y a aucune erreur, l'utilisateur passe à la page de visualisation (6) et peut sauvegarder
son parcours avec le bouton "save".
- Si l'utilisateur sauvegarde, il repasse à la page de login (1).

### Itération 6:
- Maintenant l'utilisateur passe à un menu contenant ses parcours (7) après s'être identifié (1 ou 2).
- Sur le menu, l'utilisateur créer un nouveau parcours sur la case du même nom (8), qui va le mener au menu de création (9).
- Sur le menu de création, l'utilisateur peut nommer son parcours et choisir un parcours prédéfini parmi les 
différents choix (il peut défiler pour voir d'autres parcours), notamment l'option parcours libre s'il ne veut pas
se baser sur un modèle.
- Le bouton (10) lui permet de valider et de passer à la page (3) où les matières affichées changeront en
fonction du parcours prédéfini qu'il a choisi (s'il n'a pas pris "parcours libre"), mais il doit quand-même choisir
ses UEs.
- Si l'utilisateur n'a pas choisi un des choix pour la création de son parcours, un message d'erreur s'affiche et lui
redemande de choisir (12)
- Le bouton (11) lui permet de se déconnecter et de revenir à la page de login (1)
- S'il appuie sur une case parcours déja créé, il va arriver sur une page de visualisation de celui-ci.
- Avec les flèches il peut dérouler les UEs choisies pour les semestres correspondants.
- Il peut modifier son parcours en appuyant sur l'icône du stylo, ce qui l'amène à l'image 3.
- S'il appuie sur l'icône poubelle, il va supprimer son parcours et revenir au menu (4).

### Itération 7:
- La page de création de parcours (9) va maintenant afficher un pop-up (12) pour donner à l'avance le nombre
et les types d'UEs que l'utilisateur va devoir choisir pour ce type de parcours, il peut
soit confirmer en appuyant sur créer, soit annuler s'il veut apporter une modification.
- Le menu principal (7) a subit une refonte (13). Maintenant chaque parcours créer propose deux boutons, 
"modifier" (permet de changer le parcours), et "visualiser" (même fonction que dans l'itération 6 
pour visualiser un ancien parcours). Et le bouton pour créer un nouveau parcours est placé plus bas.
- La page de création de parcours a aussi subit une refonte (14) avec notamment l'ajout d'une barre de recherche (15)
qui va afficher le nom des UEs avec le mot recherché (ici les UEs affichées contiennent toutes le mot 'base".

### Itération 8:
- Sur la page du menu principal, un bouton (16) est ajouté et permet d'entrer un code de partage de parcours (ce code est
composé de 5 chiffres).
- Une case s'affiche (17) après avoir appuyé sur le bouton (16) pour rentrer un code, s'il est faux un message d'erreur s'affiche,
mais dans le cas contraire, le parcours associé au code est enregistré dans la partie "vos parcours" et peut être traité comme tous les autres parcours déja créé.
- Les UEs ont maintenant un bouton sur le côté (18) qui permet d'afficher une nouvelle page (19) contenant les informations de 
celle-ci (le code, le nom, la catégorie, description de la matière).
- Sur la page pour finaliser et sauvegarder un parcours (6), un bouton de partage est maintenant disponible et va donner le code de partage du parcours (20) (le code peut être directement copié si l'utilisateur clique sur le code).
