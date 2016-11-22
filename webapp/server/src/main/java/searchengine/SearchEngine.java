package searchengine;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class of our search engine program.
 *
 * @author Martin Aumüller
 * @author Leonid Rusnac
 */
@Configuration
@EnableAutoConfiguration
@Path("/")
public class SearchEngine extends ResourceConfig {
    private static List<Website> list;

    public SearchEngine() {
        packages("searchengine");
    }

    /**
     * The main method of our search engine program.
     * Expects exactly one argument being provided. This
     * argument is the filename of the file containing the
     * websites.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Search Engine");

        if (args.length != 1) {
            System.out.println("Error: Please provide a filename <filename>");
            return;
        }

        // Build the list of websites using the FileHelper.
        list = FileHelper.parseFile(args[0]);

        // Later: Build the index from this list.
        SpringApplication.run(SearchEngine.class, args);
    }

    /**
     * This methods handles requests to GET requests at search.
     * It assumes that a GET request of the form "search?query=word" is made.
     *
     * @param response Http response object
     * @param query the query string
     * @return the list of url's of websites matching the query
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    public List<String> search(@Context HttpServletResponse response, @QueryParam("query") String query) {
        // Set crossdomain access. Otherwise your browser will complain that it does not want
        // to load code from a different location.
        response.setHeader("Access-Control-Allow-Origin", "*");

        List<String> resultList = new ArrayList<>();

        if (query == null) {
            return resultList;
        }

        String line = query;

        System.out.println("Handling request for query word \"" + query + "\"");

        // Search for line in the list of websites.
        for (Website w: list) {
            if (w.containsWord(line)) {
                resultList.add(w.getUrl());
            }
        }

        System.out.println("Found " + resultList.size() + " websites.");
        return resultList;
    }

}
