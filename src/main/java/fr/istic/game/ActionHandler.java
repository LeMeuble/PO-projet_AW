package fr.istic.game;

import fr.istic.Logger;
import fr.istic.MiniWars;
import fr.istic.control.Cursor;
import fr.istic.control.KeystrokeListener;
import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.map.Grid;
import fr.istic.menu.Menu;
import fr.istic.menu.MenuManager;
import fr.istic.menu.MenuModel;
import fr.istic.menu.SelectionMenu;
import fr.istic.menu.model.*;
import fr.istic.render.OverlayType;
import fr.istic.render.Popup;
import fr.istic.render.PopupRegistry;
import fr.istic.render.Renderer;
import fr.istic.terrain.Factory;
import fr.istic.terrain.Property;
import fr.istic.terrain.type.HQ;
import fr.istic.terrain.type.Port;
import fr.istic.terrain.type.Water;
import fr.istic.unit.Transport;
import fr.istic.unit.Unit;
import fr.istic.unit.UnitAction;
import fr.istic.unit.UnitType;
import fr.istic.unit.type.Submarine;
import fr.istic.util.Dijkstra;
import fr.istic.util.OptionSelector;
import fr.istic.weapon.MeleeWeapon;
import fr.istic.weapon.RangedWeapon;
import fr.istic.weapon.Weapon;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class prenant en charge les actions associees aux differentes touches pressees
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class ActionHandler {

    private final MiniWars instance;

    /**
     * Constructeur de classe
     *
     * @param instance Une instance du Jeu en cours
     */
    public ActionHandler(MiniWars instance) {

        this.instance = instance;

    }

    /**
     * Gere les touches appuyees et indique si le jeu actualiser l'ecran
     *
     * @param code la touche appuyee
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean handle(KeystrokeListener.KeyCodes code) {

        final GameState gameState = this.instance.getGameState();

        switch (code) {

            case UP:
                return this.up(gameState);
            case DOWN:
                return this.down(gameState);
            case LEFT:
                return this.left(gameState);
            case RIGHT:
                return this.right(gameState);
            case ENTER:
                return this.enter(gameState);
            case ESCAPE:
                return this.escape(gameState);
            case SPACE:
                return this.space(gameState);
            case KEY_D:
                return this.keyD(gameState);
            default:
                return false;

        }

    }

    /**
     * Gere l'appui sur la touche D
     *
     * @param gameState L'etat actuel dans lequel est le jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean keyD(GameState gameState) {

        // Si le jeu est en mode "selection"
        if (Objects.requireNonNull(gameState) == GameState.PLAYING_SELECTING) {// Passe le tour
            MenuManager.getInstance().addMenu(new NextTurnMenu.AskMenu());
            this.instance.setGameState(GameState.PLAYING_SELECTING_SKIP_TURN_ACTION);
            return true;
        }

        return false;

    }


    /**
     * Gere la touche haut
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean up(GameState gameState) {

        Logger.getLogger().log("Entered ActionHandler#up");

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            // Si le jeu est en mode "selection d'une carte"
            case MENU_MAP_SELECTION: {

                // Cree un nouveau fr.istic.menu de selection de carte, recupere le champ situe au dessus de ce fr.istic.menu et actualise l'ecran
                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.previousField();
                mapSelectionMenu.needsRefresh(true);

                return true;

            }

            // Si le jeu est en mode "selection", "selection d'une zone de depot", "selection d'un transport" ou "selection d'une cible"
            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getLogger().log("up@PLAYING_SELECTING_TARGET");

                if (cursor == null) break;

                // Update du curseur, vers le haut
                boolean updateDisplay = cursor.up();
                // Ajustement de l'ecran, si jamais le curseur est sorti du cadre
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            // Si le jeu est en mode "deplacement d'une unite"
            case PLAYING_MOVING_UNIT: {

                Logger.getLogger().log("up@PLAYING_MOVING_UNIT");
                // Si un curseur existe, actualise la fleche de deplacement vers le haut
                if (cursor != null) this.updateMovement(cursor::up);
                return true;
            }

            // Si le jeu est en mode "pause", "selection de l'unite a deposer", "selection de l'unite a creer dans une usine" ou "selection d'une action d'unite"
            case MENU_PAUSE:
            case PLAYING_SELECTING_SKIP_TURN_ACTION:
            case PLAYING_SELECTING_DROPPED_UNIT:
            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {

                Logger.getLogger().log("down@PLAYING_SELECTING_UNIT_ACTION");

                // Creation d'un fr.istic.menu contenant uniquement les actions possibles
                MenuManager.getInstance().getMenus().stream().filter(m -> m instanceof SelectionMenu).filter(Menu::isVisible).forEach(m -> {
                    ((SelectionMenu<?>) m).previous();
                    m.needsRefresh(true);
                });
                return true;
            }

        }

        return false;

    }

    /**
     * Gere la touche bas
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean down(GameState gameState) {

        Logger.getLogger().log("Entered ActionHandler#down");

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            // Si le jeu est en mode "selection d'une carte"
            case MENU_MAP_SELECTION: {

                // Cree un nouveau fr.istic.menu de selection de carte, recupere le champ situe en dessous de ce fr.istic.menu et actualise l'ecran
                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.nextField();
                mapSelectionMenu.needsRefresh(true);

                return true;

            }

            // Si le jeu est en mode "selection", "selection d'une zone de depot", "selection d'un transport" ou "selection d'une cible"
            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {

                Logger.getLogger().log("down@PLAYING_SELECTING_TARGET");

                if (cursor == null) break;

                // On deplace le cureur vers le bas, et on ajuste le decalage de l'ecran
                boolean updateDisplay = cursor.down();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            // Si le jeu est en mode "deplacement d'une unite"
            case PLAYING_MOVING_UNIT: {
                Logger.getLogger().log("down@PLAYING_MOVING_UNIT");

                // Si un curseur existe, actualise la fleche de deplacement vers le bas
                if (cursor != null) this.updateMovement(cursor::down);
                return true;
            }

            // Si le jeu est en mode "pause", "selection de passage de tour", "selection de l'unite a deposer", "selection d'action d'usine" ou "selection d'une action d'unite"
            case MENU_PAUSE:
            case PLAYING_SELECTING_SKIP_TURN_ACTION:
            case PLAYING_SELECTING_DROPPED_UNIT:
            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {

                Logger.getLogger().log("down@PLAYING_SELECTING_UNIT_ACTION");

                // On passe au prochain fr.istic.menu
                MenuManager.getInstance().getMenus().stream().filter(m -> m instanceof SelectionMenu).filter(Menu::isVisible).forEach(m -> {
                    ((SelectionMenu<?>) m).next();
                    m.needsRefresh(true);
                });
                return true;
            }

        }

        return false;

    }


    /**
     * Gere la touche gauche
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean left(GameState gameState) {

        Logger.getLogger().log("Entering ActionHandler#left");
        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            // Si le jeu est en mode "selection d'une carte"
            case MENU_MAP_SELECTION: {

                Logger.getLogger().log("left@MENU_MAP_SELECTION");

                // Cree un nouveau fr.istic.menu de selection de carte, recupere la carte precedente et actualise l'ecran
                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.previous();
                mapSelectionMenu.needsRefresh(true);
                return true;
            }

            // Si le jeu est en mode "selection", "selection d'une zone de depot", "selection d'un transport" ou "selection d'une cible"
            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getLogger().log("left@PLAYING_SELECTING_TARGET");
                if (cursor == null) break;

                // Le curseur se deplace vers la gauche, en ajustant le decalage
                boolean updateDisplay = cursor.left();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            // Si le jeu est en mode "deplacement d'une unite"
            case PLAYING_MOVING_UNIT: {
                Logger.getLogger().log("left@PLAYING_MOVING_UNIT");
                // Si le curseur exsite, on deplace la fleche vers la gauche
                if (cursor != null) this.updateMovement(cursor::left);
                return true;
            }

        }

        return false;

    }

    /**
     * Gere la touche droite
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean right(GameState gameState) {
        Logger.getLogger().log("Entering ActionHandler#right");
        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            // Si le jeu est en mode "selection d'une carte"
            case MENU_MAP_SELECTION: {

                Logger.getLogger().log("left@MENU_MAP_SELECTION");

                // Cree un nouveau fr.istic.menu de selection de carte, recupere la carte precedente et actualise l'ecran
                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.next();
                mapSelectionMenu.needsRefresh(true);
                return true;
            }

            // Si le jeu est en mode "selection", "selection d'une zone de depot", "selection d'un transport" ou "selection d'une cible"
            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getLogger().log("right@PLAYING_SELECTING_TARGET");
                if (cursor == null) break;

                // on deplace le curseur vers la droite, et on ajuste le decalage
                boolean updateDisplay = cursor.right();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            // Si le jeu est en mode "deplacement d'une unite"
            case PLAYING_MOVING_UNIT: {
                Logger.getLogger().log("right@PLAYING_MOVING_UNIT");
                // Si le curseur existe, on actualise la fleche de deplacement vers la droite
                if (cursor != null) this.updateMovement(cursor::right);
                return true;
            }

        }

        return false;

    }

    /**
     * Gere la touche entree
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean enter(GameState gameState) {

        // Initialisation des variables contenant des elements importants du jeu
        final Game game = this.instance.getCurrentGame();
        final Grid grid = this.instance.isPlaying() ? game.getGrid() : null;
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        final MenuManager menuManagerInstance = MenuManager.getInstance();
        final Renderer rendererInstance = Renderer.getInstance();

        final Player currentPlayer = this.instance.isPlaying() ? game.getCurrentPlayer() : null;

        // La case ou se situe le curseur
        final Case currentCase = grid != null ? grid.getCase(cursor.getCoordinate()) : null;
        final Unit currentUnit = grid != null ? currentCase.getUnit() : null;

        // Case selectionne = la derniere case ou l'utilisateur a appuye sur ENTER
        final Case selectedCase = grid != null ? game.getSelectedCase() : null;
        final Unit selectedUnit = selectedCase != null ? selectedCase.getUnit() : null;

        Logger.getLogger().log("enter@default");

        try {

            switch (gameState) {

                case PLAYING_ENDIND_SCREEN: {

                    this.instance.setGameState(GameState.MENU_MAP_SELECTION);
                    menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);
                    menuManagerInstance.removeMenu(MenuModel.BOTTOM_MENU);
                    this.instance.endGame();

                    menuManagerInstance.clearNonPersistent();
                    return true;
                }

                case PLAYING_SELECTING_NEXT_TURN_APPROVAL: {

                    NextTurnMenu turnMenu = (NextTurnMenu) menuManagerInstance.getMenu(MenuModel.NEXT_TURN_MENU);

                    turnMenu.fadeOut();

                    menuManagerInstance.clearNonPersistent();

                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                    return true;

                }

                // Si le jeu est dans le fr.istic.menu "Passer le tour"
                case PLAYING_SELECTING_SKIP_TURN_ACTION: {

                    NextTurnMenu.AskMenu askMenu = (NextTurnMenu.AskMenu) menuManagerInstance.getMenu(MenuModel.NEXT_TURN_ASK_MENU);

                    if (askMenu.getSelectedValue() == NextTurnMenu.Action.YES) {

                        menuManagerInstance.clearNonPersistent();

                        NextTurnMenu menu = new NextTurnMenu();
                        menuManagerInstance.addMenu(menu);

                        menu.fadeIn();
                        game.nextTurn();

                        this.instance.setGameState(GameState.PLAYING_SELECTING_NEXT_TURN_APPROVAL);
                        return true;

                    }
                    else {

                        // Le jeu retourne dans le mode "selection"
                        this.instance.setGameState(GameState.PLAYING_SELECTING);

                        // On supprime les menus non persistants de l'ecran
                        menuManagerInstance.clearNonPersistent();

                        return true;

                    }

                }

                // Si le jeu est en mode "pause"
                case MENU_PAUSE: {

                    final PauseMenu pauseMenu = (PauseMenu) menuManagerInstance.getMenu(MenuModel.PAUSE_MENU);
                    final PauseMenu.Action action = pauseMenu.getSelectedValue();

                    // Si l'action du fr.istic.menu est "continuer"
                    if (action == PauseMenu.Action.RESUME) {

                        // Le jeu retourne dans le mode "selection"
                        this.instance.setGameState(GameState.PLAYING_SELECTING);

                        // On supprime les menus non persistants de l'ecran
                        menuManagerInstance.clearNonPersistent();

                        return true;

                    }
                    // Sinon, si l'action du fr.istic.menu est "quitter"
                    else if (action == PauseMenu.Action.QUIT) {

                        final SimpleFadeInOutMenu fadeMenu = new SimpleFadeInOutMenu();
                        MenuManager.getInstance().addMenu(fadeMenu);

                        // On supprime toutes les popups, les menus non persistants
                        PopupRegistry.getInstance().clear();

                        fadeMenu.fadeIn();

                        // On affiche le fr.istic.menu de selection de carte
                        // Le jeu passe en mode "selection d'une carte"
                        menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);
                        menuManagerInstance.removeMenu(MenuModel.BOTTOM_MENU);
                        menuManagerInstance.removeMenu(MenuModel.PAUSE_MENU);
                        this.instance.setGameState(GameState.MENU_MAP_SELECTION);
                        this.instance.endGame();

                        // On termine l'instance de la partie precedente
                        fadeMenu.fadeOut();
                        menuManagerInstance.clearNonPersistent();

                        menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).needsRefresh(true);

                        return true;
                    }

                    return false;

                }

                // Si le jeu est en mode "ecran d'accueil du jeu"
                case MENU_TITLE_SCREEN: {

                    Logger.getLogger().log("enter@MENU_TITLE_SCREEN");
                    // Le jeu passe en mode "selection d'une carte"
                    this.instance.setGameState(GameState.MENU_MAP_SELECTION);

                    // On arrete d'afficher le fr.istic.menu d'accueil, et on affiche celui de selection de carte
                    rendererInstance.clearBuffer();
                    menuManagerInstance.getMenu(MenuModel.MAIN_MENU).setVisible(false);
                    menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);
                    return true;
                }

                // Si le jeu est en mode "selection d'une carte"
                case MENU_MAP_SELECTION: {

                    Logger.getLogger().log("enter@MENU_MAP_SELECTION");

                    // On cree un fr.istic.menu de selection de carte
                    MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                    rendererInstance.clearBuffer();

                    // Si une option a ete selectionnee dans le fr.istic.menu
                    if (mapSelectionMenu.getSelectedValue() != null) {
                        // On demarre une nouvelle instance du jeu, sur la carte selectionnee
                        final NextTurnMenu nextTurnMenu = new NextTurnMenu();
                        MenuManager.getInstance().addMenu(nextTurnMenu);
                        nextTurnMenu.fadeIn();
                        // On enleve le fr.istic.menu de selection de carte de l'affichage
                        mapSelectionMenu.setVisible(false);
                        this.instance.setGameState(GameState.PLAYING_SELECTING_NEXT_TURN_APPROVAL);
                        this.instance.newGame(mapSelectionMenu.getSelectedValue(), mapSelectionMenu.getSettings());
                    }
                    return true;
                }

                // Si le jeu est en mode "selection"
                case PLAYING_SELECTING: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;
                    if (currentPlayer == null) break;

                    // Si il y a une unite sur la case courante
                    if (currentCase.hasUnit()) {

                        // Si cette unite appartient au joueur courant
                        if (currentUnit.getOwner() == currentPlayer.getType()) {

                            // Si cette unite n'a pas joue
                            if (!currentUnit.hasPlayed()) {

                                // On ouvre un fr.istic.menu contenant les actions disponibles pour l'unite
                                final OptionSelector<UnitAction> actionSelector = currentUnit.getAvailableActions(currentCase, grid);

                                menuManagerInstance.addMenu(new UnitActionMenu(actionSelector));
                                game.setSelectedCase(currentCase);

                                // Le jeu passe en mode "selection de l'action d'une unite"
                                this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                            }
                        }
                    }

                    // Sinon, si la case courante est une usine (de n'importe quel type)
                    else if (currentCase.getTerrain() instanceof Factory) {

                        Factory factory = (Factory) currentCase.getTerrain();

                        // Si l'usine est possedee par le joueur courant
                        if (factory.getOwner() == currentPlayer.getType()) {

                            // Si l'usine peut creer une unite
                            if (Factory.canCreateUnit(currentCase)) {

                                // On cree un fr.istic.menu contenant toutes les unitees que l'usine peut produire, en fonction de l'argent du joueur courant
                                final OptionSelector<UnitType> unitSelector = factory.getUnitSelector(currentPlayer.getMoney());
                                menuManagerInstance.addMenu(new FactoryActionMenu(unitSelector));
                                game.setSelectedCase(currentCase);

                                // Le jeu passe en mode "Selection d'une unitee dans une usine"
                                this.instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_UNIT);

                            }
                            else
                                PopupRegistry.getInstance().push(new Popup("Avertissement!", "Impossible d'utiliser cette usine!"));

                        }

                    }

                    return true;

                }

                // Si le jeu est en mode "selection de l'action d'une unite"
                case PLAYING_SELECTING_UNIT_ACTION: {

                    Logger.getLogger().log("enter@PLAYING_SELECTING_UNIT_ACTION");

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;
                    if (game == null) break;

                    final UnitActionMenu menu = (UnitActionMenu) menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU);

                    // Si il y a un fr.istic.menu d'action d'unite existant
                    if (menu != null) {
                        Logger.getLogger().log("No fr.istic.menu");

                        // On recupere l'option selectionnee
                        final UnitAction action = menu.getSelectedValue();
                        final Unit unit = selectedCase.getUnit();

                        // Si l'action selectionnee est "deplacement"
                        if (action == UnitAction.MOVE) {

                            // On calcule tous les chemins possibles en partant de cette case
                            final Dijkstra dijkstra = new Dijkstra(currentCase, grid, unit, game.getWeather());
                            game.setSelectedCase(currentCase);

                            // On cree un nouveau mouvement partant de cette case
                            game.setMovement(new Movement(currentCase));
                            // On enregistre en "cache" l'instance de dijkstra, pour ne pas avoir a tout recalculer
                            game.setDijkstraResult(dijkstra);

                            // Le jeu passe en mode "deplacement d'une unite"
                            this.instance.setGameState(GameState.PLAYING_MOVING_UNIT);

                            // Permet d'obtenir la valeur minimale entre les points de mouvement de l'unite selon la meteo
                            // actuelle ou l'energie (essence/ration) de l'unite
                            final int point = Math.min(unit.getEnergy(), unit.getMovementPoint(game.getWeather()));

                            // On affiche un overlay sur les cases atteignables par l'unite
                            game.setOverlayCases(dijkstra.getReachableCases(point));
                            game.setOverlayType(OverlayType.MOVE);
                            menuManagerInstance.clearNonPersistent();

                        }

                        // Sinon, si l'action est "attaque", ou "attaque a distance"
                        else if (action == UnitAction.ATTACK || action == UnitAction.RANGED_ATTACK) {

                            Logger.getLogger().log("Action = Attack or RangedAttack");

                            // Si il y a une unite sur la case courante
                            if (currentCase.hasUnit()) {

                                Logger.getLogger().log("Case has fr.istic.unit");

                                // On cree une liste de toutes les armes utilisables, en fonction de l'action courante
                                List<Weapon> weapons = unit.getWeapons().stream().filter(w -> {
                                    if (action == UnitAction.ATTACK) return w instanceof MeleeWeapon;
                                    return w instanceof RangedWeapon;
                                }).collect(Collectors.toList());

                                final int minRange = weapons.stream().mapToInt(Weapon::getMinReach).min().orElse(0);
                                final int maxRange = weapons.stream().mapToInt(Weapon::getMaxReach).max().orElse(0);

                                List<Case> casesAround = grid.getCasesAround(currentCase.getCoordinate(), minRange, maxRange);

                                // On recupere une liste des cases autour du joueur, sur lesquelles il y a
                                // Une unite a portee de tir, et attaquable par notre unite
                                casesAround = casesAround.stream()
                                        .filter(c -> c.hasUnit())
                                        .filter(c -> unit.isDistanceReachable(c.distance(currentCase)))
                                        .filter(c -> unit.canAttack(c.getUnit()))
                                        .collect(Collectors.toList());

                                List<Unit> unitsAround = grid.getUnitsAround(casesAround);

                                // Si il n'y a qu'une seule unite autour
                                if (unitsAround.size() == 1) {

                                    Logger.getLogger().log("Case : one fr.istic.unit around");

                                    // L'unite courante attaque cette unite
                                    unit.attack(unitsAround.get(0));
                                    // Le jeu passe en mode "selection", on supprime le fr.istic.menu d'action de l'unite
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                                    menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);
                                    // On dit que l'unite a joue
                                    unit.setPlayed(true);
                                    // On supprime les unites mortes de la carte
                                    grid.garbageUnit();

                                }
                                // Sinon, si il y a plus d'une unite attaquable
                                else if (unitsAround.size() >= 2) {
                                    Logger.getLogger().log("Case : more than one fr.istic.unit around");
                                    // On affiche un overlay au dessus des cases avec une unite attaquable
                                    game.setOverlayCases(new HashSet<>(casesAround));
                                    game.setOverlayType(OverlayType.WEAPON);
                                    // Le jeu passe en mode "selection de cible", et on supprime le fr.istic.menu d'action de l'unite
                                    this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);
                                    menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                                }
                                else {
                                    Logger.getLogger().log("No fr.istic.unit in range");
                                }

                            }
                        }
                        // Sinon, si l'action est de capturer
                        else if (action == UnitAction.CAPTURE) {

                            Logger.getLogger().log("Action = Capture");

                            // Si la case courante est une propriete
                            if (currentCase.getTerrain() instanceof Property) {

                                Player.Type propertyOwner = ((Property) currentCase.getTerrain()).getOwner();
                                // L'unite courante capture la propriete, cela compte comme une fin de tour pour elle
                                currentUnit.capture((Property) currentCase.getTerrain());
                                unit.setPlayed(true);

                                // Si cette case de fr.istic.terrain était un QG
                                if (currentCase.getTerrain() instanceof HQ) {

                                    Logger.getLogger().log("It was an HQ!");

                                    // Si le proprietaire du QG n'en a plus
                                    if (!game.hasRemainingHQ(propertyOwner)) {

                                        Player p = game.getPlayerFromType(propertyOwner);
                                        // On tue ce joueur
                                        game.endPlayer(p);

                                        // Si la partie a un gagnant (le dernier joueur avec au moins 1 QG)
                                        if (game.hasWinner()) {

                                            EndingMenu endingMenu = new EndingMenu(game);
                                            menuManagerInstance.addMenu(endingMenu);
                                            game.endGame();
                                            endingMenu.fadeIn();

                                            // On termine la partie, et le jeu passe en mode "ecran de fin de partie"
                                            this.instance.setGameState(GameState.PLAYING_ENDIND_SCREEN);
                                            return true;

                                        }

                                    }

                                }

                            }

                            // Le jeu repasse en mode "selection", et on supprime le fr.istic.menu d'action de l'unite
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                        }
                        // Sinon, si l'action est d'attendre
                        else if (action == UnitAction.WAIT) {

                            unit.setPlayed(true);
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.removeMenu(MenuModel.UNIT_ACTION_MENU);

                        }
                        // Sinon, si l'action est de rentrer dans un vehicule de transport
                        else if (action == UnitAction.GET_IN) {

                            // On recupere les cases adjacentes a la case selectionnee
                            final List<Case> adjacentCase = grid.getAdjacentCases(game.getSelectedCase());

                            // On en extrait tous les transports ayant au moins une place de libre, appartenant au joueur courant
                            final List<Case> adjacentTransport = adjacentCase
                                    .stream()
                                    .filter(c -> c.getUnit() instanceof Transport)
                                    .filter(c -> c.hasUnit() && c.getUnit().getOwner() == currentPlayer.getType())
                                    .filter(c -> !((Transport) c.getUnit()).isFull())
                                    .collect(Collectors.toList());

                            // Si il n'y a qu'un seul transport autour
                            if (adjacentTransport.size() == 1) {

                                // On ajoute l'unite courante a l'interieur du transport, cela compte comme une action pour elle
                                unit.setPlayed(true);
                                ((Transport) adjacentTransport.get(0).getUnit()).addCarriedUnit(unit);
                                selectedCase.setUnit(null);
                                this.instance.setGameState(GameState.PLAYING_SELECTING);
                                menuManagerInstance.clearNonPersistent();

                            }
                            // Sinon, si il y a 2 transports ou plus
                            else if (adjacentTransport.size() >= 2) {

                                // Le jeu passe en mode "selection de transport"
                                game.setSelectedCase(currentCase);
                                game.setOverlayCases(new HashSet<>(adjacentTransport));
                                game.setOverlayType(OverlayType.MISC);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_TRANSPORT);
                                menuManagerInstance.clearNonPersistent();

                            }

                        }
                        // Sinon, si l'action est de deposer une unite
                        else if (action == UnitAction.DROP_UNIT) {

                            final int carriedCount = ((Transport) unit).getCarriedUnits().size();

                            // Si il n'y a qu'une seule unite de transportee dans le vehicule
                            if (carriedCount == 1) {

                                final Unit carriedUnit = ((Transport) unit).getCarriedUnits().get(0);

                                // On fait une liste des cases adjacentes, qui n'ont pas d'unite et sur lesquelles l'unite transportee peut se deplacer
                                List<Case> adjacentCase = grid.getAdjacentCases(selectedCase)
                                        .stream()
                                        .filter(c -> !c.hasUnit())
                                        .filter(c -> carriedUnit.canMoveTo(c, game.getWeather()))
                                        .collect(Collectors.toList());

                                // Si une ceule de ces cases existe
                                if (adjacentCase.size() == 1) {

                                    // On supprime l'unite du transport, et on la pose sur ladite case
                                    adjacentCase.get(0).setUnit(carriedUnit);
                                    ((Transport) unit).removeCarriedUnit(carriedUnit);
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                                    menuManagerInstance.clearNonPersistent();

                                }
                                // Sinon, si il y a plus d'une case
                                else if (adjacentCase.size() >= 2) {

                                    // Le jeu passe en mode "selection de zone de depot d'une unite"
                                    game.setSelectedCase(currentCase);
                                    this.instance.setGameState(GameState.PLAYING_SELECTING_DROP_ZONE);
                                    menuManagerInstance.clearNonPersistent();
                                    // On affiche un overlay au dessus des cases ou le depot est possible
                                    game.setOverlayType(OverlayType.MISC);
                                    game.setOverlayCases(new HashSet<>(adjacentCase));

                                }
                            }
                            // Si il y a plus d'une unite dans le transport
                            else {

                                game.setSelectedCase(currentCase);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_DROPPED_UNIT);
                                menuManagerInstance.clearNonPersistent();
                                // On affiche un fr.istic.menu de selection de l'unite a deposer
                                menuManagerInstance.addMenu(new DropUnitMenu(new OptionSelector<>(((Transport) unit).getCarriedUnits())));

                            }

                        }
                        // Sinon, si l'action est de ravitailler
                        else if (action == UnitAction.SUPPLY) {

                            // On recupere toutes les cases adjacentes, ainsi que toutes les unites adjacentes a la case courante
                            List<Case> adjacentCase = grid.getAdjacentCases(currentCase);
                            List<Unit> adjacentUnits = grid.getUnitsAround(adjacentCase);

                            boolean hasSupplied = false;

                            // Si il y a des unites autour de la case courante
                            if (!adjacentUnits.isEmpty()) {

                                // Pour chaque unite adjacente
                                for (Unit u : adjacentUnits) {

                                    // Si l'unite appartient au joueur courant
                                    if (u.getOwner() == currentPlayer.getType()) {

                                        // On ravitaille l'unite
                                        u.supply();
                                        hasSupplied = true;

                                    }

                                }

                            }

                            // Si l'unite courante a ravitaille
                            if (hasSupplied) {
                                // Cela compte comme une action
                                unit.setPlayed(true);
                                this.instance.setGameState(GameState.PLAYING_SELECTING);
                                menuManagerInstance.clearNonPersistent();
                            }

                        }
                        // Sinon, si l'action est de plonger (sous-marin)
                        else if (action == UnitAction.DIVE) {
                            // On fait plonger le sous-marin
                            ((Submarine) unit).dive();
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.clearNonPersistent();
                        }
                        // Sinon, si l'action est de faire surface (sous-marin)
                        else if (action == UnitAction.SURFACE) {
                            ((Submarine) unit).surface();
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.clearNonPersistent();
                        }
                        // Sinon, l'action est inconnue
                        else {
                            Logger.getLogger().log("Warn: Unknown action");
                        }
                    }

                    return true;
                }

                // Si le jeu est en mode "selection d'une cible"
                case PLAYING_SELECTING_TARGET: {

                    if (cursor == null) break;
                    if (grid == null) break;

                    // Si la case courante a une unite (la cible)
                    if (currentCase.hasUnit()) {

                        // On selectionne la cible qui est sur la case selectionne (l'unite du joueur courant)
                        Unit current = selectedCase.getUnit();

                        // Si cette unite peut attaquer la cible
                        if (current.canAttack(currentUnit)) {

                            // L'unite attaque la cible, cela compte comme une action
                            current.attack(currentUnit);
                            current.setPlayed(true);
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            // On supprime les unites mortes de la carte
                            grid.garbageUnit();
                            game.clearOverlayCases();

                        }


                    }

                    return true;
                }

                // Si le jeu est en mode "deplacement d'une unite"
                case PLAYING_MOVING_UNIT: {

                    if (grid == null) break;

                    // On recupere la derniere case du mouvement
                    final Case destination = game.getMovement().getMovementTail();

                    // Si la destination n'a pas d'unite
                    if (!destination.hasUnit() || destination.isFoggy()) {

                        // On deplace notre unite courante sur cette case
                        grid.moveUnit(game.getMovement());
                        game.clearOverlayCases();

                    }

                    return true;
                }

                // Si le jeu est en mode "selection d'une unite a deposer"
                case PLAYING_SELECTING_DROPPED_UNIT: {

                    // On ouvre un fr.istic.menu de selection de l'unite a deposer
                    final DropUnitMenu menu = (DropUnitMenu) menuManagerInstance.getMenu(MenuModel.DROP_UNIT_MENU);
                    // On recupere l'unite a deposer
                    final Unit droppedUnit = menu.getSelectedValue();

                    // On liste les cases adjacentes sur lesquelles il n'y a pas d'unite, et ou l'unite a deposer peut se deplacer
                    List<Case> adjacentCase = grid.getAdjacentCases(currentCase)
                            .stream()
                            .filter(c -> !c.hasUnit())
                            .filter(c -> droppedUnit.canMoveTo(c, game.getWeather()))
                            .collect(Collectors.toList());

                    // Si il n'y a qu'une seule case valide
                    if (adjacentCase.size() == 1) {

                        // On deplace l'unite du convoi vers la case
                        adjacentCase.get(0).setUnit(droppedUnit);
                        ((Transport) selectedUnit).removeCarriedUnit(droppedUnit);
                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        menuManagerInstance.clearNonPersistent();

                    }
                    // Sinon, si il y a plus d'une case valide
                    else if (adjacentCase.size() >= 2) {

                        // Le jeu passe en mode "selection de zone de depot", et on affiche un overlay sur les cases ou le depot est possible
                        this.instance.setGameState(GameState.PLAYING_SELECTING_DROP_ZONE);
                        game.setOverlayType(OverlayType.MISC);
                        game.setOverlayCases(new HashSet<>(adjacentCase));

                    }

                }

                // Si le jeu est en mode " selection d'une zone de depot"
                case PLAYING_SELECTING_DROP_ZONE: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;

                    // On affiche un fr.istic.menu de selection de l'unite a deposer
                    final DropUnitMenu menu = (DropUnitMenu) menuManagerInstance.getMenu(MenuModel.DROP_UNIT_MENU);

                    // L'unite a deposer est :
                    // Si un fr.istic.menu de depot existe, l'unite selectionne
                    // Sinon, l'unique unite du transport
                    final Unit dropUnit = menu != null ? menu.getSelectedValue() : ((Transport) selectedUnit).getCarriedUnits().get(0);

                    // Si la case courante n'a pas d'unite, qu'elle est adjacente a la case selectionne, et que l'unite a deposer peut se deplacer sur celle ci
                    if (!currentCase.hasUnit() && currentCase.isAdjacent(selectedCase) && dropUnit.canMoveTo(currentCase, game.getWeather())) {

                        // On depose l'unite  sur la case
                        currentCase.setUnit(dropUnit);
                        ((Transport) selectedUnit).removeCarriedUnit(dropUnit);
                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        menuManagerInstance.clearNonPersistent();
                        game.clearOverlayCases();

                    }

                    return true;

                }

                // Si le jeu est en mode "selection de transport"
                case PLAYING_SELECTING_TRANSPORT: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentPlayer == null) break;
                    if (selectedCase == null) break;
                    if (selectedUnit == null) break;

                    // Si la case courante est adjacente a la case selectionne
                    if (currentCase.isAdjacent(selectedCase)) {

                        // Si l'unite est un transport
                        if (currentUnit instanceof Transport) {

                            final Transport transport = (Transport) currentUnit;

                            // Si ce transport n'est pas plein, et qu'il peut faire rentrer l'unite selectionne
                            if (!transport.isFull() && transport.accept(selectedUnit)) {

                                // Si le transport appartient au joueur courant
                                if (currentUnit.getOwner() == currentPlayer.getType()) {

                                    // L'unite rentre dans le transport
                                    transport.addCarriedUnit(selectedUnit);
                                    selectedUnit.setPlayed(true);
                                    selectedCase.setUnit(null);
                                    game.clearOverlayCases();
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);

                                }

                            }

                        }

                    }

                    return true;

                }

                // Si le jeu est en mode "selection d'une unite dans une usine"
                case PLAYING_SELECTING_FACTORY_UNIT: {

                    // On ouvre un fr.istic.menu de selection d'une unite dans une usine
                    final FactoryActionMenu factoryActionMenu = (FactoryActionMenu) menuManagerInstance.getMenu(MenuModel.FACTORY_ACTION_MENU);
                    final OptionSelector<UnitType>.Option option = factoryActionMenu.getSelectedOption();

                    // Si une option a ete selectionnee
                    if (option != null) {

                        // On cree une nouvelle unstance de cette unite, qui est consideree comme ayant deja joue
                        final UnitType unitToCreate = option.getValue();
                        final Unit newUnit = unitToCreate.newInstance(currentPlayer.getType());

                        newUnit.setPlayed(true);

                        // Si la case courante est un port
                        if (currentCase.getTerrain() instanceof Port) {

                            // On cherche la premiere case d'eau adjacente au port
                            Case c = grid.getAdjacentCases(currentCase)
                                    .stream()
                                    .filter(cs -> cs.getTerrain() instanceof Water && !cs.hasUnit())
                                    .findFirst()
                                    .orElse(null);

                            // Si une telle case existe
                            if (c != null) {

                                // On fait apparaitre l'unite, et on deduit le prix de l'unite a l'argent du joueur courant
                                c.setUnit(newUnit);
                                currentPlayer.setMoney(currentPlayer.getMoney() - unitToCreate.getPrice());

                            }

                        }
                        // Sinon (si la case est une usine ou un aeroport)
                        else {
                            // On fait apparaitre l'unite, et on deduit le prix de l'unite a l'argent du joueur courant
                            currentCase.setUnit(newUnit);
                            currentPlayer.setMoney(currentPlayer.getMoney() - unitToCreate.getPrice());
                        }

                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        menuManagerInstance.clearNonPersistent();

                    }
                }

            }
        }
        catch (Exception e) {
            Logger.getLogger().write(e);
            e.printStackTrace();
        }

        return false;

    }

    /**
     * Gere la touche echap
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean escape(GameState gameState) {

        Logger.getLogger().log("Entering ActionHandler#escape");

        final Game game = this.instance.getCurrentGame();

        try {

            switch (gameState) {

                // Si le jeu est en mode "fr.istic.menu principal", ne fait rien
                case MENU_TITLE_SCREEN:
                case PLAYING_ENDIND_SCREEN:
                case PLAYING_SELECTING_NEXT_TURN_APPROVAL:
                    break;

                // Si le jeu est en mode "selection d'une carte"
                case MENU_MAP_SELECTION:
                    Logger.getLogger().log("escape@MENU_MAP_SELECTION");
                    // On revient au fr.istic.menu principal
                    this.instance.setGameState(GameState.MENU_TITLE_SCREEN);
                    MenuManager.getInstance().getMenu(MenuModel.MAIN_MENU).setVisible(true);
                    MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
                    break;

                // Si le jeu est en mode "selection"
                case PLAYING_SELECTING:
                    Logger.getLogger().log("escape@PLAYING_SELECTING");
                    // Le jeu passe en mode "fr.istic.menu pause"
                    this.instance.setGameState(GameState.MENU_PAUSE);
                    MenuManager.getInstance().addMenu(new PauseMenu());
                    break;


                default:
                    // Dans tous les autres cas
                    Logger.getLogger().log("escape@default");
                    // Le jeu revient en mode "selection"
                    instance.setGameState(GameState.PLAYING_SELECTING);
                    MenuManager.getInstance().clearNonPersistent();
                    game.resetMovement();
                    break;

            }

            if (game != null) game.clearOverlayCases();

        }
        catch (Exception e) {
            Logger.getLogger().write(e);
        }
        return true;

    }

    /**
     * Gere la touche espace
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean space(GameState gameState) {

        Logger.getLogger().log("Entering ActionHandler#space");

        final Game game = this.instance.getCurrentGame();
        final Grid grid = this.instance.isPlaying() ? game.getGrid() : null;
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;
        final Case currentCase = grid != null ? grid.getCase(cursor.getCoordinate()) : null;

        try {

            // Si le jeu est dans l'etat "selection"
            if (gameState == GameState.PLAYING_SELECTING) {
                Logger.getLogger().log("space@PLAYING_SELECTING");
                if (grid == null) return false;

                // On liste toutes les cases contenant des unites n'ayant pas joue, et appartenant au joueur courant
                final List<Case> playerUnitsCases = grid.getCases()
                        .stream()
                        .filter(Case::hasUnit)
                        .filter(c -> c.getUnit().getOwner() == game.getCurrentPlayer().getType())
                        .filter(c -> !c.getUnit().hasPlayed())
                        .collect(Collectors.toList());

                // Si la case courante est l'une de ces cases
                if (playerUnitsCases.contains(currentCase)) {

                    // Le jeu se centre sur la case suivante dans la liste
                    int index = playerUnitsCases.indexOf(currentCase);
                    index = (index + 1) % playerUnitsCases.size();

                    cursor.setCoordinate(playerUnitsCases.get(index).getCoordinate());
                    game.getView().focus(playerUnitsCases.get(index));

                }
                // Sinon, si il y a des cases dans la liste
                else if (playerUnitsCases.size() != 0) {
                    // On centre le jeu sur la premiere case de la liste
                    cursor.setCoordinate(playerUnitsCases.get(0).getCoordinate());
                    game.getView().focus(playerUnitsCases.get(0));
                }

                else {
                    PopupRegistry.getInstance().push(new Popup("Avertissement !", "Toutes vos fr.istic.unit\u00e9s ont d\u00e9j\u00e0 jou\u00e9 !"));
                }

            }
            // Sinon, si le jeu est en mode "selection de carte"
            else if (gameState == GameState.MENU_MAP_SELECTION) {

                // On actualise le champ courant (ex : Passage de tour automatique)
                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.toggleCurrentField();
                mapSelectionMenu.needsRefresh(true);

            }

        }
        catch (Exception e) {
            Logger.getLogger().write(e);
        }

        return false;

    }

    /**
     * Ajoute un mouvement (si ce dernier est possible) a la fleche de deplacement
     *
     * @param movement Le mouvement a ajouter
     */
    private void updateMovement(Runnable movement) {

        Logger.getLogger().log("updateMovement@default");

        try {

            // Definition de quelques valeurs necessaire
            final Game game = this.instance.getCurrentGame();
            final Movement move = game.getMovement();
            final Dijkstra dijkstraResult = game.getDijkstraResult();
            final Unit unit = game.getSelectedCase().getUnit();

            // Executer le Runnabe qui permet de bouget le curseur
            movement.run();

            // Recuperation de la destination
            final Coordinate destination = game.getCursor().getCoordinate();
            final Case destinationCase = game.getGrid().getCase(destination);

            // Permet d'obtenir la valeur minimale entre les points de mouvement de l'unite selon la meteo
            // actuelle ou l'energie (essence/ration) de l'unite
            final int point = Math.min(unit.getEnergy(), unit.getMovementPoint(game.getWeather()));

            // Recuperer le chemin le plus court
            List<Case> path = dijkstraResult.getShortestPathTo(destinationCase, point);

            // Si un tel chemin existe on change le mouvement pour suivre ce dernier
            if (path != null) move.setPath(path);

            // Actualiser la vue de l'ecran
            game.getView().adjustOffset();

        }
        catch (Exception e) {
            Logger.getLogger().write(e);
        }
    }

}
