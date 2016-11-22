package searchengine;

import java.util.List;

/**
 * The Website class provides the basic structure of a website - URL, title, words.
 *
 * @author Vlad Limbean
 */
public class Website {
    private String url;
    private String title;
    private List<String> words;

    /**
     * Creates a website with a URL, title and associated words.
     *
     * @param URL
     * @param siteTitle
     * @param listOfWords
     */
    public Website(String URL, String siteTitle, List<String> listOfWords){
        this.url = URL;
        this.title = siteTitle;
        this.words = listOfWords;
    }

    /**
     * Checks if any words are contained in the website.
     *
     * @return true if the list of words is not empty, false otherwise.
     */
    public boolean hasWords(){
        if (!words.isEmpty()){
            return false;
        }
        else
            {return true;}
    }
    /**
     * Checks if the website has a defined URL
     *
     * @return true if the URL is set, false otherwise.
     */
    public boolean containsURL(){
        if(url==null){
            return false;
        }
        else
            {return true;}
    }

    /**
     * Checks if the title of the website is set.
     *
     * @return true if title is set, false otherwise.
     */
    public boolean containsTitle(){
        if(title==null){
            return false;
        }
        else
            {return true;}
    }

    /**
     * Returns website URL
     *
     * @return return the website URL.
     */
    public String getUrl(){
        return this.url;
    }

    /**
     * Returns website title.
     *
     * @return the website title.
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Returns website words.
     *
     * @return list of all words.
     */

    public List<String> getWords(){
        return this.words;
    }

    /**
     * Checks if word is contained.
     *
     * @param word
     *
     * @return true if word is contained, false if otherwise.
     */
    public boolean containsWord(String word){
        return this.words.contains(word);
    }
}
