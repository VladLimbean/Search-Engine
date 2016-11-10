package searchengine;

import java.util.*;

/**
 * searchengine.InvertedIndex transforms the List of Websites to a Map object.
 * Searches for websites in said Map object.
 */
public class InvertedIndex implements Index
{
    private Map<String, List<Website>> mainMap;
    private int numberOfWebsites;

    public InvertedIndex(Map<String, List<Website>> map)
    {
        this.mainMap = map;
    }

    public void build(List<Website> listWebsites)
    {
        numberOfWebsites = listWebsites.size();
        //Go through every website in the list
        for (Website website : listWebsites)
        {
            //Go through every keyword in the website
            for (String keyWord : website.getWords())
            {
                //Create an empty list of websites (the value type of the Map)
                List<Website> emptyList;

                //If the Map contains a key equal to the current word
                if (mainMap.containsKey(keyWord))
                {
                    //Get the value of the keyword from the Map, and save it in empty list
                    emptyList = mainMap.get(keyWord);
                }
                else
                {
                    //If the map doesn't contain the keyword, create an empty list
                    emptyList = new ArrayList<Website>();
                }

                //Add the website to the list of websites, only if it's not already added
                if (!emptyList.contains(website))
                {
                    emptyList.add(website);
                }

                //Creates a new key in the Map or updates the old one, after adding the current website
                mainMap.put(keyWord, emptyList);
            }
        }
    }

    public List<Website> lookup(String key)
    {
        //Check if there is a key in the Map equal to the word we are searching for
        if (mainMap.containsKey(key))
        {
            //It does, so return the list associated with the keyword
            return mainMap.get(key);
        }
        else
        {
            //It doesn't, so return an empty list
            return new ArrayList<>();
        }
    }

    @Override
    public int getSize() {
        return numberOfWebsites;
    }

    public String toString()
    {
        return "searchengine.InvertedIndex with " + this.mainMap.getClass();
    }
}
