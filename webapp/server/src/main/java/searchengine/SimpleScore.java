package searchengine;

import searchengine.Index;
import searchengine.Score;
import searchengine.Website;

/**
 * Created by Joso on 22.11.2016.
 */
public class SimpleScore implements Score {

    @Override
    public float getScore(String someString, Website website, Index index) {

        index.lookup(someString);

        return 0;
    }
}
