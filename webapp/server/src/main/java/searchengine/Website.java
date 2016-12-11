package searchengine;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Stores the information about a single website.
 */
public class Website
{
    private String url;
    private String title;
    private Map<String, Integer> termFrequencyMap;
    private int wordsCount;

    /**
     * Constructor for creating new website objects.
     *
     * @param url       The URL of the website.
     * @param title     The title of the website.
     * @param termFrequencyMap  All keywords associated with the website.
     */
    public Website(String url, String title, Map<String, Integer> termFrequencyMap, int wordCounter) throws MalformedURLException
    {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
        {
            throw new MalformedURLException("Every website needs to start with http:// or https://");
        }

        this.url = url;
        this.title = title;
        this.termFrequencyMap = termFrequencyMap;
        this.wordsCount = wordCounter;
    }

    /**
     * Returns the URL of the website.
     *
     * @return Website's URL
     */
    public String getUrl()
    {
        return this.url;
    }

    /**
     * Returns the title of the website.
     *
     * @return Website's title.
     */
    public String getTitle()
    {
        return this.title;
    }

    /** Returns all keywords of the website.
     *
     * @return Keywords of the website.
     */
    public Map<String, Integer> getAllFrequencies()
    {
        return this.termFrequencyMap;
    }

    public int getTermFrequency(String word)
    {
        if (this.termFrequencyMap.containsKey(word))
        {
            return this.termFrequencyMap.get(word);
        }

        return 0;
    }

    public int getWordsCount()
    {
        return this.wordsCount;
    }
}
