import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Joso on 05.10.2016.
 */
public class SearchEngine {

    public static void main(String[] args) {
        System.out.println("Welcome to the Search Engine");

        if (args.length != 1) {
            System.out.println("Error: Please provide a filename <filename>");
            return;
        }

        //defines input file
        String fileName = args[0];
        // creates hashmap called index
        List<Page> index = FileHelper.parseFile(fileName);

        InvertedIndex hashIndex =
                new InvertedIndex(new HashMap<String, List<Page>>());
        hashIndex.build(index);
        System.out.println("Welcome to the search engine. Please type a word");
        // awaits command line input from user
        Scanner sc = new Scanner(System.in);
        // returns list of websites relative to user input
        while (sc.hasNext()) {
            String userInput = sc.next();
            // stops program if 'quit' is a cmd line
            if (userInput.equals("quit")){
                return;
            }

            List<Page> resultsFound = hashIndex.lookup(userInput);

            if (resultsFound.size() == 0){
             System.out.println("No results found for user input " + userInput);
            }
            else {
                for(Page s : resultsFound) {
                    System.out.println(s.getTitle() + " " + s.getUrl());
                }
            }
        }
    }
}
