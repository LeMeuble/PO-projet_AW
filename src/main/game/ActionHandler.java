package main.game;

import main.MiniWars;
import main.animation.MovementAnimation;
import main.control.Cursor;
import main.control.KeystrokeListener;
import main.map.Case;
import main.map.Grid;
import main.menu.Menu;
import main.menu.MenuModel;
import main.menu.model.FactoryActionMenu;
import main.menu.model.UnitActionMenu;
import main.terrain.Property;
import main.terrain.Terrain;
import main.terrain.type.Factory;
import main.terrain.type.HQ;
import main.unit.Unit;
import main.unit.UnitAction;
import main.unit.UnitType;
import main.util.OptionSelector;

import java.util.*;

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
            case PLAYING_SELECTING_TARGET:
                if (cursor == null) return false;

                boolean updateDisplay = cursor.up();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::up);
                return true;

            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION:
                Menu unitMenu = this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU);
                Menu factoryMenu = this.instance.getMenuManager().getMenu(MenuModel.FACTORY_ACTION_MENU);
                if (unitMenu instanceof UnitActionMenu) {
                    UnitActionMenu unitActionMenu = (UnitActionMenu) unitMenu;
                    unitActionMenu.previous();
                    unitActionMenu.needsRefresh(true);
                }
                if(factoryMenu instanceof FactoryActionMenu) {
                    FactoryActionMenu factoryActionMenu = (FactoryActionMenu) factoryMenu;
                    factoryActionMenu.previous();
                    factoryActionMenu.needsRefresh(true);
                    System.out.println("UP");
                }
                return true;

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
            case PLAYING_SELECTING_TARGET:
                if (cursor == null) return false;

                boolean updateDisplay = cursor.down();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::down);
                return true;

            case PLAYING_SELECTING_FACTORY_UNIT:
            case PLAYING_SELECTING_UNIT_ACTION:
                Menu unitMenu = this.instance.getMenuManager().getMenu(MenuModel.UNIT_ACTION_MENU);
                Menu factoryMenu = this.instance.getMenuManager().getMenu(MenuModel.FACTORY_ACTION_MENU);
                if (unitMenu instanceof UnitActionMenu) {
                    UnitActionMenu unitActionMenu = (UnitActionMenu) unitMenu;
                    unitActionMenu.next();
                    unitActionMenu.needsRefresh(true);
                }
                if(factoryMenu instanceof FactoryActionMenu) {
                    FactoryActionMenu factoryActionMenu = (FactoryActionMenu) factoryMenu;
                    factoryActionMenu.next();
                    factoryActionMenu.needsRefresh(true);
                }
                System.out.println("DOWN");
                return true;

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

            case MENU_MAP_SELECTION:

                this.instance.getRenderer().clearBuffer();
                this.instance.getMapSelector().previous();
                this.instance.getMenuManager()
                        .getMenu(MenuModel.MAP_SELECTION_MENU)
                        .needsRefresh(true);
                return true;

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_TARGET:
                if (cursor == null) return false;

                boolean updateDisplay = cursor.left();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::left);
                return true;

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

            case MENU_MAP_SELECTION:
                this.instance.getRenderer().clearBuffer();
                this.instance.getMapSelector().next();
                this.instance.getMenuManager()
                        .getMenu(MenuModel.MAP_SELECTION_MENU)
                        .needsRefresh(true);
                return true;

            case PLAYING_SELECTING:
            case PLAYING_SELECTING_TARGET:
                if (cursor == null) return false;

                boolean updateDisplay = cursor.right();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::right);
                return true;

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

            case MENU_TITLE_SCREEN:
                this.instance.getRenderer().clearBuffer();
                this.instance.setGameState(GameState.MENU_MAP_SELECTION);
                this.instance.getMenuManager().getMenu(MenuModel.MAIN_MENU).setVisible(false);
                this.instance.getMenuManager().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(true);
                return true;

            case MENU_MAP_SELECTION:
                this.instance.getRenderer().clearBuffer();
                if (this.instance.getMapSelector().getSelectedOption() != null) {
                    this.instance.newGame(this.instance.getMapSelector().getSelectedOption());
                    this.instance.getMenuManager().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);
                }
                return true;

            case PLAYING_SELECTING:

                if (cursor == null) break;
                if (grid == null) break;

                if (currentCase.hasUnit()) {

                    if (currentCase.getUnit().getOwner() == currentPlayer.getType()) {

                        //TODO: Checker si l'unite a deja joue

                        this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                        OptionSelector<UnitAction> actionSelector = new OptionSelector<UnitAction>()
                                .addOption(UnitAction.MOVE)
                                .addOption(UnitAction.ATTACK)
                                .addOption(UnitAction.CAPTURE);

                        this.instance.getMenuManager().addMenu(MenuModel.UNIT_ACTION_MENU, new UnitActionMenu(actionSelector));
                        game.setSelectedCase(currentCase);

                    }
                    else System.out.println("Warn: Unit not owned by current player");

                }
                else if (currentCase.getTerrain() instanceof Factory) {

                    Property terrain = (Property) currentCase.getTerrain();

                    if (terrain.getOwner() == currentPlayer.getType()) {

                        if(currentCase.getUnit() == null) {

                            OptionSelector<UnitType> factorySelector = new OptionSelector<>();

                            for(UnitType type : UnitType.values()) {
                                boolean isAvailable = type.getPrice() <= currentPlayer.getMoney();
                                factorySelector.addOption(type, isAvailable);
                            }

                            this.instance.getMenuManager().addMenu(MenuModel.FACTORY_ACTION_MENU, new FactoryActionMenu(factorySelector));
                            this.instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_UNIT);
                            game.setSelectedCase(currentCase);

                            return true;

                        }

                        else{
                            System.out.println("There is already a unit here !");
                        }

                    }
                    else System.out.println("Warn: This is not your factory");

                }
                else System.out.println("Warn: There is nothing to do here");

                return true;

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
                            List<Case> casesAround = game.getGrid().getCasesAround(cursor.getCurrentX(), cursor.getCurrentY(), current.getMinReach(), current.getMaxReach());
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

                                    if(game.hasWinner()) {

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
            case PLAYING_SELECTING_TARGET:

                if (cursor == null) break;
                if (grid == null) break;

                if (currentCase.hasUnit()) {

                    Unit current = game.getSelectedCase().getUnit();
                    current.attack(currentCase.getUnit());

                }

                return true;

            case PLAYING_MOVING_UNIT:

                Case source = game.getSelectedCase();
                Case destination = game.getMovement().getMovementTail();

                if (!destination.hasUnit()) {

                    this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                    MovementAnimation animation = new MovementAnimation(destination.getUnit(), game.getMovement());
                    System.out.println("Moving unit from " + source + " to " + destination);
                    //animation.waitUntilFinished(); // TODO: Animation in its own thread
                    System.out.println("Unit moved");

                    game.setSelectedCase(destination);
                    game.resetMovement();
                    destination.setUnit(source.getUnit());
                    source.setUnit(null);
                    destination.getUnit().setHasMoved(true);

                    // TODO
                    if (destination.getUnit().getType() != UnitType.HELICOPTER && destination.getUnit().getType() != UnitType.BOMBER) {

                        if (destination.getTerrain() instanceof Property) {

                            if (((Property) destination.getTerrain()).getOwner() != currentPlayer.getType()) {

                                OptionSelector<UnitAction> actionSelector = new OptionSelector<UnitAction>()
                                        .addOption(UnitAction.MOVE)
                                        .addOption(UnitAction.ATTACK)
                                        .addOption(UnitAction.CAPTURE);

                                this.instance.getMenuManager().addMenu(MenuModel.UNIT_ACTION_MENU, new UnitActionMenu(actionSelector));
                                this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);



                            }

                        }

                    }
                }


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
//                    }

                return true;
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
                return true;

            case PLAYING_SELECTING:
                this.instance.setGameState(GameState.MENU_PAUSE);
                return true;

            case MENU_PAUSE:
                this.instance.setGameState(GameState.PLAYING_SELECTING);
                return true;

            case PLAYING_SELECTING_UNIT_ACTION:
                this.instance.setGameState(GameState.PLAYING_SELECTING);
                this.instance.getMenuManager().removeMenu(MenuModel.UNIT_ACTION_MENU);
                return true;

            case PLAYING_SELECTING_FACTORY_UNIT:
                instance.setGameState(GameState.PLAYING_SELECTING);
                this.instance.getMenuManager().removeMenu(MenuModel.FACTORY_ACTION_MENU);
                return true;

            case PLAYING_MOVING_UNIT:
                instance.setGameState(GameState.PLAYING_SELECTING);
                game.resetMovement();
                return true;

        }

        return false;

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

                if(grid==null) break;

                List<Case> casesWithPlayableUnit = new LinkedList<>();

                for(Case c : grid) {

                    if(c.hasUnit() && c.getUnit().getOwner() == game.getCurrentPlayer().getType()) {
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

        Game game = this.instance.getCurrentGame();
        Movement move = game.getMovement();

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentY();

        movement.run();

        int newX = game.getCursor().getCurrentX();
        int newY = game.getCursor().getCurrentY();

        // Simulation du mouvement
        game.getMovement().update(game.getGrid().getCase(newX, newY));

        Case destination = move.getMovementTail();
        Unit currentUnit = game.getSelectedCase().getUnit();

        if (game.getMovement().isEmpty()) return;

        // TODO : Au lieu de verifier si on peut passer, cancel seulement si on peut pas passer (aka : inverser les conditions)
        // Ne peut pas passer au-dessus d'une unite sauf s'il s'agit d'une unite volante
        if (currentUnit.canMoveTo(destination.getTerrain(), game.getWeather())) {

            Terrain terrain = destination.getTerrain();

            // Verifie si le terrain n'est pas une propriete
            // Si la case en est une, verfie si le joueur courant en est proprietaire
            // Sinon, pas possble de passer dessus sans attaquer la propriete
            if (!destination.hasUnit() || (destination.hasUnit() && destination.getUnit().getOwner() == game.getCurrentPlayer().getType())) {

                // Verifie si l'unite a asser de points (en tenant en compte la meteo) pour se deplacer sur cette case
                if (move.getCost(currentUnit, game.getWeather()) <= currentUnit.getMovementPoint(game.getWeather())) {

                    // Verifie si la case n'a pas deja une unite
                    // Si c'est le cas, on verifie si notre unite est une unite volante, auquel cas elle peut passer par dessus

                    game.getView().adjustOffset();
                    return;

                }

            }
        }

        // Annule le mouvement si une des conditions n'est pas respectee
        game.getCursor().setCurrentX(x);
        game.getCursor().setCurrentY(y);
        game.getMovement().goBack();

    }

}
