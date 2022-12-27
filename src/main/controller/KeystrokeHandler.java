package main.controller;

import main.GameState;
import main.MiniWars;
import main.Player;
import main.map.Case;
import main.map.Game;
import main.map.Grid;
import main.menu.Menu;
import main.menu.MultiOptionMenu;
import main.menu.model.UnitActionMenu;


public class KeystrokeHandler {

    private final MiniWars instance;

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

        boolean updateDisplay = false;

        switch (gameState) {

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
                if (cursor == null) break;
                updateDisplay = cursor.up();
                updateDisplay |= game.getView().adjustOffset();
                break;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::up);
                updateDisplay = true;
                break;
            case PLAYING_SELECTING_UNIT_ACTION:
                Menu menu = this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);
                if (menu instanceof UnitActionMenu) {
                    UnitActionMenu unitActionMenu = (UnitActionMenu) this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);
                    unitActionMenu.previous();
                    System.out.println("Now selected: " + unitActionMenu.getSelectedOption().getName());
                    updateDisplay = true;

                }
                break;

        }

        return updateDisplay;

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

        boolean updateDisplay = false;

        switch (gameState) {

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
                if (cursor == null) break;
                updateDisplay = cursor.down();
                updateDisplay |= game.getView().adjustOffset();
                break;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::down);
                updateDisplay = true;
                break;
            case PLAYING_SELECTING_UNIT_ACTION:
                Menu menu = this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);
                if (menu instanceof UnitActionMenu) {
                    UnitActionMenu unitActionMenu = (UnitActionMenu) this.instance.getMenuManager().getMenu(Menu.Model.UNIT_ACTION_MENU);
                    unitActionMenu.next();
                    System.out.println("Now selected: " + unitActionMenu.getSelectedOption().getName());
                    updateDisplay = true;

                }
                break;

        }

        return updateDisplay;

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

        boolean updateDisplay = false;

        switch (gameState) {

            case MENU_MAP_SELECTION:

                this.instance.getRenderer().clearBuffer();
                this.instance.getMapSelector().previous();
                this.instance.getMenuManager()
                        .getMenu(Menu.Model.MAP_SELECTION_MENU)
                        .needsRefresh(true);
                updateDisplay = true;
                break;

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
                if (cursor == null) break;
                updateDisplay = cursor.left();
                updateDisplay |= game.getView().adjustOffset();
                break;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::left);
                updateDisplay = true;
                break;

        }

        return updateDisplay;

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

        boolean updateDisplay = false;

        switch (gameState) {

            case MENU_MAP_SELECTION:

                this.instance.getRenderer().clearBuffer();
                this.instance.getMapSelector().next();
                this.instance.getMenuManager()
                        .getMenu(Menu.Model.MAP_SELECTION_MENU)
                        .needsRefresh(true);
                updateDisplay = true;
                break;

            case PLAYING_SELECTING_TARGET:
            case PLAYING_SELECTING:
                if (cursor == null) break;
                updateDisplay = cursor.right();
                updateDisplay |= game.getView().adjustOffset();
                break;

            case PLAYING_MOVING_UNIT:
                if (cursor != null) this.updateMovement(cursor::right);
                updateDisplay = true;
                break;

        }

        return updateDisplay;

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

        final Player.Type currentPlayer = this.instance.isPlaying() ? game.getCurrentPlayer().getType() : null;

        final int x = cursor != null ? cursor.getCurrentX() : 0;
        final int y = cursor != null ? cursor.getCurrentY() : 0;

        boolean updateDisplay = false;

        switch (gameState) {

            case MENU_TITLE_SCREEN:
                this.instance.getRenderer().clearBuffer();
                this.instance.setGameState(GameState.MENU_MAP_SELECTION);
                this.instance.getMenuManager().getMenu(Menu.Model.MAIN_MENU).setVisible(false);
                this.instance.getMenuManager().getMenu(Menu.Model.MAP_SELECTION_MENU).setVisible(true);
                updateDisplay = true;
                break;

            case MENU_MAP_SELECTION:
                this.instance.getRenderer().clearBuffer();
                if (this.instance.getMapSelector().getSelectedMap() != null) {
                    this.instance.newGame(this.instance.getMapSelector().getSelectedMap());
                    this.instance.getMenuManager().getMenu(Menu.Model.MAP_SELECTION_MENU).setVisible(false);
                    updateDisplay = true;
                }
                break;

            case PLAYING_SELECTING:

                if (cursor == null) break;
                if (grid == null) break;

                final Case currentCase = grid.getCase(x, y);

                if (currentCase.hasUnit()) {

                    if (currentCase.getUnit().getOwner() == currentPlayer) {
                        this.instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);

                        UnitActionMenu unitActionMenu = new UnitActionMenu();
                        unitActionMenu.addOption("attack", "Attaquer", true);
                        unitActionMenu.addOption("move", "Déplacer", !currentCase.getUnit().hasMoved());
                        this.instance.getMenuManager().addMenu(Menu.Model.UNIT_ACTION_MENU, unitActionMenu);
                    } else System.out.println("Warn: Unit not owned by current player");

                }

                break;

//                if (c.getTerrain() instanceof Property && ((Property) c.getTerrain()).getOwner() == instance.getCurrentPlayer().getType()) {
//
//                    System.out.println("This is a propriety belonging to you");
//                    return true;
//
//                } else {
//
//                    return true;
//
//                }
//
//
//            case PLAYING_SELECTING_UNIT_ACTION:
//                System.out.println("You are selecting an action");
//                instance.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);
//                return true;
//
//            case PLAYING_SELECTING_FACTORY_ACTION:
//                System.out.println("You are selecting a factory action");
//                instance.setGameState(GameState.PLAYING_SELECTING_FACTORY_ACTION);
//                return true;
//
//            case PLAYING_MOVING_UNIT:
//                instance.setGameState(GameState.PLAYING_MOVING_UNIT);
//
//                Case startingPoint = this.instance.getMovementHead();
//                Case destination = this.instance.getMovementTail();
//
//                destination.setUnit(startingPoint.getUnit());
//                startingPoint.setUnit(null);
//                this.instance.setGameState(GameState.PLAYING_SELECTING);
//                this.instance.resetMovement();
//                return true;
//            case PLAYING_SELECTING:
//
//                if (cursor == null) break;
//
//                int x = game.getCursor().getCurrentX();
//                int y = game.getCursor().getCurrentY();
//
//                Case currentCase = game.getGrid().getCase(x, y);
//
//                if (currentCase.hasUnit()) {
//
//                    game.setMovement(new Movement(currentCase));
//                    this.instance.setGameState(GameState.PLAYING_MOVING_UNIT);
//
//                }
//
//                break;
        }

        return updateDisplay;

    }

    /**
     * Gere la touche echap
     *
     * @param gameState l'etat du jeu
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean escape(GameState gameState) {

//        switch (gameState) {
//
//            case PLAYING_SELECTING:
//                return true;
//
//            case PLAYING_SELECTING_UNIT_ACTION:
//                instance.setGameState(GameState.PLAYING_SELECTING);
//                return true;
//
//            case PLAYING_SELECTING_FACTORY_ACTION:
//                instance.setGameState(GameState.PLAYING_SELECTING);
//                return true;
//
//            case PLAYING_MOVING_UNIT:
//                instance.setGameState(GameState.PLAYING_SELECTING);
//                instance.resetMovement();
//                return true;
//
//        }

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

    private void updateMovement(Runnable movement) {

        Game game = this.instance.getCurrentGame();

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentX();

        // System.out.println("Moving from x=" + x + ", y=" + y);

        movement.run();

        int newX = game.getCursor().getCurrentX();
        int newY = game.getCursor().getCurrentY();

        game.getMovement().update(game.getGrid().getCase(newX, newY));

//        int newX = instance.getCursor().getCurrentX();
//        int newY = instance.getCursor().getCurrentY();
//
//        // System.out.println("Moving to x=" + newX + ", y=" + newY);
//
//        instance.updateMovement(instance.getGrid().getCase(newX, newY));
//
//        if(instance.isMovementEmpty()) {
//
//            instance.updateMovement(instance.getGrid().getCase(x, y));
//
//        }
//        else {
//
//            int newX = instance.getCursor().getCurrentX();
//            int newY = instance.getCursor().getCurrentY();
//
//            instance.updateMovement(instance.getGrid().getCase(newX, newY));
//
//        }

        game.getView().adjustOffset();
    }

}
