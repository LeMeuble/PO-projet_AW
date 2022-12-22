package main.controller;

import ressources.Config;

public class Cursor {

    private int currentX;
    private int currentY;

    public Cursor() {

        this.currentX = 0;
        this.currentY = 0;

    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void up() {

        if(this.currentY < Config.MAP_ROW_COUNT - 1) {
            this.currentY++;
        }

    }

    public void down() {

        if(this.currentY > 0) {
            this.currentY--;
        }

    }

    public void right() {

        if(this.currentX < Config.MAP_COLUMN_COUNT - 1) {
            this.currentX ++;
        }

    }

    public void left() {

        if(this.currentX > 0) {
            this.currentX--;
        }

    }

}
