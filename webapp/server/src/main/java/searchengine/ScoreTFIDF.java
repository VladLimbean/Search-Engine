package searchengine;

/**
 * Implementation of the score interface that calculates a rank using the
 * Term Frequency - Inverse Document Frequency algorithm
 */
public class ScoreTFIDF implements Score
{
    private Index index;

    public ScoreTFIDF(Index indexToUse)
    {
        this.index = indexToUse;
    }

    public double getScore(String keyword, Website website)
    {
        //Calculate the two different
        double termFrequency = calculateTermFrequency(keyword, website);
        double inverseDocFrequency = calculateInverseDocumentFrequency(keyword);

        //Multiply them to get the final score of the website
        double totalScore = termFrequency * inverseDocFrequency;

        //Return the final score
        return totalScore;
    }

    /**
     * Calculates the term frequency paramater of the website based on the specific keyword
     * @return The term frequency score of the website
     * @param keyword Keyword to use to calculate the result for
     * @param website Website to use when calculating the keyword results
     */
    private double calculateTermFrequency(String keyword, Website website)
    {
        //Initialize a counter
        int counter = 0;

        //Go through all words in the websites' list of keywords
        for(String word : website.getKeywords())
        {
            //Increment the counter when the keyword matches the word in the list
            if(word.equals(keyword))
            {
                counter++;
            }
        }

        //Return the final term frequency
        return counter;
    }

    /**
     * Calculates the inverse document frequency of a website based on the specific keyword
     * @return The inverse document frequency score of the website
     * @param keyword Keyword to calculate the score based on
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
