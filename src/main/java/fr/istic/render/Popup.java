package fr.istic.render;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant une popup
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Popup {

    private static final int DURATION = 3000;

    private final String title;
    private final String message;
    private final long creationTime;

    /**
     * Constructeur de la popup
     *
     * @param title   Le titre de la popup
     * @param message Le message de la popup
     */
    public Popup(String title, String message) {

        this.title = title;
        this.message = message;
        this.creationTime = System.currentTimeMillis();

    }

    public String getTitle() {
        return this.title;
    }

    /**
     * Permet de recuperer le message de la popup sous forme d'une liste de ligne de
     * maxLineLength characters.
     *
     * @param maxLineLength Nombre de caracteres maximums par ligne
     *
     * @return Listes des lines de caracteres
     */
    public List<String> getMessage(int maxLineLength) {

        List<String> lines = new ArrayList<>();
        String[] words = this.message.split(" ");

        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() > maxLineLength) {
                lines.add(line.toString());
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.creationTime >= DURATION;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

}
