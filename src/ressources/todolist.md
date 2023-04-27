# A faire demain

- [X] Pluie, vent
- [x] Ajouter unité sur les maps
- [X] Icon sous-marin, convoi
- [X] Icon fr.istic.map selecteur, nombre de joueur
- [X] Dernière vérification + rendu
- [X] Test grandeur nature
- [X] Finir les keyboards tips
- [X] Ecran de fin de partie
- [X] Patcher les popup
- [X] Attaque a distance cassée?

# Prioritaire :

- [ ] Systeme de logs pour les testeurs
- [X] Riposte quand attaque de case adjacente
- [X] Capture possible seulement pour les unités à pied
    - [X] Resistance = Res - pv attaquant (arrondi sup)
    - [X] Il faut être sur la case sinon annulation
    - [X] Capture => hasPlayed = true
- [X] Fonction move (Case destination) pour les unités ?

- [X] Fin de jeu
    - [X] S'il ne reste qu'un playertype de type
    - [X] Beautify (bonus)

- [X] Réorganiser le bordel du déplacement (OK ?)
    - [X] Lors du déplacement
        - [X] Impossible sur les cases avec unite ennemi
        - [X] Cout de la case
    - [X] Pour s'arrêter
        - [X] Pas d'unité sur la case
    - [X] Achat d'unité (unite apparu est indisponible pour ce tour)
        - [X] Une unité par tour par usine
        - [X] Pas d'unité sur l'usine
        - [X] Crédits
- [X] Bouger le move dans Grid#move

# Non prioritaire :

- [X] Surbrillance des cases possibles (OK?)
- [X] Arrondi des PV lors des attaques ?
- [X] Animation de direction d'unité
- [X] Portée de tir dans l'arme et pas dans l'unité ?
- [X] Une quantité effarante de commentaires / javadoc
- [X] Désengorger ActionHandler#enter

# Bonus

- [X] Animation de pluie
- [ ] Animation de vent (annulé)
- [X] Animation de mouvement des unites
- [X] Touche pour lister les unités encore utilisables (espace) 6.1
- [X] Cases de déplacement/d'attaques possibles 6.2 (Verifier si c'est mis partout)
- [X] Armes multiples 6.3
- [X] Attaques à distance (lance-missiles sol-air) 6.4
    - [X] Faire un mortier
    - [X] Faire un missile sol-air
    - [X] Faire un lance-missiles sol-air
    - [X] Implémenter les dégâts infligés sur une artillerie
    - [X] Si unite ne s'est pas déplacé lors de ce tour

- [X] Ravitaillement et réparations 6.5
    - [X] Munitions
        - [X] Utilisation en attaque ou en riposte
        - [X] Mitrailleuse infinie ??? (9-10)
    - [X] Carburant
        - [X] pt de carburant = pt de déplacement
        - [X] aérienne (cout de déplacement + fixe par tour):
        - [X] hélico = 2 pt de carburant/tour
        - [X] bombardier = 5 pt de carburant/tour
        - [X] si aérien sans fuel, détruit
        - [X] indicateur low ammo/low fuel
- [X] Classe coordonnée

- [X] Transport d'unité 6.6
    - [X] Une seule unite
    - [X] Monter à bord (case adjacente)
    - [X] Déposer (//)
    - [X] Pour l'instant seulement pour le Convoi, trouver le moyen de fix
- [X] Fin de tour automatique 6.7
    - [X] Activable/désactivable (dans Player)
    - [X] Si plus d'action possible (tt unite en hasPlayed = true && factory occupés)
- [X] Couverture de fr.istic.terrain 6.8
    - [X] -20% foret/usine
    - [X] -30% ville
    - [X] -40% montagne/qg
    - [X] aérien : pas de couverture
- [X] unite navale 6.9
    - [X] Implémenter les dégâts
    - [X] Missiles anti-navire
    - [X] Porte-avions
    - [X] Sous-marin
        - [X] Plongée
            - [X] Consommation de carburant en plongée
            - [X] Invisible sauf si unité adjacente
            - [X] Icône
        - [X] Surface
        - [X] Si en plongée, ne peut être attaqué que par sous-marin/croiseurs

- [X] brouillard de guerre 6.10
    - [X] choisir si on le veut
    - [X] Pause entre les tours
    - [X] Tir à distance impossible si case non visible
    - [X] Piège d'une unité
        - [X] Affichage de l'indicateur (Trapped)

- [X] Météo
    - [X] Pluie = -1 case de vision pour unités au sol, -2 cases de vision pour unités volantes/en montagne
    - [X] Neige = Vitesse des unités
    - [X] Vents violents = portée des attaques à distance -1 case, -20% de dégâts
    - [X] Météo aléatoire, soit en début de fr.istic.game, soit dynamique (% de changement chaque tour, +% par type de météo)
    - [X] Avertissement pour les joueurs

- [ ] Commandants (annulé: deadline)
    - [ ] Option prendre le commandement
    - [ ] Différents bonus en fonction des commandants
    - [ ] Portée des bonus allant de 1 à 5 cases
    - [ ] Si unité détruite, commandant bat en retraite

# Améliorations supplémentaires

- [X] Dijkstra
- [ ] Bot

# Readme :

- [X] UML