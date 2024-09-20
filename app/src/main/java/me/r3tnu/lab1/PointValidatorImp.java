package me.r3tnu.lab1;

import java.util.Arrays;

import me.r3tnu.lab1.exceptions.ValidationExeption;
import me.r3tnu.lab1.interfaces.PointValidator;

public class PointValidatorImp implements PointValidator {
    public void validate(Point point) throws ValidationExeption{
        if (point.anyIsNull()) {
            throw new ValidationExeption("Some of the point coordinates are null");
        }
        
        boolean xIsValid = point.getX() >= -3 && point.getX() <= 5;
        boolean yIsValid = point.getY() >= -5 && point.getY() <= 3;
        boolean rIsValid = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0).contains(point.getR());

        if (!xIsValid || !yIsValid || !rIsValid) {
            throw new ValidationExeption("Some of the point values are invalid");
        }
    } 
}