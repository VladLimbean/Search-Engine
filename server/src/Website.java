import java.util.List;

/**
 * Created by Joso on 15.11.2016.
 */
public class Website {

    private String url;
    private String title;
    private List<String> words;

    Website(String url, String title, List<String> words){
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
