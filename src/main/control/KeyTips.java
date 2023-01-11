package main.control;

import main.game.GameState;
import main.game.Player;
import main.map.Case;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum KeyTips {

    SELECTING_MOVE("arrows", 48d, 32d, "D\u00e9placer", GameState.PLAYING_SELECTING),
    SELECTING_ENTER("enter", 16d, 16d, "Utiliser", Case::hasAvailableAction, GameState.PLAYING_SELECTING),
    SELECTING_FIND_ACTION("space", 16d, 16d, "Unit\u00e9 disp.", GameState.PLAYING_SELECTING),
    SELECTING_SKIP_TURN("d", 16d, 16d, "Passer le tour", GameState.PLAYING_SELECTING),
    SELECTING_PAUSE_MENU("escape", 16d, 16d, "Pause", GameState.PLAYING_SELECTING),

    ACTION_MENU_SELECT("up_down", 16d, 32, "S\u00e9l\u00e9ct.", GameState.PLAYING_SELECTING_FACTORY_UNIT, GameState.PLAYING_SELECTING_UNIT_ACTION, GameState.PLAYING_SELECTING_DROPPED_UNIT),
    ACTION_MENU_VALIDATE("enter", 16d, 16d, "Valider", GameState.PLAYING_SELECTING_FACTORY_UNIT, GameState.PLAYING_SELECTING_UNIT_ACTION, GameState.PLAYING_SELECTING_DROPPED_UNIT),
    ACTION_MENU_LEAVE("escape", 16d, 16d, "Annuler", GameState.PLAYING_SELECTING_FACTORY_UNIT, GameState.PLAYING_SELECTING_UNIT_ACTION, GameState.PLAYING_SELECTING_DROPPED_UNIT);


    private final String texture;
    private final double textureWidth;
    private final double textureHeight;
    private final String text;
    private final BiPredicate<Case, Player.Type> condition;
    private final GameState[] associatedState;

    KeyTips(String texture, double textureWidth, double textureHeight, String text, GameState... associatedState) {

        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.text = text;
        this.condition = (c, p) -> true;
        this.associatedState = associatedState;

    }

    KeyTips(String texture, double textureWidth, double textureHeight, String text, BiPredicate<Case, Player.Type> condition, GameState... associatedState) {

        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.text = text;
        this.condition = condition;
        this.associatedState = associatedState;

    }

    public String getTexture() {
        return this.texture;
    }

    public double getTextureWidth() {
        return this.textureWidth;
    }

    public double getTextureHeight() {
        return this.textureHeight;
    }

    public String getText() {
        return this.text;
    }

    public static List<KeyTips> getAssociatedKeyTips(GameState state, Case currentCase, Player.Type player) {
        return Arrays.stream(KeyTips.values())
                .filter(keyTip -> Arrays.asList(keyTip.associatedState).contains(state) || keyTip.associatedState.length == 0)
                .filter(keyTip -> keyTip.condition.test(currentCase, player))
                .collect(Collectors.toList());
    }

}
