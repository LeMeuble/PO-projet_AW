package main.game;

import main.Logger;
import main.MiniWars;
import main.animation.MovementAnimation;
import main.control.Cursor;
import main.control.KeystrokeListener;
import main.map.Case;
import main.map.Coordinate;
import main.map.Grid;
import main.menu.ActionMenu;
import main.menu.Menu;
import main.menu.MenuManager;
import main.menu.MenuModel;
import main.menu.model.FactoryActionMenu;
import main.menu.model.UnitActionMenu;
import main.render.OverlayType;
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

        }

        if (this.instance.getCurrentGame().getCurrentPlayer().hasAutoTurnSkipping()) {

            if (!this.instance.getCurrentGame().hasRemainingAction()) {

                this.instance.getCurrentGame().nextTurn();

            }

        }

        return false;

    }

    public boolean keyD(GameState gameState) {

        this.instance.getCurrentGame().getCurrentPlayer().setMoney(this.instance.getCurrentGame().getCurrentPlayer().getMoney() + (int) (Math.random() * 1000));

//        switch (gameState) {
//            case PLAYING_SELECTING:
//                this.instance.getCurrentGame().nextTurn();
//                break;
//            case PLAYING_SELECTING_UNIT_ACTION:
//                this.instance.getCurrentGame().getSelectedCase().getUnit().getWeapons().forEach(w -> {
//                    System.out.println(w.getClass() + " " + w.getAmmo());
//                });
//                break;
//        }

        return true;

    }


    /**
     * Gere la touche haut
     *
     * @param gameState l'etat du jeu
     *
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean up(GameState gameState) {

        Logger.getInstanceActionHandler().log("Entered ActionHandler#up");

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getInstanceActionHandler().log("up@PLAYING_SELECTING_TARGET");

                if (cursor == null) break;

                boolean updateDisplay = cursor.up();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {

                Logger.getInstanceActionHandler().log("up@PLAYING_MOVING_UNIT");

                if (cursor != null) this.updateMovement(cursor::up);
                return true;
            }

            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {

                Logger.getInstanceActionHandler().log("up@PLAYING_SELECTING_UNIT_ACTION");

                List<Menu> menus = MenuManager.getInstance().getMenus();
                for (Menu menu : menus) {
                    if (menu instanceof ActionMenu) {
                        ((ActionMenu) menu).previous();
                        menu.needsRefresh(true);
                    }
                }
                return false;
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

        Logger.getInstanceActionHandler().log("Entered ActionHandler#down");

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {

                Logger.getInstanceActionHandler().log("down@PLAYING_SELECTING_TARGET");

                if (cursor == null) break;

                boolean updateDisplay = cursor.down();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                Logger.getInstanceActionHandler().log("down@PLAYING_MOVING_UNIT");

                if (cursor != null) this.updateMovement(cursor::down);
                return true;
            }

            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {

                Logger.getInstanceActionHandler().log("down@PLAYING_SELECTING_UNIT_ACTION");

                MenuManager.getInstance().getMenus().stream().filter(m -> m instanceof ActionMenu).forEach(m -> {
                    ((ActionMenu) m).next();
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
        Logger.getInstanceActionHandler().log("Entering ActionHandler#left");
        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION: {
                Logger.getInstanceActionHandler().log("left@MENU_MAP_SELECTION");
                Renderer.getInstance().clearBuffer();
                this.instance.getMapSelector().previous();
                MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getInstanceActionHandler().log("left@PLAYING_SELECTING_TARGET");
                if (cursor == null) break;

                boolean updateDisplay = cursor.left();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                Logger.getInstanceActionHandler().log("left@PLAYING_MOVING_UNIT");
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
        Logger.getInstanceActionHandler().log("Entering ActionHandler#right");
        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION: {
                Logger.getInstanceActionHandler().log("right@MENU_MAP_SELECTION");
                Renderer.getInstance().clearBuffer();
                this.instance.getMapSelector().next();
                MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                Logger.getInstanceActionHandler().log("right@PLAYING_SELECTING_TARGET");
                if (cursor == null) break;

                boolean updateDisplay = cursor.right();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                Logger.getInstanceActionHandler().log("right@PLAYING_MOVING_UNIT");
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

        final Game game = this.instance.getCurrentGame();
        final Grid grid = this.instance.isPlaying() ? game.getGrid() : null;
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        final MenuManager menuManagerInstance = MenuManager.getInstance();
        final Renderer rendererInstance = Renderer.getInstance();

        final Player currentPlayer = this.instance.isPlaying() ? game.getCurrentPlayer() : null;

        final int x = cursor != null ? cursor.getCoordinate().getX() : 0;
        final int y = cursor != null ? cursor.getCoordinate().getY() : 0;

        final Case currentCase = grid != null ? grid.getCase(x, y) : null;
        final Case selectedCase = grid != null ? game.getSelectedCase() : null;

        final Unit currentUnit = grid != null ? currentCase.getUnit() : null;

        Logger.getInstanceActionHandler().log("enter@default");

        try {

            switch (gameState) {

                case MENU_TITLE_SCREEN: {
                    Logger.getInstanceActionHandler().log("enter@MENU_TITLE_SCREEN");
                    rendererInstance.clearBuffer();
                    this.instance.setGameState(GameState.MENU_MAP_SELECTION);
                    menuManagerInstance.getMenu(MenuModel.MAIN_MENU).setVisible(false);
                    menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);

                    return true;
                }

                case MENU_MAP_SELECTION: {
                    Logger.getInstanceActionHandler().log("enter@MENU_MAP_SELECTION");
                    rendererInstance.clearBuffer();
                    if (this.instance.getMapSelector().getSelectedOption() != null) {
                        this.instance.newGame(this.instance.getMapSelector().getSelectedValue());
                        menuManagerInstance.getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
                    }
                    return true;
                }

                case PLAYING_SELECTING: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;
                    if (currentPlayer == null) break;

                    if (currentCase.hasUnit()) {

                        if (currentUnit.getOwner() == currentPlayer.getType()) {

                            if (!currentUnit.hasPlayed()) {

                                final OptionSelector<UnitAction> actionSelector = currentUnit.getAvailableActions(currentCase, grid);

                                menuManagerInstance.addMenu(new UnitActionMenu(actionSelector));
                                game.setSelectedCase(currentCase);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                            }
                            else System.out.println("Warn: Unit has already played");
                        }
                        else System.out.println("Warn: Unit not owned by current player");
                    }
                    else if (currentCase.getTerrain() instanceof Factory) {

                        Factory factory = (Factory) currentCase.getTerrain();

                        if (factory.getOwner() == currentPlayer.getType()) {

                            System.out.println("Factory owned by current player");
                            if (Factory.canCreateUnit(currentCase)) {

                                final OptionSelector<UnitType> unitSelector = factory.getUnitSelector(currentPlayer.getMoney());
                                System.out.println(unitSelector.getValues());
                                menuManagerInstance.addMenu(new FactoryActionMenu(unitSelector));
                                game.setSelectedCase(currentCase);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_UNIT);

                            }
                            else System.out.println("Warn: There is already a unit here / u can't!");

                        }
                        else System.out.println("Warn: Factory not owned by current player");

                    }

                    else System.out.println("Warn: There is nothing to do here");

                    return true;

                }

                case PLAYING_SELECTING_UNIT_ACTION: {

                    Logger.getInstanceActionHandler().log("enter@PLAYING_SELECTING_UNIT_ACTION");

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;
                    if (game == null) break;

                    final UnitActionMenu menu = (UnitActionMenu) menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU);

                    if (menu != null) {
                        Logger.getInstanceActionHandler().log("No menu");
                        final UnitAction action = menu.getSelectedValue();
                        final Unit unit = selectedCase.getUnit();

                        if (action == UnitAction.MOVE) {
                            Logger.getInstanceActionHandler().log("Action = move");
                            final Dijkstra dijkstra = new Dijkstra(currentCase, grid, unit, game.getWeather());
                            game.setSelectedCase(currentCase);
                            game.setMovement(new Movement(currentCase));
                            game.setDijkstraResult(dijkstra);

                            this.instance.setGameState(GameState.PLAYING_MOVING_UNIT);

                            final int point = Math.min(unit.getEnergy(), unit.getMovementPoint(game.getWeather()));

                            game.setOverlayCases(dijkstra.getReachableCases(point));
                            game.setOverlayType(OverlayType.MOVE);
                            menuManagerInstance.clearNonPersistent();

                        }
                        else if (action == UnitAction.ATTACK || action == UnitAction.RANGED_ATTACK) {

                            Logger.getInstanceActionHandler().log("Action = Attack or RangedAttack");

                            if (currentCase.hasUnit()) {
                                Logger.getInstanceActionHandler().log("Case has unit");
                                List<Weapon> weapons = unit.getWeapons().stream().filter(w -> {
                                    if (action == UnitAction.ATTACK) return w instanceof MeleeWeapon;
                                    return w instanceof RangedWeapon;
                                }).collect(Collectors.toList());

                                final int minRange = weapons.stream().mapToInt(Weapon::getMinReach).min().orElse(0);

                                final int maxRange = weapons.stream().mapToInt(Weapon::getMaxReach).max().orElse(0);

                                List<Case> casesAround = grid.getCasesAround(x, y, minRange, maxRange);

                                game.setOverlayCases(new HashSet<>(casesAround));
                                game.setOverlayType(OverlayType.WEAPON);

                                casesAround = casesAround.stream().filter(c -> c.hasUnit()).filter(c -> unit.isDistanceReachable(c.distance(currentCase))).filter(c -> unit.canAttack(c.getUnit())).collect(Collectors.toList());

                                List<Unit> unitsAround = grid.getUnitsAround(casesAround);

                                if (unitsAround.size() == 1) {

                                    Logger.getInstanceActionHandler().log("Case : one unit around");

                                    System.out.println("OK: There is one unit in range");
                                    if (unit.canAttack(unitsAround.get(0))) {

                                        unit.attack(unitsAround.get(0));
                                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                                        menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);
                                        unit.setPlayed(true);
                                        grid.garbageUnit();
                                    }

                                }
                                else if (unitsAround.size() >= 2) {
                                    Logger.getInstanceActionHandler().log("Case : more than one unit around");
                                    System.out.println("OK: There are multiple units in range");
                                    this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);
                                    menuManagerInstance.getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                                }
                                else System.out.println("Warn: Not unit in range");
                                Logger.getInstanceActionHandler().log("No unit in range");
                            }

                        }

                        else if (action == UnitAction.CAPTURE) {

                            Logger.getInstanceActionHandler().log("Action = Capture");

                            if (currentCase.getTerrain() instanceof Property) {

                                Player.Type propertyOwner = ((Property) currentCase.getTerrain()).getOwner();
                                currentUnit.capture((Property) currentCase.getTerrain());

                                unit.setPlayed(true);

                                if (currentCase.getTerrain() instanceof HQ) {

                                    Logger.getInstanceActionHandler().log("It was an HQ!");

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

                        }
                        else if (action == UnitAction.WAIT) {

                            unit.setPlayed(true);
                            this.instance.setGameState(GameState.PLAYING_SELECTING);
                            menuManagerInstance.removeMenu(MenuModel.UNIT_ACTION_MENU);

                        }
                        else if (action == UnitAction.GET_IN) {

                            List<Case> adjacentCase = grid.getAdjacentCases(game.getSelectedCase());
                            List<Unit> adjacentUnit = grid.getUnitsAround(adjacentCase);

                            List<Transport> adjacentTransport = adjacentUnit.stream().filter(u -> u instanceof Transport).filter(u -> u.getOwner() == currentPlayer.getType()).map(u -> (Transport) u).filter(u -> !((Transport) u).isCarryingUnit()).collect(Collectors.toList());

                            if (adjacentTransport.size() == 1) {

//                                adjacentTransport.get(0).setCarriedUnit(unit);
                                game.getSelectedCase().setUnit(null);
                                this.instance.setGameState(GameState.PLAYING_SELECTING);
                                menuManagerInstance.clearNonPersistent();

                            }
                            else if (adjacentTransport.size() >= 2) {

                                game.setSelectedCase(currentCase);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_TRANSPORT);
                                menuManagerInstance.clearNonPersistent();

                            }

                        }
                        else if (action == UnitAction.DROP_UNIT) {

                            List<Case> adjacentCase = grid.getAdjacentCases(currentCase).stream().filter(c -> !c.hasUnit()).collect(Collectors.toList());

                            if (adjacentCase.size() == 1) {

//                                adjacentCase.get(0).setUnit(((Transport) unit).getCarriedUnit());
                                this.instance.setGameState(GameState.PLAYING_SELECTING);

                                menuManagerInstance.clearNonPersistent();
//                                ((Transport) unit).setCarriedUnit(null);


                            }
                            else if (adjacentCase.size() >= 2) {

                                game.setSelectedCase(currentCase);
                                this.instance.setGameState(GameState.PLAYING_SELECTING_DROP_ZONE);
                                menuManagerInstance.clearNonPersistent();
                                game.setOverlayType(OverlayType.MISC);
                                game.setOverlayCases(new HashSet<>(adjacentCase));

                            }

                        }
                        else if (action == UnitAction.SUPPLY) {

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
                            }
                            else System.out.println("Warn: No unit to reload");
                            Logger.getInstanceActionHandler().log("Warn: No unit to reload");

                        }
                        else {
                            System.out.println("Warn: Unknown action");
                            Logger.getInstanceActionHandler().log("Warn: Unknown action");
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

                    Case source = selectedCase;
                    boolean trapped = game.getMovement().pathTrapped(source.getUnit());
                    Case destination = game.getMovement().getMovementTail();

                    if (!destination.hasUnit()) {

                        Unit u = source.getUnit();
                        u.setMoved(true);

                        if (u instanceof Submarine && ((Submarine) u).isUnderwater()) {
                            u.setEnergy(u.getEnergy() - game.getMovement().getCost(u, game.getWeather()) * 2);
                        }
                        else {
                            u.setEnergy(u.getEnergy() - game.getMovement().getCost(u, game.getWeather()));
                        }

                        source.setUnit(null);


                        MiniWars.getInstance().setGameState(GameState.PLAYING_RENDERING_MOVING_UNIT);
                        MovementAnimation animation = new MovementAnimation(u, game.getMovement());

                        rendererInstance.addMovementAnimation(animation);

                        // boolean piégé
                        animation.waitUntilFinished();
                        System.out.println("trapped: " + trapped);
                        // dire que l'unité est piégé

                        destination.setUnit(u);
                        grid.moveUnit(source, destination, game.getMovement());

                        game.setSelectedCase(destination);
                        System.out.println("selected case: " + selectedCase);

                        game.clearOverlayCases();
                        this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                        OptionSelector<UnitAction> actions = destination.getUnit().getAvailableActions(destination, grid);
                        menuManagerInstance.addMenu(new UnitActionMenu(actions));

                    }

                    return true;
                }

                case PLAYING_SELECTING_DROP_ZONE: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;

                    if (!currentCase.hasUnit() && currentCase.isAdjacent(selectedCase)) {

//                        Unit carriedUnit = ((Transport) game.getSelectedCase().getUnit()).getCarriedUnit();

//                        currentCase.setUnit(carriedUnit);
//                        ((Transport) game.getSelectedCase().getUnit()).setCarriedUnit(null);
                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        game.clearOverlayCases();

                    }

                    return true;

                }

                case PLAYING_SELECTING_TRANSPORT: {

                    if (cursor == null) break;
                    if (grid == null) break;
                    if (currentCase == null) break;

                    if (currentCase.isAdjacent(selectedCase)) {

                        if (currentUnit instanceof Transport) {

                            if (currentUnit.getOwner() == currentPlayer.getType()) {

                                if (!((Transport) currentUnit).isCarryingUnit()) {

//                                    ((Transport) transportUnit).setCarriedUnit(game.getSelectedCase().getUnit());
                                    selectedCase.setUnit(null);
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);

                                }

                            }

                        }

                    }

                }

                case PLAYING_SELECTING_FACTORY_UNIT: {

                    final FactoryActionMenu factoryActionMenu = (FactoryActionMenu) menuManagerInstance.getMenu(MenuModel.FACTORY_ACTION_MENU);
                    final UnitType unitToCreate = factoryActionMenu.getSelectedOption().getValue();
                    final Unit newUnit = unitToCreate.newInstance(currentPlayer.getType());
//                    newUnit.setPlayed(true);
//todo remettre ct pour un dbug
                    if(currentCase.getTerrain() instanceof Port) {

                        Case c = grid.getAdjacentCases(currentCase).stream().filter(cs -> cs.getTerrain() instanceof Water && !cs.hasUnit()).findFirst().orElse(null);
                        if(c != null) {

                            c.setUnit(newUnit);

                        } else System.out.println("ERROR! Impossible de creer l'unite navale!");

                    }
                    else {
                        currentCase.setUnit(newUnit);
                    }

                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                    menuManagerInstance.clearNonPersistent();

                }

            }
        }
        catch (Exception e) {
            Logger.getInstanceActionHandler().write(e);
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

        Logger.getInstanceActionHandler().log("Entering ActionHandler#escape");

        final Game game = this.instance.getCurrentGame();

        try {

            switch (gameState) {

                case MENU_MAP_SELECTION:
                    Logger.getInstanceActionHandler().log("escape@MENU_MAP_SELECTION");
                    this.instance.setGameState(GameState.MENU_TITLE_SCREEN);
                    MenuManager.getInstance().getMenu(MenuModel.MAIN_MENU).setVisible(true);
                    MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
                    break;

                case PLAYING_SELECTING:
                    Logger.getInstanceActionHandler().log("escape@PLAYING_SELECTING");
                    this.instance.setGameState(GameState.MENU_PAUSE);
                    break;

                case PLAYING_SELECTING_DROP_ZONE:
                case PLAYING_SELECTING_TRANSPORT:
                case MENU_PAUSE:
                    Logger.getInstanceActionHandler().log("escape@MENU_PAUSE");
                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                    break;

                default:
                    Logger.getInstanceActionHandler().log("escape@default");
                    instance.setGameState(GameState.PLAYING_SELECTING);
                    MenuManager.getInstance().clearNonPersistent();
                    game.resetMovement();
                    break;

            }

            game.clearOverlayCases();
        }
        catch (Exception e) {
            Logger.getInstanceActionHandler().write(e);
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

        Logger.getInstanceActionHandler().log("Entering ActionHandler#space");

        final Game game = this.instance.getCurrentGame();
        final Grid grid = this.instance.isPlaying() ? game.getGrid() : null;
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;
        final Case currentCase = grid != null ? grid.getCase(cursor.getCoordinate().getX(), cursor.getCoordinate().getY()) : null;

        try {

            if (gameState == GameState.PLAYING_SELECTING) {
                Logger.getInstanceActionHandler().log("space@PLAYING_SELECTING");
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

                }
                else if (playerUnitsCases.size() != 0) {
                    cursor.setCoordinate(playerUnitsCases.get(0).getCoordinate());
                }
            }
        }
        catch (Exception e) {
            Logger.getInstanceActionHandler().write(e);
        }

        return false;

    }

    /**
     * Ajoute un mouvement (si ce dernier est possible) a la flèche de deplacement
     *
     * @param movement Le mouvement a ajouter
     */
    private void updateMovement(Runnable movement) {

        Logger.getInstanceActionHandler().log("updateMovement@default");

        try {

            final Game game = this.instance.getCurrentGame();
            final Movement move = game.getMovement();
            final Dijkstra dijkstraResult = game.getDijkstraResult();
            final Unit unit = game.getSelectedCase().getUnit();

            movement.run();

            final Coordinate destination = game.getCursor().getCoordinate();
            final Case destinationCase = game.getGrid().getCase(destination.getX(), destination.getY());

            final int point = Math.min(unit.getEnergy(), unit.getMovementPoint(game.getWeather()));

            List<Case> path = dijkstraResult.getShortestPathTo(destinationCase, point);

            if (path != null) move.setPath(path);

            game.getView().adjustOffset();

        }
        catch (Exception e) {
            Logger.getInstanceActionHandler().write(e);
        }
    }

}
