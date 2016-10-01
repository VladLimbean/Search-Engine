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

        System.out.println("Please enter a word");

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.next();
            // Search for line in the list of websites.
            for (Website w: list) {
                if (w.containsWord(line)) {
                    System.out.println("Word found on " + w.getUrl());
                }
            }
        }

    }
}
