import java.util.HashMap;
import java.util.List;

/**
 * Created by Vlad on 05/10/2016.
 */
public class Page {
    private String url;
    private String title;
    private List<String> words;

    // constructor assigns url and title of page
    public Page(String url, String title, List<String> words){
        this.url = url;
        this.title = title;
        this.words = words;

    }
    public boolean containsWord(String word) {
        return this.words.contains(word);
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getWords(){
        return this.words;
    }
}
