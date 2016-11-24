package searchengine;

import searchengine.Index;
import searchengine.Website;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joso on 16.11.2016.
 */
public class SimpleIndex implements Index {

    private List<Website> listOfWebsite;

    @Override
    public void build(List<Website> listWebsite) {
        this.listOfWebsite = listWebsite;
        }

    @Override
    public List<Website> lookup(String query) {

        List<Website> resultsFound = new ArrayList<>();

        for(Website w : this.listOfWebsite){
            if(w.containsWord(query)){
                resultsFound.add(w);
            }
        }
        return resultsFound;
    }
}
