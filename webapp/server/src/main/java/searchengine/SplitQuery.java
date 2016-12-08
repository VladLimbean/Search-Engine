package searchengine;

import searchengine.Website;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IRCT on 28/11/2016.
 */
public class SplitQuery {
    //
    public static List<Website> finalPageList;
    public static PageRank rank = new PageRank();

    //create method to split query by OR and White Space
    public static List<Website> splitInput(String query, InterfaceIndex index) {
        finalPageList = new ArrayList<>();
        //array that holds each query value split by OR
        String[] orSplit = query.split(" OR ");
        //for loop for each words a, in array of orSplit
        for (String a : orSplit) {
            //split them also by a white space
            //create array variable to store each word from orSplit by whiteSpace " "
            String[] wsSplit = a.split(" ");
            //for loop for each word b in whitespaceSplit array
            for (String b : wsSplit) {
                //create a temporary list of website pages, that can be found using each of these words
                List<Website> tempList = index.findKeyword(b.toLowerCase());
                Map<String, Integer>  tempHash = new HashMap<>();
                //for loop for each Website object in our tempList of website pages do following
                for (Website website : tempList) {
                    //check if the main
                    if (!finalPageList.contains(website)) {
                        finalPageList.add(website);

                    }
                }
            }
        }

        //return final pageList of Pages matching query
        return finalPageList;
    }

}
