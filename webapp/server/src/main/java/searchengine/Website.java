package searchengine;

import java.util.List;

/**
 * Created by IRCT on 11/19/2016.
 */
public class Website {
    private String url;
    private String title;
    private List<String> content;

    // Define a constructor for accessing the object variables
    public Website(String url, String title, List<String> webWords) {
        this.url = url;
        this.title = title;
        this.content = webWords;
    }

    // getter for URL of a Website Object
    public String getUrl() {
        return this.url;
    }

    // getter for Title of a Website Object
    public String getTitle() {
        return this.title;

    }

    // getter for Content of a Website Object
    public List<String> getContent() {
        return this.content;
    }

    public boolean hasKeyword(String query) {
        return this.title.contains(query);
    }


}

