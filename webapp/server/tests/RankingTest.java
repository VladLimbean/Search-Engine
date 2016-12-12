import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchengine.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 22-Nov-16.
 */
public class RankingTest
{
    private Index index;
    private Score rankingHandler;

    @Before
    public void setUp() throws Exception
    {
        List<Website> testList = FileHelper.parseFile("test-resources/test.txt");
        this.index = new InvertedIndex(true);
        this.index.build(testList);

        this.rankingHandler = new ScoreBM25(this.index);
    }

    @After
    public void tearDown() throws Exception
    {
        this.index = null;
        this.rankingHandler = null;
    }

    @Test
    public void testAmerica() throws Exception
    {
        List<Website> americaTest =
                this.index.lookup("america");
        assertEquals(1, americaTest.size());
        assertEquals("United States", americaTest.get(0).getTitle());
        this.rankingHandler.calculateInverseDocumentFrequency("america", americaTest.size());
        //Score for BM25
        assertEquals(2.626865671641791, this.rankingHandler.getScore("america", americaTest.get(0)), 1e-15);
        //Score for TFIDF
        //assertEquals(2, this.rankingHandler.getScore("america", americaTest.get(0)), 1e-15);
    }

    @Test
    public void testScandinavia() throws Exception
    {
        List<Website> scandinaviaTest = this.index.lookup("scandinavia");
        assertEquals(2, scandinaviaTest.size());
        this.rankingHandler.calculateInverseDocumentFrequency("scandinavia", scandinaviaTest.size());
        //Score for BM25
        assertEquals(0.9128630705394192, this.rankingHandler.getScore("scandinavia", scandinaviaTest.get(0)), 1e-15);
        assertEquals(1.8384401114206126, this.rankingHandler.getScore("scandinavia", scandinaviaTest.get(1)), 1e-15);
        //Score for TFIDF
        //assertEquals(2.626865671641791, this.rankingHandler.getScore("scandinavia", scandinaviaTest.get(0)), 1e-15);
        //assertEquals(2.626865671641791, this.rankingHandler.getScore("scandinavia", scandinaviaTest.get(1)), 1e-15);

    }

    @Test
    public void complexQueryTestWithoutOR() throws Exception
    {
        List<Website> queryTest =
                QuerySplit.getMatchingWebsites("scandinavia europe", this.index, this.rankingHandler);
        assertEquals(2, queryTest.size());
        assertEquals("Scandinavia", queryTest.get(0).getTitle());
        assertEquals("Denmark", queryTest.get(1).getTitle());

        this.rankingHandler.calculateInverseDocumentFrequency("scandinavia", index.lookup("scandinavia").size());
        //Score for BM25
        assertEquals(1.8384401114206126, this.rankingHandler.getScore("scandinavia", queryTest.get(0)), 1e-15);
        assertEquals(0.9128630705394192, this.rankingHandler.getScore("scandinavia", queryTest.get(1)), 1e-15);

        this.rankingHandler.calculateInverseDocumentFrequency("europe", index.lookup("europe").size());
        //Score for BM25
        assertEquals(0.7630215864179299, this.rankingHandler.getScore("scandinavia", queryTest.get(0)), 1e-15);
        assertEquals(0.3788724059806873, this.rankingHandler.getScore("scandinavia", queryTest.get(1)), 1e-15);
    }

    @Test
    public void complexQueryTestWithOR() throws Exception
    {
        List<Website> queryTest =
                QuerySplit.getMatchingWebsites("scandinavia OR europe", this.index, this.rankingHandler);
        assertEquals(3, queryTest.size());
        assertEquals("Scandinavia", queryTest.get(0).getTitle());
        assertEquals("Denmark", queryTest.get(1).getTitle());
        assertEquals("Japan", queryTest.get(2).getTitle());
    }
}