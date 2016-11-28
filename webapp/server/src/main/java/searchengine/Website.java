package searchengine;

import java.util.List;

/**
 * Stores the information about a single website.
 */
public class Website
{
    private String url;
    private String title;
    private List<String> keywords;

    /**
     * Constructor for creating new website objects
     * @param url The URL of the website
     * @param title The title of the website
     * @param keywords All keywords associated with the website
     */
    public Website(String url, String title, List<String> keywords) throws IllegalArgumentException
    {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
        {
            throw new IllegalArgumentException("Every website needs to start with http:// or https://");
        }

        this.url = url;
        this.title = title;
        this.keywords = keywords;
    }

    /**
     * Returns the URL of the website
     * @return the website's URL
     */
    public String getUrl()
    {
        return this.url;
    }

    /**
     * Returns the title of the website
     * @return the website's title
     */
    public String getTitle()
    {
        return this.title;
    }

    /** Returns all keywords of the website
     * @return the website's keywords
     */
    public List<String> getKeywords()
    {
        return this.keywords;
    }
}
