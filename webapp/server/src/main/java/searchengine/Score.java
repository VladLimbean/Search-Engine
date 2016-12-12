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
    double getScore(String keyword, Website website);

    /**
     * Calculates the inverse document frequency of a given query word.
     *
     * @param keyword           Query word.
     * @param numberOfResults   A number representing the inverse document frequency of a word across all websites.
     */
    void calculateInverseDocumentFrequency(String keyword, int numberOfResults);
}
