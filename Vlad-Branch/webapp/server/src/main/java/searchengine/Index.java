package searchengine;

import java.util.List;

public interface Index {
    /**
     * Returns a List of Websites in a List or a Hash map
     *
     * @param list
     * @author Vlad Limbean
     */
    void build(List<Website> list);

    /** returns the list of Websites where a provided word occurs
     *
     * @param query
     * @author Vlad Limbean
     */
    List<Website> lookup(String query);
}
