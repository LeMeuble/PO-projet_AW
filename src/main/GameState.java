package main;

public enum GameState {

    MENU_TITLE_SCREEN, // Ecran titre
    MENU_MAP_SELECTION, // Selection de la carte
    MENU_PAUSE, // Menu pause

    PLAYING_SELECTING, // Selection d'une unite
    PLAYING_SELECTING_UNIT_ACTION, // Selection d'une action pour une unite
    PLAYING_MOVING_UNIT, // Deplacement d'une unite (fleche)
    PLAYING_SELECTING_TARGET, // Selection d'une cible
    PLAYING_SELECTING_FACTORY_ACTION, // Selection d'une action pour une usine
    ENDIND_SCREEN; // Ecran de fin


}
