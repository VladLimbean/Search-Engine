import java.util.*;

/**
 * Created by Joso on 15.11.2016.
 */

public class SearchEngine {

    public static void main(String[] args) {
        System.out.println("Welcome to the search engine");

        if (args.length != 1) {
            System.out.println("Error: Please provide a file name for your file");
            return;
        }

        List<Website> list = FileHelper.parseFile(args[0]);


        System.out.println("Please enter a word");

        Index index = new InvertedIndex(new TreeMap<String,List<Website>>());
        index.build(list);
        Scanner invertedIndex = new Scanner(System.in);
        while(invertedIndex.hasNext()) {
            String query = invertedIndex.next();

            long startTime = System.nanoTime();
                List<Website> something = index.lookup(query);
            long elapsedTime = System.nanoTime() - startTime;

            System.out.println("It took " +
                    (elapsedTime) + "nanoseconds" );
            for(Website fromInvertedIndex : something) {
                System.out.println(fromInvertedIndex.getUrl());
            }
            }
        }
    }

