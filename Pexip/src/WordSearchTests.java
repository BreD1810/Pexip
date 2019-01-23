import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


public class WordSearchTests
{

    @BeforeEach
    void setUp()
    {
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void gridGeneration()
    {
        String gridInput = "aabc";
        Grid grid = new Grid(gridInput);
        ArrayList<Integer> aLocations = new ArrayList<>();
        aLocations.add(0);
        aLocations.add(1);
        ArrayList<Integer> bLocation = new ArrayList<>();
        bLocation.add(2);
        ArrayList<Integer> cLocation = new ArrayList<>();
        cLocation.add(3);
        assertEquals(aLocations, grid.getCharLocations('a'));
        assertEquals(bLocation, grid.getCharLocations('b'));
        assertEquals(cLocation, grid.getCharLocations('c'));
    }

}
