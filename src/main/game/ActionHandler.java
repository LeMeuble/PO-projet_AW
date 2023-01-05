package main.game;

import main.MiniWars;
import main.animation.MovementAnimation;
import main.control.Cursor;
import main.control.KeystrokeListener;
import main.map.Case;
import main.map.Grid;
import main.menu.ActionMenu;
import main.menu.MenuManager;
import main.menu.MenuModel;
import main.menu.model.FactoryActionMenu;
import main.menu.model.UnitActionMenu;
import main.render.Renderer;
import main.terrain.Property;
import main.terrain.type.Factory;
import main.terrain.type.HQ;
import main.unit.Transport;
import main.unit.Unit;
import main.unit.UnitAction;
import main.unit.UnitType;
import main.util.OptionSelector;

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
        return false;

    }

    public boolean keyD(GameState gameState) {

        switch (gameState) {
            case PLAYING_SELECTING:
                this.instance.getCurrentGame().nextTurn();
                break;
            case PLAYING_SELECTING_UNIT_ACTION:
                this.instance.getCurrentGame().getSelectedCase().getUnit().getWeapons().forEach(w -> {
                    System.out.println(w.getClass() + " " + w.getAmmo());
                });
                break;
        }

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

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                if (cursor == null) break;

                boolean updateDisplay = cursor.up();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                if (cursor != null) this.updateMovement(cursor::up);
                return true;
            }

            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {
                MenuManager.getInstance().getMenus().stream().filter(m -> m instanceof ActionMenu).forEach(m -> {
                    ((ActionMenu) m).previous();
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

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                if (cursor == null) break;

                boolean updateDisplay = cursor.down();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
                if (cursor != null) this.updateMovement(cursor::down);
                return true;
            }

            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION: {
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

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION: {

                Renderer.getInstance().clearBuffer();
                this.instance.getMapSelector().previous();
                MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                if (cursor == null) break;

                boolean updateDisplay = cursor.left();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
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

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION: {
                Renderer.getInstance().clearBuffer();
                this.instance.getMapSelector().next();
                MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case PLAYING_SELECTING_TARGET: {
                if (cursor == null) break;

                boolean updateDisplay = cursor.right();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;
            }

            case PLAYING_MOVING_UNIT: {
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

        final Player currentPlayer = this.instance.isPlaying() ? game.getCurrentPlayer() : null;

        final int x = cursor != null ? cursor.getCurrentX() : 0;
        final int y = cursor != null ? cursor.getCurrentY() : 0;

        final Case currentCase = grid != null ? grid.getCase(x, y) : null;

        switch (gameState) {

            case MENU_TITLE_SCREEN: {
                Renderer.getInstance().clearBuffer();
                this.instance.setGameState(GameState.MENU_MAP_SELECTION);
                MenuManager.getInstance().getMenu(MenuModel.MAIN_MENU).setVisible(false);
                MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);

                return true;
            }

            case MENU_MAP_SELECTION: {
                Renderer.getInstance().clearBuffer();
                if (this.instance.getMapSelector().getSelectedOption() != null) {
                    this.instance.newGame(this.instance.getMapSelector().getSelectedOption());
                    MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
                }
                return true;
            }

            case PLAYING_SELECTING: {

                if (cursor == null) break;
                if (grid == null) break;
                if (currentCase == null) break;
                if (currentPlayer == null) break;

                if (currentCase.hasUnit()) {

                    final Unit unit = currentCase.getUnit();

                    if (unit.getOwner() == currentPlayer.getType()) {

                        if (!unit.hasPlayed()) {

                            OptionSelector<UnitAction> actionSelector = unit.getAvailableActions(currentCase, grid);

                            MenuManager.getInstance().addMenu(new UnitActionMenu(actionSelector));
                            game.setSelectedCase(currentCase);

                            this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                        }
                        else System.out.println("Warn: This unit has already played");
                    }
                    else System.out.println("Warn: Unit not owned by current player");
                }
                else if (currentCase.getTerrain() instanceof Factory) {

                    if (Factory.canCreateUnit(currentCase, currentPlayer)) {

                        MenuManager.getInstance().addMenu(new FactoryActionMenu(UnitType.asSelector(currentPlayer.getMoney())));
                        game.setSelectedCase(currentCase);

                        this.instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_UNIT);

                    }
                    else System.out.println("Warn: There is already a unit here / u can't!");
                }
                else System.out.println("Warn: There is nothing to do here");

                return true;
            }

            case PLAYING_SELECTING_UNIT_ACTION: {

                Renderer.getInstance().clearBuffer();

                UnitActionMenu menu = (UnitActionMenu) MenuManager.getInstance().getMenu(MenuModel.UNIT_ACTION_MENU);

                if (menu != null) {

                    UnitAction action = ((UnitActionMenu) menu).getSelectedOption();
                    Unit unit = game.getSelectedCase().getUnit();

                    if (action == UnitAction.MOVE) {

                        game.setSelectedCase(currentCase);
                        game.setMovement(new Movement(currentCase));
                        MenuManager.getInstance().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                        this.instance.setGameState(GameState.PLAYING_MOVING_UNIT);

                    }
                    else if (action == UnitAction.ATTACK || action == UnitAction.RANGED_ATTACK) {

                        if (currentCase.hasUnit()) {

                            Unit current = currentCase.getUnit();
                            List<Case> casesAround = game.getGrid().getCasesAround(cursor.getCurrentX(), cursor.getCurrentY(), current.getMinWeaponRange(), current.getMaxWeaponRange());

                            casesAround = casesAround.stream()
                                    .filter(c -> current.isDistanceReachable(c.distance(currentCase)))
                                    .filter(c -> current.canAttack(currentCase.getUnit()))
                                    .collect(Collectors.toList());

                            List<Unit> unitsAround = game.getGrid().getUnitsAround(casesAround);

                            System.out.println(unitsAround);

                            if (unitsAround.size() == 1) {

                                System.out.println("OK: There is one unit in range");
                                if (current.canAttack(unitsAround.get(0))) {

                                    current.attack(unitsAround.get(0));
                                    this.instance.setGameState(GameState.PLAYING_SELECTING);
                                    MenuManager.getInstance().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);
                                    unit.setPlayed(true);
                                    game.getGrid().garbageUnit();
                                }

                            }

                            else if (unitsAround.size() >= 2) {

                                System.out.println("OK: There are multiple units in range");
                                this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);
                                MenuManager.getInstance().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                            }
                            else System.out.println("Warn: Not unit in range");

                        }

                    }

                    else if (action == UnitAction.CAPTURE) {

                        if (currentCase.getTerrain() instanceof Property) {

                            Player.Type propertyOwner = ((Property) currentCase.getTerrain()).getOwner();
                            currentCase.getUnit().capture((Property) currentCase.getTerrain());

                            unit.setPlayed(true);

                            if (currentCase.getTerrain() instanceof HQ) {

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
                        MenuManager.getInstance().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                    }
                    else if (action == UnitAction.WAIT) {

                        unit.setPlayed(true);
                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        MenuManager.getInstance().removeMenu(MenuModel.UNIT_ACTION_MENU);

                    }
                    else if (action == UnitAction.GET_IN) {

                        List<Case> adjacentCase = game.getGrid().getAdjacentCases(currentCase);
                        List<Unit> adjacentUnit = game.getGrid().getUnitsAround(adjacentCase);

                        List<Transport> adjacentTransport = adjacentUnit.stream()
                                .filter(u -> u instanceof Transport)
                                .filter(u -> u.getOwner() == currentPlayer.getType())
                                .map(u -> (Transport) u)
                                .filter(u -> !((Transport) u).isCarryingUnit())
                                .collect(Collectors.toList());

                        if (adjacentTransport.size() == 1) {

                            adjacentTransport.get(0).setCarriedUnit(unit);
                            currentCase.setUnit(null);

                        }
                        else if (adjacentTransport.size() >= 2) {

                            game.setSelectedCase(currentCase);
                            this.instance.setGameState(GameState.PLAYING_SELECTING_TRANSPORT);

                        }

                    }
                    else if (action == UnitAction.DROP_UNIT) {

                        List<Case> adjacentCase = game.getGrid().getAdjacentCases(currentCase).stream().filter(c -> !c.hasUnit()).collect(Collectors.toList());

                        if (adjacentCase.size() == 1) {

                            adjacentCase.get(0).setUnit(((Transport) unit).getCarriedUnit());
                            ((Transport) unit).setCarriedUnit(null);

                        }
                        else if (adjacentCase.size() >= 2) {

                            game.setSelectedCase(currentCase);
                            this.instance.setGameState(GameState.PLAYING_SELECTING_DROP_ZONE);

                        }

                    }
                    else if (action == UnitAction.SUPPLY) {

                        List<Case> adjacentCase = game.getGrid().getAdjacentCases(currentCase);
                        List<Unit> adjacentUnits = game.getGrid().getUnitsAround(adjacentCase);

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
                        }
                        else System.out.println("Warn: No unit to supply");

                    }
                    else System.out.println("Warn: Unknown action");
                }

                return true;
            }
            case PLAYING_SELECTING_TARGET: {

                if (cursor == null) break;
                if (grid == null) break;

                if (currentCase.hasUnit()) {

                    Unit current = game.getSelectedCase().getUnit();

                    if (current.canAttack(currentCase.getUnit())) {

                        current.attack(currentCase.getUnit());
                        this.instance.setGameState(GameState.PLAYING_SELECTING);
                        game.getGrid().garbageUnit();

                    }


                }

                return true;
            }

            case PLAYING_MOVING_UNIT: {

                Case source = game.getSelectedCase();
                Case destination = game.getMovement().getMovementTail();

                if (!destination.hasUnit()) {

                    grid.moveUnit(source, destination);
                    destination.getUnit().setMoved(true);

                    // Animation
                    MovementAnimation animation = new MovementAnimation(source.getUnit(), game.getMovement());
                    game.resetMovement();
                    // Wait for animation to finish

                    game.setSelectedCase(destination);
                    this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                    OptionSelector<UnitAction> actions = destination.getUnit().getAvailableActions(destination, grid);

                    MenuManager.getInstance().addMenu(new UnitActionMenu(actions));
                    this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                    //TODO: Animation de mouvement

                }

                return true;
            }

            case PLAYING_SELECTING_DROP_ZONE: {

                if (cursor == null) break;
                if (grid == null) break;
                if (currentCase == null) break;

                if (!currentCase.hasUnit() && currentCase.isAdjacent(game.getSelectedCase())) {

                    Unit carriedUnit = ((Transport) game.getSelectedCase().getUnit()).getCarriedUnit();

                    currentCase.setUnit(carriedUnit);
                    ((Transport) game.getSelectedCase().getUnit()).setCarriedUnit(null);

                }

                return true;

            }

            case PLAYING_SELECTING_TRANSPORT: {

                if (cursor == null) break;
                if (grid == null) break;
                if (currentCase == null) break;

                if (currentCase.isAdjacent(game.getSelectedCase())) {

                    Unit transportUnit = currentCase.getUnit();

                    if (transportUnit instanceof Transport) {

                        if (transportUnit.getOwner() == currentPlayer.getType()) {

                            if (!((Transport) transportUnit).isCarryingUnit()) {

                                ((Transport) transportUnit).setCarriedUnit(game.getSelectedCase().getUnit());
                                game.getSelectedCase().setUnit(null);

                            }

                        }

                    }

                }

            }

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

        final Game game = this.instance.getCurrentGame();

        switch (gameState) {

            case MENU_MAP_SELECTION:
                this.instance.setGameState(GameState.MENU_TITLE_SCREEN);
                MenuManager.getInstance().getMenu(MenuModel.MAIN_MENU).setVisible(true);
                MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
                break;

            case PLAYING_SELECTING:
                this.instance.setGameState(GameState.MENU_PAUSE);
                break;

            case PLAYING_SELECTING_DROP_ZONE:
            case PLAYING_SELECTING_TRANSPORT:
            case MENU_PAUSE:
                this.instance.setGameState(GameState.PLAYING_SELECTING);
                break;

            case PLAYING_MOVING_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION:
            case PLAYING_SELECTING_FACTORY_UNIT:
                Renderer.getInstance().clearBuffer();
                instance.setGameState(GameState.PLAYING_SELECTING);
                MenuManager.getInstance().clearNonPersistent();
                game.resetMovement();
                break;

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

        final Game game = this.instance.getCurrentGame();
        final Grid grid = this.instance.isPlaying() ? game.getGrid() : null;
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;
        final Case currentCase = grid != null ? grid.getCase(cursor.getCurrentX(), cursor.getCurrentY()) : null;

        switch (gameState) {

            case PLAYING_SELECTING:

                if (grid == null) break;

                List<Case> playerUnitsCases = grid.getCases()
                        .stream()
                        .filter(c -> c.hasUnit() && c.getUnit().getOwner() == game.getCurrentPlayer().getType())
                        .filter(c -> !c.getUnit().hasPlayed())
                        .collect(Collectors.toList());

                if(playerUnitsCases.contains(currentCase)) {

                    int index = playerUnitsCases.indexOf(currentCase);
                    index = (index + 1) % playerUnitsCases.size();

                    cursor.setCurrentX(playerUnitsCases.get(index).getX());
                    cursor.setCurrentY(playerUnitsCases.get(index).getY());

                } else if(playerUnitsCases.size() != 0){
                    cursor.setCurrentX(playerUnitsCases.get(0).getX());
                    cursor.setCurrentY(playerUnitsCases.get(0).getY());
                }

        }

        return false;

    }

    /**
     * Ajoute un mouvement (si ce dernier est possible) a la flèche de deplacement
     *
     * @param movement Le mouvement a ajouter
     */
    private void updateMovement(Runnable movement) {

        final Game game = this.instance.getCurrentGame();
        final Movement move = game.getMovement();

        final int x = game.getCursor().getCurrentX();
        final int y = game.getCursor().getCurrentY();

        movement.run();

        int newX = game.getCursor().getCurrentX();
        int newY = game.getCursor().getCurrentY();

        // Simulation du mouvement
        game.getMovement().update(game.getGrid().getCase(newX, newY));

        Case destination = move.getMovementTail();
        Unit currentUnit = game.getSelectedCase().getUnit();

        if (game.getMovement().isEmpty()) return;

        if (currentUnit.canMoveTo(destination, game.getWeather())) {

            // Verifie si l'unite a asser de points (en tenant en compte de la meteo) pour se deplacer sur cette case
            if (move.getCost(currentUnit, game.getWeather()) <= currentUnit.getMovementPoint(game.getWeather())) {

                game.getView().adjustOffset();
                return;

            }
        }

        // Annule le mouvement si une des conditions n'est pas respectee
        game.getCursor().setCurrentX(x);
        game.getCursor().setCurrentY(y);
        game.getMovement().goBack();

    }

}
