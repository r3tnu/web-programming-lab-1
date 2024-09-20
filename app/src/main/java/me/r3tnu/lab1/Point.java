package me.r3tnu.lab1;

import java.util.Objects;

public class Point {
    private Double R = null;
    private Double Y = null;
    private Double X = null;

    public Point() {}

    public Point(double pX, double pY, double pR) {
        this.X = pX;
        this.Y = pY;
        this.R = pR;
    }

    public void setR(double pR) {
        this.R = pR;
    }

    public void setRString(String pR) {
        this.R = Double.valueOf(pR);
    }

    public double getR() {
        return this.R;
    }

    public void setY(double pY) {
        this.Y = pY;
    }

    public void setYString(String pY) {
        this.Y = Double.valueOf(pY);
    }

    public double getY() {
        return this.Y;
    }

    public void setX(double pX) {
        this.X = pX;
    }

    public void setXString(String pX) {
        this.X = Double.valueOf(pX);
    }

    public double getX() {
        return this.X;
    }

    public boolean anyIsNull() {
        return Objects.isNull(R) || Objects.isNull(Y) || Objects.isNull(X);
    }
}