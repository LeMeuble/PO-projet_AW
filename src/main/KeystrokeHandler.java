package main;

import librairies.AssociationTouches;
import main.terrain.Grid;

public class KeystrokeHandler {

    private final Jeu game;

    public KeystrokeHandler(Jeu game) {

        this.game = game;

    }

    /**
     * Gere les touches appuyees et indique si le jeu actualiser l'ecran
     *
     * @param association la touche appuyee
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    public boolean handle(AssociationTouches association, PlayerState playerState) {

        if (association.isHaut()) return up(playerState);
        if (association.isBas()) return down(playerState);
        if (association.isGauche()) return left(playerState);
        if (association.isDroite()) return right(playerState);
        if (association.isEntree()) return enter(playerState);
        if (association.isEchap()) return escape(playerState);

        return false;

    }

    /**
     * Gere la touche haut
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean up(PlayerState playerState) {

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentY();

        switch (playerState) {

            case SELECTING:
                game.getCursor().up();
                return true;

            case MOVING_UNIT:
                game.updateMovement(game.getGrid().getCase(x, y));
                System.out.println("Going up");
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
    private boolean down(PlayerState playerState) {

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentY();

        switch (playerState) {

            case SELECTING:
                game.getCursor().down();
                return true;

            case MOVING_UNIT:
                game.updateMovement(game.getGrid().getCase(x, y));
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
    private boolean left(PlayerState playerState) {

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentY();

        switch (playerState) {

            case SELECTING:
                game.getCursor().left();
                return true;

            case MOVING_UNIT:
                game.updateMovement(game.getGrid().getCase(x, y));
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
    private boolean right(PlayerState playerState) {

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentY();

        switch (playerState) {

            case SELECTING:
                game.getCursor().right();
                return true;

            case MOVING_UNIT:
                game.updateMovement(game.getGrid().getCase(x, y));
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
    private boolean enter(PlayerState playerState) {

        Grid grid = game.getGrid();

        switch (playerState) {

            case SELECTING:

                int x = game.getCursor().getCurrentX();
                int y = game.getCursor().getCurrentY();


                if(grid.getCase(x, y).hasUnit() && grid.getCase(x, y).getUnit().getOwner() == game.getCurrentPlayer()) {

                    System.out.print("There is a unit here : ");
                    System.out.println(game.getGrid().getCase(x, y).getUnit());
                    // game.setPlayerState(PlayerState.SELECTING_UNIT_ACTION);
                    game.setPlayerState(PlayerState.MOVING_UNIT); // C'est la pour du debug
                    return true;

                }

                // Detecter si la case est une propriete
                //if(game.getGrid().getCase(x, y).getTerrain())

            case SELECTING_UNIT_ACTION:
                System.out.println("You are selecting an action");
                game.setPlayerState(PlayerState.SELECTING_UNIT_ACTION);
                return true;

            case FACTORY_ACTION:
                System.out.println("You are selecting a factory action");
                game.setPlayerState(PlayerState.FACTORY_ACTION);
                return true;

            case MOVING_UNIT:
                game.setPlayerState(PlayerState.MOVING_UNIT);
                return true;
        }

        return false;

    }

    /**
     * Gere la touche echap
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean escape(PlayerState playerState) {

        switch (playerState) {

            case SELECTING:
                game.setPlayerState(PlayerState.SELECTING);
                return true;

            case SELECTING_UNIT_ACTION:
                game.setPlayerState(PlayerState.SELECTING);
                return true;

            case FACTORY_ACTION:
                game.setPlayerState(PlayerState.SELECTING);
                return true;

            case MOVING_UNIT:
                game.setPlayerState(PlayerState.SELECTING);
                return true;

        }

        return false;

    }

    /**
     * Gere la touche espace
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean space(PlayerState playerState) {

        return false;

    }

}
