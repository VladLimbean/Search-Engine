package searchengine;

/**
 * Implementation of the score interface.
 * Calculates website rank using: Term Frequency - Inverse Document Frequency algorithm
 */
public class ScoreTFIDF implements Score
{
    private Index index;

    /**
     * Constructor stores index data structure.
     *
     * @param indexToUse Index data structure to be used.
     */
    public ScoreTFIDF(Index indexToUse)
    {
        this.index = indexToUse;
    }

    /**
     * Computes the total score of a website relative to a query.
     *
     * @param keyword Query word used to determine the list of websites to be scored.
     * @param website Website that will have its score calculated.
     * @return        Number value representing the Website score.
     */
    public double getScore(String keyword, Website website)
    {
        //Calculate the two different
        double termFrequency = website.getTermFrequency(keyword);
        double inverseDocFrequency = calculateInverseDocumentFrequency(keyword);

        //Multiply them to get the final score of the website
        double totalScore = termFrequency * inverseDocFrequency;

        //Return the final score
        return totalScore;
    }

    /**
     * Calculates the inverse document frequency score of a website based on a given query.
     *
     * @param keyword Given query word.
     *
     * @return        Number value representing the Inverse Document Frequency rank.
     */
    private double calculateInverseDocumentFrequency(String keyword)
    {
        //Get the total number of websites
        int numberOfWebsites = index.getSize();
        //Get the total number of websites
        int numberOfMatches = index.lookup(keyword).size();

        //If the denominator is 0, the inverse document frequency cannot be calculated
        if (numberOfMatches == 0)
        {
            return 0;
        }

        //Calculate the inverse document frequency
        double result = Math.log10((double)numberOfWebsites / (double)numberOfMatches) / Math.log10(2);

        //Return the result
        return result;
    }
}
