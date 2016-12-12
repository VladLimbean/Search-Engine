import searchengine.*;

import java.util.List;

/**
 * Created by ivanm on 11-Dec-16.
 */
public class BenchmarkQuerySplit
{
    private static final int testLoopsCount = 1000;
    private static final String[] testQueries = new String[] {"america", "america denmark", "of university", "america OR denmark"};

    public static void main(String[] args)
    {
        //Parse the file
        List<Website> websites = FileHelper.parseFile(args[0]);

        //Create the Simple Index
        Index simpleIndex = new SimpleIndex();
        simpleIndex.build(websites);
        //Test the Simple Index
        testQuery(simpleIndex);

        //Create the Inverted Index with a TreeMap
        Index invertedIndexTree = new InvertedIndex(false);
        invertedIndexTree.build(websites);
        //Test the Inverted Index
        testQuery(invertedIndexTree);

        //Create the Inverted Index with a HashMap
        Index invertedIndexHash = new InvertedIndex(true);
        invertedIndexHash.build(websites);
        //Test the Inverted Index
        testQuery(invertedIndexHash);
    }

    private static void testQuery(Index indexToTest)
    {
        //Empty for loop so Java can "warm up"
        int a = 0;
        for (int i = 0; i < 1000000; i++)
        {
            a++;
        }

        //Create the ranking handler
        Score rankingHandler = new ScoreTFIDF(indexToTest);

        //Save the time when the test starts
        long startTime = System.nanoTime();

        //Test the lookup for a range of words by going through them
        for (String query : testQueries)
        {
            for (int i = 0; i < testLoopsCount; i++)
            {
                //Execute the lookup function
                List<Website> results = QuerySplit.getMatchingWebsites(query, indexToTest, rankingHandler);
            }
        }

        //Get the total time
        long totalTime = (System.nanoTime() - startTime);

        //Calculate the average runtime
        long averageTime = (totalTime / (testLoopsCount * testQueries.length));

        //Print the result
        System.out.printf(
                "Average query split time for %s after running %s times is %s nanoseconds.\n",
                indexToTest.toString(),
                testLoopsCount,
                averageTime);
    }
}