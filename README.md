# VanaNetwork
Ce plugin comporte plusieurs commande de gestion du server bungee cord:

Les joueurs ayant accès à la maintenance on automatiquement accès à la whitelist
/mt on/off          : pour dés/activer la maintenance sur le server
/mt list            : pour afficher les joueurs ayant accès à la maintenance
/mt add <pseudo>    : poour ajouter un joueur à la liste de la maintenance
/mt remove <pseudo> : pour retirer un joueur à la liste de maintenance

/wh on/off          : pour dés/activer la whitelist sur le server
/wh list            : pour afficher les joueurs ayant accès à la whitelist
/wh add <pseudo>    : poour ajouter un joueur à la liste de la whitelist
/wh tempadd <pseudo>: pour ajouter un joueur temporèrement à la whitelist
/wh remove <pseudo> : pour retirer un joueur à la liste de whitelist


Un motd custom est appliquer lorsque la maintenance ou que la whitelist est activé. 
Pour le modifier ou le supprimer rendez-vous dans: 
https://github.com/nicofighter45/VanaNetwork/blob/master/src/fr/nicofighter45/vananetwork/Listenner.java
ligne 62 à 82
