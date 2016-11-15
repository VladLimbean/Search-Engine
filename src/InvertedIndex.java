import java.util.*;

/**
 * Holds all websites in a Map object
 */
public class InvertedIndex implements Index
{
    private Map<String, List<Website>> websites;

    /**
     * Creates the InvertedIndex object
     * @param shouldUseHashMap True if HashMap is to be used, false if TreeMap is to be used
     */
    public InvertedIndex(boolean shouldUseHashMap)
    {
        if (shouldUseHashMap)
        {
            this.websites = new HashMap<>();
        }
        else
        {
            this.websites = new TreeMap<>();
        }
    }

    public void build(List<Website> websites)
    {
        //Go through every website in the list
        for (Website website : websites)
        {
            //Go through every keyword in the website
            for (String word : website.getKeywords())
            {
                //Create an empty list of websites (the value type of the Map)
                List<Website> emptyList;

                //If the Map contains a key equal to the current word
                if (this.websites.containsKey(word))
                {
                    //Get the value of the keyword from the Map, and save it in empty list
                    emptyList = this.websites.get(word);
                }
                else
                {
                    //If the map doesn't contain the keyword, create an empty list
                    emptyList = new ArrayList<>();
                }

                //Add the website to the list of websites, only if it's not already added
                if (!emptyList.contains(website))
                {
                    emptyList.add(website);
                }

                //Creates a new key in the Map or updates the old one, after adding the current website
                this.websites.put(word, emptyList);
            }
        }
    }

    public List<Website> lookup(String query)
    {
        //Check if there is a key in the Map equal to the word we are searching for
        if (this.websites.containsKey(query))
        {
            //It does, so return the list associated with the keyword
            return this.websites.get(query);
        }
        else
        {
            //It doesn't, so return an empty list
            return new ArrayList<>();
        }
    }
}
