import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grid
{

    private String contents;
    private final int ROW_LENGTH;
    private Map<Character, ArrayList<Integer>> charLocations = new HashMap<>();

    Grid(String input)
    {
        contents = input;
        ROW_LENGTH = Double.valueOf(Math.sqrt(input.length())).intValue();
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

    public ArrayList<Integer> getCharLocations(char character) {return charLocations.get(character);}

    /**
     * @param location The location of the character being queried
     * @param noBelow The number of positions below the queried character
     * @return The character in the requested position - Returns a space if invalid
     */
    public char getCharBelow(int location, int noBelow)
    {
        int position = location + (noBelow * ROW_LENGTH);
        if(location < 0 || location > contents.length() || noBelow < 1 || position >= contents.length())
            return ' ';
        return contents.charAt(position);
    }

    /**
     * @param location The location of the character being queried
     * @param noRight The number of positions to the right of the queried character
     * @return The character in the requested position - Returns a space if invalid
     */
    public char getCharRight(int location, int noRight)
    {
        int noLeftOnRow = (Math.round(location/ROW_LENGTH) + 1) * ROW_LENGTH - location - 1;
        if(location < 0 || location > contents.length() || noRight < 1 || noRight > noLeftOnRow)
            return ' ';
        return contents.charAt(location + noRight);
    }

    public int getRowLength() {return ROW_LENGTH;}

}