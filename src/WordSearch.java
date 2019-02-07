import java.util.ArrayList;

public class WordSearch
{

    private Grid grid;

    WordSearch(String grid) {this.grid = new Grid(grid);}

    WordSearch(Grid grid) {this.grid = grid;}

    /**
     * @param word The word being searched for
     * @return true if the word is in the grid, false otherwise
     */
    public boolean is_present(String word)
    {
        if(!isValidWord(word))
            return false;

        //Check if the list exists if short enough
        if(word.length() <= Grid.INDEX_LIMIT)
            return containsString(word);

        //Otherwise, check left and right directions from possible positions
        ArrayList<Integer> beginningLocations = grid.getStringLocations(word.substring(0, Grid.INDEX_LIMIT));
        if(beginningLocations == null)
            return false;

        for(int location:beginningLocations)
        {
            if(wordRight(word, location) || wordDown(word, location))
                return true;
        }

        return false;
    }

    private boolean isValidWord(String word)
    {
        if (word.length() < 1 || word.length() > grid.ROW_LENGTH)
            return false;

        for(char character:word.toCharArray())
        {
            if(!(character <= 'z' && character >= 'a'))
                return false;
        }

        return true;
    }

    /**
     * Check if there are locations for a string
     */
    private boolean containsString(String string) {return grid.getStringLocations(string) != null;}

    /**
     * Check for a word in the right direction
     */
    private boolean wordRight(String word, int location)
    {
        for(int i = 0; i < word.length(); i++)
        {
            if(word.charAt(i) != grid.getCharRight(location, i))
                return false;
        }
        return true;
    }

    /**
     * Check for a word in the down direction
     */
    private boolean wordDown(String word, int location)
    {
        for(int i = 0; i < word.length(); i++)
        {
            if(word.charAt(i) != grid.getCharBelow(location, i))
                return false;
        }
        return true;
    }

}
