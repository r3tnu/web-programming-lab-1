package me.r3tnu.lab1;

public class Point {
    private double r;
    private double y;
    private double x;

    public Point(double pX, double pY, double pR) {
        this.x = pX;
        this.y = pY;
        this.r = pR;
    }

    public double getR() {
        return this.r;
    }

    public double getY() {
        return this.y;
    }

    public double getX() {
        return this.x;
    }
}