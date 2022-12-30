# Prioritaire :

- [X] Riposte quand attaque de case adjacente
- [X] Capture possible seulement pour les unités à pied
    - [X] Resistance = Res - pv attaquant (arrondi sup)
    - [X] Il faut etre sur la case sinon annulation
    - [X] Capture => hasPlayed = true

- [X] Fin de jeu
    - [X] S'il ne reste qu'un playertype de type
    - [ ] Beautify 
  
- [ ] Réorganiser le bordel du déplacement (OK?)
    - [X] Lors du déplacement
        - [X] Impossible sur les cases avec unite ennemi
        - [X] Cout de la case
    - [X] Pour s'arrêter
        - [X] Pas d'unité sur la case
    - [ ] Achat d'unité (unite apparu est indisponible pour ce tour)
        - [X] Pas d'unité sur l'usine
        - [X] Crédits

Non prioritaire :

- [ ] Surbrillance des cases possibles
- [ ] Arrondi des PV lors de attaques ?
- [ ] Animation de pluie
- [ ] Animation de direction d'unité

Bonus :
- [ ] Animation de mouvement des unites
- [ ] Classe coordonnée
- [ ] Ajuster les PV ?
- [ ] Touche pour lister les unités encore utilisables (espace) 6.1
- [ ] Cases de déplacement/d'attaques possibles 6.2
- [ ] Armes multiples 6.3
- [ ] Attaques à distance (faire un mortier) (lance missile sol-air) 6.4
    - [ ] Si unite ne s'est pas déplacé lors de ce tour

- [ ] Ravitaillement et réparations 6.5
    - [ ] Munitions
        - [ ] Utilisation en attaque ou en riposte
        - [ ] Mitrailleuse infinie ??? (9-10)
    - [ ] Carburant
        - [ ] pt de carburant = pt de déplacement
        - [ ] aerienne (cout de deplacement + fixe par tour):
            - [ ] helico = 2 pt de carburant/tour
            - [ ] bombardier = 5 pt de carburant/tour
        - [ ] si aérien sans fuel, détruit
        - [ ] indicateur low ammo/low fuel

- [ ] Transport d'unite 6.6
    - [ ] Une seule unite
    - [ ] Monter à bord (case adjacente)
    - [ ] Depoter (//)
- [ ] Fin de tour automatique 6.7
    - [ ] Activable/desactivable (dans Player)
    - [ ] Si plus d'action possible (tt unite en hasPlayed = true && factory occupés)
- [ ] Converture de terrain 6.8
    - [ ] -20% foret/usine
    - [ ] -30% ville
    - [ ] -40% montagne/qg
    - [ ] aerien : pas de couverture
- [ ] unite navale 6.9
- [ ] brouillard
    - [ ] choisir si on le veut
    - 


Readme :
- [ ] UML