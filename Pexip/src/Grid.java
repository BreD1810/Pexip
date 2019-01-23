import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grid
{

    private String contents;
    private int sideLength;
    private Map<Character, ArrayList<Integer>> charLocations = new HashMap<>();

    Grid(String input)
    {
        contents = input;
        sideLength = Double.valueOf(Math.sqrt(input.length())).intValue();
        generateLocations();
    }

    private void generateLocations()
    {
        for(int i = 0; i < contents.length(); i++)
        {
            char currentChar = contents.charAt(i);
            if(charLocations.containsKey(currentChar))
            {
                charLocations.get(currentChar).add(i);
            }
            else
            {
                ArrayList<Integer> locationList = new ArrayList<>();
                locationList.add(i);
                charLocations.put(currentChar, locationList);
            }
        }
    }

}
