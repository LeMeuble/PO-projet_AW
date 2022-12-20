package main;

import librairies.AssociationTouches;
import main.terrain.Case;
import main.terrain.Grid;
import main.terrain.Property;
import main.terrain.Terrain;
import main.terrain.type.Factory;
import main.unit.OnFoot;
import main.unit.Unit;
import main.weather.Weather;
import ressources.Affichage;

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

        switch (playerState) {

            case SELECTING:
                this.game.getCursor().up();
                return true;

            case MOVING_UNIT:

                this.updateMovement(() -> game.getCursor().up());

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

        switch (playerState) {

            case SELECTING:
                this.game.getCursor().down();
                return true;

            case MOVING_UNIT:
                this.updateMovement(() -> this.game.getCursor().down());
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

        switch (playerState) {

            case SELECTING:
                this.game.getCursor().left();
                return true;

            case MOVING_UNIT:
                this.updateMovement(() -> this.game.getCursor().left());
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

        switch (playerState) {

            case SELECTING:
                game.getCursor().right();
                return true;

            case MOVING_UNIT:
                this.updateMovement(() -> this.game.getCursor().right());
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

                Case c = grid.getCase(x, y);

                if(c.hasUnit() && c.getUnit().getOwner() == game.getCurrentPlayer()) {

                    int result = Affichage.popup("Quelle action voulez faire?", new String[]{"Déplacement", "Attaque"}, true, 0);

                    if(result == 0) {

                        System.out.print("There is a unit here : ");
                        System.out.println(game.getGrid().getCase(x, y).getUnit());
                        // game.setPlayerState(PlayerState.SELECTING_UNIT_ACTION);
                        game.setPlayerState(PlayerState.MOVING_UNIT); // C'est la pour du debug

                        this.game.updateMovement(grid.getCase(x, y));
                        
                    } else if (result == 1) {

                    }


                    return true;

                }

                if(c.getTerrain() instanceof Factory && ((Factory) c.getTerrain()).getOwner() == game.getCurrentPlayer()) {

                    System.out.println("This is a factory belonging to you");
                    this.game.setPlayerState(PlayerState.FACTORY_ACTION);
                    return true;

                }

                else {

                    return false;

                }


            case SELECTING_UNIT_ACTION:
                System.out.println("You are selecting an action");
                game.setPlayerState(PlayerState.SELECTING_UNIT_ACTION);
                return true;

            case FACTORY_ACTION:
                System.out.println("You are selecting a factory action");
                game.setPlayerState(PlayerState.FACTORY_ACTION);
                return true;

            case MOVING_UNIT:

                Case startingPoint = this.game.getMovementHead();
                Case destination = this.game.getMovementTail();

                if(destination != null) {

                    if(!(destination.getTerrain() instanceof Property) && !destination.hasUnit()) {

                        destination.setUnit(startingPoint.getUnit());
                        startingPoint.setUnit(null);

                        this.game.setPlayerState(PlayerState.SELECTING);
                        this.game.resetMovement();
                        return true;

                    }

                }

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
                return true;

            case SELECTING_UNIT_ACTION:
                game.setPlayerState(PlayerState.SELECTING);
                return true;

            case FACTORY_ACTION:
                game.setPlayerState(PlayerState.SELECTING);
                return true;

            case MOVING_UNIT:
                game.setPlayerState(PlayerState.SELECTING);
                game.resetMovement();
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

    private void updateMovement(Runnable movement) {

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentY();

        movement.run(); // Move the cursor in the direction

        int newY = game.getCursor().getCurrentY();
        int newX = game.getCursor().getCurrentX();

        this.game.updateMovement(game.getGrid().getCase(newX, newY));

        Case c = this.game.getMovementTail();
        Unit currentUnit = this.game.getMovementHead().getUnit();

        if(this.game.isMovementEmpty()) return;
        if(!c.hasUnit() && currentUnit.canMoveTo(c, Weather.CLEAR)) {

            Movement move = this.game.getMovement();

            System.out.println(move.getCost(currentUnit, Weather.CLEAR) + "   " + currentUnit.getPM());

            if(move.getCost(currentUnit, Weather.CLEAR) <= currentUnit.getPM()) return;

        }

        this.game.getCursor().setCurrentX(x);
        this.game.getCursor().setCurrentY(y);
        this.game.getMovement().popLast();

    }

}
