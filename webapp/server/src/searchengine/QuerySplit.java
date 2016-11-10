package searchengine;

import java.util.ArrayList;
import java.util.List;


/**
 * Handles the search by splitting the search query by OR, then by whitespaces.
 * Then the search is executed multiple times for each keyword in the query.
 */
public class QuerySplit {
    /**
     * Searches for a specific query.
     * The query is split by OR, then by whitespace.
     * Finally, the search is executed for each keyword in the query
     * @param query The whole string that the user searches for
     * @param index The index which holds all websites
     * @return A list of results for the whole query
     */
    public static List<Website> searchSplitter(String query, Index index) {
        //Create the list that will hold all results from every search
        List<Website> finalResult = new ArrayList<>();

        //Split the query by OR
        String[] splitByOR = query.split(" OR ");

        for(String s : splitByOR){
            //Split all subqueries by whitespace
            String[] splitBySPACE = s.split(" ");

            for (String e : splitBySPACE){
                //Search for each specific keyword (by lowercase)
                List<Website> tempResults = index.lookup(e.toLowerCase());

                //Add all partial results to the final list, while excluding duplicates
                for (Website website : tempResults) {
                    if (!finalResult.contains(website)) {
                        finalResult.add(website);
                    }
                }
            }
        }

        return finalResult;
    }

    /**
     * Searches for a specific query.
     * The query is split by OR, then by whitespace.
     * Finally, the search is executed for each keyword in the query
     * @param query The whole string that the user searches for
     * @param index The index which holds all websites
     * @return A list of results for the whole query
     */
    public static List<Website> getMatchingWebsites(String query, Index index){
        //Split the query by OR
        String[] splitByOR = query.split(" OR ");
        //Execute the splitting by space and searching, and return the results from that operation
        return evaluateFullQuery(splitByOR, index);
    }

    /** Partial method which handles the process of splitting by whitespace
     * @param splitByOR The result from splitting the whole query by OR
     * @param index Index object which holds all websites
     * @return The final list of websites after searching all subqueries
     */
    private static List<Website> evaluateFullQuery(String[] splitByOR, Index index){
        //Create a list which will contain the final results
        List<Website> resultList = new ArrayList<>();

        for (String s :  splitByOR){
            //Split every subquery by whitespace
            String[] splitByWhitespace = s.split(" ");

            //Get the results for the specific subquery
            List<Website> tempList = evaluateSubQuery(splitByWhitespace, index);

            //Add these results to the final list of results, excluding the duplicates
            for(Website e: tempList){
                if(!resultList.contains(e)){
                    resultList.add(e);
                }
            }
        }

        return resultList;
    }

    /** Partial method which searches for specific keywords
     * @param splitByWhitespace The result from splitting the query by whitespace
     * @param index Index object in which to search
     * @return List of results from searching a specific list of subqueries
     */
    private static List<Website> evaluateSubQuery(String[] splitByWhitespace, Index index){
        //Create the list that will hold all results
        List<Website> finalResult = new ArrayList<>();

        for(String s: splitByWhitespace){
            //Search for every specific keyword and add the results from it to the list
            finalResult.addAll(index.lookup(s));
        }

        //Return the list
        return finalResult;
    }
}