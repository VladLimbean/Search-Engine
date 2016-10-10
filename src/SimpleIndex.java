import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 10/10/2016.
 */
public class SimpleIndex implements Index {
    public List<Page> listWebsites;

    public void build(List<Page> listPages) {
        this.listWebsites = listPages;
    }

    public List<Page> lookup(String key) {
        List<Page> resultsFound = new ArrayList<Page>();
        for (Page w: this.listWebsites) {
            if (w.containsWord(key)) {
                resultsFound.add(w);
            }
        }
        return resultsFound;
    }
}
