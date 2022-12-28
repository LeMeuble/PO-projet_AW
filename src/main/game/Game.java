package main.game;

import main.control.Cursor;
import main.map.Case;
import main.map.Grid;
import main.map.MapMetadata;
import main.parser.MapParser;
import main.weather.Weather;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe representant une partie dans une carte.
 * Une partie est caracterisee par / contient :
 * - Une carte (La grille)
 * - Une metadonnee de carte
 * - Le joueur courant
 * - Les joueurs
 * - Le curseur de selection
 * - Le temps (météo)
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 */
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

    /**
     * Constructeur de Game.
     * Permet de creer une partie a partir des metadonnees de la carte.
     *
     * @param mapMetadata Metadonnee de la carte a charger
     *                    pour cette nouvelle partie.
     *
     * @see MapMetadata
     */
    public Game(MapMetadata mapMetadata) {

        this.cursor = new Cursor(mapMetadata.getWidth(), mapMetadata.getHeight());
        this.movement = null;
        this.currentPlayer = Player.Type.RED;
        this.mapMetadata = mapMetadata;
        this.grid = MapParser.parseMap(mapMetadata);
        this.view = new GameView(this.grid, this.cursor, mapMetadata.getWidth(), mapMetadata.getHeight());
        this.players = new HashMap<>();
        this.weather = Weather.random();

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
     * Obtenir la vue de la partie.
     * La vue est utilisee pour permettre l'adaptation
     * de la carte (grille) reelle avec la grille de la
     * fenetre de jeu.
     *
     * @return La vue de la partie.
     *
     * @see GameView
     */
    public GameView getView() {
        return this.view;
    }

    /**
     * Obtenir la largeur de la carte a partir
     * des metadonnees de la carte.
     *
     * @return Largeur de la carte.
     */
    public int getWidth() {
        return this.mapMetadata.getWidth();
    }

    /**
     * Obtenir la hauteur de la carte a partir
     * des metadonnees de la carte.
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

            if(player.isAlive()) count++;

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

    // TODO: Adapter selon les joueurs en vie
    public void nextPlayer() {

        int nextPlayer = this.currentPlayer.getValue() + 1 > this.getPlayerCount() ? 1 : this.currentPlayer.getValue() + 1;
        this.currentPlayer = Player.Type.fromValue(nextPlayer);

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
        return this.weather;
    }

    /**
     * Changer la meteo actuelle de la partie.
     *
     * @param weather
     *
     * @see Weather
     */
    public void setWeather(Weather weather) {
        this.weather = weather;
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
     * Annuler le mouvement en cours.
     * Revient a faire :
     * <code>
     *     Game#setMovement(null);
     * </code>
     */
    public void cancelMovement() {
        this.movement = null;
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

}
