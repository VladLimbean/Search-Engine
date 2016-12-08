package searchengine;

import searchengine.Website;

/**
 * Created by IRCT on 11/27/2016.
 */
public interface InterfaceRanking {
    //interface method
    int termFrequency(String query, Website page);

    //Ranking Interface => method getPageRankScore, applies to any future page ranking method
    double getPageRankScore(String query, Website page);

}
