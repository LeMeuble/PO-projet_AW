/**
 * package principal
 */
package main;

import librairies.AssociationTouches;
import librairies.StdDraw;
import main.controller.Cursor;
import main.controller.KeystrokeHandler;
import main.controller.KeystrokeListener;
import main.terrain.Case;
import main.terrain.Grid;
import ressources.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Jeu {

    public static int borderX;
    public static int borderY;

    private Movement movement;
    private GameState gameState;
    private Player.Type currentPlayer;

    private final Grid grid;
    private final main.controller.Cursor cursor;
    private final KeystrokeListener keystrokeListener;
    private final KeystrokeHandler keystrokeHandler;
    private final Map<Player.Type, Player> players;

    public Jeu(String file) throws Exception {

        String[][] parsed = ParseurCartes.parseCarte(file);

        GameMap map = MapParsing.listAvailableMaps().get(0);

        this.grid = MapParsing.parseMap(map);

        borderX = map.getWidth(); //parsed[0].length;
        borderY = map.getHeight(); //parsed.length;

        this.players = new HashMap<>();

        this.players.put(Player.Type.RED, new Player(Player.Type.RED));
        this.players.put(Player.Type.BLUE, new Player(Player.Type.BLUE));

        this.currentPlayer = Player.Type.RED; // rouge commence
        this.cursor = new main.controller.Cursor();
        this.keystrokeListener = new KeystrokeListener();
        this.keystrokeHandler = new KeystrokeHandler(this);


        this.gameState = GameState.PLAYING_SELECTING;

        Config.setDimension(borderX, borderY);

        this.bindKeystrokes();

        StdDraw.enableDoubleBuffering();
        this.display();

    }

    public boolean isOver() {
        return this.gameState == GameState.ENDIND_SCREEN;
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

        StdDraw.show();

    }

    public void afficheStatutJeu() {
        Affichage.videZoneTexte();
        Affichage.afficheTexteDescriptif("Status du jeu");
    }

    public void bindKeystrokes() {
        this.keystrokeListener.setHandler((code) -> {
            boolean updateDisplay = this.keystrokeHandler.handle(code);
            if(updateDisplay) this.display();
        });
        this.keystrokeListener.start();
    }

    public void update() {

        AssociationTouches toucheSuivante = AssociationTouches.trouveProchaineEntree(); //cette fonction boucle jusqu'a la prochaine entree de l'utilisateur
//
//        boolean updateDisplay = this.keystrokeHandler.handle(toucheSuivante, this.playerState);
//
//
////        //  ATTENTION ! si vous voulez detecter d'autres touches que 't',
////        //  vous devez les ajouter au tableau Config.TOUCHES_PERTINENTES_CARACTERES
////        if (toucheSuivante.isCaractere('t')) {
////            String[] options = {"Oui", "Non", "Peut-etre", "Cancel"};
////            if (Affichage.popup("Finir le tour de XXX?", options, true, 1) == 0) {
////                //le choix 0, "Oui", a été selectionné
////                //TODO: passer au joueur suivant
////                System.out.println("FIN DE TOUR");
////            }
////
////            updateDisplay = true;
////        }
//
//        if (updateDisplay) display();

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

    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayer);
    }

    public void setCurrentPlayer(Player.Type currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void updateMovement(Case newCase) {

        if(this.movement == null) {

            this.movement = new Movement(newCase);

        }
        else {
            this.movement.update(newCase);
        }

    }

    public void resetMovement() {

        this.movement = null;

    }

    public boolean isMovementEmpty() {
        if(this.movement == null) {
            return true;
        }
        else {
            return this.movement.isEmpty();
        }

    }

    public Case getMovementHead() {

        return this.movement.getHead();

    }

    public Case getMovementTail() {

        return this.movement.getTail();

    }

    public void end() {

        this.keystrokeListener.stop();

    }
}

