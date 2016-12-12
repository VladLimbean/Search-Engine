import searchengine.*;

import java.math.BigInteger;
import java.util.List;

/**
 * Tests the build and lookup time of all three indices
 */
public class BenchmarkIndex
{
    private static final int testLoopsCount = 1000;
    private static final String[] testQueries = new String[] {"america", "denmark", "of", "university"};

    public static void main(String[] args)
    {
        //Parse the file
        List<Website> websites = FileHelper.parseFile(args[0]);

        //Create the Simple Index
        Index simpleIndex = new SimpleIndex();
        //Test the Simple Index
        //testBuild(simpleIndex, websites);
        simpleIndex.build(websites);
        testLookup(simpleIndex);

        //Create the Inverted Index with a TreeMap
        Index invertedIndexTree = new InvertedIndex(false);
        //Test the Inverted Index
        //testBuild(invertedIndexTree, websites);
        invertedIndexTree.build(websites);
        testLookup(invertedIndexTree);

        //Create the Inverted Index with a HashMap
        Index invertedIndexHash = new InvertedIndex(true);
        //Test the Inverted Index
        //testBuild(invertedIndexHash, websites);
        invertedIndexHash.build(websites);
        testLookup(invertedIndexHash);
    }

    private static void testBuild(Index indexToTest, List<Website> listOfWebsites)
    {
        //Empty for loop so Java can "warm up"
        int a = 0;
        for (int i = 0; i < 1000000; i++)
        {
            a++;
        }

        //Save the time when the test starts
        long startTime = System.nanoTime();

        for (int i = 0; i < testLoopsCount; i++)
        {
            //Execute the build function
            indexToTest.build(listOfWebsites);
        }

        //Get the total time
        long totalTime = System.nanoTime() - startTime;

        //Calculate the average runtime
        long averageTime = (totalTime / testLoopsCount);

        //Print the result
        System.out.printf(
                "Average build time for %s after running %s times is %s nanoseconds \n",
                indexToTest.toString(),
                testLoopsCount,
                averageTime);
    }

    private static void testLookup(Index indexToTest)
    {
        //Empty for loop so Java can "warm up"
        int a = 0;
        for (int i = 0; i < 1000000; i++)
        {
            a++;
        }

        //Save the time when the test starts
        long startTime = System.nanoTime();

        //Test the lookup for a range of words by going through them
        for (String word : testQueries)
        {
            for (int i = 0; i < testLoopsCount; i++)
            {
                //Execute the lookup function
                List<Website> results = indexToTest.lookup(word);
            }
        }

        //Get the total time
        long totalTime = (System.nanoTime() - startTime);

        //Calculate the average runtime
        long averageTime = (totalTime / (testLoopsCount * testQueries.length));

        //Print the result
        System.out.printf(
                "Average lookup time for %s after running %s times is %s nanoseconds.\n",
                indexToTest.toString(),
                testLoopsCount,
                averageTime);
    }
}