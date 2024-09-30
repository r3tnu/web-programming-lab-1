package me.r3tnu.lab1;

public class Result {
    private boolean result;
    private double x;
    private double y;
    private double r;


    public Result(boolean result, double x, double y, double r) {
        this.result = result;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    boolean getResult() {
        return this.result;
    }

    double getX() {
        return this.x;
    }

    double getY() {
        return this.y;
    }

    double getR() {
        return this.r;
    }
}
