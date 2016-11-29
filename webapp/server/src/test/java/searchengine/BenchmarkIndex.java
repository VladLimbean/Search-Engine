package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Joso on 22.11.2016.
 */
public class BenchmarkIndex {

    private static Index hashIndex;
    private static Index treeIndex;
    private static Index simpleIndex;
    private static int testLoop = 1000;

    public static void main(String[] args) {

        List<Website> list = FileHelper.parseFile(args[0]);

        long buildHashTime = buildHashMapFromInvertedIndex(list);
        System.out.printf("building using hashmap took %d miiliseconds%n" , buildHashTime);

        long buildtreeTime = buildTreeMapFromInvertedIndex(list);
        System.out.printf("building using treemap took %d milliseconds%n" , buildtreeTime);

        long buildSimpleTime = buildSimpleTime(list);
        System.out.printf("building using Simple took %d milliseconds%n" , buildSimpleTime);

        String lookupWord = "for";

        long lookupHashTime = lookupHashTime(lookupWord);
        System.out.printf("hashLookup used %d milliseconds searching after word %s %n", lookupHashTime,lookupWord);

        long lookupTreeTime = lookupTreeTime(lookupWord);
        System.out.printf("treeLookup used %d milliseconds searching after word %s%n",lookupTreeTime,lookupWord);

        long lookupSimpleTime = lookupSimpleTime(lookupWord);
        System.out.printf("simpleLookup used %d milliseconds searching after word %s%n",lookupSimpleTime,lookupWord);
    }

    /**
     * @param someList List of all websites from fileHelper
     */
    private static long buildHashMapFromInvertedIndex(List<Website> someList) {

        hashIndex = new InvertedIndex(new HashMap<>());

        long start = System.nanoTime();
        hashIndex.build(someList);
        long end = (System.nanoTime() - start)/1000;

        return end;
    }

    /**
     * @param query word that the user searches for
     * @return The time it takes to lookup word using HashMap
     */
    private static long lookupHashTime(String query) {

        long startTime = System.nanoTime();
        for(int i = 0 ; i<testLoop ; i++) {
            hashIndex.lookup(query);
        }
        long endTime = (System.nanoTime() - startTime)/1000;

        return endTime;
    }

    /**
     * @param anotherList List of all websites from fileHelper
     */
    private static long buildTreeMapFromInvertedIndex(List<Website> anotherList) {
        treeIndex = new InvertedIndex(new TreeMap<>());

        long start = System.nanoTime();
        treeIndex.build(anotherList);
        long end = (System.nanoTime() - start)/1000;

        return end;
    }

    /**
     * @param query word that the user searches for
     * @return The time it takes to check for a query using TreeMap
     */
    private static long lookupTreeTime(String query) {

        long startTime = System.nanoTime();
        for(int i = 0 ; i< testLoop; i++) {
            treeIndex.lookup(query);
        }
        long endTime = (System.nanoTime() - startTime)/1000;

        return endTime;
    }

    /**
     * @param simpleWebsites List of all websites from filehelper
     */
    public static long buildSimpleTime(List<Website> simpleWebsites) {
        simpleIndex = new SimpleIndex();

        long start = System.nanoTime();
        simpleIndex.build(simpleWebsites);
        long endTime = (System.nanoTime() - start)/1000;

        return endTime;
    }

    /**
     * @param query = word that the user searches for
     * @return The time it takes to search for a query using SimpleIndex
     */
    public static  long lookupSimpleTime(String query) {

        long startTime = System.nanoTime();
        for(int i = 0 ; i< testLoop; i++) {
            simpleIndex.lookup(query);
        }
        long endTime = (System.nanoTime() - startTime)/1000;

        return endTime;
    }
}