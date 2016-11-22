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
public class SearchEngine extends ResourceConfig
{
    private static Index index;

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
    public static void main(String[] args)
    {
        System.out.println("Welcome to the Search Engine");

        if (args.length != 1)
        {
            System.out.println("Error: Please provide a filename <filename>");
            return;
        }

        // Build the list of websites using the FileHelper.
        List<Website> list = FileHelper.parseFile(args[0]);

        //Create the Index and build it using the parsed list
        index = new InvertedIndex(true);
        index.build(list);

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
    public List<String> search(@Context HttpServletResponse response, @QueryParam("query") String query)
    {
        // Set crossdomain access. Otherwise your browser will complain that it does not want
        // to load code from a different location.
        response.setHeader("Access-Control-Allow-Origin", "*");

        if (query == null)
        {
            return new ArrayList<String>();
        }

        System.out.println("Handling request for query word \"" + query + "\"");


        //Search for the query in the list of websites.
        List<Website> resultList = index.lookup(query);

        //Create the string list that will be returned by the method
        List<String> listToReturn = new ArrayList<>();
        for (Website w : resultList)
        {
            listToReturn.add(w.getUrl());
        }

        System.out.println("Found " + listToReturn.size() + " websites.");

        //Return the final list
        return listToReturn;
    }
}
