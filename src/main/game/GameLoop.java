package main.game;

import main.Logger;
import main.render.PopupRegistry;

public class GameLoop {

    private final Thread thread;
    private Runnable handler;
    private volatile boolean isRunning;

    public GameLoop() {

        this.thread = new Thread(this::run);
        this.handler = () -> {
            throw new UnsupportedOperationException("No handler defined");
        };
        this.isRunning = false;

    }

    public void setHandler(Runnable handler) {
        this.handler = handler;
    }


    public void start() {

        if (!this.isRunning) {
            this.thread.setDaemon(true);
            this.thread.start();
            this.isRunning = true;
        }

    }

    public void stop() {
        this.isRunning = false;
        try {
            this.thread.join();
        }
        catch (InterruptedException ignored) {}
    }

    @SuppressWarnings("BusyWait")
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
