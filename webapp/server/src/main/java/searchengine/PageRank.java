package searchengine;

import searchengine.InterfaceIndex;
import searchengine.InterfaceRanking;

/**
 * Created by IRCT on 11/27/2016.
 * Holds all the necessary ranking algorithms
 */

public class PageRank implements InterfaceRanking {
    //store the index somewhere
    private InterfaceIndex index;
    //variables to store finalScore and termFrequency
    private double finalScore;
    private int termCounter;
    private int indexSize;
    private int matchedSites;
    private double calculatedInverseFrequency;

    //calculate term frequency, if you can
    public int termFrequency(String keyword, Website website) {
        termCounter = 0;
        //loop trough words in the content
        //for each match increment the termCounter variable by +1
        for (String word : website.getContent()) {
            if (word.equals(keyword)) {
                //this should do it
                termCounter++;
            }
        }

        //return the final result of termFrequency
        return termCounter;
    }


    //method to calculate document inverse frequency
    private double inverseFrequency(String query, InterfaceIndex index){
    //get the total number of websites, stored in our index
        indexSize = index.getSize();
        //get number of websites, from index, that could have been found using the provided query
        matchedSites = index.findKeyword(query).size();
        //calculate the inverse document frequency
        calculatedInverseFrequency = Math.log10((double)indexSize/(double)matchedSites/Math.log10(2));
        //return the the result to the caller
        return calculatedInverseFrequency;
    }

    //get final score for each website page that matches the provided query
    public double getPageRankScore(String query, Website page) {
        //calculate termFrequency then inverse Frequency, for each website object,
        // then multiply those values and return finalPageRank score
        double pageTermFrequency = termFrequency(query,page);
        double pageInverseFrequency = inverseFrequency(query, index);
        double finalPageRank = pageInverseFrequency * pageTermFrequency;
        //return the result to the caller
        return finalPageRank;
    }

}
