import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchengine.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 17-Nov-16.
 */
public class IndexTest
{
    private Index indexToTest;

    @Before
    public void setUp() throws Exception
    {
        List<Website> testList = FileHelper.parseFile("test-resources/test.txt");
        this.indexToTest = new SimpleIndex();
        this.indexToTest.build(testList);
    }

    @After
    public void tearDown() throws Exception
    {
        this.indexToTest = null;
    }

    @Test
    public void lookupTest() throws Exception
    {
        List<Website> americaTest = this.indexToTest.lookup("america");
        assertEquals(1, americaTest.size());
        assertEquals("United States", americaTest.get(0).getTitle());
    }

    @Test
    public void secondWebsiteTest() throws Exception
    {
        List<Website> denmarkTest = this.indexToTest.lookup("denmark");
        assertEquals(1, denmarkTest.size());
        assertEquals("Denmark", denmarkTest.get(0).getTitle());
    }

    @Test
    public void thirdWebsiteTest() throws Exception
    {
        List<Website> japanTest = this.indexToTest.lookup("japan");
        assertEquals(1, japanTest.size());
        assertEquals("Japan", japanTest.get(0).getTitle());
    }

    @Test
    public void noResultsTest() throws Exception
    {
        List<Website> noResultsTest = this.indexToTest.lookup("asdf");
        assertEquals(0, noResultsTest.size());
    }
}