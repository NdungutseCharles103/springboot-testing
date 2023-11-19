package rw.ac.rca.gradesclassb.utils;

import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

public class SchoolMenuTest {
    @Test
    void whenSunday_schoolMenu_shouldBeMaize(){
        assertEquals(SchoolMenu.getMenuByDayOfWeek(DayOfWeek.SUNDAY),"Maize");
    }
    @Test
    void whenMonday_schoolMenu_shouldBePotato(){
        assertEquals(SchoolMenu.getMenuByDayOfWeek(DayOfWeek.MONDAY),"Potato");
    }

    @Test
    void whenFriday_schoolMenu_shouldNotBeSnacks(){
        assertNotEquals(SchoolMenu.getMenuByDayOfWeek(DayOfWeek.FRIDAY),"Snacks");
    }
}
