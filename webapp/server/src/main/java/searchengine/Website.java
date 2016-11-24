package searchengine;

import java.util.List;

/**
 * A {@code Website} is the basic entity of the search engine.
 * It contains all the data of a website and offers some methods
 * to check whether a word is contained one a website.
 *
 * @author Martin Aumüller
 */
public class Website {

    private String url;
    private String title;
    private List<String> words;

    public Website(String url, String title, List<String> words){
        this.url = url;
        this.title= title;
        this.words = words;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getWords(){
        return words;
    }

    public boolean containsWord(String word) {
        return this.words.contains(word);
    }
}
