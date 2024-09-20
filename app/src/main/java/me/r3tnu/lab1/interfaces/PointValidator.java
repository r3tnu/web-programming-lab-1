package me.r3tnu.lab1.interfaces;

import me.r3tnu.lab1.Point;
import me.r3tnu.lab1.exceptions.ValidationExeption;;

public interface PointValidator {
    public void validate(Point point) throws ValidationExeption;
}
