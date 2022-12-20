package main.controller;

import jdk.nashorn.internal.ir.annotations.Ignore;
import librairies.StdDraw;

import java.util.*;
import java.util.function.Consumer;

public class KeystrokeListener {

    public enum KeyCodes {

        ENTER(10),
        LEFT(37),
        UP(38),
        RIGHT(39),
        DOWN(40),
        SHIFT(16),
        ESCAPE(27),
        KEY_T('t');

        private final int code;

        KeyCodes(int code) {
            this.code = code;
        }

        KeyCodes(char c) {
            this.code = (int) c - 32;
        }

        public int getCode() {
            return this.code;
        }

    }

    private Thread thread;
    private Consumer<KeyCodes> handler;
    private final Set<KeyCodes> keyDowns;

    private volatile boolean isRunning;

    public KeystrokeListener() {

        this.keyDowns = new HashSet<>();
        this.thread = null;
        this.isRunning = false;

    }

    public void setHandler(Consumer<KeyCodes> handler) {
        this.handler = handler;
    }

    public void start() {

        this.isRunning = true;
        this.thread = new Thread(this::run);
        this.thread.start();

    }

    public void stop() {

        this.isRunning = false;
        this.thread.interrupt();

    }

    @SuppressWarnings("BusyWait")
    private void run() {

        try {

            synchronized (this) {

                while (this.isRunning) {

                    for (KeyCodes key : KeyCodes.values()) {

                        if (StdDraw.isKeyPressed(key.getCode()) && !this.keyDowns.contains(key)) {

                            this.keyDowns.add(key);
                            this.handler.accept(key);

                        } else if (!StdDraw.isKeyPressed(key.getCode())) {

                            this.keyDowns.remove(key);

                        }

                    }

                    Thread.sleep(5);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
