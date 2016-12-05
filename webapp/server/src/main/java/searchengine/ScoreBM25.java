package searchengine;

/**
 * This class computes the BM25 ranking algorithm for our search engine.
 */
public class ScoreBM25 implements Score {

    public Index index;

    public ScoreBM25(Index index){
        this.index = index;
    }

    public double getScore(String query, Website website){

        double invertedTermFrequency = inverseTermFrequency(query);
        double termFrequencyStar = termFrequencyStar(query, website);

        double bm25 = termFrequencyStar * invertedTermFrequency;

        return bm25;
    }

    public double termFrequency(String query, Website website)
    {
        //Initialize a counter
        int counter = 0;

        //Go through all words in the websites' list of keywords
        for(String word : website.getKeywords())
        {
            //Increment the counter when the keyword matches the word in the list
            if(word.equals(query))
            {
                counter++;
            }
        }

        //Return the final term frequency
        return counter;
    }

    public double inverseTermFrequency(String query)
    {
        //Get the total number of websites
        int numberOfWebsites = index.getSize();
        //Get the total number of websites
        int numberOfMatches = index.lookup(query).size();

        //If the denominator is 0, the inverse document frequency cannot be calculated
        if (numberOfMatches == 0)
        {
            return 0;
        }

        double result = Math.log10((double)numberOfWebsites / (double)numberOfMatches) / Math.log10(2);

        return result;
    }

    public double termFrequencyStar(String query, Website website)
    {
        //defining equation constants
        double k = 1.75;
        double b = 0.75;

        double TF = termFrequency(query, website);
        double words = website.getKeywords().size()/index.getAverageWordsCount();
        double div = k*(1-b + (b*words)) + TF;

        double result = TF * (k+1)/ div;

        return result;
    }
}
