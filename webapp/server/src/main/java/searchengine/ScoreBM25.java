package searchengine;

/**
 * This class computes the BM25 ranking algorithm for our search engine.
 */
public class ScoreBM25 implements Score {

    private Index index;
    private double inverseDocumentFrequency;

    public ScoreBM25(Index index)
    {
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
                Math.log10((double)numberOfWebsites / (double)numberOfResults) / Math.log10(2);
    }

    public double termFrequencyStar(String query, Website website)
    {
        //defining equation constants
        double k = 1.75;
        double b = 0.75;

        double TF = website.getTermFrequency(query);
        double words = website.getWordsCount()/index.getAverageWordsCount();
        double div = k*(1-b + (b*words)) + TF;

        double result = TF * (k+1)/ div;

        return result;
    }
}
