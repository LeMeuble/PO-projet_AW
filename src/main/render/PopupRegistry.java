package main.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class PopupRegistry {

    private static PopupRegistry instance;

    public final Stack<Popup> popups;

    private PopupRegistry() {
        this.popups = new Stack<>();
    }

    public static PopupRegistry getInstance() {
        if(instance == null) {
            instance = new PopupRegistry();
        }
        return instance;
    }

    public void push(Popup popup) {
        this.popups.push(popup);
    }

    public List<Popup> getPopups() {
        return new ArrayList<>(this.popups);
    }

}
