package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 07/11/2016.
 */
public class QuerySplit {

    public static List<Website> searchSplitter(String query, Index index){
        List<String> resultQuery = new ArrayList<>();

        String[] splitByOR = query.split(" OR ");
        for(String s : splitByOR){
            String[] splitBySPACE = s.split(" ");
            for (String e : splitBySPACE){
                resultQuery.add(e.toLowerCase());
            }
        }
        List<Website> finalResult = new ArrayList<>();
        for (String s : resultQuery){
            {
                List<Website> tempList = index.lookup(s);
                for(Website e:tempList){
                    if (!finalResult.contains(e)){
                        finalResult.add(e);
                    }
                }
            }
        }
        return finalResult;
    }
    public static List<Website> guy1(String query, Index index){
        List<String> resultQuery = new ArrayList<>();

        for(String s : query.split(" OR ")){
            resultQuery.add(s.toLowerCase());
        }
        return guy2(resultQuery, index);
    }

    private static List<Website> guy2(List<String> guy1resultQuery, Index index){
        List<Website> resultList = new ArrayList<>();

        for (String s :  guy1resultQuery){
            String[] a = s.split(" ");
            List<Website> forTemp = guy3(a, index);
            for(Website e: forTemp){
                if(!resultList.contains(e)){
                    resultList.add(e);
                }
            }
        }
        return resultList;
    }
    private static List<Website> guy3(String[] guy2resultQuery, Index index){
        List<Website> finalResult = new ArrayList<>();
        for(String s: guy2resultQuery){
            finalResult.addAll(index.lookup(s));
        }
        return finalResult;
    }
}
