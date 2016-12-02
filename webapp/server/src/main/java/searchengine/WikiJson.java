package searchengine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Stores one results from @class Crawler
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
    public JsonWikiLinks[] links;

    public class JsonWikiLinks{
        @JsonProperty("title")
        public String title;
    }
}
