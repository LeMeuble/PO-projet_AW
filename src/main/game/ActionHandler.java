package main.game;

import main.MiniWars;
import main.animation.MovementAnimation;
import main.control.Cursor;
import main.control.KeystrokeListener;
import main.map.Case;
import main.map.Grid;
import main.menu.ActionMenu;
import main.menu.Menu;
import main.menu.MenuModel;
import main.menu.model.FactoryActionMenu;
import main.menu.model.UnitActionMenu;
import main.terrain.Property;
import main.terrain.type.Factory;
import main.terrain.type.HQ;
import main.unit.Unit;
import main.unit.UnitAction;
import main.unit.UnitType;
import main.util.OptionSelector;

import java.util.LinkedList;
import java.util.List;

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
            case KEY_D:
                return this.keyD(gameState);

        }
        return false;

    }

    public boolean keyD(GameState gameState) {

        this.instance.getCurrentGame().nextPlayer();
        this.instance.getCurrentGame().nextTurn();

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
                Menu unitMenu = this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU);
                Menu factoryMenu = this.instance.getMenuManager().getMenu(MenuModel.FACTORY_ACTION_MENU);
                if (unitMenu != null) {
                    ((ActionMenu<?>) unitMenu).previous();
                    unitMenu.needsRefresh(true);
                }
                if (factoryMenu != null) {
                    ((ActionMenu<?>) factoryMenu).previous();
                    factoryMenu.needsRefresh(true);
                }
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
                Menu unitMenu = this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU);
                Menu factoryMenu = this.instance.getMenuManager().getMenu(MenuModel.FACTORY_ACTION_MENU);
                if (unitMenu != null) {
                    ((ActionMenu<?>) unitMenu).next();
                    unitMenu.needsRefresh(true);
                }
                if (factoryMenu != null) {
                    ((ActionMenu<?>) factoryMenu).next();
                    factoryMenu.needsRefresh(true);
                }
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

                this.instance.getRenderer().clearBuffer();
                this.instance.getMapSelector().previous();
                this.instance.getMenuManager().getMenu(MenuModel.MAP_SELECTION_MENU).needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
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
                this.instance.getRenderer().clearBuffer();
                this.instance.getMapSelector().next();
                this.instance.getMenuManager().getMenu(MenuModel.MAP_SELECTION_MENU).needsRefresh(true);
                return true;
            }

            case PLAYING_SELECTING:
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
                this.instance.getRenderer().clearBuffer();
                this.instance.setGameState(GameState.MENU_MAP_SELECTION);
                this.instance.getMenuManager().getMenu(MenuModel.MAIN_MENU).setVisible(false);
                this.instance.getMenuManager().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);
                return true;
            }

            case MENU_MAP_SELECTION: {
                this.instance.getRenderer().clearBuffer();
                if (this.instance.getMapSelector().getSelectedOption() != null) {
                    this.instance.newGame(this.instance.getMapSelector().getSelectedOption());
                    this.instance.getMenuManager().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
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

                            this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                            OptionSelector<UnitAction> actionSelector = unit.getAvailableActions(currentCase, grid);

                            this.instance.getMenuManager().addMenu(new UnitActionMenu(actionSelector));
                            game.setSelectedCase(currentCase);

                        }
                        else System.out.println("Warn: Unit not owned by current player");

                    }
                    else if (currentCase.getTerrain() instanceof Factory) {

                        if (Factory.canCreateUnit(currentCase, currentPlayer)) {

                            OptionSelector<UnitType> factorySelector = new OptionSelector<>();

                            for (UnitType type : UnitType.values()) {
                                boolean isAvailable = type.getPrice() <= currentPlayer.getMoney();
                                factorySelector.addOption(type, isAvailable);
                            }

                            this.instance.getMenuManager().addMenu(new FactoryActionMenu(factorySelector));
                            this.instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_UNIT);
                            game.setSelectedCase(currentCase);

                        }
                        else System.out.println("There is already a unit here !");
                    }
                    else System.out.println("Warn: There is nothing to do here");
                }
                return true;
            }

            case PLAYING_SELECTING_UNIT_ACTION:

                Menu menu = this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU);
                //Todo better code:

                if (menu instanceof UnitActionMenu) {

                    UnitAction action = ((UnitActionMenu) menu).getSelectedOption();

                    if (action == UnitAction.MOVE) {

                        this.instance.setGameState(GameState.PLAYING_MOVING_UNIT);
                        game.setSelectedCase(currentCase);
                        game.setMovement(new Movement(currentCase));
                        this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                    }
                    else if (action == UnitAction.ATTACK) {

                        if (game.getSelectedCase().hasUnit()) {

                            Unit current = game.getSelectedCase().getUnit();
                            List<Case> casesAround = game.getGrid().getCasesAround(cursor.getCurrentX(), cursor.getCurrentY(), current.getMinWeaponRange(), current.getMaxWeaponRange());
                            List<Unit> unitsAround = game.getGrid().getUnitsAround(casesAround);

                            System.out.println(unitsAround);

                            if (unitsAround.size() == 0) {

                                System.out.println("No units in range");

                            }

                            if (unitsAround.size() == 1) {

                                System.out.println("There is one unit in range");
                                current.attack(unitsAround.get(0));
                                this.instance.setGameState(GameState.PLAYING_SELECTING);
                                this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                            }

                            if (unitsAround.size() >= 2) {

                                System.out.println("There are multiple units in range");
                                this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);
                                this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                            }

                        }

                    }

                    else if (action == UnitAction.CAPTURE) {

                        if (currentCase.getTerrain() instanceof Property) {

                            Player.Type propertyOwner = ((Property) currentCase.getTerrain()).getOwner();
                            game.getSelectedCase().getUnit().capture((Property) currentCase.getTerrain());

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
                        this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU).setVisible(false);

                    }
                }

                return true;
            case PLAYING_SELECTING_TARGET: {

                if (cursor == null) break;
                if (grid == null) break;

                if (currentCase.hasUnit()) {

                    Unit current = game.getSelectedCase().getUnit();
                    current.attack(currentCase.getUnit());

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

                    this.instance.getMenuManager().addMenu(new UnitActionMenu(actions));
                    this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                    // Todo : Anim de mouvement

//                    Exemple code:
//                    MovementAnimation animation = new MovementAnimation(unit, movement);
//                    // animation.next();
//                    this.instance.getRenderer().addAnimation(animation);
//                    animation.waitUntilFinished(); // blocant
//                    // gamestae = PLAYING_SELECTING
//                    for(Case c : game.getMovement().getCases()) {
//
//                        c.setUnit(courante.getUnit());
//                        courante.setUnit(null);
//                        courante = c;
//                        this.instance.getRenderer().render();
//
                }

                return true;
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

        this.instance.getRenderer().clearBuffer();

        switch (gameState) {

            case MENU_MAP_SELECTION:
                this.instance.setGameState(GameState.MENU_TITLE_SCREEN);
                break;

            case PLAYING_SELECTING:
                this.instance.setGameState(GameState.MENU_PAUSE);
                break;

            case MENU_PAUSE:
                this.instance.setGameState(GameState.PLAYING_SELECTING);
                break;

            case PLAYING_SELECTING_UNIT_ACTION:
                this.instance.setGameState(GameState.PLAYING_SELECTING);
                this.instance.getMenuManager().removeMenu(MenuModel.UNIT_ACTION_MENU);
                break;

            case PLAYING_SELECTING_FACTORY_UNIT:
                instance.setGameState(GameState.PLAYING_SELECTING);
                this.instance.getMenuManager().removeMenu(MenuModel.FACTORY_ACTION_MENU);
                break;

            case PLAYING_MOVING_UNIT:
                instance.setGameState(GameState.PLAYING_SELECTING);
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

        switch (gameState) {

            case PLAYING_SELECTING:

                if (grid == null) break;

                List<Case> casesWithPlayableUnit = new LinkedList<>();

                for (Case c : grid) {

                    if (c.hasUnit() && c.getUnit().getOwner() == game.getCurrentPlayer().getType()) {
                        casesWithPlayableUnit.add(c);
                    }

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

        // Ne peut pas passer au-dessus d'une unite sauf s'il s'agit d'une unite volante
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
