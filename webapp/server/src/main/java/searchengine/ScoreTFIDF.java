package searchengine;

/**
 * Implementation of the score interface.
 * Calculates website rank using: Term Frequency - Inverse Document Frequency algorithm
 */
public class ScoreTFIDF implements Score
{
    private Index index;
    private final double logOfTwo;

    /**
     * Constructor stores index data structure.
     *
     * @param indexToUse Index data structure to be used.
     */
    public ScoreTFIDF(Index indexToUse)
    {
        this.index = indexToUse;
        this.logOfTwo = Math.log10(2);
    }

    /**
     * Computes the total score of a website relative to a query.
     *
     * @param keyword   Query word used to determine the list of websites to be scored.
     * @param website   Website that will have its score calculated.
     * @return          Number value representing the Website score.
     */
    public double getScore(String keyword, Website website, int numberOfResults)
    {
        //Calculate the term frequency and the inverse document frequency
        double termFrequency = website.getTermFrequency(keyword);
        double inverseDocumentFrequency = calculateInverseDocumentFrequency(keyword, numberOfResults);

        //Multiply them to get the final score of the website
        double totalScore = termFrequency * inverseDocumentFrequency;

        //Return the final score
        return totalScore;
    }

    /**
     * Calculates the inverse document frequency of a given query word.
     *
     * @param keyword           Query word.
     * @param numberOfResults   A number representing the inverse document frequency of a word across all websites.
     */
    private double calculateInverseDocumentFrequency(String keyword, int numberOfResults)
    {
        //If the denominator is 0, the inverse document frequency cannot be calculated
        if (numberOfResults == 0)
        {
            return 0;
        }

        //Get the total number of websites
        int numberOfWebsites = index.getSize();

        //Calculate the inverse document frequency
        double inverseDocumentFrequency =
                Math.log10((double)numberOfWebsites / (double)numberOfResults) / this.logOfTwo;
        return inverseDocumentFrequency;
    }
}
