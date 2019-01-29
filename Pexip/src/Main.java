import java.util.ArrayList;

/**
 * Example implementation of how the WordSearch class will be used.
 * Needs to be edited to include a proper grid and list of words to be searched for.
 */
public class Main
{

    public static void main(String[] args)
    {
        ArrayList<String> words_to_find = new ArrayList<>();
        String grid = "";

        WordSearch ws = new WordSearch(grid);
        for(String word:words_to_find)
            if (ws.is_present(word))
                System.out.println("found " + word);
    }

}
