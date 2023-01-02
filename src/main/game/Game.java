package main.game;

import com.sun.istack.internal.Nullable;
import main.MiniWars;
import main.control.Cursor;
import main.map.Case;
import main.map.Grid;
import main.map.MapMetadata;
import main.parser.MapParser;
import main.terrain.Property;
import main.terrain.Terrain;
import main.terrain.TerrainType;
import main.terrain.type.HQ;
import main.unit.Unit;
import main.weather.Weather;
import main.weather.WeatherManager;
import ressources.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe representant une partie dans une carte. Une partie est caracterisee par / contient : - Une carte (La grille) -
 * Une metadonnee de carte - Le joueur courant - Les joueurs - Le curseur de selection - Le temps (météo)
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
public class Game {


    private final MiniWars instance;
    private final MapMetadata mapMetadata;
    private final Grid grid;
    private final GameView view;
    private final Map<Player.Type, Player> players;
    private final WeatherManager weatherManager;
    private Cursor cursor;
    private Movement movement;
    private Player.Type currentPlayer;
    private Case selectedCase;

    /**
     * Constructeur de Game. Permet de creer une partie a partir des metadonnees de la carte.
     *
     * @param mapMetadata Metadonnee de la carte a charger pour cette nouvelle partie.
     *
     * @see MapMetadata
     */
    public Game(MiniWars instance, MapMetadata mapMetadata) {

        this.instance = instance;
        this.cursor = new Cursor(mapMetadata.getWidth(), mapMetadata.getHeight());
        this.movement = null;
        this.currentPlayer = Player.Type.RED;
        this.mapMetadata = mapMetadata;
        this.grid = MapParser.parseMap(mapMetadata);
        this.view = new GameView(this.grid, this.cursor, mapMetadata.getWidth(), mapMetadata.getHeight());
        this.players = new HashMap<>();
        this.weatherManager = new WeatherManager();

        for (int i = 1; i <= mapMetadata.getPlayerCount(); i++) {
            players.put(Player.Type.fromValue(i), new Player(Player.Type.values()[i]));
        }

    }

    /**
     * Obtenir les metadonnees de la carte.
     *
     * @return Les metadonnees de la carte.
     *
     * @see MapMetadata
     */
    public MapMetadata getMetadata() {
        return this.mapMetadata;
    }

    /**
     * Obtenir la grille de la carte.
     *
     * @return La grille de la carte.
     *
     * @see Grid
     */
    public Grid getGrid() {
        return this.grid;
    }

    /**
     * Obtenir la vue de la partie. La vue est utilisee pour permettre l'adaptation de la carte (grille) reelle avec la
     * grille de la fenetre de jeu.
     *
     * @return La vue de la partie.
     *
     * @see GameView
     */
    public GameView getView() {
        return this.view;
    }

    public List<Player> getPlayers() {
        return (List<Player>) this.players.values();
    }

    /**
     * Obtenir la largeur de la carte a partir des metadonnees de la carte.
     *
     * @return Largeur de la carte.
     */
    public int getWidth() {
        return this.mapMetadata.getWidth();
    }

    /**
     * Obtenir la hauteur de la carte a partir des metadonnees de la carte.
     *
     * @return Hauteur de la carte.
     */
    public int getHeight() {
        return this.mapMetadata.getHeight();
    }

    /**
     * Obtenir le nombre de joueurs de la partie.
     *
     * @return Nombre de joueurs de la partie.
     */
    public int getPlayerCount() {
        return this.mapMetadata.getPlayerCount();
    }

    /**
     * Obtenir le nombre de joueurs encore en vie.
     *
     * @return Nombre de joueurs encore en vie.
     */
    public int getAlivePlayerCount() {

        int count = 0;

        for (Player player : this.players.values()) {

            if (player.isAlive()) count++;

        }

        return count;

    }

    /**
     * Obtenir l'instance du joueur courant.
     *
     * @return Instance du joueur courant.
     *
     * @see Player
     */
    public Player getCurrentPlayer() {
        return this.players.get(currentPlayer);
    }

    public Player getPlayerFromType(Player.Type playerType) {

        return this.players.get(playerType);

    }

    public boolean hasRemainingHQ(Player.Type player) {

        return this.grid.getCases()
                .stream()
                .filter(c -> c.getTerrain() instanceof HQ)
                .anyMatch(c -> ((HQ) c.getTerrain()).getOwner() == player);

    }

    public void nextTurn() {

        // TODO: System de brouillard si actif

        this.weatherManager.clock();
        if (this.weatherManager.willChange()) {

            System.out.println("Weather will change on next turn to " + this.weatherManager.getNextWeather().name());

        }

        Player nextPlayer = this.nextPlayer();
        for (Case c : this.grid.getCases()) {

            Terrain terrain = c.getTerrain();
            Unit unit = c.getUnit();

            if (terrain instanceof Property) {

                Property property = (Property) terrain;

                if (property.getOwner() != Player.Type.NEUTRAL) {
                    Player currentPlayer = getPlayerFromType(property.getOwner());
                    currentPlayer.setMoney(currentPlayer.getMoney() + 1000);
                }

                if (unit == null) {

                    property.setDefense(property.getDefense() + Config.PROPERTY_DEFAULT_RECOVERY);

                }
                else if (unit.getOwner() == property.getOwner()) {

                    unit.supply();

                }


            }
            if (unit != null) {
                unit.setPlayed(false);
                unit.setMoved(false);
            }

        }


    }

    public void endGame() {

        System.out.println("fin de jeu");

    }

    public boolean hasWinner() {
        return this.getAlivePlayerCount() == 1;
    }

    @Nullable
    public Player.Type getWinner() {
        //todo remove this
        Player.Type winner = null;
        List<HQ> hqs =
                this.grid.getCases()
                        .stream()
                        .filter(c -> c.getTerrain().getType() == TerrainType.HQ)
                        .map(c -> (HQ) c.getTerrain())
                        .collect(Collectors.toList());

        for (HQ hq : hqs) {

            if (winner == null) {

                winner = hq.getOwner();

            }
            else if (winner != hq.getOwner()) return null;

        }
        return winner;

    }

    public Player nextPlayer() {

        if (this.getAlivePlayerCount() != 0) {

            do {

                int nextPlayer = this.currentPlayer.getValue() + 1 > this.getPlayerCount() ? 1 : this.currentPlayer.getValue() + 1;
                this.currentPlayer = Player.Type.fromValue(nextPlayer);

            } while (!this.getCurrentPlayer().isAlive());

        }

        return this.getCurrentPlayer();

    }

    /**
     * Obtenir le curseur de selection.
     *
     * @return Curseur de selection
     *
     * @see Cursor
     */
    public Cursor getCursor() {
        return this.cursor;
    }

    /**
     * Obtenir la meteo actuelle de la partie.
     *
     * @return Meteo actuelle de la partie
     *
     * @see Weather
     */
    public Weather getWeather() {
        return this.weatherManager.getCurrentWeather();
    }

    public WeatherManager getWeatherManager() {
        return this.weatherManager;
    }

    /**
     * Obtenir le mouvement en cours.
     *
     * @return Mouvement en cours
     *
     * @see Movement
     */
    public Movement getMovement() {
        return this.movement;
    }

    /**
     * Definir une nouvelle instance de mouvement.
     *
     * @param movement Nouvelle instance de mouvement
     */
    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    /**
     * Annuler le mouvement en cours. Revient a faire :
     * <code>
     * Game#setMovement(null);
     * </code>
     */
    public void resetMovement() {
        this.movement = null;
    }

    /**
     * Obtenir la case selectionnee.
     *
     * @return Case selectionnee
     *
     * @see Case
     */
    public Case getSelectedCase() {
        return this.selectedCase;
    }

    /**
     * Definir et memoriser une nouvelle case selectionnee.
     *
     * @param selectedCase Nouvelle case selectionnee
     *
     * @see Case
     */
    public void setSelectedCase(Case selectedCase) {
        this.selectedCase = selectedCase;
    }

    public void endPlayer(Player player) {

        for (Case c : this.grid.getCases()) {

            if (c.hasUnit()) {
                if (c.getUnit().getOwner() == player.getType()) {
                    c.setUnit(null);
                }
            }

            if (c.getTerrain() instanceof Property) {

                if (((Property) c.getTerrain()).getOwner() == player.getType()) {
                    ((Property) c.getTerrain()).setOwner(Player.Type.NEUTRAL);
                }

            }

        }

    }

}
