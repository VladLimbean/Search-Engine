import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the FileHelper class
 */
public class FileHelperTest
{
    List<Website> parsedList;

    @Before
    public void setUp() throws Exception
    {
        this.parsedList = FileHelper.parseFile("test-resources/test.txt");
    }

    @After
    public void tearDown() throws Exception
    {
        this.parsedList = null;
    }

    @Test
    public void checkContentsTest() throws Exception
    {
        assertEquals(this.parsedList.size(), 3);

        Website first = this.parsedList.get(0);
        assertEquals(first.getUrl(), "https://en.wikipedia.org/wiki/United_States");
        assertEquals(first.getTitle(), "United States");
        assertEquals(first.getKeywords().size(), 5);
        List<String> firstWords = first.getKeywords();
        assertEquals(firstWords.get(0), "the");
        assertEquals(firstWords.get(4), "america");

        Website second = this.parsedList.get(1);
        assertEquals(second.getUrl(), "https://en.wikipedia.org/wiki/Denmark");
        assertEquals(second.getTitle(), "Denmark");
        assertEquals(second.getKeywords().size(), 7);
        List<String> secondWords = second.getKeywords();
        assertEquals(secondWords.get(0), "denmark");
        assertEquals(secondWords.get(6), "europe");

        Website third = this.parsedList.get(2);
        assertEquals(third.getUrl(), "https://en.wikipedia.org/wiki/Japan");
        assertEquals(third.getTitle(), "Japan");
        assertEquals(third.getKeywords().size(), 13);
        List<String> thirdWords = third.getKeywords();
        assertEquals(thirdWords.get(0), "japan");
        assertEquals(thirdWords.get(12), "ocean");
    }
}