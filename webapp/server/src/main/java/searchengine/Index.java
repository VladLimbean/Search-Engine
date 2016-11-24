package searchengine;

import searchengine.Website;

import java.util.List;

/**
 * Created by Joso on 16.11.2016.
 */
public interface Index {

    void build(List<Website> listWebsite);

    List<Website> lookup(String query);

}
