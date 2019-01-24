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

        if(word.length() == 1)
            return containsCharacter(word.charAt(0));

        ArrayList<Integer> firstCharLocations = grid.getCharLocations(word.charAt(0));
        for(int location:firstCharLocations)
        {
            if(wordRight(word, location) || wordDown(word, location))
                return true;
        }

        return false;
    }

    private boolean isValidWord(String word)
    {
        if (word.length() < 1 || word.length() > grid.getRowLength())
            return false;

        for(char character:word.toCharArray())
        {
            if(!(character <= 'z' && character >= 'a'))
                return false;
        }

        return true;
    }

    private boolean containsCharacter(char character) {return grid.getCharLocations(character) != null;}

    private boolean wordRight(String word, int location)
    {
        for(int i = 1; i < word.length(); i++)
        {
            if(word.charAt(i) != grid.getCharRight(location, i))
                return false;
        }
        return true;
    }
    
    private boolean wordDown(String word, int location)
    {
        for(int i = 1; i < word.length(); i++)
        {
            if(word.charAt(i) != grid.getCharBelow(location, i))
                return false;
        }
        return true;
    }

}
