package searchengine;

import java.util.*;

/**
 * Stores keywords all related websites in a Map structure.
 */
public class InvertedIndex implements Index
{
    private Map<String, List<Website>> websites;

    private int websitesCount;
    private double averageWordsCount;

    /**
     * Creates the index structure which holds .
     *
     * @param shouldUseHashMap True if HashMap is used to index websites; False if TreeMap is used to index websites.
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

        this.websitesCount = 0;
    }

    /**
     * Creates the data structure that will index all websites.
     *
     * @param websites  Website list provided by FileHelper.
     */
    public void build(List<Website> websites)
    {
        this.websitesCount = websites.size();
        int numberOfWords = 0;
        //Go through every website in the list
        for (Website website : websites)
        {
            //Sums the number of words for every website
            numberOfWords += website.getWordsCount();

            //Go through every keyword in the website
            for (String word : website.getAllFrequencies().keySet())
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
            // calculates the average number of words across all websites
            averageWordsCount = numberOfWords / websitesCount;
        }
    }

    /**
     * Searches for a phrase or words within the index and lists the websites it occurs in.
     *
     * @param query     Query word to look-up.
     * @return          A list of websites containing the query word.
     */
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

    /**
     * Calculates the number of websites contained in the index.
     *
     * @return A number value equal to the number of websites in the index.
     */
    public int getSize()
    {
        return this.websitesCount;
    }

    /**
     * Returns a string representation of the Class showing the data type being used by the index.
     *
     * @return a string representation of the class and data type used to index the .txt file.
     */
    public String toString() {
        return this.getClass().toString() + " with " + this.websites.getClass().toString();
    }

    /**
     * Calculates the average number of words across all websites in the index.
     *
     * @return  double representation of the average number of words.
     */
    public double getAverageWordsCount(){
        return averageWordsCount;
    }
}