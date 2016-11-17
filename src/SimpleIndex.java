import java.util.ArrayList;
import java.util.List;

/**
 * SimpleIndex holds all website in a List object
 */
public class SimpleIndex implements Index
{
    private List<Website> websites;

    public void build(List<Website> websites)
    {
        this.websites = websites;
    }

    public List<Website> lookup(String query)
    {
        //Initialize the list that will hold all results
        List<Website> results = new ArrayList<>();

        //Go though all websites
        for (Website w : websites)
        {
            //If the specific website contains the query, add the website to the list of results
            if (w.getKeywords().contains(query))
            {
                results.add(w);
            }
        }

        //Return all websites that were found
        return results;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
