import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class WordSearchTests
{

    private Grid smallGrid;
    private WordSearch smallGridSearch;

    @BeforeEach
    void setUp()
    {
        smallGrid = new Grid("aaabccbbc");
        smallGridSearch = new WordSearch(smallGrid);
    }

    @Test
    void gridGeneration()
    {
        ArrayList<Integer> aLocations = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> bLocation = new ArrayList<>(Arrays.asList(3, 6, 7));
        ArrayList<Integer> cLocation = new ArrayList<>(Arrays.asList(4, 5, 8));
        assertEquals(aLocations, smallGrid.getCharLocations('a'));
        assertEquals(bLocation, smallGrid.getCharLocations('b'));
        assertEquals(cLocation, smallGrid.getCharLocations('c'));
        assertNull(smallGrid.getCharLocations('d'));
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
