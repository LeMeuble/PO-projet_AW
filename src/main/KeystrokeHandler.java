package main;

import librairies.AssociationTouches;

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
                game.getCursor().up();
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
                game.getCursor().down();
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
                game.getCursor().left();
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

        return false;

    }

    /**
     * Gere la touche echap
     *
     * @param playerState l'etat du joueur
     * @return true si le jeu doit actualiser l'ecran
     */
    private boolean escape(PlayerState playerState) {

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
