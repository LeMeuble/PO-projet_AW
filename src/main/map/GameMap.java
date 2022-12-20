package main.map;

import main.Movement;
import main.Player;
import main.controller.Cursor;
import ressources.MapParsing;

import java.util.HashMap;
import java.util.Map;

public class GameMap {


    private Cursor cursor;
    private Movement movement;
    private Player.Type currentPlayer;

    private final MapMetadata metadata;
    private final Grid grid;
    private final Map<Player.Type, Player> players;

    public GameMap(MapMetadata metadata) {

        this.cursor = new Cursor();
        this.movement = null;
        this.currentPlayer = Player.Type.RED; // rouge commence
        this.metadata = metadata;
        this.grid = MapParsing.parseMap(metadata);
        this.players = new HashMap<>();

        for (int i = 1; i <= metadata.getPlayerCount(); i++) {
            players.put(Player.Type.fromValue(i), new Player(Player.Type.values()[i]));
        }

    }

    public MapMetadata getMetadata() {
        return metadata;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getWidth() {
        return metadata.getWidth();
    }

    public int getHeight() {
        return metadata.getHeight();
    }

    public int getPlayerCount() {
        return metadata.getPlayerCount();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public void nextPlayer() {

        int nextPlayer = this.currentPlayer.getValue() + 1 > this.getPlayerCount() ? 1 : this.currentPlayer.getValue() + 1;
        this.currentPlayer = Player.Type.fromValue(nextPlayer);

    }

    public Cursor getCursor() {
        return this.cursor;
    }

}
