import java.util.List;
import java.util.Scanner;

public class SearchEngine
{
    public static void main(String[] args)
    {
        //Parse the .txt file
        List<Website> websites = FileHelper.parseFile(args[0]);

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

            //Start a counter
            int counter = 0;


            //Go though all websites
            for (Website w : websites)
            {
                //If the specific website contains the query, print the website
                if (w.getKeywords().contains(query))
                {
                    System.out.println(w.getTitle() + ": " + w.getUrl());
                    counter++;
                }
            }

            //Print a message if no results were found
            if (counter == 0)
            {
                System.out.println("No results found!");
            }
        }
    }
}
