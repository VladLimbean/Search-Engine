import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchengine.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 22-Nov-16.
 */
public class QuerySplitTest
{
    private Index indexToTest;
    private Score rankingHandler;

    @Before
    public void setUp() throws Exception
    {
        List<Website> testList = FileHelper.parseFile("test-resources/test.txt");
        this.indexToTest = new InvertedIndex(true);
        this.indexToTest.build(testList);

        this.rankingHandler = new ScoreTFIDF(this.indexToTest);
    }

    @After
    public void tearDown() throws Exception
    {
        this.indexToTest = null;
        this.rankingHandler = null;
    }

    @Test
    public void shouldHaveResultsTest() throws Exception
    {
        List<Website> shouldHaveResults = QuerySplit.getMatchingWebsites("america", this.indexToTest, this.rankingHandler);
        assertEquals(1, shouldHaveResults.size());
        assertEquals("United States", shouldHaveResults.get(0).getTitle());
    }

    @Test
    public void shouldNotHaveResults() throws Exception
    {
        List<Website> shouldNotHaveResults =
                QuerySplit.getMatchingWebsites("asdf", this.indexToTest, this.rankingHandler);
        assertEquals(0, shouldNotHaveResults.size());
    }

    @Test
    public void testComplexQueryWithoutOR() throws Exception
    {
        List<Website> complexQueryTestWithoutOR =
                QuerySplit.getMatchingWebsites("states america", this.indexToTest, this.rankingHandler);
        assertEquals(1, complexQueryTestWithoutOR.size());
        assertEquals("United States", complexQueryTestWithoutOR.get(0).getTitle());
    }

    @Test
    public void testComplexQueryWithOR() throws Exception
    {
        List<Website> complexQueryTestWithOR = QuerySplit.getMatchingWebsites("states america OR denmark country", this.indexToTest, this.rankingHandler);
        assertEquals(2, complexQueryTestWithOR.size());
        assertEquals("United States", complexQueryTestWithOR.get(0).getTitle());
        assertEquals("Denmark", complexQueryTestWithOR.get(1).getTitle());
    }

    @Test
    public void testZeroResultsComplexQuery() throws Exception
    {
        //This should have 0 results because there are no websites that have BOTH america AND denmark
        List<Website> zeroResultsComplexQuery = QuerySplit.getMatchingWebsites("america denmark", this.indexToTest, this.rankingHandler);
        assertEquals(0, zeroResultsComplexQuery.size());
    }

    @Test
    public void testTwoSpacesQuery() throws Exception
    {
        //States  america has two spaces between them
        List<Website> results = QuerySplit.getMatchingWebsites("states  america", this.indexToTest, this.rankingHandler);
        assertEquals(1, results.size());
    }

    @Test
    public void testTwoSpacesBetweenOrQuery() throws Exception
    {
        //States  OR  america has two spaces before OR and two after OR
        List<Website> results = QuerySplit.getMatchingWebsites("states  OR  denmark", this.indexToTest, this.rankingHandler);
        assertEquals(2, results.size());
    }

    @Test
    public void testOnlySpaces() throws Exception
    {
        List<Website> results = QuerySplit.getMatchingWebsites("     ", this.indexToTest, this.rankingHandler);
        assertEquals(0, results.size());
    }

    @Test
    public void testOnlyOR() throws Exception
    {
        List<Website> results = QuerySplit.getMatchingWebsites(" OR ", this.indexToTest, this.rankingHandler);
        assertEquals(0, results.size());
    }

    @Test
    public void testOnlyORAndSpaces() throws Exception
    {
        List<Website> results = QuerySplit.getMatchingWebsites("     OR           ", this.indexToTest, this.rankingHandler);
        assertEquals(0, results.size());
    }

    @Test
    public void testComplexQueryManyOROperators() throws Exception
    {
        List<Website> results = QuerySplit.getMatchingWebsites("denmark OR scandinavia OR america OR scandinavia denmark", this.indexToTest, this.rankingHandler);
        assertEquals(3, results.size());
        assertEquals("Denmark", results.get(0).getTitle());
        assertEquals("Scandinavia", results.get(1).getTitle());
        assertEquals("United States", results.get(2).getTitle());
    }
}