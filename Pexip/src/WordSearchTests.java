import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class WordSearchTests
{

    private static final int ROW_LENGTH = 10000;
    private static String largeGridString;
    private static Grid smallGrid, largeGrid;
    private static WordSearch smallGridSearch, largeGridSearch;
    private static ArrayList<String> words;
    private static Random rand = new Random();

    @BeforeAll
    static void setUp()
    {
        smallGrid = new Grid("aaabccbbc");
        smallGridSearch = new WordSearch(smallGrid);
        largeGrid = generateLargeGrid();
        largeGridSearch = new WordSearch(largeGrid);
        words = generateWords();
    }

    private static Grid generateLargeGrid()
    {
        Random rand = new Random();
        StringBuilder gridString = new StringBuilder();

        for(int i = 0; i < ((ROW_LENGTH * ROW_LENGTH) - 9); i++)
            gridString.append((char)(rand.nextInt(26) + 'a'));

        //"testwords" going right in a valid position
        int position = rand.nextInt(ROW_LENGTH*ROW_LENGTH) - 9;
        int noLeftOnRow = (Math.round(position/ROW_LENGTH) + 1) * ROW_LENGTH - position - 1;
        while(noLeftOnRow <= 9)
        {
            position = rand.nextInt(ROW_LENGTH*ROW_LENGTH) - 9;
            noLeftOnRow = (Math.round(position/ROW_LENGTH) + 1) * ROW_LENGTH - position - 1;
        }
        gridString.insert(position, "testwords");

        //"correctword" going down in a valid position
        String word = "correctword";
        position = rand.nextInt(ROW_LENGTH * ROW_LENGTH);
        while(position + (11 * ROW_LENGTH) > (ROW_LENGTH * ROW_LENGTH))
            position = rand.nextInt(ROW_LENGTH * ROW_LENGTH);
        for(int i = 0; i < word.length(); i++)
        {
            int charPosition = position + (i * ROW_LENGTH);
            gridString.replace(charPosition, charPosition, String.valueOf(word.charAt(i)));
        }

        largeGridString = gridString.toString();
        return new Grid(gridString.toString());
    }

    private static ArrayList<String> generateWords()
    {
        ArrayList<String> words = new ArrayList<>();
        for(int i = 0; i < 1000000; i++)
        {
            if(rand.nextBoolean())
                words.add(generateRightWord());
            else
                words.add(generateDownWord());
        }
        return words;
    }

    private static String generateRightWord()
    {
        int wordLength = rand.nextInt(25) + 1;
        int row = rand.nextInt(ROW_LENGTH);
        int column = rand.nextInt(ROW_LENGTH - wordLength);
        int position = (ROW_LENGTH * row) + column;
        return largeGridString.substring(position, position + wordLength + 1);
    }

    private static String generateDownWord()
    {
        StringBuilder word = new StringBuilder();
        int wordLength = rand.nextInt(25) + 1;
        int row = rand.nextInt(ROW_LENGTH - wordLength);
        int column = rand.nextInt(ROW_LENGTH);
        int position = (ROW_LENGTH * row) + column;
        for(int i = 0; i < wordLength; i++)
        {
            word.append(largeGridString.charAt(position + (i * ROW_LENGTH)));
        }
        return word.toString();
    }

    private boolean largeGridContains(String word)
    {
        if(largeGridString.contains(word))
        {
            int location = largeGridString.indexOf(word);
            int noLeftOnRow = (Math.round(location/ROW_LENGTH) + 1) * ROW_LENGTH - location - 1;
            if(noLeftOnRow >= word.length())
            {
                return true;
            }
        }

        int nextLetterPosition;
        ArrayList<Integer> possibleWordStarts = largeGrid.getStringLocations(word.substring(0, Grid.INDEX_LIMIT));
        for(int position : possibleWordStarts)
        {
            for(int i = 1; i < word.length(); i++)
            {
                nextLetterPosition = position + (i * ROW_LENGTH);
                if(nextLetterPosition >= largeGridString.length() || !(largeGridString.charAt(nextLetterPosition) == word.charAt(i)))
                    break;
                if(i == word.length()-1)
                    return true;
            }
        }

        return false;
    }

    //"aaabccbbc"
    @Test
    void gridGeneration()
    {
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 2)), smallGrid.getStringLocations("a"));
        assertEquals(new ArrayList<>(Arrays.asList(3, 6, 7)), smallGrid.getStringLocations("b"));
        assertEquals(new ArrayList<>(Arrays.asList(4, 5, 8)), smallGrid.getStringLocations("c"));
//        assertEquals(new ArrayList<>(Arrays.asList(0)), smallGrid.getStringLocations("aaa"));
        assertNull(smallGrid.getStringLocations("aaa"));
//        assertEquals(new ArrayList<>(Arrays.asList(0, 1)), smallGrid.getStringLocations("aa"));
        assertNull(smallGrid.getStringLocations("aa"));
//        assertEquals(new ArrayList<>(Arrays.asList(0)), smallGrid.getStringLocations("abb"));
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
//        assertTrue(smallGrid.check("aaa", 0));
//        assertTrue(smallGrid.checkWordRight("aa", 1));
//        assertFalse(smallGrid.checkWordRight("aa", 2));
//        assertFalse(smallGrid.checkWordRight("aa", 3));
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

    @Test
    void largeGrid1WordRight()
    {
        assertTrue(largeGridSearch.is_present("testwords"));
//        assertTrue(largeGridContains("testwords"));
//        assertSame(largeGridContains("abcdefghi"), largeGridSearch.is_present("abcdefghi"));
    }

    @Test
    void largeGrid1WordDown()
    {
        assertTrue(largeGridSearch.is_present("correctword"));
//        assertTrue(largeGridContains("correctword"));
//        assertSame(largeGridContains("abcdefghi"), largeGridSearch.is_present("abcdefghi"));
    }

    @Test
    void largeWordNumber()
    {
        for (String word:words)
            assertTrue(largeGridSearch.is_present(word));
    }

}
