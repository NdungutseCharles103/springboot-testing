package rw.ac.rca.gradesclassb.utils;

public class DegreeConversion {
    public static double convertToCelsius(double fahrenheit){
        return (fahrenheit - 32) * 5 / 9;
    }
    public static double convertToFahrenheit(double celsius){
        return (celsius * 9 / 5) + 32;
    }
}
