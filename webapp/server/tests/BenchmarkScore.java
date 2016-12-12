import searchengine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to benchmark the Okapi BM25 algorithm.
 *
 */
public class BenchmarkScore
{
    private static final int testLoopsCount = 1000;
    public static String[] testQuery = new String[]{"america", "denmark", "of", "university"};

    public static void main(String[] args) {

        List<Website> testList = FileHelper.parseFile("Data/enwiki-medium.txt");

        InvertedIndex testIndex = new InvertedIndex(true);
        testIndex.build(testList);

        Score tfidf = new ScoreTFIDF(testIndex);
        runBenchmark(testIndex, tfidf);

        Score BM25 = new ScoreBM25(testIndex);
        runBenchmark(testIndex, BM25);
    }

    public static void runBenchmark(Index testIndex, Score score)
    {
        long startTime = System.nanoTime();
        for (String word : testQuery)
        {
            List<Website> result = testIndex.lookup(word);

            for (Website w : result)
            {
                for (int i = 0; i < testLoopsCount; i++)
                {
                    score.getScore(word, w, result.size());
                }
            }
        }

        long totalTime = System.nanoTime() - startTime;

        totalTime = totalTime / (testLoopsCount * testQuery.length);

        System.out.printf(
                "Average time for %s is %s ns\n",
                score.getClass(),
                totalTime);
    }
}
