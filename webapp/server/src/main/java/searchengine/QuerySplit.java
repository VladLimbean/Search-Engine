package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the search for complex queries that hold multiple words and subqueries
 * Eg. "President USA OR Queen Denmark" will search for both president USA and queen denmark
 */
public class QuerySplit
{
    /**
     * Searches for every word of a complex query and returns all results taht match it
     * @param query Search string that the user entered
     * @param index Index to use for searching purposes
     * @return Final list of results
     */
    public static List<Website> getMatchingWebsites(String query, Index index)
    {
        //Split the query by the " OR " operator
        String[] splitByOr = query.split(" OR ");

        //Search for all subqueries and save the results in a list
        List<Website> finalResults = evaluateFullQuery(splitByOr, index);

        //Return all matching websites
        return finalResults;
    }

    /**
     * Handles the complex query search
     * @param splitByOr
     * @param index
     * @return
     */
    private static List<Website> evaluateFullQuery(String[] splitByOr, Index index)
    {
        //Initialize an object that will hold the results
        List<Website> resultsFromFullquery = new ArrayList<>();

        //Go though all strings that are split by the " OR " operator
        for (String fullquery : splitByOr)
        {
            //Split the substring by the whitespace operator
            String[] splitBySpace = fullquery.split(" ");

            //Execute the search for each specific keyword
            List<Website> partialResults = evaluateSubQuery(splitBySpace, index);

            //Add all results in the final list, if they are not already there
            for (Website w : partialResults)
            {
                if (!resultsFromFullquery.contains(w))
                {
                    //The website should be in the final list but is not there yet, so add it
                    resultsFromFullquery.add(w);
                }
            }
        }

        //Return the list that holds all results
        return resultsFromFullquery;
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
                //Since this is the first iteration of the loop, add all results to the list
                resultsToReturn = partialResults;
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
}
