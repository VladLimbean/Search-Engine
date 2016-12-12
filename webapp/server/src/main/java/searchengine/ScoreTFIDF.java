package searchengine;

/**
 * Implementation of the score interface.
 * Calculates website rank using: Term Frequency - Inverse Document Frequency algorithm
 */
public class ScoreTFIDF implements Score
{
    private Index index;
    private double inverseDocumentFrequency;

    /**
     * Constructor stores index data structure.
     *
     * @param indexToUse Index data structure to be used.
     */
    public ScoreTFIDF(Index indexToUse)
    {
        this.index = indexToUse;
        this.inverseDocumentFrequency = 0;
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
        calculateInverseDocumentFrequency(keyword, numberOfResults);
        if (this.inverseDocumentFrequency == 0)
        {
            return 0;
        }

        //Calculate the two different
        double termFrequency = website.getTermFrequency(keyword);

        //Multiply them to get the final score of the website
        double totalScore = termFrequency * this.inverseDocumentFrequency;

        //Return the final score
        return totalScore;
    }

    /**
     * Calculates the inverse document frequency of a given query word.
     *
     * @param keyword           Query word.
     * @param numberOfResults   A number representing the inverse document frequency of a word across all websites.
     */
    private void calculateInverseDocumentFrequency(String keyword, int numberOfResults)
    {
        //If the denominator is 0, the inverse document frequency cannot be calculated
        if (numberOfResults == 0)
        {
            this.inverseDocumentFrequency = 0;
        }

        //Get the total number of websites
        int numberOfWebsites = index.getSize();

        this.inverseDocumentFrequency =
                Math.log10((double)numberOfWebsites / (double)numberOfResults) / Math.log10(2);
    }
}
