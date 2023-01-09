package main.render;

import librairies.StdDraw;
import ressources.Config;
import ressources.DisplayUtil;
import ressources.PathUtil;

import java.util.*;

public class PopupRegistry {

    public static final int MAX_POPUPS = 5;

    private static final class InstanceHolder {

        static final PopupRegistry instance = new PopupRegistry();

    }
    public volatile Queue<Popup> popups;
    public volatile boolean needsRefresh;

    private PopupRegistry() {
        this.popups = new PriorityQueue<>(Comparator.comparingLong(Popup::getCreationTime));
    }

    public static PopupRegistry getInstance() {
        return InstanceHolder.instance;
    }

    public synchronized void push(Popup popup) {
        if (this.popups.size() >= MAX_POPUPS) {
            this.popups.poll();
        }
        this.popups.add(popup);
        this.needsRefresh = true;
    }

    public synchronized List<Popup> getPopups() {
        return new ArrayList<>(this.popups);
    }

    public synchronized void garbageCollect() {
        this.needsRefresh = this.popups.removeIf(Popup::isExpired);
    }

    public synchronized void clear() {
        this.popups.clear();
        this.needsRefresh = true;
    }

    public synchronized boolean needsRefresh() {
        return this.needsRefresh;
    }

    public synchronized void needsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }

    public void render() {

        double actualWidth = 960 + 32;
        double actualHeight = 406;

        double aroundMargin = 32;
        double innerMargin = 10;

        double x = (Config.WIDTH - (actualWidth / 10)) - aroundMargin;
        double y = Config.HEIGHT - (actualHeight / 10) - aroundMargin;

        double widthOffset = 0;
        for (Popup popup : this.getPopups()) {
            widthOffset = Math.max(widthOffset, DisplayUtil.getTextWidth(Config.FONT_20, popup.getTitle()) + 40 - actualWidth / 5);
        }

        for (Popup popup : this.getPopups()) {

            DisplayUtil.drawPicture(x, y, PathUtil.getGlobalGuiPath("popup"), actualWidth / 5 + widthOffset, actualHeight / 5);

            StdDraw.setFont(Config.FONT_20);
            StdDraw.setPenColor(StdDraw.BLACK);

            StdDraw.textLeft(x - actualWidth / 10 + 8, y + 18, popup.getTitle());
            StdDraw.setFont(Config.FONT_18);
            StdDraw.setPenColor(StdDraw.GRAY);

            int i = 0;
            for (String line : popup.getMessage(22)) {
                StdDraw.textLeft(x - actualWidth / 10 + 8, y - 2 - i * 20, line);
                i++;
            }

            y -= actualHeight / 5 + innerMargin;
        }
        this.needsRefresh(false);

    }

}
