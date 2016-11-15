package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        Map<Website, Double> finalResult = new HashMap<>();

        //Split the query by OR
        String[] splitByOR = query.split(" OR ");

        for(String s : splitByOR) {
            //Split all subqueries by whitespace
            String[] splitBySPACE = s.split(" ");

            List<Website> websitesMatched = new ArrayList<>();
            for (int i = 0; i < splitBySPACE.length; i++ ) {
                //Search for each specific keyword (by lowercase)
                List<Website> tempResults = index.lookup(splitBySPACE[i].toLowerCase());
                if (i == 0) {
                    websitesMatched.addAll(tempResults);
                } else {
                    websitesMatched.retainAll(tempResults);
                }
            }

            //Add all partial results to the final list, while excluding duplicates
            for (Website website : websitesMatched) {
                double finalScore = 0;

                //Calculate the total score of the subquery
                for (String e : splitBySPACE) {
                    finalScore += FinalScore.getScore(e.toLowerCase(), website, index);
                }

                //Check the map for this website
                if (finalResult.containsKey(website)) {
                    //The map contains the website, so check it's currently saved score
                    double currentSiteValue = finalResult.get(website);

                    //Check if the current score is bigger than the score saved in the map
                    if (finalScore > currentSiteValue) {
                        //Update the score because we found a max
                        finalResult.put(website, finalScore);
                    }
                }
                else {
                    //The website is not contained in the map, so add it
                    finalResult.put(website, finalScore);
                }
            }
        }

        //final boss
        return finalResult.entrySet().stream().sorted((x,y)->y.getValue().compareTo(x.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
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

        for (int i = 0; i < splitByWhitespace.length; i++){
            //Search for every specific keyword
            List<Website> partialResults = index.lookup(splitByWhitespace[i]);
            if (i == 0) {
                finalResult.addAll(partialResults);
            }
            else {
                finalResult.retainAll(partialResults);
            }
        }

        //Return the list
        return finalResult;
    }
}
