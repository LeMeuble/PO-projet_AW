/**
 * package principal
 */
package main;

import librairies.AssociationTouches;
import librairies.StdDraw;
import main.terrain.Case;
import main.terrain.Grid;
import ressources.Affichage;
import ressources.Config;
import ressources.ParseurCartes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Jeu {

    public static int borderX;
    public static int borderY;

    private final Grid grid;

    private Player.Type currentPlayer;

    private final Map<Player.Type, Player> players;

    private final Cursor cursor;
    private final KeystrokeHandler keystrokeHandler;
    private Movement movement;

    private GameState gameState;
    private PlayerState playerState;

    public Jeu(String file) throws Exception {

        String[][] parsed = ParseurCartes.parseCarte(file);

        borderX = parsed[0].length;
        borderY = parsed.length;

        this.players = new HashMap<>();

        this.players.put(Player.Type.RED, new Player(Player.Type.RED));
        this.players.put(Player.Type.BLUE, new Player(Player.Type.BLUE));

        this.currentPlayer = Player.Type.RED; // rouge commence
        this.cursor = new Cursor();
        this.keystrokeHandler = new KeystrokeHandler(this);

        this.grid = new Grid(parsed);

        this.gameState = GameState.PLAYING;
        this.playerState = PlayerState.SELECTING;

        Config.setDimension(borderX, borderY);

    }

    public boolean isOver() {
        return this.gameState == GameState.ENDED;
    }

    public void display() {

        StdDraw.clear();
        afficheStatutJeu();

        // Rendu de la grille (cases, terrains et unites)
        for (int y = 0; y < borderY; y++) {

            for (int x = 0; x < borderX; x++) {

                Case c = this.grid.getCase(x, y);

                Affichage.dessineImageDansCase(x, y, c.getTerrain().getFile());
                if (c.hasUnit()) Affichage.dessineImageDansCase(x, y, c.getUnit().getFile());

            }
        }

        // Rendu d'une potentielle fleche de deplacement
        if (this.movement != null) {

            for (Movement.Arrow arrow : this.movement.toDirectionalArrows()) {

                Affichage.dessineImageDansCase(arrow.getCase().getX(), arrow.getCase().getY(), arrow.getPath());

            }

        }

        // Rendu du curseur
        Color color = this.currentPlayer == Player.Type.RED ? Color.RED : Color.BLUE;
        Affichage.dessineCurseur(this.cursor.getCurrentX(), this.cursor.getCurrentY(), color);

        StdDraw.show(); //montre a l'ecran les changements demandes
    }

    public void afficheStatutJeu() {
        Affichage.videZoneTexte();
        Affichage.afficheTexteDescriptif("Status du jeu");
    }

    public void initialDisplay() {
        StdDraw.enableDoubleBuffering(); // rend l'affichage plus fluide: tout draw est mis en buffer et ne s'affiche qu'au prochain StdDraw.show();
        display();
    }

    public void update() {

        AssociationTouches toucheSuivante = AssociationTouches.trouveProchaineEntree(); //cette fonction boucle jusqu'a la prochaine entree de l'utilisateur

        boolean updateDisplay = this.keystrokeHandler.handle(toucheSuivante, this.playerState);


//        //  ATTENTION ! si vous voulez detecter d'autres touches que 't',
//        //  vous devez les ajouter au tableau Config.TOUCHES_PERTINENTES_CARACTERES
//        if (toucheSuivante.isCaractere('t')) {
//            String[] options = {"Oui", "Non", "Peut-etre", "Cancel"};
//            if (Affichage.popup("Finir le tour de XXX?", options, true, 1) == 0) {
//                //le choix 0, "Oui", a été selectionné
//                //TODO: passer au joueur suivant
//                System.out.println("FIN DE TOUR");
//            }
//
//            updateDisplay = true;
//        }

        if (updateDisplay) display();

    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public Player.Type getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player.Type currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void updateMovement(Case newCase) {

        this.movement.update(newCase);

    }

}

