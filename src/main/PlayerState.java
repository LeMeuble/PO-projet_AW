package main;

public enum PlayerState {

    WAITING,
    SELECTING, // Choisir une unite
    SELECTING_UNIT_ACTION, // Choisir une action pour l'unite
    SELECTING_TARGET,
    FACTORY_ACTION, // Action pour une usine
    MOVING_UNIT, // Choisir un terrain

}
