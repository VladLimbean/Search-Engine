import java.util.List;

/**
 * Created by maau on 27/09/16.
 */
public class Website {

    private String url;
    private String title;
    private List<String> words;

    public Website(String url, String title, List<String> words) {
        this.url = url;
        this.title = title;
        this.words = words;
    }


    public String getUrl() {
        return this.url;
    }

    public String getTitle() {
        return this.title;

    }

    public boolean containsWord(String word) {
        return this.words.contains(word);
    }
}
