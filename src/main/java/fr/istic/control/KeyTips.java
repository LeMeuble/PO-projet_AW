package fr.istic.control;

import fr.istic.MiniWars;
import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.game.Game;
import fr.istic.game.GameState;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Classe representant les indicateurs de touches et leurs conditions
 * d'affichages.
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public enum KeyTips {


    MOVE("arrows", 48d, 32d, "S\u00e9lectionner",
            GameState.PLAYING_SELECTING,
            GameState.PLAYING_MOVING_UNIT,
            GameState.PLAYING_SELECTING_TARGET,
            GameState.PLAYING_SELECTING_TRANSPORT,
            GameState.PLAYING_SELECTING_DROP_ZONE
    ),
    UP_DOWN_MENU("up_down", 16d, 32d, "S\u00e9lectionner",
            GameState.PLAYING_SELECTING_SKIP_TURN_ACTION,
            GameState.PLAYING_SELECTING_FACTORY_UNIT,
            GameState.PLAYING_SELECTING_UNIT_ACTION,
            GameState.PLAYING_SELECTING_DROPPED_UNIT
    ),

    ESCAPE_PAUSE("escape", 16d, 16d, "Pause", GameState.PLAYING_SELECTING),
    ESCAPE("escape", 16d, 16d, "Annuler",
            GameState.PLAYING_SELECTING_FACTORY_UNIT,
            GameState.PLAYING_SELECTING_UNIT_ACTION,
            GameState.PLAYING_SELECTING_DROPPED_UNIT,
            GameState.PLAYING_SELECTING_SKIP_TURN_ACTION,
            GameState.PLAYING_SELECTING_TARGET,
            GameState.PLAYING_SELECTING_TRANSPORT,
            GameState.PLAYING_SELECTING_DROP_ZONE,
            GameState.PLAYING_MOVING_UNIT
    ),

    ENTER_SELECTING("enter", 16d, 16d, "Utiliser", Case::hasAvailableAction, GameState.PLAYING_SELECTING),
    ENTER_VALIDATE_MENU("enter", 16d, 16d, "Valider",
            GameState.PLAYING_SELECTING_SKIP_TURN_ACTION,
            GameState.PLAYING_SELECTING_DROPPED_UNIT,
            GameState.PLAYING_SELECTING_FACTORY_UNIT,
            GameState.PLAYING_SELECTING_UNIT_ACTION
    ),
    ENTER_VALIDATE_DROP("enter", 16d, 16d, "D\u00e9poser",
            (c, p) -> {
                final Game game = MiniWars.getInstance().getCurrentGame();
                return game != null && (game.getOverlayCases().contains(c) || game.getOverlayCases().isEmpty());
            },
            GameState.PLAYING_SELECTING_DROP_ZONE
    ),
    ENTER_VALIDATE_TARGET("enter", 16d, 16d, "Attaquer",
            (c, p) -> {
                final Game game = MiniWars.getInstance().getCurrentGame();
                return game != null && (game.getOverlayCases().contains(c) || game.getOverlayCases().isEmpty());
            },
            GameState.PLAYING_SELECTING_TARGET
    ),
    ENTER_VALIDATE_MOVE("enter", 16d, 16d, "Se d\u00e9placer",
            (c, p) -> {
                final Game game = MiniWars.getInstance().getCurrentGame();
                return game != null && (game.getOverlayCases().contains(c) || game.getOverlayCases().isEmpty());
            },
            GameState.PLAYING_MOVING_UNIT
    ),
    ENTER_VALIDATE_TRANSPORT("enter", 16d, 16d, "Monter",
            (c, p) -> {
                final Game game = MiniWars.getInstance().getCurrentGame();
                return game != null && (game.getOverlayCases().contains(c) || game.getOverlayCases().isEmpty());
            },
            GameState.PLAYING_SELECTING_TRANSPORT
    ),

    SPACE_SELECTING("space", 16d, 16d, "Unit\u00e9s disp.", GameState.PLAYING_SELECTING),
    D_S("d", 16d, 16d, "Passer le tour", GameState.PLAYING_SELECTING);


    private final String texture;
    private final double textureWidth;
    private final double textureHeight;
    private final String text;
    private final BiPredicate<Case, Player.Type> condition;
    private final GameState[] associatedState;


    /**
     * Constructeur de l'enumeration.
     *
     * @param texture         Le nom de la texture a afficher (dans le dossier des textures)
     * @param textureWidth    La largeur de la texture
     * @param textureHeight   La hauteur de la texture
     * @param text            Texte a afficher
     * @param associatedState Les etats de jeu associes a l'indicateur
     */
    KeyTips(String texture, double textureWidth, double textureHeight, String text, GameState... associatedState) {

        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.text = text;
        this.condition = (c, p) -> true;
        this.associatedState = associatedState;

    }

    /**
     * Constructeur de l'enumeration avec condition.
     *
     * @param texture         Le nom de la texture a afficher (dans le dossier des textures)
     * @param textureWidth    La largeur de la texture
     * @param textureHeight   La hauteur de la texture
     * @param text            Texte a afficher
     * @param condition       Condition d'affichage dependante de {@link Case} et {@link Player.Type}
     * @param associatedState Les etats de jeu associes a l'indicateur
     */
    KeyTips(String texture, double textureWidth, double textureHeight, String text, BiPredicate<Case, Player.Type> condition, GameState... associatedState) {

        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.text = text;
        this.condition = condition;
        this.associatedState = associatedState;

    }

    /**
     * Obtenir la liste des indicateurs associes a un etat de jeu, une case et une joueur.
     *
     * @param state       Etat de jeu actuel.
     * @param currentCase Case actuelle.
     * @param player      Joueur actuel.
     *
     * @return Liste des indicateurs associes a un etat de jeu, une case et une joueur.
     *
     * @see GameState
     * @see Case
     * @see Player.Type
     */
    public static List<KeyTips> getAssociatedKeyTips(GameState state, Case currentCase, Player.Type player) {
        return Arrays.stream(KeyTips.values())
                .filter(keyTip -> Arrays.asList(keyTip.associatedState).contains(state) || keyTip.associatedState.length == 0)
                .filter(keyTip -> keyTip.condition.test(currentCase, player))
                .collect(Collectors.toList());
    }

    /**
     * Obtenir la texture de l'indicateur.
     *
     * @return La texture de l'indicateur
     */
    public String getTexture() {
        return this.texture;
    }

    /**
     * Obtenir la largeur de la texture de l'indicateur.
     *
     * @return La largeur de la texture de l'indicateur
     */
    public double getTextureWidth() {
        return this.textureWidth;
    }

    /**
     * Obtenir la hauteur de la texture de l'indicateur.
     *
     * @return La hauteur de la texture de l'indicateur
     */
    public double getTextureHeight() {
        return this.textureHeight;
    }

    /**
     * Obtenir le texte de l'indicateur.
     *
     * @return Le texte de l'indicateur
     */
    public String getText() {
        return this.text;
    }

}
