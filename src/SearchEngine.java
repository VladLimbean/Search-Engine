import java.util.List;
import java.util.Scanner;

public class SearchEngine
{
    public static void main(String[] args)
    {
        //Parse the .txt file
        List<Website> websites = FileHelper.parseFile(args[0]);
        Index index = new InvertedIndex(true);
        index.build(websites);

        //Print out some friendly messages
        System.out.println("Welcome to the Search Engine!");
        System.out.println("Please write a word you want to search for");
        System.out.println("Remember that searching for quit will terminate the program");

        //Initialize a scanner that takes the user input
        Scanner userInputScanner = new Scanner(System.in);
        while (userInputScanner.hasNext())
        {
            //Get the word the user wants to search for
            String query = userInputScanner.next();

            //Quit the program if the user searched for quit
            if (query.equals("quit"))
            {
                System.out.println("Bye :)");
                break;
            }

            //Execute the search
            List<Website> results = index.lookup(query);

            //Go though all websites in the results
            for (Website w : results)
            {
                System.out.println(w.getTitle() + ": " + w.getUrl());
            }

            //Print a message if no results were found
            if (results.size() == 0)
            {
                System.out.println("No results found!");
            }
        }
    }
}
