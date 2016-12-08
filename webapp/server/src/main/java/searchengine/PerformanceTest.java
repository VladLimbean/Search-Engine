package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by IRCT on 11/28/2016.
 */
public class PerformanceTest {
    private static long startTime;
    private static long endTime;
    private static long totalTimeSimple;
    private static long totalTimeInvertedHash;
    private static long totalTimeInvertedTree;
    private static SimpleIndex simple;
    private static InvertedIndex invertedHash;
    private static InvertedIndex invertedTree;
    private static String keywords = "Anatolia";

    public PerformanceTest() {
    }

    public static void main(String[] args) {
        List list = FileHelper.providedFile("WikiFiles\\wiki-small.txt");
        runTest(list);
        runTestSearch(list);
    }

    /**
     * Simple method to test the speed of building list
     * @param list
     */
    public static void runTest(List<Website> list) {
        simple = new SimpleIndex();
        int i = 0;
        int j = 0;
        long[] timeArray;

        for (timeArray = new long[1000]; i < 1000; ++i) {
            startTime = System.nanoTime();
            simple.buildList(list);
            endTime = System.nanoTime() - startTime;
            timeArray[i] = endTime;
        }
        for (totalTimeSimple = 0L; j < timeArray.length; ++j) {
            totalTimeSimple += timeArray[j];
        }
        System.out.println("\n\n Normal Index Map");
        System.out.print("Total time building simple: " + totalTimeSimple);
        System.out.println("\nTime in ms: " + totalTimeSimple / 1000L);


        invertedHash = new InvertedIndex(new HashMap());
        i = 0;
        j = 0;
        for (long[] timeArrayInverted = new long[1000]; i < 1000; ++i) {
            startTime = System.nanoTime();
            invertedHash.buildList(list);
            endTime = System.nanoTime() - startTime;
            timeArray[i] = endTime;
        }
        for (totalTimeInvertedHash = 0L; j < timeArray.length; ++j) {
            totalTimeInvertedHash += timeArray[j];
        }

        System.out.println("\n\n Inverted Index Hashmap");
        System.out.println("Total time building hashmap: " + totalTimeInvertedHash);
        System.out.println("Time in ms: " + totalTimeInvertedHash / 1000L);

        invertedTree = new InvertedIndex(new TreeMap());
        i = 0;
        j = 0;
        for (long[] timeArrayInvertedTree = new long[1000]; i < 1000; ++i) {
            startTime = System.nanoTime();
            simple.buildList(list);
            endTime = System.nanoTime() - totalTimeInvertedTree;
            timeArray[i] = endTime;
        }

        for (totalTimeInvertedTree = 0L; j < timeArray.length; ++j) {
            totalTimeSimple += timeArray[j];
        }

        System.out.println("\n\n Inverted Index Treemap");
        System.out.println("Total time building tree map: " + totalTimeSimple);
        System.out.println("Time in ms: " + totalTimeSimple / 1000L);
               
        
    }
        
    /**
     * Simple Test method to Analyze speed of searching based, in index, on pre defined keyword
     * @param list
     * 
     */
    public static void runTestSearch(List<Website> list) {
        int i = 0;
        SimpleIndex simple = new SimpleIndex();
        simple.buildList(list);
        long startTime = System.nanoTime();
        long[] time = new long[1000];
        long totalTime;
        long startTimeHash;
        for (totalTime = 0L; i < 1000; ++i) {
            simple.findKeyword(keywords.toLowerCase());
            startTimeHash = System.nanoTime() - startTime;
            time[i] = startTimeHash;
            totalTime += time[i];
        }
        System.out.println("\n Normal index - SEARCH");
        System.out.println("Time it took to find: " + keywords + " 1000x  was : " + totalTime);
        System.out.println("Time in ms: " + totalTime / 1000L);

        invertedHash.buildList(list);
        startTimeHash = System.nanoTime();
        long[] timeTree = new long[1000];
        int totalTimeTree = 0;
        long startTimeTree;
        for (i = 0; i < 1000; ++i) {
            invertedHash.findKeyword(keywords);
            startTimeTree = System.nanoTime() - startTimeHash;
            time[i] = startTimeTree;
            totalTimeTree += time[i];
        }
        System.out.println("\n\n Inverted Index Hash - SEARCH");
        System.out.println("Time it took to find: " + keywords + " 1000x  was : " + totalTimeTree);
        System.out.println("Time in ms " + totalTimeTree / 1000L);
        invertedTree.buildList(list);
        startTimeTree = System.nanoTime();
        simple.findKeyword(keywords);
        for (i = 0; i < 1000; ++i) {
            invertedTree.findKeyword(keywords);
            long endTime = System.nanoTime() - startTimeTree;
            time[i] = endTime;
            totalTimeTree += time[i];
        }
        System.out.println("\n\n Inverted Index Tree - SEARCH");
        System.out.println("Time it took to find: " + keywords + " 1000x  was : " + totalTimeTree);
        System.out.println("Time in ms: " + totalTimeTree / 1000L);
        System.out.println("recorded for Simple list Search: " + time[20] + " and " + time[500] + " and " + time[989]);

    }
}
