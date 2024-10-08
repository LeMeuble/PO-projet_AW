package fr.istic;

import fr.istic.PathUtil;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant de faire des logs de l'etat du jeu
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Logger {

    private static final String LOG_DIR = PathUtil.ROOT_FOLDER + PathUtil.SEP + "logs";

    /**
     * Enumeration des types de messages (informatif, erreur, plantage...)
     */
    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    private static class InstanceHolder {

        private static final Map<String, Logger> loggers = new HashMap<>();

        private static Logger getLogger(String name) {
            if (!loggers.containsKey(name)) {
                loggers.put(name, new Logger(name));
            }
            return loggers.get(name);
        }

    }

    static {

        final File directory = new File(LOG_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

    }

    private final String name;
    private BufferedWriter writer;
    private boolean isInitialized = false;

    public Logger(String name) {

        this.name = name;
        this.initWriter();

    }

    public static void closeAll() {
        InstanceHolder.loggers.values().forEach(Logger::close);
    }

    public static Logger getLogger() {

        return InstanceHolder.getLogger(Thread.currentThread().getName().toLowerCase());

    }

    public void initWriter() {
        try {
            this.writer = new BufferedWriter(new FileWriter(LOG_DIR + PathUtil.SEP + name + ".log", true), 512);
            this.isInitialized = true;
        }
        catch (IOException ignored) {
            ignored.printStackTrace();
            this.writer = null;
            this.isInitialized = false;
        }

    }

    public String getDatetime() {

        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

    }

    public void log(String message) {
        this.log(Level.INFO, message);
    }

    public void log(Level level, String message) {

        try {

            if (!this.isInitialized) {
                this.initWriter();
            }

            this.writer.write("[" + this.getDatetime() + "]" + " (" + level.name() + ") " + message + "\n");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write(Exception exception) {

        try {

            if (!this.isInitialized) {
                this.initWriter();
            }

            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);

            String[] lines = sw.toString().split(System.getProperty("line.separator"));
            for (String line : lines) {
                this.writer.write("[" + this.getDatetime() + "]" + " (" + Level.ERROR.name() + ") " + line + "\n");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void close() {
        try {
            this.writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
