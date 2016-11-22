package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex implements Index {
    public Map<String, List<Website>> mapOfWebsites;

    /**
     * Receives a List of Websites
     * Creates a hash map with Key - query word & Values - List of websites
     *
     * @param list
     */
    public void build(List<Website> list) {
        mapOfWebsites = new HashMap<>();

        for(Website w : list){
            for(String s : w.getWords()){
                if (mapOfWebsites.containsKey(s)){
                    List<Website> currentList = mapOfWebsites.get(s);
                    if (!currentList.contains(w)){
                        currentList.add(w);
                    }
                    mapOfWebsites.put(s, currentList);
                }
                else{
                    List<Website> newList = new ArrayList<>();
                    newList.add(w);
                    mapOfWebsites.put(s,newList);
                }
            }

        }
    }


    public List<Website> lookup(String query)
    {

        if (mapOfWebsites.containsKey(query)){
            return mapOfWebsites.get(query);
        }
        else{
            return new ArrayList<>();
        }
    }
}
