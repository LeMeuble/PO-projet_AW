package main;

import java.io.*;

public class Logger {

    public static Logger instanceGameLoop = new Logger("logs-gameloop.txt");
    public static Logger instanceMain = new Logger("logs-main.txt");
    public static Logger instanceActionHandler = new Logger("logs-handler.txt");

    private final String output;

    public Logger(String output) {

        this.output = output;

    }

    public static Logger getInstanceGameLoop() {
        return instanceGameLoop;
    }

    public static Logger getInstanceMain() {
        return instanceMain;
    }

    public static Logger getInstanceActionHandler() {
        return instanceActionHandler;
    }

    public void log(String message) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(output, true));
            writer.write(message + "\n");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write(Exception exception) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(output, true));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            writer.write(sw.toString());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
