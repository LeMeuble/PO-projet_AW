package main.controller;

import main.GameState;
import main.MiniWars;
import main.Movement;
import main.Player;
import main.map.Case;
import main.map.Game;
import main.map.Grid;
import main.menu.Menu;
import main.menu.MultiOptionMenu;
import main.menu.model.FactoryActionMenu;
import main.menu.model.UnitActionMenu;
import main.terrain.Property;
import main.terrain.Terrain;
import main.terrain.type.Factory;
import main.unit.Flying;
import main.unit.Unit;
import main.weather.Weather;

import java.util.List;

/**
 * Class prenant en charge les actions associees aux differentes touches pressees
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class KeystrokeHandler {

    private final MiniWars instance;

    /**
     * Constructeur de classe
     * @param instance Une instance du Jeu en cours
     */
    public KeystrokeHandler(MiniWars instance) {

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
            case KEY_D:
                return this.keyD(gameState);

        }
        return false;

    }

    public boolean keyD(GameState gameState) {

        this.instance.getCurrentGame().nextPlayer();
        return true;

    }


    /**
     * Gere la touche haut
     *
     * @param gameState l'etat du jeu
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean up(GameState gameState) {

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
                if (cursor == null) return false;

                boolean updateDisplay = cursor.up();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::up);
                return true;

            case PLAYING_SELECTING_UNIT_ACTION:
                Menu menu = this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);
                if (menu instanceof UnitActionMenu) {
                    UnitActionMenu unitActionMenu = (UnitActionMenu) this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);
                    unitActionMenu.previous();
                    System.out.println("Now selected: " + unitActionMenu.getSelectedOption().getName());
                    return true;
                }
                break;

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

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
                if (cursor == null) return false;

                boolean updateDisplay = cursor.down();
                updateDisplay |= game.getView().adjustOffset();

                return updateDisplay;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::down);
                return true;

            case PLAYING_SELECTING_UNIT_ACTION:
                Menu menu = this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);
                if (menu instanceof UnitActionMenu) {
                    UnitActionMenu unitActionMenu = (UnitActionMenu) menu;
                    unitActionMenu.next();
                    System.out.println("Now selected: " + unitActionMenu.getSelectedOption().getName());

                }
                break;

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

        final Game game = this.instance.getCurrentGame();
        final Cursor cursor = this.instance.isPlaying() ? game.getCursor() : null;

        switch (gameState) {

            case MENU_MAP_SELECTION:

                this.instance.getRenderer().clearBuffer();
                this.instance.getMapSelector().previous();
                this.instance.getMenuManager()
                        .getMenu(Menu.Model.MAP_SELECTION_MENU)
                        .needsRefresh(true);
                return true;

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
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
                        .getMenu(Menu.Model.MAP_SELECTION_MENU)
                        .needsRefresh(true);
                return true;

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
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
                this.instance.getMenuManager().getMenu(Menu.Model.MAIN_MENU).setVisible(false);
                this.instance.getMenuManager().getMenu(Menu.Model.MAP_SELECTION_MENU).setVisible(true);
                return true;

            case MENU_MAP_SELECTION:
                this.instance.getRenderer().clearBuffer();
                if (this.instance.getMapSelector().getSelectedMap() != null) {
                    this.instance.newGame(this.instance.getMapSelector().getSelectedMap());
                    this.instance.getMenuManager().getMenu(Menu.Model.MAP_SELECTION_MENU).setVisible(false);
                }
                return true;

            case PLAYING_SELECTING:

                if (cursor == null) break;
                if (grid == null) break;

                if (currentCase.hasUnit()) {

                    if (currentCase.getUnit().getOwner() == currentPlayer.getType()) {

                        this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                        UnitActionMenu unitActionMenu = new UnitActionMenu();
                        unitActionMenu.addOption("move", "Déplacer", !currentCase.getUnit().hasMoved());
                        unitActionMenu.addOption("attack", "Attaquer", true);
                        this.instance.getMenuManager().addMenu(Menu.Model.UNIT_ACTION_MENU, unitActionMenu);

                    } else System.out.println("Warn: Unit not owned by current player");

                } else if (currentCase.getTerrain() instanceof Factory) {

                    Property terrain = (Property) currentCase.getTerrain();

                    if (terrain.getOwner() == currentPlayer.getType()) {

                        FactoryActionMenu factoryActionMenu = new FactoryActionMenu(currentPlayer.getType());
                        this.instance.getMenuManager().addMenu(Menu.Model.FACTORY_ACTION_MENU, factoryActionMenu);
                        this.instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_UNIT);

                    } else System.out.println("Warn: This is not your factory");

                } else System.out.println("Warn: There is nothing to do here");

                return true;

            case PLAYING_SELECTING_UNIT_ACTION:

                Menu menu = this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);

                if (menu instanceof UnitActionMenu) {

                    MultiOptionMenu.Option options = ((UnitActionMenu) menu).getSelectedOption();

                    if (options.getKey().equals("move")) {

                        this.instance.setGameState(GameState.PLAYING_MOVING_UNIT);
                        game.setSelectedCase(currentCase);
                        game.setMovement(new Movement(currentCase));

                    } else if (options.getKey().equals("attack")) {

                        this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);
                        if (game.getSelectedCase().hasUnit()) {
                            Unit current = game.getSelectedCase().getUnit();
                            List<Case> casesAround = game.getGrid().getCasesAround(cursor.getCurrentX(), cursor.getCurrentY(), current.getMinReach(), current.getMaxReach());
                            List<Unit> unitsAround = game.getGrid().getUnitsAround(casesAround);

                            if (unitsAround.size() == 0) {

                                System.out.println("No units in range");
                                this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);

                            }

                            if (unitsAround.size() == 1) {

                                System.out.println("There is one unit in range");
                                current.attack(unitsAround.get(0));

                            }

                            if (unitsAround.size() >= 2) {

                                System.out.println("There are multiple units in range");
                                this.instance.setGameState(GameState.PLAYING_SELECTING_TARGET);

                            }

                        }

                    }
                }

                return true;
            case PLAYING_MOVING_UNIT:

                Case source = game.getSelectedCase();
                Case destination = game.getMovement().getDestination();

                //Todo: check si on peut s'arreter ici

                return true;
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
                this.instance.getMenuManager().removeMenu(Menu.Model.UNIT_ACTION_MENU);
                return true;

            case PLAYING_SELECTING_FACTORY_UNIT:
                instance.setGameState(GameState.PLAYING_SELECTING);
                this.instance.getMenuManager().removeMenu(Menu.Model.FACTORY_ACTION_MENU);
                return true;

            case PLAYING_MOVING_UNIT:
                instance.setGameState(GameState.PLAYING_SELECTING);
                game.cancelMovement();
                return true;

        }

        return false;

    }

    /**
     * Gere la touche espace
     *
     * @param gameState l'etat du jeu
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean space(GameState gameState) {

        return false;

    }

    /**
     * Ajoute un mouvement (si ce dernier est possible) a la flèche de deplacement
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

        Case destination = move.getDestination();
        Unit currentUnit = game.getSelectedCase().getUnit();

        if (game.getMovement().isEmpty()) return;

        // Ne peut pas passer au-dessus d'une unite sauf s'il s'agit d'une unite volante
        if (currentUnit.canMoveTo(destination.getTerrain(), game.getWeather())) {

            Terrain terrain = destination.getTerrain();

            // Verifie si le terrain n'est pas une propriete
            // Si la case en est une, verfie si le joueur courant en est proprietaire
            // Sinon, pas possble de passer dessus sans attaquer la propriete
            if(!(terrain instanceof Property) || ((Property) terrain).getOwner() == game.getCurrentPlayer().getType()) {

                // Verifie si l'unite a asser de points (en tenant en compte la meteo) pour se deplacer sur cette case
                if (move.getCost(currentUnit, game.getWeather()) <= currentUnit.getMovementPoint(game.getWeather())) {

                    // Verifie si la case n'a pas deja une unite
                    // Si c'est le cas, on verifie si notre unite est une unite volante, auquel cas elle peut passer par dessus

                    // Todo : Pouvoir passer sous une unite volante
                    // Todo : Ne pas pouvoir s'arreter sur une unite
                    if (!destination.hasUnit() || currentUnit instanceof Flying) {

                        game.getView().adjustOffset();
                        return;
                    }

                }

            }

        }

        // Annule le mouvement si une des conditions n'est pas respectee
        game.getCursor().setCurrentX(x);
        game.getCursor().setCurrentY(y);
        game.getMovement().goBack();

    }

}
