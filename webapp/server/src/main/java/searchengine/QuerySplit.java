package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles the search for complex queries that hold multiple words and subqueries
 * Eg. "President USA OR Queen Denmark" will search for both president USA and queen denmark
 */
public class QuerySplit
{
    /**
     * Searches for every word of a complex query and returns all results taht match it
     *
     * @param query          Search string that the user entered
     * @param index          Index to use for searching purposes
     * @param rankingHandler Ranking object that will rank the website result list
     * @return Final list of results
     */
    public static List<Website> getMatchingWebsites(String query, Index index, Score rankingHandler)
    {
        //Split the query by the " OR " operator
        String[] splitByOr = query.split(" OR ");

        //Search for all subqueries and save the results in a list
        List<Website> finalResults = evaluateFullQuery(splitByOr, index, rankingHandler);

        //Return all matching websites, ranked using the provided ranking handler
        return finalResults;
    }

    /**
     * Handles the complex query search
     *
     * @param splitByOr String array that holds all substrings without the " OR " operator
     * @param index     Index used for searching
     * @return
     */
    private static List<Website> evaluateFullQuery(String[] splitByOr, Index index, Score rankingHandler)
    {
        //Initialize the map that holds all results. Key is the website, value is it's rank in the query
        Map<Website, Double> rankingMap = new HashMap<>();

        //Go though all strings that are split by the " OR " operator
        for (String fullquery : splitByOr) {
            //Split the substring by the whitespace operator
            String[] splitBySpace = fullquery.split(" ");

            //Execute the search for each specific keyword
            List<Website> partialResults = evaluateSubQuery(splitBySpace, index);

            //Add all results in the final list, if they are not already there
            for (Website w : partialResults)
            {
                //Calculate the ranking score of the website
                double scoreForQuery = calculateSubqueryRanking(splitBySpace, w, index, rankingHandler);

                //Update the ranking map based on the score that was just calculated
                rankingMap = updateWebsiteInMap(w, scoreForQuery, rankingMap);
            }
        }

        //final boss
        return rankingMap.entrySet().stream().sorted((x, y) -> y.getValue()
                .compareTo(x.getValue())).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static List<Website> evaluateSubQuery(String[] splitBySpace, Index index)
    {
        //Initialize the list that will hold all search results
        List<Website> resultsToReturn = null;

        for (String subquery : splitBySpace)
        {
            //Search for the subquery
            List<Website> partialResults = index.lookup(subquery.toLowerCase());

            //If the resultsToReturn list is null, that means we are in the first iteration of the loop
            if (resultsToReturn == null)
            {
                //Actually create the list
                resultsToReturn = new ArrayList<>();

                //Since this is the first iteration of the loop, add all results to the list
                resultsToReturn.addAll(partialResults);
            }
            else
            {
                //This is NOT the first iteration of the loop, only keep the websites that are in both lists
                resultsToReturn.retainAll(partialResults);
            }
        }

        //Return the final list of results for the subquery
        return resultsToReturn;
    }

    private static double calculateSubqueryRanking(String[] splitByWhitespace, Website website, Index indexToUse, Score rankingHandler) {
        //Initialize the score holder
        double scoreForQuery = 0;

        //Increment the score of the subquery for each string in it
        for (String substring : splitByWhitespace)
        {
            scoreForQuery += rankingHandler.getScore(substring.toLowerCase(), website);
        }

        //Return the final score of the whole query
        return scoreForQuery;
    }

    private static Map<Website, Double> updateWebsiteInMap(Website websiteToUpdate, double newlyCalculatedScore, Map<Website, Double> currentMap)
    {
        //Check is the website is already in the ranking map
        if (currentMap.containsKey(websiteToUpdate))
        {
            //The map contains the website, so check its' currently saved score
            double scoreInMap = currentMap.get(websiteToUpdate);

            //Check if the calculated score is bigger than the score saved in the map
            if (newlyCalculatedScore > scoreInMap)
            {
                //Update the score because we found a max
                currentMap.put(websiteToUpdate, newlyCalculatedScore);
            }
        }
        else
        {
            //This is the first time we add this website to the rankin
            currentMap.put(websiteToUpdate, newlyCalculatedScore);
        }

        return currentMap;
    }
}
