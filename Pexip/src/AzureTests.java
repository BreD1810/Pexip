import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class AzureTests
{

    private static Grid smallGrid;
    private static WordSearch smallGridSearch;
    private static ArrayList<String> words;
    private static Random rand = new Random();

    @BeforeAll
    static void setUp()
    {
        smallGrid = new Grid("aaabccbbc");
        smallGridSearch = new WordSearch(smallGrid);
    }

    @Test
    void gridGeneration()
    {
        assertEquals(new ArrayList<>(Arrays.asList(0)), smallGrid.getStringLocations("a"));
        assertEquals(new ArrayList<>(Arrays.asList(3)), smallGrid.getStringLocations("b"));
        assertEquals(new ArrayList<>(Arrays.asList(4)), smallGrid.getStringLocations("c"));
        assertEquals(new ArrayList<>(Arrays.asList(0)), smallGrid.getStringLocations("aa"));
        assertEquals(new ArrayList<>(Arrays.asList(0)), smallGrid.getStringLocations("aaa"));
        assertNull(smallGrid.getStringLocations("aaaa"));
        assertNull(smallGrid.getStringLocations("d"));
    }

    @Test
    void characterBelow()
    {
        assertEquals('c', smallGrid.getCharBelow(1, 1));
        assertEquals('b', smallGrid.getCharBelow(1, 2));
        assertEquals('b', smallGrid.getCharBelow(3, 1));
        assertEquals(' ', smallGrid.getCharBelow(3, 2));
        assertEquals(' ', smallGrid.getCharBelow(-1, 1));
        assertEquals(' ', smallGrid.getCharBelow(3, -1));
    }

    @Test
    void characterRight()
    {
        assertEquals('b', smallGrid.getCharRight(6, 1));
        assertEquals('c', smallGrid.getCharRight(6, 2));
        assertEquals('a', smallGrid.getCharRight(0, 2));
        assertEquals(' ', smallGrid.getCharRight(2, 1));
        assertEquals(' ', smallGrid.getCharRight(0, 3));
        assertEquals(' ', smallGrid.getCharRight(-1, 1));
        assertEquals(' ', smallGrid.getCharRight(1, -1));
        assertEquals(' ', smallGrid.getCharRight(8, 1));
        assertEquals(' ', smallGrid.getCharRight(9, 1));
    }

    @Test
    void smallGrid()
    {
        assertTrue(smallGridSearch.is_present("aaa"));
        assertTrue(smallGridSearch.is_present("abb"));
        assertTrue(smallGridSearch.is_present("acb"));
        assertTrue(smallGridSearch.is_present("bc"));
        assertTrue(smallGridSearch.is_present("a"));
        assertFalse(smallGridSearch.is_present(""));
        assertFalse(smallGridSearch.is_present("abc"));
        assertFalse(smallGridSearch.is_present("accc"));
    }

}
