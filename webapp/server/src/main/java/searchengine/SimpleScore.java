package searchengine;

import searchengine.Index;
import searchengine.Score;
import searchengine.Website;

import java.util.List;
import java.util.Map;

/**
 * Created by Joso on 22.11.2016.
 */
public class SimpleScore implements Score {

    Index indexToUse;

    SimpleScore(Index indexToUse){
        this.indexToUse = indexToUse;
    }

    @Override
    public float getScore(String someString, Website website, Index indexToUse) {

        int counter = 0;

                for(String s : website.getWords()) {

                    if (s.equals(someString) ){
                        counter ++;
                }
        }
        return counter;
    }
}
