package me.r3tnu.lab1;

import java.util.Arrays;

import me.r3tnu.lab1.exceptions.ValidationExeption;
import me.r3tnu.lab1.interfaces.PointValidator;

public class PointValidatorImp implements PointValidator {
    public void validate(Point point) throws ValidationExeption{
       
        boolean isXValid = point.getX() > -3 && point.getX() < 5;
        boolean isYValid = point.getY() > -5 && point.getY() < 3;
        boolean isRValid = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0).contains(point.getR());

        String errorMessageString = "";
        if (!isXValid) {
            errorMessageString.concat("The x parameter must be in (-3; 5)\n");
        }
        if (!isYValid) {
            errorMessageString.concat("The y parameter must be in (-5; 3)\n");
        }
        if (!isRValid) {
            errorMessageString.concat("The r parameter must be in {1.0, 1.5, 2.0, 2.5, 3.0}");
        }

        if (!errorMessageString.isEmpty()) {
            throw new ValidationExeption(errorMessageString);
        }
    } 
}