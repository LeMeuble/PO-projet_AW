package main.game;

/**
 * Classe representant l'etat du jeu
 * Comprends a la fois l'etat en dehors d'une partie
 * et l'etat d'une partie en cours
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
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
    // Deplacement d'une unite (fleche)
    PLAYING_MOVING_UNIT,
    // Selection d'une cible
    PLAYING_SELECTING_TARGET,
    // Selection d'une action pour une usine
    PLAYING_SELECTING_FACTORY_UNIT,
    // Ecran de fin
    PLAYING_ENDIND_SCREEN;


}
