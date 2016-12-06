package searchengine;

import searchengine.Index;
import searchengine.Website;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joso on 22.11.2016.
 */
public class Query {

    public static List<Website> split(String query, Index index) {
        List<Website> splitByOrWebsites = new ArrayList<>();

        String[] splitByOr = query.split(" OR ");

        for (String d: splitByOr) {

            String[] splitByWhiteSpace = d.split(" ");

            for (String e : splitByWhiteSpace) {
                List<Website> temp = index.lookup(e.toLowerCase());

                for (Website website : temp) {
                    if (!splitByOrWebsites.contains(website)) {
                        splitByOrWebsites.add(website);
                    }
                }
            }
        }
        return splitByOrWebsites;
    }

}