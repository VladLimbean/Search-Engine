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
                QuerySplit.getMatchingWebsites("america", this.index, this.rankingHandler);
        assertEquals(1, americaTest.size());
        assertEquals("United States", americaTest.get(0).getTitle());
    }

    @Test
    public void testScandinavia() throws Exception
    {
        List<Website> scandinaviaTest =
                QuerySplit.getMatchingWebsites("scandinavia", this.index, this.rankingHandler);
        assertEquals(2, scandinaviaTest.size());
        assertEquals("Scandinavia", scandinaviaTest.get(0).getTitle());
        assertEquals("Denmark", scandinaviaTest.get(1).getTitle());
    }

    @Test
    public void complexQueryTestWithoutOR() throws Exception
    {
        List<Website> queryTest =
                QuerySplit.getMatchingWebsites("scandinavia europe", this.index, this.rankingHandler);
        assertEquals(2, queryTest.size());
        assertEquals("Scandinavia", queryTest.get(0).getTitle());
        assertEquals("Denmark", queryTest.get(1).getTitle());
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