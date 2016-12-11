package searchengine;

/**
 * This class computes the BM25 ranking algorithm for our search engine.
 */
public class ScoreBM25 implements Score
{
    private final double kMultiplier = 1.75;
    private final double bMultiplier = 0.75;
    private final double logOfTwo;

    private Index index;
    private double inverseDocumentFrequency;

    public ScoreBM25(Index index)
    {
        this.logOfTwo = Math.log10(2);
        this.index = index;
        this.inverseDocumentFrequency = 0;
    }

    public double getScore(String query, Website website)
    {
        if (this.inverseDocumentFrequency == 0)
        {
            return 0;
        }

        double termFrequencyStar = termFrequencyStar(query, website);

        double bm25 = termFrequencyStar * this.inverseDocumentFrequency;

        return bm25;
    }

    @Override
    public void calculateInverseDocumentFrequency(String keyword, int numberOfResults)
    {
        //If the denominator is 0, the inverse document frequency cannot be calculated
        if (numberOfResults == 0)
        {
            this.inverseDocumentFrequency = 0;
        }

        //Get the total number of websites
        int numberOfWebsites = index.getSize();

        this.inverseDocumentFrequency =
                Math.log10((double)numberOfWebsites / (double)numberOfResults) / this.logOfTwo;
    }

    public double termFrequencyStar(String query, Website website)
    {
        double termFrequency = website.getTermFrequency(query);
        double words = website.getWordsCount()/index.getAverageWordsCount();

        double div = kMultiplier * (1 - bMultiplier + (bMultiplier * words)) + termFrequency;

        double result = termFrequency * (kMultiplier + 1) / div;

        return result;
    }
}