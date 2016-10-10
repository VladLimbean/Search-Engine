import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Joso on 05.10.2016.
 */
public class SearchEngine {

    public static void main(String[] args) {
        //defines input file
        String fileName = "Data/enwiki-tiny.txt";
        // creates new object that will read txt file
        TextReader textReader = new TextReader();
        // creates hashmap called index
        List<Page> index = FileHelper.parseFile(fileName);

        InvertedIndex hashIndex = new InvertedIndex();
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
