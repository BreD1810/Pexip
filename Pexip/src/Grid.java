import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grid
{

    private String contents;
    public final int ROW_LENGTH;
    private Map<String, ArrayList<Integer>> stringLocations = new HashMap<>();
    public static final int INDEX_LIMIT = 3;
    public final int GRID_LENGTH;

    Grid(String input)
    {
        contents = input;
        GRID_LENGTH = input.length();
        ROW_LENGTH = Double.valueOf(Math.sqrt(GRID_LENGTH)).intValue();
        generateLocations();
    }

    private void generateLocations()
    {
        for(int i = 0; i < contents.length(); i++)
        {
            char currentChar = contents.charAt(i);
            StringBuilder rightString = new StringBuilder().append(currentChar);
            StringBuilder downString = new StringBuilder().append(currentChar);
            addLocation(String.valueOf(currentChar), i);
            int rightPosition, downPosition;

            //Index substrings up to length INDEX_LIMIT
            for(int offset = 1; offset < INDEX_LIMIT; offset++)
            {
                if(offset <= (Math.round(i/ROW_LENGTH) + 1) * ROW_LENGTH - i - 1 && (i + offset) < GRID_LENGTH)
                {
                    rightString.append(contents.charAt(i + offset));
                    addLocation(rightString.toString(), i);
                }
                
                downPosition = i + (offset * ROW_LENGTH);
                if(downPosition < GRID_LENGTH)
                {
                    downString.append(contents.charAt(downPosition));
                    addLocation(downString.toString(), i);
                }
            }

        }
    }

    private void addLocation(String subWord, int location)
    {
        if(stringLocations.containsKey(subWord))
        {
            stringLocations.get(subWord).add(location);
        }
        else
        {
            ArrayList<Integer> locationList = new ArrayList<>();
            locationList.add(location);
            stringLocations.put(subWord, locationList);
        }
    }


    public ArrayList<Integer> getStringLocations(String string) {return stringLocations.get(string);}

    /**
     * @param location The location of the character being queried
     * @param noBelow The number of positions below the queried character
     * @return The character in the requested position - Returns a space if invalid
     */
    public char getCharBelow(int location, int noBelow)
    {
        int position = location + (noBelow * ROW_LENGTH);
        if(location < 0 || location >= GRID_LENGTH || noBelow < 0 || position >= GRID_LENGTH)
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
        if(location < 0 || location >= GRID_LENGTH || noRight < 0 || noRight > noLeftOnRow || (location + noRight) >= GRID_LENGTH)
            return ' ';
        return contents.charAt(location + noRight);
    }

    //From testing, this is slower than getCharRight ?
//    public boolean checkWordRight(String word, int location)
//    {
//        int endLocation = location + word.length() - 1;
//        if(endLocation >= contents.length() || Math.round(location/ROW_LENGTH) != Math.round
//        (endLocation/ROW_LENGTH) || !contents.substring(location,
//                endLocation+1).equals(word))
//            return false;
//        return true;
//    }

}