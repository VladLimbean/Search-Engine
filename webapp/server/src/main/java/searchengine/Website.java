package searchengine;
import java.util.HashMap;
import java.util.List;

/**
 * Holds a single Website object which was parsed from a txt file.
 * It holds information about the title, the URL, and all words associated with the website
 */
public class Website
{
    private String url;
    private String title;
    private List<String> words;

    public Website(String url, String title, List<String> words)
    {
        //Set the website's URL
        this.url = url;

        //Set the website's title
        this.title = title;

        //Set the website's list of keywords
        this.words = words;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getTitle()
    {
        return this.title;
    }

    public List<String> getWords()
    {
        return this.words;
    }
}
