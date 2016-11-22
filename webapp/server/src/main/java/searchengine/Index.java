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
     * Searches for websites in the saved "index" based on a search phrase
     * @param query Phrase to search
     */
    List<Website> lookup(String query);
}
