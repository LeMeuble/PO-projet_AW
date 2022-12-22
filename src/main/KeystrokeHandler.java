package main;

import librairies.AssociationTouches;
import main.terrain.Case;
import main.terrain.Grid;
import main.terrain.Property;
import main.terrain.type.Factory;
import main.unit.Flying;
import main.unit.Unit;
import main.weather.Weather;
import ressources.Affichage;

import java.util.List;

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

            case SELECTING_TARGET:
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

            case SELECTING_TARGET:
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

            case SELECTING_TARGET:
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

            case SELECTING_TARGET:
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

        int x = game.getCursor().getCurrentX();
        int y = game.getCursor().getCurrentY();

        Case c = grid.getCase(x, y);

        switch (playerState) {

            case SELECTING:

                if(c.hasUnit() && c.getUnit().getOwner() == game.getCurrentPlayer() && !c.getUnit().hasPlayed()) {

                    Unit unit = c.getUnit();
                    int result = Affichage.popup("Quelle action voulez faire?", new String[]{"Déplacement", "Attaque"}, true, 0);

                    if(result == 0 && !c.getUnit().hasMoved()) {
                        game.setPlayerState(PlayerState.MOVING_UNIT);
                        this.game.updateMovement(grid.getCase(x, y));
                    } else if (result == 1) {

                        List<Case> cases = this.game.getGrid().getCasesAround(x, y, unit.getMinReach(), unit.getMaxReach());

                        boolean hasUnit = false;

                        for(Case ca : cases) {
                            if(ca.hasUnit() && ca.getUnit().getOwner() != game.getCurrentPlayer()) {
                                hasUnit = true;
                                break;
                            }

                        }

                        if(!hasUnit) {
                            System.out.println("no unit");
                            return false;
                        }

                        this.game.setCurrentUnit(c.getUnit());
                        this.game.setPlayerState(PlayerState.SELECTING_TARGET);
                        System.out.println("selecting target unit");

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

            case SELECTING_TARGET:

               if(c.hasUnit() && c.getUnit().getOwner() != game.getCurrentPlayer()) {

                   game.getCurrentUnit().attack(c.getUnit());
                   game.getCurrentUnit().setPlayed(true);
                   game.setPlayerState(PlayerState.SELECTING);
                   c.garbageUnit();

               }

               return true;

            case SELECTING_UNIT_ACTION:

                int result = Affichage.popup("Quelle action voulez faire?", new String[]{"Déplacement", "Attaque"}, true, 0);

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
                        destination.getUnit().setMoved(true);
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

                System.out.println(this.game.getCurrentPlayer());
                this.game.setCurrentPlayer(this.game.getCurrentPlayer() == Player.Type.RED ? Player.Type.BLUE : Player.Type.RED);
                this.game.getGrid().newTurn();
                System.out.println(this.game.getCurrentPlayer());
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

        movement.run(); // Move the cursor in the direction (simulate)

        int newY = game.getCursor().getCurrentY();
        int newX = game.getCursor().getCurrentX();

        this.game.updateMovement(game.getGrid().getCase(newX, newY));

        Case c = this.game.getMovementTail();
        Unit currentUnit = this.game.getMovementHead().getUnit();

        if(this.game.isMovementEmpty()) return;

        if(!c.hasUnit() || currentUnit instanceof Flying) {

            if(currentUnit.canMoveTo(c, Weather.CLEAR)) {

                Movement move = this.game.getMovement();

                System.out.println(move.getCost(currentUnit, Weather.CLEAR) + "   " + currentUnit.getPM());

                if(move.getCost(currentUnit, Weather.CLEAR) <= currentUnit.getPM()) return;

            }

        }

        this.game.getCursor().setCurrentX(x);
        this.game.getCursor().setCurrentY(y);
        this.game.getMovement().popLast();

    }

}
