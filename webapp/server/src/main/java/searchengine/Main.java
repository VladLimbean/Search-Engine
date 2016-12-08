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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The main class of our search engine program.
 *
 * @author Martin Aumüller
 * @author Leonid Rusnac
 */
@Configuration
@EnableAutoConfiguration
@Path("/")
public class Main extends ResourceConfig {
    private static List<Website> list;
    //Hold message from initial file checker
    private static String fileScanMessage;
    //checks if file is readable
    private static boolean isValid = true;
    //stores user input
    private static String filePathGiven;
    //remembers an input that is of a valid file
    private static String filePathValid;
    //holds a list of websites from FileHelper
    private static List<Website> listOfValidWebsites;
    //time measure variable start,end & total
    private static long startTime;
    private static long endTime;
    private static long totalTime;
    //stores the amount of lines in the provided file
    private static int count;
    private  static InterfaceIndex index;


    public Main() {
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
        //Ask user for a file input
        System.out.println("***Search Console*** Please, provide a filepath for search, to configure website library:\n");
        //Initialize Scanner
        Scanner console = new Scanner(System.in);
        //write input to variable
        filePathGiven = console.nextLine();
        //get return string -message, by running method initialFileAnalysis
        String message = initialFileAnalysis(filePathGiven);
        //print returned message
        System.out.print(message);
        while (!isValid) {
            //inform user and ask for file again
            System.out.print("***Search Console*** Are you sure following file path:   "
                    + filePathGiven + "   ,is a correct path? :)\n");
            System.out.print("***Search Console*** Here, try again!: \n");
            //listen on next line and re-write filePathGiven
            filePathGiven = console.nextLine();
            //get return string -message, by running method initialFileAnalysis
            message = initialFileAnalysis(filePathGiven);
            //print message
            System.out.print(message);
        }
        //Pass FilePathValid to the FileHelper Class and Store returned list in the listOfAllWebsites
        listOfValidWebsites = FileHelper.providedFile(filePathValid);
        //create inverted index, using hashmap
        index = new InvertedIndex(new HashMap<>());
        // Inform user of what is happening
        System.out.println("\n***Search Console*** Starting the process of building searchable list...");
        //check start time
        startTime = System.nanoTime();
        //initialize buildList method and provide list to populate
        index.buildList(listOfValidWebsites);
        //check end time
        endTime = System.nanoTime();
        //calculate total time
        totalTime = (endTime - startTime) / 100000;
        //informs user about build time result
        System.out.println("***Search Console*** The list has been built in: " + totalTime + " ms...");
        // Inform the Admin
        System.out.println("***Search Console*** Starting Spring Https:// server");

        // Later: Build the index from this list.
        SpringApplication.run(Main.class, args);
    }

    /**
     * This methods handles requests to GET requests at search.
     * It assumes that a GET request of the form "search?query=word" is made.
     *
     * @param response Http response object
     * @param query    the query string
     * @return the list of url's of websites matching the query
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    public List<String> search(@Context HttpServletResponse response, @QueryParam("query") String query) {
        // Set crossdomain access. Otherwise your browser will complain that it does not want
        // to load code from a different location.
        response.setHeader("Access-Control-Allow-Origin", "*");

        if (query == null) {
            return new ArrayList<String>();
        }

        System.out.println("Handling request for query word \"" + query + "\"");

        //Search for the query in the list of websites.
        List<Website> resultList = SplitQuery.splitInput(query, index);

        //Create the string list that will be returned by the method
        List<String> listToReturn = new ArrayList<>();
        for (Website w : resultList) {
            String arrayToString = w.getContent().toString()
                    .replace(",", "")  //remove the commas
                    .replace("[", "")  //remove the right bracket
                    .replace("]", "")  //remove the left bracket
                    .trim();

            listToReturn.add("<br><a href=\"" + w.getUrl() + "\" class=\"red-text text-lighten-2\"><h4>" + w.getTitle() + "</h4></a>");
            listToReturn.add ("<b class=\"red-text text-lighten-3\">Dexcription: </b>" + "..." + w.getTitle() + " " +arrayToString + "...");
            listToReturn.add("<b class=\"red-text text-lighten-3\">Full Source URL: </b><a href=\"" + w.getUrl() + "\">" + w.getUrl() + "</a></p>");
            listToReturn.add("</p>");

        }

        System.out.println("Found " + resultList.size() + " websites.");

        //Return the final list
        return listToReturn;
    }



    //This method checks the validity file and catches all Input Stream Exceptions to secure the application from just failing when there is wrong input
    private static String initialFileAnalysis(String filePath) {
        //reset count back to 0
        count = 0;
        //try block
        try {
            //initialize FileReader with FilePath
            FileReader fr = new FileReader(filePath);
            //initialize scanner to read file
            Scanner scan = new Scanner(fr);
            //while loop to count lines
            while (scan.hasNextLine()) {
                scan.nextLine();
                //add +1 for each line in file
                count++;
            }

            if (count == 0) {
                isValid = false;
            } else {
                //provide a message if the file is OK
                fileScanMessage = "***Search Console*** The file you have provided contains:"
                        + count + " lines... Sending file for web analysis!";
                //set sivalid to be true so the while statement in main doesnt do anything
                isValid = true;
                //save provided file path to be then processed by File Reader
                filePathValid = filePath;

            }
            //close scanner
            scan.close();
            //catch exceptions like FileNotFound
        } catch (IOException e) {
            //informs user about the files issue
            fileScanMessage = "***Search Console*** File not found or could not have been opened & "
                    + count + " lines of text were found :'(\n";
            //set isValid false, main will do while loop, to ask user again for file path and repeat until conditions are met
            isValid = false;

        }
        //give back the file status message
        return fileScanMessage;
    }
}