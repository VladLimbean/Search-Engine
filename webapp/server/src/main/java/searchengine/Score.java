package searchengine;

/**
 * Calculates the ranking score of a specific website containing a specific query.
 */
public interface Score
{
    /**
     * Calculates the rank score of a website.
     *
     * @param keyword Query word used to determine the list of websites to be scored.
     * @param website Website that will have its score calculated.
     * @return        The score of the website for the given query.
     */
    double getScore(String keyword, Website website, int numberOfResults);
}
