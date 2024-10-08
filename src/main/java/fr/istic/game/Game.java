package fr.istic.game;

import fr.istic.Config;
import fr.istic.MiniWars;
import fr.istic.control.Cursor;
import fr.istic.map.Case;
import fr.istic.map.Grid;
import fr.istic.map.MapMetadata;
import fr.istic.menu.MenuManager;
import fr.istic.menu.model.BottomMenu;
import fr.istic.parser.MapParser;
import fr.istic.render.OverlayType;
import fr.istic.render.Popup;
import fr.istic.render.PopupRegistry;
import fr.istic.terrain.Factory;
import fr.istic.terrain.Property;
import fr.istic.terrain.Terrain;
import fr.istic.terrain.TerrainType;
import fr.istic.terrain.type.FactoryTerrain;
import fr.istic.terrain.type.HQ;
import fr.istic.terrain.type.Port;
import fr.istic.unit.*;
import fr.istic.util.Dijkstra;
import fr.istic.weather.Weather;
import fr.istic.weather.WeatherManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe representant une partie dans une carte.
 * Une partie est caracterisee par / contient :
 * - Une carte (La grille)
 * - Une metadonnee de carte
 * - Le joueur courant
 * - Les joueurs
 * - Le curseur de selection
 * - Le temps (meteo)
 * - etc (autres donnees utilitaires, ex: resultat des calculs de Dijkstra)
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Game {

    private final Grid grid;
    private final GameView view;
    private final Cursor cursor;
    private final Settings settings;
    private final MapMetadata mapMetadata;
    private final WeatherManager weatherManager;
    private final Map<Player.Type, Player> players;
    private int day;
    private final long startTime;
    private long duration;
    private Movement movement;
    private Case selectedCase;
    private Dijkstra dijkstraResult;
    private Player.Type currentPlayer;

    private volatile Set<Case> overlayCases;
    private volatile OverlayType overlayType;

    /**
     * Constructeur de Game. Permet de creer une partie a partir des metadonnees de la carte.
     *
     * @param mapMetadata Metadonnee de la carte a charger pour cette nouvelle partie.
     *
     * @see MapMetadata
     */
    public Game(MapMetadata mapMetadata, Settings settings) {

        this.day = 1;
        this.duration = 0;
        this.startTime = System.currentTimeMillis();
        this.settings = settings;
        this.cursor = new Cursor(mapMetadata.getWidth(), mapMetadata.getHeight());
        this.movement = null;
        this.currentPlayer = Player.Type.RED;
        this.mapMetadata = mapMetadata;
        this.grid = MapParser.parseMap(mapMetadata);
        this.view = new GameView(this.grid, this.cursor, mapMetadata.getWidth(), mapMetadata.getHeight());
        this.players = new HashMap<>();
        this.weatherManager = new WeatherManager();
        this.selectedCase = null;
        this.overlayCases = Collections.synchronizedSet(new HashSet<>());
        this.overlayType = OverlayType.MISC;

        settings.configureWeatherManager(weatherManager);

        for (int i = 1; i <= mapMetadata.getPlayerCount(); i++) {
            players.put(Player.Type.fromValue(i), new Player(Player.Type.values()[i]));
        }

        MenuManager.getInstance().addMenu(new BottomMenu());

    }


    public long getDuration() {
        return this.duration;
    }

    public int getDay() {

        return this.day;

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
     * Obtenir les parametres de la partie.
     *
     * @return Les parametres de la partie
     *
     * @see Settings
     */
    public Settings getSettings() {
        return this.settings;
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
     * Obtenir l'instance Dijkstra enregistree dans la partie.
     *
     * @return Instance de Dijkstra enregistree dans la partie.
     *
     * @see Dijkstra
     */
    public Dijkstra getDijkstraResult() {
        return this.dijkstraResult;
    }

    /**
     * Definir une instance de Dijkstra a enregister.
     * Cette methode de faire en quelque sorte de stocker le resultat d'un
     * calcul Dijkstra en "cache".
     *
     * @param dijkstraResult Instance de Dijkstra a enregistrer.
     */
    public void setDijkstraResult(Dijkstra dijkstraResult) {
        this.dijkstraResult = dijkstraResult;
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
        return new ArrayList<>(this.players.values());
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

    /**
     * Obtenir l'instance du joueur a partir de son type.
     *
     * @param playerType Type du joueur a obtenir.
     *
     * @return Instance du joueur. Ou null si le joueur n'existe pas.
     */
    public Player getPlayerFromType(Player.Type playerType) {

        return this.players.get(playerType);

    }

    /**
     * Determiner si un joueur a un QG restant.
     *
     * @param player Type du joueur a verifier.
     *
     * @return true si le joueur a un QG restant, false sinon.
     */
    public boolean hasRemainingHQ(Player.Type player) {

        return getRemainingHQ(player) != null;

    }

    /**
     * Obtenir le premier QG restant trouve d'un joueur.
     *
     * @param player Type du joueur a verifier.
     *
     * @return Instance du QG restant. Ou null si le joueur n'a plus de QG.
     */
    public Case getRemainingHQ(Player.Type player) {

        return this.grid.getCases()
                .stream()
                .filter(c -> c.getTerrain() instanceof HQ).filter(c -> ((HQ) c.getTerrain()).getOwner() == player)
                .findFirst()
                .orElse(null);

    }

    /**
     * Obtenir la liste des cases a surligner.
     *
     * @return Set des cases a surligner.
     *
     * @implNote Thread-safe.
     */
    public synchronized Set<Case> getOverlayCases() {
        return this.overlayCases;
    }

    /**
     * Definir une liste de cases a surligner.
     *
     * @param overlayCases Set des cases a surligner.
     *
     * @implNote Thread-safe.
     */
    public synchronized void setOverlayCases(Set<Case> overlayCases) {
        this.overlayCases = overlayCases;
    }

    /**
     * Obtenir le type de surlignage a utiliser.
     *
     * @return Type de surlignage a utiliser.
     *
     * @implNote Thread-safe.
     */
    public synchronized OverlayType getOverlayType() {
        return this.overlayType;
    }

    /**
     * Definir le type de surlignage a utiliser.
     *
     * @param overlayType Type de surlignage a utiliser.
     *
     * @implNote Thread-safe.
     */
    public synchronized void setOverlayType(OverlayType overlayType) {
        this.overlayType = overlayType;
    }

    /**
     * Effacer les cases en surbrilance.
     */
    public synchronized void clearOverlayCases() {
        this.overlayCases.clear();
    }

    /**
     * @return
     */
    public boolean isSoftLocked() {

        return this.grid
                .getCases()
                .stream()
                .noneMatch(c -> c.getUnit() instanceof OnFoot ||
                        (c.getTerrain() instanceof FactoryTerrain && ((Property) c.getTerrain()).getOwner() != Player.Type.NEUTRAL)
                );

    }

    /**
     * Cette methode passe la partie au tour suivant.
     * Plusieurs elements sont modifies lors de ce changement de tour :
     * - Le joueur courant est change.
     */
    public void nextTurn() {

        final Player currentPlayer = MiniWars.getInstance().getCurrentGame().getCurrentPlayer();
        final Player.Type previousPlayer = this.currentPlayer;
        final Player.Type nextPlayer = this.nextPlayer().getType();

        if (this.isSoftLocked()) {

            PopupRegistry.getInstance()
                    .push(new Popup("Avertissement!", "La partie est bloqu\u00e9! (Softlock)"));

        }

        // Il s'agit d'un nouveau jour, si l'on a fait une "rotation" complete des joueurs.
        final boolean newDay = previousPlayer.ordinal() > nextPlayer.ordinal();

        if (newDay) {
            this.weatherManager.clock(this.getAlivePlayerCount());
            this.day++;
        }

        if (this.weatherManager.willChange())
            PopupRegistry.getInstance()
                    .push(new Popup("Changement m\u00e9t\u00e9o!", "La m\u00e9t\u00e9o va changer ! (" + this.weatherManager.getNextWeather().getName() + ")"));


        this.grid.resetFogOfWar(this.settings.isFogOfWar());

        for (Case c : this.grid.getCases()) {

            final Terrain terrain = c.getTerrain();
            final Unit unit = c.getUnit();

            if (newDay) {

                if (unit != null) {

                    if ((unit instanceof Flying || unit instanceof Naval)) {
                        unit.setEnergy(unit.getEnergy() - unit.getDailyEnergyConsumption());
                    }

                }

                if (terrain instanceof Property) {

                    final Property property = (Property) terrain;

                    if (property.getOwner() != Player.Type.NEUTRAL) {
                        final Player owner = this.getPlayerFromType(property.getOwner());
                        owner.setMoney(owner.getMoney() + 1000);
                    }
                    if (unit == null) property.setDefense(property.getDefense() + Config.PROPERTY_DEFAULT_RECOVERY);

                    if (property.getOwner() == this.currentPlayer) {

                        int supplyMaxRadius = property instanceof Port ? 1 : 0;

                        final List<Unit> unitsArounds = this.grid.getUnitsAround(this.grid.getCasesAround(c.getCoordinate(), 0, supplyMaxRadius));

                        for (Unit u : unitsArounds) {

                            if (u.getOwner() == this.currentPlayer) {

                                if (u instanceof Naval || u.getCoordinate().distance(c.getCoordinate()) == 0) {
                                    u.supply();

                                    for (int i = 0; i < 2; i++) {

                                        if (u.getHealth() < Config.UNIT_MAX_HEALTH) {

                                            if (currentPlayer.getMoney() >= u.getRepairCost()) {

                                                u.repair(1);
                                                currentPlayer.setMoney(currentPlayer.getMoney() - u.getRepairCost());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (unit != null) {

                unit.setPlayed(false);
                unit.setMoved(false);

                if ((unit instanceof Flying || unit instanceof Naval) && newDay) {
                    if (!unit.hasEnergy()) c.setUnit(null);
                }

                if (unit instanceof Transport) {
                    for (Unit u : ((Transport) unit).getCarriedUnits()) {
                        u.setPlayed(false);
                        u.setMoved(false);
                    }
                }

            }

        }

        this.view.focus(this.getRemainingHQ(nextPlayer));
        this.cursor.setCoordinate(this.getRemainingHQ(nextPlayer).getCoordinate());
        this.grid.getCases().forEach(c -> {
            this.grid.updateFogOfWar(c, c.getUnit());
        });

    }

    /**
     * Reinitialise le brouillard de guerre pour le prochain joueur et centre l'ecran sur son QG
     */
    public void startGame() {

        this.grid.resetFogOfWar(settings.isFogOfWar());
        if (settings.isFogOfWar()) {
            this.grid.getCases().forEach(c -> {
                this.grid.updateFogOfWar(c, c.getUnit());
            });
        }

        this.cursor.setCoordinate(this.getRemainingHQ(this.currentPlayer).getCoordinate());
        this.view.focus(this.getRemainingHQ(this.currentPlayer));

    }

    /**
     * Comptabilise les statistiques des joueurs, pour l'affichage en fin de partie
     */
    public void endGame() {

        this.duration = System.currentTimeMillis() - this.startTime;

        int units = 0;
        int properties = 0;

        for (Case c : this.grid.getCases()) {
            if (c.getUnit() != null && c.getUnit().getOwner() == this.getWinner()) {
                units++;
            }

            if (c.getTerrain() instanceof Property && ((Property) c.getTerrain()).getOwner() == this.getWinner()) {
                properties++;
            }
        }

        this.getPlayerFromType(this.getWinner()).setStatUnitCount(units);
        this.getPlayerFromType(this.getWinner()).setStatPropertyCount(properties);

    }

    public boolean hasWinner() {
        return this.getAlivePlayerCount() == 1;
    }

    /**
     * Renvoie le gagnant de la partie. Le gagnant d'une partie est le seul joueur possedant encore des QG.
     *
     * @return Le joueur gagnant de la partie s'il existe, null sinon
     */
    public Player.Type getWinner() {
        Player.Type winner = null;
        List<HQ> hqs = this.grid.getCases()
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

    /**
     * @return Le prochain joueur vivant
     */
    public Player nextPlayer() {

        if (this.getAlivePlayerCount() != 0) {

            do {

                final int nextPlayer = this.currentPlayer.getValue() + 1 > this.getPlayerCount() ? 1 : this.currentPlayer.getValue() + 1;
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

    /**
     * Termine l'instance d'un joueur, et recupere ses statistiques
     *
     * @param player Le joueur a tuer
     */
    public void endPlayer(Player player) {

        int units = 0;
        int properties = 0;

        for (Case c : this.grid.getCases()) {

            if (c.hasUnit()) {
                if (c.getUnit().getOwner() == player.getType()) {
                    c.setUnit(null);
                    units++;
                }
            }

            if (c.getTerrain() instanceof Property) {

                if (((Property) c.getTerrain()).getOwner() == player.getType()) {
                    ((Property) c.getTerrain()).setOwner(Player.Type.NEUTRAL);
                    properties++;
                }

            }

        }

        player.setAlive(false);
        player.setStatUnitCount(units);
        player.setStatPropertyCount(properties + 1);

    }

    /**
     * Verifie si le joueur courant a encore des actions disponibles. Les actions disponibles sont : deplacer une unite
     * ou produire une unite dans une usine
     *
     * @return true si le joueur courant a encore des actions disponibles, false sinon
     */
    public boolean hasRemainingAction() {

        for (Case c : this.grid.getCases()) {

            if (c.hasUnit() && c.getUnit().getOwner() == this.currentPlayer) {

                if (!c.getUnit().hasPlayed()) return true;

            }
            if (c.getTerrain() instanceof Factory) {

                final Factory factory = (Factory) c.getTerrain();

                if (factory.getOwner() == this.currentPlayer) {
                    if (Factory.canCreateUnit(c)) {
                        if (factory.anythingBuyable(this.getPlayerFromType(this.currentPlayer).getMoney())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
