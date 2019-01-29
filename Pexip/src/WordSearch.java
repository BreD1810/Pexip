import java.util.ArrayList;

public class WordSearch
{

    private Grid grid;

    WordSearch(String grid) {this.grid = new Grid(grid);}

    WordSearch(Grid grid) {this.grid = grid;}

    public boolean is_present(String word)
    {
        if(!isValidWord(word))
            return false;

        if(word.length() <= Grid.INDEX_LIMIT)
            return containsString(word);


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

    private boolean containsString(String string) {return grid.getStringLocations(string) != null;}

    private boolean wordRight(String word, int location)
    {
        for(int i = 0; i < word.length(); i++)
        {
            if(word.charAt(i) != grid.getCharRight(location, i))
                return false;
        }
        return true;
    }
    
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
