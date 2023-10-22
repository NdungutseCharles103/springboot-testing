package rw.ac.rca.gradesclassb.utils;

import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class DegreeConversionTest {
    @Test
    void canConvertToCelsius(){
        double actual = DegreeConversion.convertToCelsius(32);
        double expected = 0;
        Assert.assertEquals(actual,expected);
    }

    @Test
    void canConvertToFahrenheit(){
        double actual = DegreeConversion.convertToFahrenheit(0);
        double expected = 32;
        Assert.assertEquals(actual,expected);
    }

    // happy path
    @Test
    void canConvertToCelsius2(){
        double actual = DegreeConversion.convertToCelsius(168.8);
        double expected = 76;
        Assert.assertEquals(actual,expected);
    }

    // happy path 2
    @Test
    void canConvertToFahrenheit2(){
        double actual = DegreeConversion.convertToFahrenheit(30);
        double expected = 86;
        Assert.assertEquals(actual,expected);
    }
}
