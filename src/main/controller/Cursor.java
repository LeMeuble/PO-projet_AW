package main.controller;

import ressources.Config;

public class Cursor {

    private final int maxWidth;
    private final int maxHeight;
    private int currentX;
    private int currentY;
    private boolean needsRender;

    public Cursor(int maxWidth, int maxHeight) {

        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        this.currentX = 0;
        this.currentY = 0;

    }

    public int getCurrentX() {
        return this.currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }


    public boolean up() {

        if(this.currentY < this.maxHeight - 1) {
            this.currentY++;
            this.needsRender = true;
            return true;
        }
        return false;

    }

    public boolean down() {

        if(this.currentY > 0) {
            this.currentY--;
            this.needsRender = true;
            return true;
        }
        return false;

    }

    public boolean right() {

        if(this.currentX < this.maxWidth - 1) {
            this.currentX++;
            this.needsRender = true;
            return true;
        }
        return false;

    }

    public boolean left() {

        if(this.currentX > 0) {
            this.currentX--;
            this.needsRender = true;
            return true;
        }
        return false;

    }

    public boolean needsRefresh() {
        return this.needsRender;
    }

    public void refreshed() {
        this.needsRender = false;
    }

}
