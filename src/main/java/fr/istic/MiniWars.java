package fr.istic; /**
 * package principal
 */

import fr.istic.game.Game;
import fr.istic.game.GameState;
import fr.istic.game.Settings;
import fr.istic.control.KeystrokeListener;
import fr.istic.game.*;
import fr.istic.map.MapMetadata;
import fr.istic.menu.MenuManager;
import fr.istic.menu.MenuModel;
import fr.istic.menu.model.MainMenu;
import fr.istic.menu.model.MapSelectionMenu;
import fr.istic.menu.model.NextTurnMenu;
import fr.istic.render.Renderer;

/**
 * Classe principale du jeu Cette classe est le point d'entree du programme Elle initialise les composants du jeu et
 * lance la boucle principale
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class MiniWars {

    private static MiniWars instance;

    private final KeystrokeListener keystrokeListener;
    private final ActionHandler actionHandler;
    private final GameLoop gameLoop;
    private volatile Game currentGame;
    private GameState gameState;

    /**
     * Constructeur de fr.istic.MiniWars
     * Initialise les instances gerant les entrees clavier, la boucle du jeu
     * Ouvre le fr.istic.menu de selection des cartes
     */
    private MiniWars() {

        this.currentGame = null;
        this.gameState = GameState.MENU_TITLE_SCREEN;

        this.keystrokeListener = new KeystrokeListener();
        this.keystrokeListener.setHandler(this::handleKey);

        this.gameLoop = new GameLoop();
        this.gameLoop.setHandler(this::update);

        this.actionHandler = new ActionHandler(this);

        this.registerDefaultMenus();
        this.gameLoop.start();
        this.keystrokeListener.start();

        this.update();

    }

    /**
     * Permet de lancer le jeu et de retourner son instance.
     *
     * @return Instance du jeu.
     */
    public static MiniWars launch() {

        instance = new MiniWars();
        return instance;

    }

    /**
     * Obtenir l'instance actuelle du jeu.
     *
     * @return Instance du jeu.
     */
    public static MiniWars getInstance() {

        return MiniWars.instance;

    }

    /**
     * @return true si le jeu est termine, false sinon
     */
    public boolean isOver() {
        return this.gameState == null;
    }

    /**
     * Gere l'appui sur une touche
     *
     * @param keycode Le code de la touche qui a ete pressee
     */
    public void handleKey(KeystrokeListener.KeyCodes keycode) {
        if (this.actionHandler.handle(keycode)) this.update();

        if (this.isPlaying()) {

            final Game game = this.getCurrentGame();
            if (game.getSettings().isAutoEndTurn()) {
                if (!game.hasRemainingAction()) {

                    NextTurnMenu menu = new NextTurnMenu();
                    MenuManager.getInstance().addMenu(menu);

                    menu.fadeIn();
                    game.nextTurn();

                    this.setGameState(GameState.PLAYING_SELECTING_NEXT_TURN_APPROVAL);

                }
            }
        }
    }

    /**
     * Methode appellee pour permettre d'indiquer au {@link Renderer} qu'il est necessaire de rendre le jeu.
     * Cette methode est appellee uniquement apres une touche ou par la {@link GameLoop}
     */
    public void update() {
        Renderer.getInstance().render(this.gameState, this.currentGame);
    }

    /**
     * Indique si le jeu est en partie.
     *
     * @return true si une partie est en cours, false sinon.
     */
    public synchronized boolean isPlaying() {
        return this.currentGame != null;
    }

    /**
     * @return L'instance du jeu
     */
    public synchronized Game getCurrentGame() {
        return this.currentGame;
    }

    /**
     * Lancer une nouvelle partie.
     *
     * @param mapMetadata Donner la fr.istic.map a lancer.
     * @param settings    Parametre pour la partie.
     */
    public synchronized void newGame(MapMetadata mapMetadata, Settings settings) {

        this.currentGame = new Game(mapMetadata, settings);
        this.currentGame.startGame();
    }

    /**
     * Metre fin a une partie.
     */
    public synchronized void endGame() {
        this.currentGame = null;
    }

    /**
     * Obtenir l'etat actuel du jeu.
     *
     * @return Etat actuel du jeu.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Definir l'etat actuel du jeu.
     *
     * @param gameState Nouvel etat de jeu
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Mettre fin au programme de facon 'popre' en arretant les threads.
     */
    public void end() {
        this.keystrokeListener.stop();
        this.gameLoop.stop();
        Logger.closeAll();
    }

    /**
     * Enregistrer les menus par default persistents
     */
    private void registerDefaultMenus() {

        MenuManager.getInstance().addMenu(new MainMenu());
        MenuManager.getInstance().addMenu(new MapSelectionMenu());
        MenuManager.getInstance().getMenu(MenuModel.MAP_SELECTION_MENU).setVisible(false);

    }

}

