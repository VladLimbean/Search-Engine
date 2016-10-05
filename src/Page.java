/**
 * Created by Vlad on 05/10/2016.
 */
public class Page {
    private String url;
    private String title;
    // constructor assigns url and title of page
    public Page(String url, String title){
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

}
