package fr.istic.game;

import fr.istic.MiniWars;
import fr.istic.render.PopupRegistry;

/**
 * Classe representant la boucle de jeu
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class GameLoop {

    private final Thread thread;
    private Runnable handler;
    private volatile boolean isRunning;

    /**
     * Constructeur de la GameLoop
     */
    public GameLoop() {

        this.thread = new Thread(this::run);
        this.handler = () -> {
            throw new UnsupportedOperationException("No handler defined");
        };
        this.isRunning = false;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> MiniWars.getInstance().end()));

    }

    /**
     * Definir la methode a appelle pour actualiser le jeu.
     *
     * @param handler Methode a appeller
     */
    public void setHandler(Runnable handler) {
        this.handler = handler;
    }

    /**
     * Permet le demarrage de la thread interne.
     */
    public void start() {

        if (!this.isRunning) {
            this.thread.setDaemon(true);
            this.thread.start();
            this.isRunning = true;
        }

    }

    /**
     * Permet de stopper la thread interne.
     */
    public void stop() {
        this.isRunning = false;
        try {
            this.thread.join();
        }
        catch (InterruptedException ignored) {
        }
    }

    @SuppressWarnings("BusyWait")
    /**
     * Runnable de la thread interne.
     */
    public void run() {

        try {

            while (this.isRunning) {
                PopupRegistry.getInstance().garbageCollect();
                this.handler.run();
                Thread.sleep(50);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
