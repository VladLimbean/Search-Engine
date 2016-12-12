package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleIndex stores all websites in a List object.
 */
public class SimpleIndex implements Index
{
    private List<Website> websites;
    private double averageNumberOfWords;

    /**
     * Creates a list of websites in the form of a List object.
     *
     * @param websites Website list provided by FileHelper.
     */
    public void build(List<Website> websites)
    {
        this.websites = websites;

        int totalWords = 0;
        for (Website w : this.websites)
        {
            totalWords += w.getWordsCount();
        }
        this.averageNumberOfWords = totalWords / this.websites.size();
    }

    /**
     * Searches for a specific keyword / string in the list of websites.
     *
     * @param query     Query word to look-up.
     * @return          A list of websites containing which contain the query word.
     */
    public List<Website> lookup(String query)
    {
        //Initialize the list that will hold all results
        List<Website> results = new ArrayList<>();

        //Go though all websites
        for (Website w : websites)
        {
            //If the specific website contains the query, add the website to the list of results
            if (w.getTermFrequency(query) > 0)
            {
                results.add(w);
            }
        }

        //Return all websites that were found
        return results;
    }

    /**
     * Calculates the number of websites in the list.
     *
     * @return Integer representing the number of websites in the list.
     */
    public int getSize()
    {
        return this.websites.size();
    }

    /**
     * Returns the name of the used index (SimpleIndex).
     *
     * @return name index.
     */
    public String toString() {
        return this.getClass().toString();
    }

    /**
     * Calculates the average number of words in the entire list of websites.
     *
     * @return an integer representing average number of words across all websites.
     */
    public double getAverageWordsCount()
    {
        return this.averageNumberOfWords;
    }
}