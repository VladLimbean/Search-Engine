package searchengine;

import searchengine.Index;
import searchengine.Website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Joso on 17.11.2016.
 */
public class InvertedIndex implements Index {

    private Map<String,List<Website>> mainMap;

    public InvertedIndex(Map<String, List<Website>> map){
        this.mainMap = map;
    }

    @Override
    public void build(List<Website> listWebsite) {
        // We get all websites from the list of websites
        for(Website website : listWebsite){
            //We get all words in all the websites
            for(String word: website.getWords()){

                List<Website> mapWebsite = new ArrayList<>();

                if(!mainMap.containsKey(word)){
                    mapWebsite.add(website);
                }
                else{
                    mapWebsite = mainMap.get(word);
                    mapWebsite.add(website);
                }
                mainMap.put(word,mapWebsite);
            }
        }
    }

    @Override
    public List<Website> lookup(String query) {
        List<Website> keyWebsites = new ArrayList<>();

        if(mainMap.containsKey(query)){
            keyWebsites = mainMap.get(query);
        }
        return keyWebsites;
    }
}
