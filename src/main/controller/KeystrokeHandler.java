package main.controller;

import main.GameState;
import main.Jeu;
import main.map.GameMap;
import main.weather.Weather;


public class KeystrokeHandler {

    private final Jeu game;

    public KeystrokeHandler(Jeu game) {

        this.game = game;

    }

    /**
     * Gere les touches appuyees et indique si le jeu actualiser l'ecran
     *
     * @param code la touche appuyee
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean handle(KeystrokeListener.KeyCodes code) {

        final GameState gameState = this.game.getGameState();

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

    public boolean keyD(GameState playerState) {

        this.game.getGameMap().setWeather(Weather.random());
        return true;

    }


    /**
     * Gere la touche haut
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean up(GameState playerState) {

        final GameMap gameMap = this.game.getGameMap();
        final Cursor cursor = this.game.isPlaying() ? gameMap.getCursor() : null;

        switch (playerState) {

            case PLAYING_SELECTING:
                if(cursor != null) cursor.up();
                return true;

            case PLAYING_MOVING_UNIT:

                if(cursor != null) this.updateMovement(cursor::up);
                return true;

        }

        return false;

    }

    /**
     * Gere la touche bas
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean down(GameState playerState) {

        final GameMap gameMap = this.game.getGameMap();
        final Cursor cursor = this.game.isPlaying() ? gameMap.getCursor() : null;

        switch (playerState) {

            case PLAYING_SELECTING:
                if(cursor != null) cursor.down();
                return true;

            case PLAYING_MOVING_UNIT:

                if(cursor != null) this.updateMovement(cursor::down);
                return true;

        }

        return false;

    }


    /**
     * Gere la touche gauche
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean left(GameState playerState) {

        final GameMap gameMap = this.game.getGameMap();
        final Cursor cursor = this.game.isPlaying() ? gameMap.getCursor() : null;

        switch (playerState) {

            case MENU_MAP_SELECTION:

                this.game.getRenderer().clearBuffer();
                this.game.getMapSelector().previous();
                return true;

        }


        return false;

    }

    /**
     * Gere la touche droite
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean right(GameState playerState) {

        final GameMap gameMap = this.game.getGameMap();
        final Cursor cursor = this.game.isPlaying() ? gameMap.getCursor() : null;

        switch (playerState) {

            case MENU_MAP_SELECTION:

                this.game.getMapSelector().next();
                return true;

        }

        return false;

    }

    /**
     * Gere la touche entree
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean enter(GameState playerState) {

        final GameMap gameMap = this.game.getGameMap();
        final Cursor cursor = this.game.isPlaying() ? gameMap.getCursor() : null;

        switch (playerState) {

            case MENU_TITLE_SCREEN:
                this.game.getRenderer().clearBuffer();
                this.game.setGameState(GameState.MENU_MAP_SELECTION);
                return true;
            case MENU_MAP_SELECTION:
                this.game.getRenderer().clearBuffer();
                if(this.game.getMapSelector().getSelectedMap() != null) {
                    this.game.newGame(this.game.getMapSelector().getSelectedMap());
                }
//            case PLAYING_SELECTING:
//
////                int x = game.getCursor().getCurrentX();
////                int y = game.getCursor().getCurrentY();
////
////                Case c = grid.getCase(x, y);
////
////                if (c.hasUnit() && c.getUnit().getOwner() == game.getCurrentPlayer().getType()) {
////
////                    System.out.print("There is a unit here : ");
////                    System.out.println(game.getGrid().getCase(x, y).getUnit());
////                    // game.setPlayerState(PlayerState.SELECTING_UNIT_ACTION);
////                    game.setGameState(GameState.PLAYING_MOVING_UNIT); // C'est la pour du debug
////
////                    this.game.updateMovement(grid.getCase(x, y));
////
////                    return true;
////
////                }
////
////                if (c.getTerrain() instanceof Property && ((Property) c.getTerrain()).getOwner() == game.getCurrentPlayer().getType()) {
////
////                    System.out.println("This is a propriety belonging to you");
////                    return true;
////
////                } else {
////
////                    return true;
////
////                }
//
//
//            case PLAYING_SELECTING_UNIT_ACTION:
//                System.out.println("You are selecting an action");
//                game.setGameState(GameState.PLAYING_SELECTING_UNIT_ACTION);
//                return true;
//
//            case PLAYING_SELECTING_FACTORY_ACTION:
//                System.out.println("You are selecting a factory action");
//                game.setGameState(GameState.PLAYING_SELECTING_FACTORY_ACTION);
//                return true;
//
//            case PLAYING_MOVING_UNIT:
//                game.setGameState(GameState.PLAYING_MOVING_UNIT);
//
//                Case startingPoint = this.game.getMovementHead();
//                Case destination = this.game.getMovementTail();
//
//                destination.setUnit(startingPoint.getUnit());
//                startingPoint.setUnit(null);
//                this.game.setGameState(GameState.PLAYING_SELECTING);
//                this.game.resetMovement();
//                return true;
        }

        return false;

    }

    /**
     * Gere la touche echap
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean escape(GameState playerState) {

//        switch (playerState) {
//
//            case PLAYING_SELECTING:
//                return true;
//
//            case PLAYING_SELECTING_UNIT_ACTION:
//                game.setGameState(GameState.PLAYING_SELECTING);
//                return true;
//
//            case PLAYING_SELECTING_FACTORY_ACTION:
//                game.setGameState(GameState.PLAYING_SELECTING);
//                return true;
//
//            case PLAYING_MOVING_UNIT:
//                game.setGameState(GameState.PLAYING_SELECTING);
//                game.resetMovement();
//                return true;
//
//        }

        return false;

    }

    /**
     * Gere la touche espace
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean space(GameState playerState) {

        return false;

    }

    private void updateMovement(Runnable movement) {

//        int x = game.getCursor().getCurrentX();
//        int y = game.getCursor().getCurrentY();
//
//        // System.out.println("Moving from x=" + x + ", y=" + y);
//
//        movement.run();
//
//        int newX = game.getCursor().getCurrentX();
//        int newY = game.getCursor().getCurrentY();
//
//        // System.out.println("Moving to x=" + newX + ", y=" + newY);
//
//        game.updateMovement(game.getGrid().getCase(newX, newY));

//        if(game.isMovementEmpty()) {
//
//            game.updateMovement(game.getGrid().getCase(x, y));
//
//        }
//        else {
//
//            int newX = game.getCursor().getCurrentX();
//            int newY = game.getCursor().getCurrentY();
//
//            game.updateMovement(game.getGrid().getCase(newX, newY));
//
//        }
    }

}
