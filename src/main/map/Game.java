package main.map;

import main.Movement;
import main.Player;
import main.controller.Cursor;
import main.render.GameView;
import main.weather.Weather;
import ressources.MapParsing;

import java.util.HashMap;
import java.util.Map;

public class Game {


    private Cursor cursor;
    private Movement movement;
    private Player.Type currentPlayer;
    private Case selectedCase;
    private Weather weather;

    private final MapMetadata mapMetadata;
    private final Grid grid;
    private final GameView view;
    private final Map<Player.Type, Player> players;

    public Game(MapMetadata mapMetadata) {

        this.cursor = new Cursor(mapMetadata.getWidth(), mapMetadata.getHeight());
        this.movement = null;
        this.currentPlayer = Player.Type.RED;
        this.mapMetadata = mapMetadata;
        this.grid = MapParsing.parseMap(mapMetadata);
        this.view = new GameView(this.grid, this.cursor, mapMetadata.getWidth(), mapMetadata.getHeight());
        this.players = new HashMap<>();
        this.weather = Weather.random();

        for (int i = 1; i <= mapMetadata.getPlayerCount(); i++) {
            players.put(Player.Type.fromValue(i), new Player(Player.Type.values()[i]));
        }

    }

    public MapMetadata getMetadata() {
        return this.mapMetadata;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public GameView getView() {
        return this.view;
    }

    public int getWidth() {
        return this.mapMetadata.getWidth();
    }

    public int getHeight() {
        return this.mapMetadata.getHeight();
    }

    public int getPlayerCount() {
        return this.mapMetadata.getPlayerCount();
    }

    public Player getCurrentPlayer() {
        return this.players.get(currentPlayer);
    }

    public void nextPlayer() {

        int nextPlayer = this.currentPlayer.getValue() + 1 > this.getPlayerCount() ? 1 : this.currentPlayer.getValue() + 1;
        this.currentPlayer = Player.Type.fromValue(nextPlayer);

    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public Weather getWeather() {
        return this.weather;
    }
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Movement getMovement() {
        return this.movement;
    }
    public void cancelMovement() {
        this.movement = null;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Case getSelectedCase() {
        return this.selectedCase;
    }

    public void setSelectedCase(Case selectedCase) {
        this.selectedCase = selectedCase;
    }

}
