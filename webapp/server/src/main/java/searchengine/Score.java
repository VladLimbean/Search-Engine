package searchengine;

/**
 * Calculates the ranking score of a specific website, based on the keyword that was searched
 */
public interface Score
{
    /**
     * Calculates the ranking score of a website.
     * @param keyword Word to calculate the score of
     * @param website Website that will have its score calculated
     * @param index Index that holds all websites
     * @return The score of the website for this specific keyword
     */
    double getScore(String keyword, Website website, Index index);
}
