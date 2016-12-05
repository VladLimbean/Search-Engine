import searchengine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to benchmark the Okapi BM25 algorithm.
 *
 */
public class BenchmarkScore {

    public static String[] testQuery = new String[]{"america", "denmark", "of", "university"};

    public static void main(String[] args) {

        List<Website> testList = FileHelper.parseFile("Data/enwiki-tiny.txt");

        InvertedIndex testIndex = new InvertedIndex(true);
        testIndex.build(testList);

        Score tfidf = new ScoreTFIDF(testIndex);
        Score BM25 = new ScoreBM25(testIndex);

        runBenchmark(testIndex, tfidf);
        runBenchmark(testIndex, BM25);

    }
    public static void runBenchmark(Index testIndex, Score score){

        long totalTime = 0;
        for (String word : testQuery)
        {
            List<Website> result = testIndex.lookup(word);
            for (Website w : result){

                long startTime = System.nanoTime();
                for (int i = 0; i < 1000; i++) {
                    score.getScore(word, w);
                }
                long endTime = System.nanoTime();
                totalTime += (endTime-startTime)/1000;
            }
        }
        System.out.println("Average time for " + score.getClass() + " is " + totalTime/testQuery.length +" ns");
    }
}
