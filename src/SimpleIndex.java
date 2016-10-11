import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 10/10/2016.
 */
public class SimpleIndex implements Index {
    public List<Page> listWebsites;

    public void build(List<Page> listPages)
    {
        this.listWebsites = listPages;
    }

    public List<Page> lookup(String key) {
        List<Page> resultsFound = new ArrayList<Page>();
        for (Page page : this.listWebsites)
        {
            if (page.containsWord(key)) {
                resultsFound.add(page);
            }
        }
        return resultsFound;
    }
}
