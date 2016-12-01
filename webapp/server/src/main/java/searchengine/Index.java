package searchengine;

import java.util.List;

/**
 * Holds all websites from a .txt file and provides search logic for queries
 */
public interface Index
{
    /**
     * Creates the "index" that will hold all websites.
     * @param websites Website list provided by FileHelper
     */
    void build(List<Website> websites);


    /**
     * Searches for websites containing a query provided by user.
     * @param query Phrase/word to search for
     */
    List<Website> lookup(String query);

    /**
     * Calculates the total number of websites in the index
     * @return The total number of websites
     */
    int getSize();
}
