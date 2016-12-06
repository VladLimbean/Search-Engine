package searchengine;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanm on 06-Dec-16.
 */
public class SearchResultsHolder
{
    private String time;
    private List<PartialWebsite> websites;

    public SearchResultsHolder(long time, List<Website> websites)
    {
        if (time == 0)
        {
            this.time = "No input provided.";
        }
        else if (time > 1000000000)
        {
            double timeInSeconds = (double)time / (double)1000000000;
            this.time = "Found " + websites.size() + " websites. Search took " + timeInSeconds + " s";
        }
        else
        {
            double timeInMs = (double)time / (double)1000000;
            this.time = "Found " + websites.size() + " websites. Search took " + timeInMs + " ms";
        }

        this.websites = new ArrayList<>();
        for (Website w : websites)
        {
            this.websites.add(new PartialWebsite(w.getTitle(), w.getUrl(), w.getExtract()));
        }
    }

    public String getTime() {
        return time;
    }

    private class PartialWebsite
    {
        private String title;
        private String url;
        private String extract;

        public PartialWebsite(String title, String url, String extract)
        {
            this.title = title;
            this.url = url;
            this.extract = extract;
        }
    }
}
