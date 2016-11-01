import java.util.ArrayList;
import java.util.List;

/**
 * SimpleIndex is almost equal to the prototype.
 * It holds all websites in a List object.
 * Searching is basically going through every keyword in every website.
 */
public class SimpleIndex implements Index
{
    public List<Website> listWebsites;

    public void build(List<Website> listWebsites)
    {
        //Set the instance variable so it holds all websites
        this.listWebsites = listWebsites;
    }

    public List<Website> lookup(String key)
    {
        //Create a new list with all results found
        List<Website> resultsFound = new ArrayList<Website>();

        //Go through every website in the list of websites
        for (Website website : this.listWebsites)
        {
            //Check if the list of words contains the keyword we need to search for
            if (website.getWords().contains(key))
            {
                //If it does, add the website to the list of results
                resultsFound.add(website);
            }
        }

        //Return the final list of results
        return resultsFound;
    }

    public String toString()
    {
        return "SimpleIndex";
    }
}
