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
- La modélisation se base sur celle de l'itération 3.
- L'utilisateur peut changer de semestre en cliquant en haut à droite (voir l'image). Si il le fait, une
nouvelle activité s'affiche avec les catégories du semestre choisies (non déroulée).
- Les ues ont maintenant un code couleur :
    - Verte : l'ue est disponible.
    - Rouge : l'ue ne peux être choisies (pas encore d'explication)
    - Bleu : l'ue est selectionnée.
