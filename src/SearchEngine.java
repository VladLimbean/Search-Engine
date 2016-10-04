import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by maau on 27/09/16.
 */
public class SearchEngine {
    public static void main(String[] args) {
        System.out.println("Welcome to the Search Engine");

        if (args.length != 1) {
            System.out.println("Error: Please provide a filename <filename>");
            return;
        }
        List<Website> list = FileHelper.parseFile(args[0]);

        System.out.println("Please enter a word. Entering \"quit\" will stop the program");

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.next();

            //in case line arg is 'quit' program terminates
            if (line.equals("quit")) {
                return;
            }

            //Search for the line in the list of websites.
            //boolean wordsFound tracks if at least one website is found
            boolean wordsFound = false;

            for (Website w: list) {
                if (w.containsWord(line)) {
                    System.out.println("Word found on " + w.getUrl());
                    //words found, tracker becomes true
                    wordsFound = true;
                }
            }

            //if wordsFound is false, then there were no results during the search
            if (!wordsFound){
                System.out.println("No words found!");
            }
        }

    }
}
