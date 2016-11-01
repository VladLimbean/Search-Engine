import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for our Search Engine. Handles the creation of an index,
 * searching for user words, and printing the results on the console.
 * The process happens in the main function.
 */
public class SearchEngine
{
    public static void main(String[] args)
    {
        //Print out the welcoming message
        System.out.println("Welcome to the Search Engine");

        //Stop execution if no .txt file was added for FileHelper
        if (args.length != 1) {
            System.out.println("Error: Please provide a filename <filename>");
            return;
        }

        //Parse the txt file and create a list of Websites
        List<Website> index = FileHelper.parseFile(args[0]);

        //Create the InvertedIndex object and build it
        InvertedIndex hashIndex =
                new InvertedIndex(new HashMap<String, List<Website>>());
        hashIndex.build(index);

        //Ask the user for a query
        System.out.println("Please type a word to search for: ");

        //Create the scanner which will take user input
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            //Get the user's input
            String userInput = sc.next();

            //Stop the program if the user entered the query "quit"
            if (userInput.equals("quit")){
                return;
            }

            //Search for the user's input and get a list of results
            List<Website> resultsFound = hashIndex.lookup(userInput);

            if (resultsFound.size() == 0)
            {
                //Print a message to the user that no results were found
                System.out.println("No results found for user input " + userInput);
            }
            else
            {
                System.out.println(resultsFound.size() + " websites found:");
                //Print every website from the results list on a separate line
                for(Website s : resultsFound)
                {
                    System.out.println(s.getTitle() + " " + s.getUrl());
                }
            }
        }
    }
}
