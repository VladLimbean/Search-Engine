package searchengine;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles the search for complex queries which hold multiple words or subqueries.
 * For example, "President USA OR Queen Denmark" will search for both 'president USA' and 'queen denmark'.
 */
public class QuerySplit
{
    /**
     * Searches for every word in complex query and returns all matching websites. The function will receive the user's
     * query and split it into subqueries taking into account the ' OR ' operator.
     *
     * For example, "President USA OR Queen Denmark" will be split into 'president USA' and 'queen denmark'.
     *
     * @param query          Search string entered by the user.
     * @param index          Index structure containing the Websites and associated words.
     * @param rankingHandler Object which ranks the Websites in the index.
     *
     * @return               List of ranked results.
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
     * Receives the split query string elements and further breaks them down by whitespace.
     * For example, the function receives 'president USA' and 'queen denmark' and will break these down to 'president',
     * 'USA', 'queen' and 'denmark'.
     *
     * The results from each individual query is ranked.
     *
     * @param splitByOr String array that holds all substrings without the " OR " operator.
     * @param index     Index data structure used to hold the websites and associated words.
     * @return          A list of ranked websites.
     */
    private static List<Website> evaluateFullQuery(String[] splitByOr, Index index, Score rankingHandler)
    {
        //Initialize the map that holds all results. Key is the website, value is it's rank in the query
        Map<Website, Double> rankingMap = new HashMap<>();

        //Go though all strings that are split by the " OR " operator
        for (String fullquery : splitByOr)
        {
            //Split the substring by the whitespace operator
            String[] splitBySpace = fullquery.split(" ");

            //Execute the search for each specific keyword
            Pair<List<Website>, int[]> partialResults = evaluateSubQuery(splitBySpace, index);
            List<Website> partialResultsList = partialResults.getKey();
            int[] resultsCount = partialResults.getValue();

            //Calculate the total ranking score of all websites for the specific subquery
            HashMap<Website, Double> websitesScore =
                    calculateSubqueryRanking(splitBySpace, resultsCount, partialResultsList, rankingHandler);

            //Iterate through the map
            for (Map.Entry<Website, Double> website : websitesScore.entrySet())
            {
                //Update the ranking map based on the score that was just calculated
                rankingMap = updateWebsiteInMap(website.getKey(), website.getValue(), rankingMap);
            }
        }

        //final boss
        return rankingMap.entrySet().stream().sorted((x, y) -> y.getValue()
                .compareTo(x.getValue())).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Searches for websites containing a phrase.
     *
     * @param splitBySpace String array representing the query split by whitespace.
     * @param index        The index data structure holding the Websites and associated words.
     * @return             A list of websites containing the subqueries.
     */
    private static Pair<List<Website>, int[]> evaluateSubQuery(String[] splitBySpace, Index index)
    {
        //Initialize the list that will hold all search results
        List<Website> resultsToReturn = null;
        int[] resultsPerWord = new int[splitBySpace.length];

        for (int i = 0; i < splitBySpace.length; i++)
        {
            String subquery = splitBySpace[i];

            //Check if the subquery is an empty string
            if (subquery.isEmpty())
            {
                //It is, so skip searching for it.
                continue;
            }

            //Search for the subquery
            List<Website> partialResults = index.lookup(subquery.toLowerCase());

            //Save the number of results that this word has
            resultsPerWord[i] = partialResults.size();

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

        //Make sure the function doesn't return a null list
        if (resultsToReturn == null)
        {
            resultsToReturn = new ArrayList<>();
        }
        
        //Return the final list of results for the subquery
        return new Pair<>(resultsToReturn, resultsPerWord);
    }

    /**
     * Calculates the ranking of a specific website for a given query.
     *
     * @param splitByWhitespace A string array containing individual query words.
     * @param numberOfResults   Total amount of results found for the specific subquery.
     * @param results           List of websites to calculate the score for.
     * @param rankingHandler    Score calculator object.
     *
     * @return                  Returns the rank of all websites in the list relative to a search query.
     */
    private static HashMap<Website, Double> calculateSubqueryRanking(String[] splitByWhitespace, int[] numberOfResults, List<Website> results, Score rankingHandler)
    {
        //Initialize the score holder
        HashMap<Website, Double> scoresPerWebsite = new HashMap<>();

        //Iterate through all query words in the subquery
        for (int i = 0; i < splitByWhitespace.length; i++)
        {
            //Remove capitalization
            String toLowerCase = splitByWhitespace[i].toLowerCase();

            //Calculate the inverse document frequency of the word
            rankingHandler.calculateInverseDocumentFrequency(toLowerCase, numberOfResults[i]);

            for (Website website : results)
            {
                //Calculate the score of a website towards the query word
                double scoreForQuery = rankingHandler.getScore(toLowerCase, website);

                //If the website has a score already calculated for another query word...
                if (scoresPerWebsite.containsKey(website))
                {
                    //...Get it and add it to the current score
                    scoreForQuery += scoresPerWebsite.get(website);
                }

                //Update the total score of the website
                scoresPerWebsite.put(website, scoreForQuery);
            }
        }

        //Return the final score of the whole query
        return scoresPerWebsite;
    }

    /**
     * Updates an index data structure holding the websites and their rank relative to a query. Higher ranks are
     * overriden in the index data structure.
     *
     * @param websiteToUpdate       Website to be handled.
     * @param newlyCalculatedScore  Score of websiteToUpdate
     * @param currentMap            Index structure which holds the website and its respective value.
     *
     * @return                      A map structure with websites as key and rank as the value.
     */
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
