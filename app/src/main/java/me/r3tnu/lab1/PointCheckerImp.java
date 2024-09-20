package me.r3tnu.lab1;

import me.r3tnu.lab1.interfaces.PointChecker;

public class PointCheckerImp implements PointChecker{
    public boolean checkPoint(Point point) {
        double x = point.getX(), y = point.getY(), r = point.getR();
        boolean firstQuadrant = x >= Math.sqrt(r*r - y*y) && x >= 0 && y <= Math.sqrt(r*r - x*x) && y >= 0;
        boolean secondQuadrant = x <= r && x >= 0 && y <= r && y >= 0;
        boolean fourthQuadrant = x >= (-r - 2*y) && x <= 0 && y >= (-0.5*x - 0.5*r) && y <= 0;

        return firstQuadrant || secondQuadrant || fourthQuadrant;
    }
}
