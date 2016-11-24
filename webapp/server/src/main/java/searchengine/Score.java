package searchengine;

import searchengine.Index;
import searchengine.Website;

/**
 * Created by Joso on 22.11.2016.
 */
public interface Score {

    float getScore(String someString, Website website, Index index);

}
