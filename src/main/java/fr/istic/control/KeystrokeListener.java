package fr.istic.control;


import fr.istic.StdDraw;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Classe permettant de detecter les touches appuyees.
 * Declenchement lors de l'appui sur une touche et non lors du relachement.
 * Pour ajouter une touche a detecter, il suffit de l'ajoute dans l'enumeration {@link KeyCodes}.
 * <p>
 * La methode {@link #setHandler(Consumer)} permet de definir une methode
 * a appeler depuis la thread lors de l'appui.
 * <p>
 * Elle remplace la classe AssociationTouches et apporte quelques ameliorations :
 * - Thread separee (pas de blocage de la thread principale)
 * - Gestion des touches appuyees en meme temps
 * - Definition des touches a ecouter dans une enum
 *
 * @author PandaLunatique
 * @author LeMeuble
 * @see KeyCodes
 */
public class KeystrokeListener {

    /**
     * Enumeration des touches a ecouter.
     * Les touches definies dans cette enumeration sont les touches
     * qui seront ecoutees par le {@link KeystrokeListener}.
     * Les codes sont ceux de la librairie {@link StdDraw}.
     */
    public enum KeyCodes {

        ENTER(10),
        LEFT(37),
        UP(38),
        RIGHT(39),
        DOWN(40),
        SHIFT(16),
        ESCAPE(27),
        SPACE(32),
        KEY_T('t'),
        KEY_D('d');

        private final int code;

        KeyCodes(int code) {
            this.code = code;
        }

        KeyCodes(char c) {
            this.code = (int) c - 32;
        }

        /**
         * Obtient le code de la touche
         *
         * @return Le code de la touche
         */
        public int getCode() {
            return this.code;
        }

    }

    private final Set<KeyCodes> keyDowns;
    private final Thread thread;
    private Consumer<KeyCodes> handler;
    private volatile boolean isRunning;

    /**
     * Constructeur de KeystrokeListener
     */
    public KeystrokeListener() {

        this.keyDowns = new HashSet<>();
        this.thread = new Thread(this::run);
        this.handler = (key) -> {
            throw new UnsupportedOperationException("No handler defined");
        };
        this.isRunning = false;

    }

    /**
     * Definir la methode a appeler lors de l'appui sur une touche
     *
     * @param handler La methode a appeler
     */
    public void setHandler(Consumer<KeyCodes> handler) {
        this.handler = handler;
    }

    /**
     * Demarre le thread d'ecoute des touches
     * Si le thread est deja demarre, ne fait rien
     */
    public void start() {

        if (!this.isRunning) {
            this.thread.setDaemon(true);
            this.thread.start();
            this.isRunning = true;
        }

    }

    /**
     * Arrete le thread d'ecoute des touches
     * Si le thread est deja arrete, ne fait rien
     */
    public void stop() {

        this.isRunning = false;
        try {
            this.thread.join();
        }
        catch (InterruptedException ignored) {
        }

    }

    /**
     * Methode principale du thread
     * Ecoute les touches appuyees et les ajoute dans la liste
     * des touches appuyees.
     */
    @SuppressWarnings("BusyWait")
    private void run() {

        try {

            while (this.isRunning) {

                // On parcourt toutes les touches definies
                for (KeyCodes key : KeyCodes.values()) {

                    // Si la touche est appuyee
                    if (StdDraw.isKeyPressed(key.getCode()) && !this.keyDowns.contains(key)) {

                        this.keyDowns.add(key);
                        this.handler.accept(key); // On appelle la methode definie

                        // Si la touche est relachee
                    }
                    else if (!StdDraw.isKeyPressed(key.getCode())) {

                        this.keyDowns.remove(key);

                    }

                }

                // Optimisation : Eviter a la JVM de tourner le plus vite possible
                Thread.sleep(5);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
