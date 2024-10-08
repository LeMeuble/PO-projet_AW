package fr.istic.game;

/**
 * Classe representant l'etat du jeu
 * Comprends a la fois l'etat en dehors d'une partie
 * et l'etat d'une partie en cours
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public enum GameState {

    /**
     * Etat du jeu : Menu principal
     */
    // Ecran titre
    MENU_TITLE_SCREEN,
    // Selection de la carte
    MENU_MAP_SELECTION,
    // Menu pause
    MENU_PAUSE,

    /**
     * Etat du jeu : Partie en cours
     */
    // Selection d'une unite
    PLAYING_SELECTING,
    // Selection d'une action pour une unite
    PLAYING_SELECTING_UNIT_ACTION,

    PLAYING_SELECTING_SKIP_TURN_ACTION,

    PLAYING_SELECTING_NEXT_TURN_APPROVAL,
    // Deplacement d'une unite (fleche)
    PLAYING_MOVING_UNIT,
    // Selection d'une cible
    PLAYING_RENDERING_MOVING_UNIT,
    // Selection d'une cible
    PLAYING_SELECTING_TARGET,
    // Selection d'une action pour une usine
    PLAYING_SELECTING_FACTORY_UNIT,
    // Selection d'un transport
    PLAYING_SELECTING_TRANSPORT,
    // Selection d'une zone de depot d'unite
    PLAYING_SELECTING_DROP_ZONE,
    PLAYING_SELECTING_DROPPED_UNIT,
    // Ecran de fin,
    PLAYING_ENDIND_SCREEN


}
