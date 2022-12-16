package main;

public class Cursor {

    private int currentX;
    private int currentY;

    public Cursor() {

        currentX = 0;
        currentY = 0;

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

        if(this.currentY < Jeu.borderY - 1) {

            this.currentY ++;

        }

    }

    public void down() {

        if(this.currentY > 0) {

            this.currentY--;

        }

    }

    public void left() {

        if(this.currentX > 0) {

            this.currentX--;

        }

    }

    public void right() {

        if(this.currentX < Jeu.borderX - 1) {

            this.currentX ++;

        }

    }

}
