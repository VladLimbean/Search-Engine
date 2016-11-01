import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by IRCT on 11/1/2016.
 */
public class PerformanceTest {
    public static void main (String[] args) {
        List<Website> list = FileHelper.parseFile("Data\\enwiki-medium.txt");
        PerformanceTest.runTest(list);
        PerformanceTest.runTestSearch(list);

    }

    public static void runTest(List<Website> list) {

        SimpleIndex simple =  new SimpleIndex();
        int n = 0;
        long[] timeArray = new long[1000];

        while (n<1000) {
            long startTime = System.nanoTime();

            simple.build(list);

            long endTime = System.nanoTime() - startTime;
            timeArray[n] = endTime;
            n++;
        }

        int count = 0;
        long totalTime = 0;

                while (count<timeArray.length){
                            totalTime= totalTime +  timeArray[count];
                    count++;
                }

                System.out.print("Total time is: " + totalTime);
              System.out.println("Average time: " + totalTime/1000);




        InvertedIndex simpleInverted =  new InvertedIndex(new HashMap<>());
         n = 0;
        long[] timeArrayInverted = new long[1000];

        while (n<1000) {
            long startTime = System.nanoTime();

            simple.build(list);

            long endTime = System.nanoTime() - startTime;
            timeArray[n] = endTime;
            n++;
        }

        count = 0;
        long totalTimeInverted = 0;

        while (count<timeArray.length){
            totalTime= totalTime +  timeArray[count];
            count++;
        }
        System.out.println("Inverted Index");

        System.out.println("Total time is: " + totalTime);
        System.out.println("Average time: " + totalTime/1000);




    InvertedIndex simpleInvertedTree =  new InvertedIndex(new TreeMap<>());
     n = 0;
    long[] timeArrayInvertedTree = new long[1000];

        while (n<1000) {
        long startTime = System.nanoTime();

        simple.build(list);

        long endTime = System.nanoTime() - startTime;
        timeArray[n] = endTime;
        n++;
    }

    count = 0;
    long totalTimeInvertedTree = 0;

                while (count<timeArray.length){
        totalTime= totalTime +  timeArray[count];
        count++;
    }
        System.out.println("Using tree map");

        System.out.println("Total time is: " + totalTime);
              System.out.println("Average time: " + totalTime/1000);



}

public static void runTestSearch(List<Website> list) {
    String keywords = "aa";
    int i = 0;
    SimpleIndex simple = new SimpleIndex();
    simple.build(list);
    long startTime = System.nanoTime();
    long[] time = new long[1000];
    long totalTime = 0;


    while (i < 1000) {
         List<Website> resultsFound = simple.lookup(keywords);
            long endTime = System.nanoTime() - startTime;
            time[i] = endTime;
            totalTime = totalTime + time[i];
        i++;
    }

    System.out.println("Time it took to find thousan times: " + totalTime);
    System.out.println("Average Time: " + totalTime/1000);

    InvertedIndex invertedIndHash = new InvertedIndex(new HashMap<>());
    invertedIndHash.build(list);
    long startTimeHash = System.nanoTime();
    long[] timeTree = new long[1000];
    long totalTimeTree = 0;
    i = 0;

    while (i < 1000) {
        List<Website> resultsFoundHash = invertedIndHash.lookup(keywords);
        long endTime = System.nanoTime() - startTimeHash;
        time[i] = endTime;
        totalTimeTree = totalTimeTree + time[i];
        i++;
    }
    System.out.println("\n INVERTED INDEX HASH SEARCH");
    System.out.println("Time it took to find thousan times: " + totalTimeTree);
    System.out.println("Average Time: " + totalTimeTree/1000);

    InvertedIndex invertedIndTree = new InvertedIndex(new TreeMap<>());
    invertedIndTree.build(list);
    long startTimeTree = System.nanoTime();
    List<Website> resultsFoundTree = simple.lookup(keywords);
     timeTree = new long[1000];
     totalTimeTree = 0;
    i = 0;


    while (i < 1000) {
        resultsFoundTree = invertedIndTree.lookup(keywords);
        long endTime = System.nanoTime() - startTimeTree;
        time[i] = endTime;
        totalTimeTree = totalTimeTree + time[i];
        i++;
    }

    System.out.println("\n INVERTED INDEX TREE SEARCH");
    System.out.println("Time it took to find thousan times: " + totalTimeTree);
    System.out.println("Average Time: " + totalTimeTree/1000);
}

}
