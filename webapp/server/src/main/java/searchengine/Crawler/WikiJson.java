package searchengine.Crawler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Stores data of a crawled websites.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiJson {

    @JsonProperty("title")
    public String title;

    @JsonProperty("extract")
    public String extract;

    @JsonProperty("fullurl")
    public String fullurl;

    @JsonProperty("links")
    public List<JsonWikiLinks> links;

    public class JsonWikiLinks{
        @JsonProperty("title")
        public String title;
    }
}
