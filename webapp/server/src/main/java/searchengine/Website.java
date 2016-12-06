package searchengine;

import java.util.HashMap;
import java.util.List;

/**
 * Stores the information about a single website.
 */
public class Website
{
    private String url;
    private String title;
    private List<String> keywords;
    private HashMap<String, Integer> wordFreqIndex;

    /**
     * Constructor for creating new website objects.
     *
     * @param url       The URL of the website.
     * @param title     The title of the website.
     * @param keywords  All keywords associated with the website.
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
        this.wordFreqIndex = new HashMap<>();

        buildWordFrequencyMap();
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
    public List<String> getKeywords()
    {
        return this.keywords;
    }

    public int getWordFreq(String keyWord){
        if (wordFreqIndex.containsKey(keyWord))
        {
            return wordFreqIndex.get(keyWord);
        }
        else
        {
            return 0;
        }
    }
    public void buildWordFrequencyMap(){
        for(String word : keywords){
            if (!wordFreqIndex.containsKey(word))
            {
                wordFreqIndex.put(word, 1);
            }
            else
            {
                wordFreqIndex.put(word, wordFreqIndex.get(word) + 1);
            }
        }
    }
}
