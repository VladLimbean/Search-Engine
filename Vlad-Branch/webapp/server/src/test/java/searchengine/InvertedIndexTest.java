package searchengine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vlad on 22/11/2016.
 */
public class InvertedIndexTest {
    private List<Website> testList;
    private InvertedIndex buildIndex;
    private InvertedIndex testIndex;

    @Before
    public void setUp(){
        testList = new ArrayList<Website>();
        testList.add(new Website("4chan.org", "m00t", new ArrayList<String>()));
        //testIndex.build(testList);
    }
    @Test
    public void lookupTest() throws Exception{
        assertEquals(new ArrayList<Website>(), testIndex.lookup(""));
    }
    @Test
    public void buildHashmapBenchmark(){
        buildIndex = new InvertedIndex(new HashMap<>());

        testList = FileHelper.fileReader("Data/enwiki-medium.txt");

        int warmup = 5;
        for (int i=0; i<warmup; i++){
            buildIndex.build(testList);
        }
        long startTime = System.nanoTime();
        buildIndex.build(testList);
        long endTime = System.nanoTime();

        // Build function execution time is: 124104358849 - 12m, 52s, 566ms on the Medium.txt
        long stableBuildTime = endTime - startTime;
        System.out.println("Build function execution time is: " + stableBuildTime);
    }
    @Test
    // Build function execution time is: 133273958221 - 13m, 33s, 76ms
    public void buildTreemapBenchmark(){
        buildIndex = new InvertedIndex(new TreeMap<>());

        testList = FileHelper.fileReader("Data/enwiki-medium.txt");

        int warmup = 5;
        for (int i=0; i<warmup; i++){
            buildIndex.build(testList);
        }
        long startTime = System.nanoTime();
        buildIndex.build(testList);
        long endTime = System.nanoTime();

        long stableBuildTime = endTime - startTime;
        System.out.println("Build function execution time is: " + stableBuildTime);
    }
}
