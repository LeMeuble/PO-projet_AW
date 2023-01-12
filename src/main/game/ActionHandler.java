package main.game;

import main.Logger;
import main.MiniWars;
import main.control.Cursor;
import main.control.KeystrokeListener;
import main.map.Case;
import main.map.Coordinate;
import main.map.Grid;
import main.menu.*;
import main.menu.model.*;
import main.render.OverlayType;
import main.render.Popup;
import main.render.PopupRegistry;
import main.render.Renderer;
import main.terrain.Factory;
import main.terrain.Property;
import main.terrain.type.HQ;
import main.terrain.type.Port;
import main.terrain.type.Water;
import main.unit.Transport;
import main.unit.Unit;
import main.unit.UnitAction;
import main.unit.UnitType;
import main.unit.type.Submarine;
import main.util.Dijkstra;
import main.util.OptionSelector;
import main.weapon.MeleeWeapon;
import main.weapon.RangedWeapon;
import main.weapon.Weapon;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class prenant en charge les actions associees aux differentes touches pressees
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
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

        }

        return false;

    }

    /**
     * Gere l'appui sur la touche D
     *
     * @param gameState L'etat actuel dans lequel est le jeu
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean keyD(GameState gameState) {

        switch (gameState) {

            // Si le jeu est en mode "selection"
            case PLAYING_SELECTING:
                // Passe le tour
                this.instance.getCurrentGame().nextTurn();
                break;

//            //Todo : virer cette option
//            case PLAYING_SELECTING_FACTORY_UNIT:
//                this.instance.getCurrentGame().getCurrentPlayer().setMoney(this.instance.getCurrentGame().getCurrentPlayer().getMoney() + (int) (Math.random() * 1000));
//
//                break;
        }

        return true;

    }


    /**
     * Gere la touche haut
     *
     * @param gameState l'etat du jeu
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean up(GameState gameState) {

        Logger.getLogger().log("Entered ActionHandler#up");

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            // Si le jeu est en mode "selection d'une carte"
            case MENU_MAP_SELECTION: {

                // Cree un nouveau menu de selection, et actualise l'ecran
                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.previousField();
                mapSelectionMenu.needsRefresh(true);

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
                // Si le curseur n'est pas nul, actualise la fleche de deplacement vers le haut
                if (cursor != null) this.updateMovement(cursor::up);
                return true;
            }

            // Si le jeu est en mode "pause", "selection de l'unite a deposer", "selection de l'unite a creer dans une usine" ou "selection d'une action d'unite"
            case MENU_PAUSE:
            case PLAYING_SELECTING_DROPPED_UNIT:
            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {

                System.out.println("up@PLAYING_SELECTING_UNIT_ACTION");

                Logger.getLogger().log("down@PLAYING_SELECTING_UNIT_ACTION");

                // Creation d'un menu contenant uniquement les actions possibles
                MenuManager.getInstance().getMenus().stream().filter(m -> m instanceof SelectionMenu).filter(Menu::isVisible).forEach(m -> {
                    ((SelectionMenu<?>) m).previous();
                    m.needsRefresh(true);
                    System.out.println(m);
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
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean down(GameState gameState) {

        Logger.getLogger().log("Entered ActionHandler#down");

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION: {

                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.nextField();
                mapSelectionMenu.needsRefresh(true);

            }

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {

                Logger.getLogger().log("down@PLAYING_SELECTING_TARGET");

                if (cursor == null) break;

                boolean updateDisplay = cursor.down();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                Logger.getLogger().log("down@PLAYING_MOVING_UNIT");

                if (cursor != null) this.updateMovement(cursor::down);
                return true;
            }

            case MENU_PAUSE:
            case PLAYING_SELECTING_DROPPED_UNIT:
            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {

                Logger.getLogger().log("down@PLAYING_SELECTING_UNIT_ACTION");

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
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean left(GameState gameState) {

        Logger.getLogger().log("Entering ActionHandler#left");
        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION: {

                Logger.getLogger().log("left@MENU_MAP_SELECTION");

                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.previous();
                mapSelectionMenu.needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getLogger().log("left@PLAYING_SELECTING_TARGET");
                if (cursor == null) break;

                boolean updateDisplay = cursor.left();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                Logger.getLogger().log("left@PLAYING_MOVING_UNIT");
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
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean right(GameState gameState) {
        Logger.getLogger().log("Entering ActionHandler#right");
        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION: {

                Logger.getLogger().log("left@MENU_MAP_SELECTION");

                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.next();
                mapSelectionMenu.needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getLogger().log("right@PLAYING_SELECTING_TARGET");
                if (cursor == null) break;

                boolean updateDisplay = cursor.right();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                Logger.getLogger().log("right@PLAYING_MOVING_UNIT");
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

        final Case currentCase = grid != null ? grid.getCase(cursor.getCoordinate()) : null;
        final Unit currentUnit = grid != null ? currentCase.getUnit() : null;

        final Case selectedCase = grid != null ? game.getSelectedCase() : null;
        final Unit selectedUnit = selectedCase != null ? selectedCase.getUnit() : null;

        Logger.getLogger().log("enter@default");

        try {

            switch (gameState) {

                // Si le jeu est en mode "pause"
                case MENU_PAUSE: {

                    final PauseMenu pauseMenu = (PauseMenu) menuManagerInstance.getMenu(MenuModel.PAUSE_MENU);
                    final PauseMenu.Action action = pauseMenu.getSelectedValue();

                    // Si l'action du menu est "continuer"
                    if (action == PauseMenu.Action.RESUME) {

                        // Le jeu retourne dans le mode "selection"
                        this.instance.setGameState(GameState.PLAYING_SELECTING);

                        // On supprime les menus non persistants de l'ecran
                        menuManagerInstance.clearNonPersistent();

                        return true;

                    }
                    // Sinon, si l'action du menu est "quitter"
                    else if (action == PauseMenu.Action.QUIT) {

                        // Le jeu passe en mode "selection d'une carte"
                        this.instance.setGameState(GameState.MENU_MAP_SELECTION);

                        // On supprime toutes les popups, les menus non persistants
                        PopupRegistry.getInstance().clear();
                        menuManagerInstance.clearNonPersistent();

                        // On affiche le menu de selection de carte
                        menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);
                        menuManagerInstance.removeMenu(MenuModel.BOTTOM_MENU);

                        // On termine l'instance de la partie precedente
                        this.instance.endGame();
                        System.out.println("Quitting game");
                        return true;
                    }

                    return false;

                }

                // Si le jeu est en mode "ecran d'accueil du jeu"
                case MENU_TITLE_SCREEN: {
                    Logger.getLogger().log("enter@MENU_TITLE_SCREEN");
                    // Le jeu passe en mode "selection d'une carte"
                    this.instance.setGameState(GameState.MENU_MAP_SELECTION);

                    // On arrete d'afficher le menu d'accueil, et on affiche celui de selection de carte
                    rendererInstance.clearBuffer();
                    menuManagerInstance.getMenu(MenuModel.MAIN_MENU).setVisible(false);
                    menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);
                    return true;
                }

                // Si le jeu est en mode "selection d'une carte"
                case MENU_MAP_SELECTION: {

                    Logger.getLogger().log("enter@MENU_MAP_SELECTION");

                    // On cree un menu de selection de carte
                    MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);

                    rendererInstance.clearBuffer();

                    // Si une option a ete selectionnee dans le menu
                    if (mapSelectionMenu.getSelectedValue() != null) {
                        // On demarre une nouvelle instance du jeu, sur la carte selectionnee
                        this.instance.newGame(mapSelectionMenu.getSelectedValue(), mapSelectionMenu.getSettings());
                        // On enleve le menu de selection de carte de l'affichage
                        mapSelectionMenu.setVisible(false);
                    }
                    return true;
                }

                // Si le jeu est en mode "sélection"
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

                                // On ouvre un menu contenant les actions disponibles pour l'unite
                                final OptionSelector<UnitAction> actionSelector = currentUnit.getAvailableActions(currentCase, grid);

                                menuManagerInstance.addMenu(new UnitActionMenu(actionSelector));
                                game.setSelectedCase(currentCase);

                                // Le jeu passe en mode "selection de l'action d'une unite"
                                this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                            } else System.out.println("Warn: Unit has already played");
                        } else System.out.println("Warn: Unit not owned by current player");
                    }

                    // Sinon, si la case courante est une usine (de n'importe quel type)
                    else if (currentCase.getTerrain() instanceof Factory) {

                        Factory factory = (Factory) currentCase.getTerrain();

                        // Si l'usine est possedee par le joueur courant
                        if (factory.getOwner() == currentPlayer.getType()) {

                            // Si l'usine peut creer une unite
                            if (Factory.canCreateUnit(currentCase)) {

                                // On cree un menu contenant toutes les unitees que l'usine peut produire, en fonction de l'argent du joueur courant
                                final OptionSelector<UnitType> unitSelector = factory.getUnitSelector(currentPlayer.getMoney());
                                System.out.println(unitSelector.getValues());
                                menuManagerInstance.addMenu(new FactoryActionMenu(unitSelector));
                                game.setSelectedCase(currentCase);

                                // Le jeu passe en mode "Selection d'une unitee dans une usine"
                                this.instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_UNIT);

                            } else System.out.println("Warn: There is already a unit here / u can't!");

                        } else System.out.println("Warn: Factory not owned by current player");

                    } else System.out.println("Warn: There is nothing to do here");

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

                    // Si il y a un menu d'action d'unite existant
                    if (menu != null) {
                        Logger.getLogger().log("No menu");

                        // On recupere l'option selectionnee
                        final UnitAction action = menu.getSelectedValue();
                        final Unit unit = selectedCase.getUnit();

                        // Si l'action selectionnee est "deplacement"
                        if (action == UnitAction.MOVE) {

                            Logger.getLogger().log("Action = move");

                            // On calcule tous les chemins possibles en partant de cette case
                            final Dijkstra dijkstra = new Dijkstra(currentCase, grid, unit, game.getWeather());
                            game.setSelectedCase(currentCase);
                            // On cree un nouveau mouvement partant de cette case
                            game.setMovement(new Movement(currentCase));
                            // On enregistre en "cache" l'instance de dijkstra, pour ne pas avoir a tout recalculer
                            game.setDijkstraResult(dijkstra);

                            // Le jeu passe en mode "deplacement d'une unite"
                            this.instance.setGameState(GameState.PLAYING_MOVING_UNIT);

                            // TODO: jsp
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

                                Logger.getLogger().log("Case has unit");

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

                                    Logger.getLogger().log("Case : one unit around");

                                    // L'unite courante attaque cette unite
                                    unit.attack(unitsAround.get(0));
                                    // Le jeu passe en mode "selection", on supprime le menu d'action de l'unite
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                                    menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);
                                    // On dit que l'unite a joue
                                    unit.setPlayed(true);
                                    // On supprime les unites mortes de la carte
                                    grid.garbageUnit();

                                }
                                // Sinon, si il y a plus d'une unite attaquable
                                else if (unitsAround.size() >= 2) {
                                    Logger.getLogger().log("Case : more than one unit around");
                                    System.out.println("OK: There are multiple units in range");
                                    // On affiche un overlay au dessus des cases avec une unite attaquable
                                    game.setOverlayCases(new HashSet<>(casesAround));
                                    game.setOverlayType(OverlayType.WEAPON);
                                    this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);
                                    menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                                } else {
                                    System.out.println("Warn: Not unit in range");
                                    Logger.getLogger().log("No unit in range");
                                }

                            }

                        } else if (action == UnitAction.CAPTURE) {

                            Logger.getLogger().log("Action = Capture");

                            if (currentCase.getTerrain() instanceof Property) {

                                Player.Type propertyOwner = ((Property) currentCase.getTerrain()).getOwner();
                                currentUnit.capture((Property) currentCase.getTerrain());

                                unit.setPlayed(true);

                                if (currentCase.getTerrain() instanceof HQ) {

                                    Logger.getLogger().log("It was an HQ!");

                                    if (!game.hasRemainingHQ(propertyOwner)) {

                                        Player p = game.getPlayerFromType(propertyOwner);

                                        p.setAlive(false);
                                        game.endPlayer(p);

                                        if (game.hasWinner()) {

                                            game.endGame();
                                            this.instance.setGameState(GameState.PLAYING_ENDIND_SCREEN);
                                            return true;

                                        }

                                    }

                                }

                            }

                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                        } else if (action == UnitAction.WAIT) {

                            unit.setPlayed(true);
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.removeMenu(MenuModel.UNIT_ACTION_MENU);

                        } else if (action == UnitAction.GET_IN) {

                            final List<Case> adjacentCase = grid.getAdjacentCases(game.getSelectedCase());

                            final List<Transport> adjacentTransport = adjacentCase
                                    .stream()
                                    .filter(c -> c.getUnit() instanceof Transport)
                                    .filter(c -> c.hasUnit() && c.getUnit().getOwner() == currentPlayer.getType())
                                    .map(u -> (Transport) u.getUnit())
                                    .filter(t -> !t.isFull())
                                    .collect(Collectors.toList());

                            if (adjacentTransport.size() == 1) {

                                unit.setPlayed(true);
                                adjacentTransport.get(0).addCarriedUnit(unit);
                                selectedCase.setUnit(null);
                                this.instance.setGameState(GameState.PLAYING_SELECTING);
                                menuManagerInstance.clearNonPersistent();

                            } else if (adjacentTransport.size() >= 2) {

                                game.setSelectedCase(currentCase);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_TRANSPORT);
                                menuManagerInstance.clearNonPersistent();

                            }

                        } else if (action == UnitAction.DROP_UNIT) {

                            final int carriedCount = ((Transport) unit).getCarriedUnits().size();

                            if (carriedCount == 1) {

                                final Unit carriedUnit = ((Transport) unit).getCarriedUnits().get(0);

                                List<Case> adjacentCase = grid.getAdjacentCases(selectedCase)
                                        .stream()
                                        .filter(c -> !c.hasUnit())
                                        .filter(c -> carriedUnit.canMoveTo(c, game.getWeather()))
                                        .collect(Collectors.toList());

                                if (adjacentCase.size() == 1) {

                                    adjacentCase.get(0).setUnit(carriedUnit);
                                    ((Transport) unit).removeCarriedUnit(carriedUnit);
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                                    menuManagerInstance.clearNonPersistent();

                                } else if (adjacentCase.size() >= 2) {

                                    game.setSelectedCase(currentCase);
                                    this.instance.setGameState(GameState.PLAYING_SELECTING_DROP_ZONE);
                                    menuManagerInstance.clearNonPersistent();
                                    game.setOverlayType(OverlayType.MISC);
                                    game.setOverlayCases(new HashSet<>(adjacentCase));

                                }
                            } else {

                                game.setSelectedCase(currentCase);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_DROPPED_UNIT);
                                menuManagerInstance.clearNonPersistent();
                                menuManagerInstance.addMenu(new DropUnitMenu(new OptionSelector<>(((Transport) unit).getCarriedUnits())));

                            }

                        } else if (action == UnitAction.SUPPLY) {

                            List<Case> adjacentCase = grid.getAdjacentCases(currentCase);
                            List<Unit> adjacentUnits = grid.getUnitsAround(adjacentCase);

                            boolean hasSupplied = false;

                            if (!adjacentUnits.isEmpty()) {

                                for (Unit u : adjacentUnits) {

                                    if (u.getOwner() == currentPlayer.getType()) {

                                        u.supply();
                                        hasSupplied = true;

                                    }

                                }

                            }

                            if (hasSupplied) {
                                unit.setPlayed(true);
                                this.instance.setGameState(GameState.PLAYING_SELECTING);
                                menuManagerInstance.clearNonPersistent();
                            } else System.out.println("Warn: No unit to reload");
                            Logger.getLogger().log("Warn: No unit to reload");

                        } else if (action == UnitAction.DIVE) {
                            ((Submarine) unit).dive();
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.clearNonPersistent();
                        } else if (action == UnitAction.SURFACE) {
                            ((Submarine) unit).surface();
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.clearNonPersistent();
                        } else {
                            System.out.println("Warn: Unknown action");
                            Logger.getLogger().log("Warn: Unknown action");
                        }
                    }

                    return true;
                }
                case PLAYING_SELECTING_TARGET: {

                    if (cursor == null) break;
                    if (grid == null) break;

                    if (currentCase.hasUnit()) {

                        Unit current = selectedCase.getUnit();

                        if (current.canAttack(currentUnit)) {

                            current.attack(currentUnit);
                            current.setPlayed(true);
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            grid.garbageUnit();
                            game.clearOverlayCases();

                        }


                    }

                    return true;
                }

                case PLAYING_MOVING_UNIT: {

                    if (grid == null) break;

                    final Case destination = game.getMovement().getMovementTail();

                    if (!destination.hasUnit()) {

                        grid.moveUnit(game.getMovement());
                        game.clearOverlayCases();

                    }

                    return true;
                }

                case PLAYING_SELECTING_DROPPED_UNIT: {

                    final DropUnitMenu menu = (DropUnitMenu) menuManagerInstance.getMenu(MenuModel.DROP_UNIT_MENU);
                    final Unit droppedUnit = menu.getSelectedValue();

                    List<Case> adjacentCase = grid.getAdjacentCases(currentCase)
                            .stream()
                            .filter(c -> !c.hasUnit())
                            .filter(c -> droppedUnit.canMoveTo(c, game.getWeather()))
                            .collect(Collectors.toList());

                    if (adjacentCase.size() == 1) {

                        adjacentCase.get(0).setUnit(droppedUnit);
                        ((Transport) selectedUnit).removeCarriedUnit(droppedUnit);
                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        menuManagerInstance.clearNonPersistent();

                    } else if (adjacentCase.size() >= 2) {

                        this.instance.setGameState(GameState.PLAYING_SELECTING_DROP_ZONE);
                        game.setOverlayType(OverlayType.MISC);
                        game.setOverlayCases(new HashSet<>(adjacentCase));

                    }

                }

                case PLAYING_SELECTING_DROP_ZONE: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;

                    final DropUnitMenu menu = (DropUnitMenu) menuManagerInstance.getMenu(MenuModel.DROP_UNIT_MENU);

                    final Unit dropUnit = menu != null ? menu.getSelectedValue() : ((Transport) selectedUnit).getCarriedUnits().get(0);

                    if (!currentCase.hasUnit() && currentCase.isAdjacent(selectedCase) && dropUnit.canMoveTo(currentCase, game.getWeather())) {

                        currentCase.setUnit(dropUnit);
                        ((Transport) selectedUnit).removeCarriedUnit(dropUnit);
                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        menuManagerInstance.clearNonPersistent();
                        game.clearOverlayCases();

                    }

                    return true;

                }

                case PLAYING_SELECTING_TRANSPORT: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentPlayer == null) break;
                    if (selectedCase == null) break;

                    if (currentCase.isAdjacent(selectedCase)) {

                        if (currentUnit instanceof Transport) {

                            final Transport transport = (Transport) currentUnit;

                            if (!transport.isFull() && transport.accept(selectedUnit)) {

                                if (currentUnit.getOwner() == currentPlayer.getType()) {

                                    transport.addCarriedUnit(selectedUnit);
                                    selectedCase.setUnit(null);
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);

                                }

                            }

                        }

                    }

                    return true;

                }

                case PLAYING_SELECTING_FACTORY_UNIT: {

                    final FactoryActionMenu factoryActionMenu = (FactoryActionMenu) menuManagerInstance.getMenu(MenuModel.FACTORY_ACTION_MENU);
                    final OptionSelector<UnitType>.Option option = factoryActionMenu.getSelectedOption();

                    if (option != null) {

                        final UnitType unitToCreate = option.getValue();
                        final Unit newUnit = unitToCreate.newInstance(currentPlayer.getType());

                        newUnit.setPlayed(true);

                        if (currentCase.getTerrain() instanceof Port) {

                            Case c = grid.getAdjacentCases(currentCase)
                                    .stream()
                                    .filter(cs -> cs.getTerrain() instanceof Water && !cs.hasUnit())
                                    .findFirst()
                                    .orElse(null);

                            if (c != null) {

                                c.setUnit(newUnit);
                                currentPlayer.setMoney(currentPlayer.getMoney() - unitToCreate.getPrice());

                            } else System.out.println("ERROR! Impossible de creer l'unite navale!");

                        } else {
                            currentCase.setUnit(newUnit);
                            currentPlayer.setMoney(currentPlayer.getMoney() - unitToCreate.getPrice());
                        }

                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        menuManagerInstance.clearNonPersistent();

                    }
                }

            }
        } catch (Exception e) {
            Logger.getLogger().write(e);
            e.printStackTrace();
        }

        return false;

    }

    /**
     * Gere la touche echap
     *
     * @param gameState l'etat du jeu
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean escape(GameState gameState) {

        Logger.getLogger().log("Entering ActionHandler#escape");

        final Game game = this.instance.getCurrentGame();

        try {

            switch (gameState) {

                case MENU_TITLE_SCREEN:
                    break;

                case MENU_MAP_SELECTION:
                    Logger.getLogger().log("escape@MENU_MAP_SELECTION");
                    this.instance.setGameState(GameState.MENU_TITLE_SCREEN);
                    MenuManager.getInstance().getMenu(MenuModel.MAIN_MENU).setVisible(true);
                    MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
                    break;

                case PLAYING_SELECTING:
                    Logger.getLogger().log("escape@PLAYING_SELECTING");
                    this.instance.setGameState(GameState.MENU_PAUSE);
                    MenuManager.getInstance().addMenu(new PauseMenu());
                    break;

//                case PLAYING_SELECTING_DROP_ZONE:
//                case PLAYING_SELECTING_TRANSPORT:
//                case MENU_PAUSE:
//                    Logger.getLogger().log("escape@MENU_PAUSE");
//                    this.instance.setGameState(GameState.PLAYING_SELECTING);
//                    break;

                default:
                    Logger.getLogger().log("escape@default");
                    instance.setGameState(GameState.PLAYING_SELECTING);
                    MenuManager.getInstance().clearNonPersistent();
                    game.resetMovement();
                    break;

            }

            game.clearOverlayCases();
        } catch (Exception e) {
            Logger.getLogger().write(e);
        }
        return true;

    }

    /**
     * Gere la touche espace
     *
     * @param gameState l'etat du jeu
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean space(GameState gameState) {

        Logger.getLogger().log("Entering ActionHandler#space");

        final Game game = this.instance.getCurrentGame();
        final Grid grid = this.instance.isPlaying() ? game.getGrid() : null;
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;
        final Case currentCase = grid != null ? grid.getCase(cursor.getCoordinate()) : null;

        try {

            if (gameState == GameState.PLAYING_SELECTING) {
                Logger.getLogger().log("space@PLAYING_SELECTING");
                if (grid == null) return false;

                final List<Case> playerUnitsCases = grid.getCases()
                        .stream()
                        .filter(Case::hasUnit)
                        .filter(c -> c.getUnit().getOwner() == game.getCurrentPlayer().getType())
                        .filter(c -> !c.getUnit().hasPlayed())
                        .collect(Collectors.toList());

                if (playerUnitsCases.contains(currentCase)) {

                    int index = playerUnitsCases.indexOf(currentCase);
                    index = (index + 1) % playerUnitsCases.size();

                    cursor.setCoordinate(playerUnitsCases.get(index).getCoordinate());
                    game.getView().focus(playerUnitsCases.get(index));

                } else if (playerUnitsCases.size() != 0) {
                    cursor.setCoordinate(playerUnitsCases.get(0).getCoordinate());
                    game.getView().focus(playerUnitsCases.get(0));
                }

                else {
                    PopupRegistry.getInstance().push(new Popup("Avertissement !", "Toutes vos unit\u00e9s ont d\u00e9j\u00e0 jou\u00e9 !"));
                }

            }
            else if (gameState == GameState.MENU_MAP_SELECTION) {

                MapSelectionMenu mapSelectionMenu = (MapSelectionMenu) MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU);
                mapSelectionMenu.toggleCurrentField();
                mapSelectionMenu.needsRefresh(true);

            }

        } catch (Exception e) {
            Logger.getLogger().write(e);
        }

        return false;

    }

    /**
     * Ajoute un mouvement (si ce dernier est possible) a la flèche de deplacement
     *
     * @param movement Le mouvement a ajouter
     */
    private void updateMovement(Runnable movement) {

        Logger.getLogger().log("updateMovement@default");

        try {

            final Game game = this.instance.getCurrentGame();
            final Movement move = game.getMovement();
            final Dijkstra dijkstraResult = game.getDijkstraResult();
            final Unit unit = game.getSelectedCase().getUnit();

            movement.run();

            final Coordinate destination = game.getCursor().getCoordinate();
            final Case destinationCase = game.getGrid().getCase(destination);

            final int point = Math.min(unit.getEnergy(), unit.getMovementPoint(game.getWeather()));

            List<Case> path = dijkstraResult.getShortestPathTo(destinationCase, point);

            if (path != null) move.setPath(path);

            game.getView().adjustOffset();

        } catch (Exception e) {
            Logger.getLogger().write(e);
        }
    }

}
