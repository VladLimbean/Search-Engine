package searchengine;
import java.util.*;

/**
 * Created by IRCT on 11/1/2016.
 */
public class PerformanceTest
{
    public static void main (String[] args)
    {
        List<Website> list = FileHelper.parseFile("Data\\enwiki-medium.txt");

        SimpleIndex simpleIndex = new SimpleIndex();
        //testBuilding(simpleIndex, list);
        //testLookup(simpleIndex, list);

        InvertedIndex hashMapIndex = new InvertedIndex(new HashMap<>());
        //testBuilding(hashMapIndex, list);
        testLookup(hashMapIndex, list);

        InvertedIndex treeMapIndex = new InvertedIndex(new TreeMap<>());
        //testBuilding(treeMapIndex, list);
        testLookup(treeMapIndex, list);
    }

    private static void testBuilding(Index index, List<Website> list)
    {
        //Initialize counter and empty array
        int n = 0;
        long[] timeArray = new long[1000];

        //Execute build test 1000 times
        while (n<1000)
        {
            long startTime = System.nanoTime();
            index.build(list);
            long endTime = System.nanoTime() - startTime;
            timeArray[n] = endTime;
            n++;
        }

        //Calculate average time
        int count = 0;
        long totalTime = 0;
        while (count<timeArray.length)
        {
            totalTime= totalTime +  timeArray[count];
            count++;
        }

        //Print results
        System.out.println("Running .build test for " + index.toString());
        System.out.println("Total time is: " + totalTime);
        System.out.println("Average time: " + totalTime/1000);
        System.out.println();
    }

    public static void testLookup(Index index, List<Website> list)
    {
        //Build the SimpleIndex or the InvertedIndex
        index.build(list);

        //Initialize the keywords that we search for
        String[] keywords = new String[] { "usa", "in", "film", "a" };

        for (String keyword : keywords) {
            //Initialize variables for tests
            int i = 0;
            long[] time = new long[1000];
            long totalTime = 0;

            //Run tests a 1000 times
            while (i < 1000) {
                long startTime = System.nanoTime();
                List<Website> resultsFound = index.lookup(keyword);
                long endTime = System.nanoTime() - startTime;
                time[i] = endTime;
                totalTime = totalTime + time[i];
                i++;
            }

            //Print results
            System.out.println("Running .lookup test for " + index.toString());
            System.out.println("Time it took to find thousand times: " + totalTime);
            System.out.println("Average Time: " + totalTime / 1000);
            System.out.println();
        }
    }
}
