package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ivanm on 15-Nov-16.
 */
public class QueryRank {
    public static List<Website> calculateRanking(List<Website> results, String query, Index index) {
        //Initialize the map that is used for the ranking
        Map<Website, Double> finalRank = new HashMap<>();
        
        //Split the query by OR
        String[] splitByOR = query.split(" OR ");

        for (String s : splitByOR) {
            //Split the subquery by space
            String[] splitByWhitespace = s.split(" ");

            //Go through all results
            for (Website website : results) {
                double finalScore = 0;

                //Calculate the total score of the subquery
                for (String e : splitByWhitespace) {
                    finalScore += FinalScore.getScore(e.toLowerCase(), website, index);
                }

                //Check the map for this website
                if (finalRank.containsKey(website)) {
                    //The map contains the website, so check it's currently saved score
                    double currentSiteValue = finalRank.get(website);

                    //Check if the current score is bigger than the score saved in the map
                    if (finalScore > currentSiteValue) {
                        //Update the score because we found a max
                        finalRank.put(website, finalScore);
                    }
                }
                else {
                    //The website is not contained in the map, so add it
                    finalRank.put(website, finalScore);
                }
            }
        }

        //final boss
        return finalRank.entrySet().stream().sorted((x,y)->y.getValue()
                .compareTo(x.getValue())).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
