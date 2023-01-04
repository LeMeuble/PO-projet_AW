package main.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class PopupRegistry {

    public final Stack<Popup> popups;

    public PopupRegistry() {
        this.popups = new Stack<>();
    }

    public void push(Popup popup) {
        this.popups.push(popup);
    }

    public List<Popup> getPopups() {
        return new ArrayList<>(this.popups);
    }



}
