package src.basics;

import java.util.Random;

public class CustomRand extends Random{
    
    public double getUniform(double lower, double upper)
    {
        // Compatiple whether lower < upper or not
        return Math.min(lower,upper) + Math.abs(upper - lower)*nextDouble();
    }
}
