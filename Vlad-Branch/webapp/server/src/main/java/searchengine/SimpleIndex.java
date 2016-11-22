package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleIndex receives a List of Websites and offers the possibility check for the occurence of a give word per website
 *
 */
public class SimpleIndex implements Index {
    List<Website> listOfWebsites;

    /**
     * Creates the list of websites
     *
     * @param list
     */
    public void build(List<Website> list) {
        listOfWebsites = list;
    }

    /**
     * Checks if the query word is fetured in any of the websites contained in the list
     *
     * @param query
     * @return
     */
    public List<Website> lookup(String query) {
        List<Website> result = new ArrayList<>();

        for(Website w : listOfWebsites){
            if(w.getWords().contains(query)){
                if (!result.contains(w)){
                result.add(w);}
            }
        }
        return result;
    }
}
