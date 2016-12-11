import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import searchengine.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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
        assertEquals(this.parsedList.size(), 4);

        Website first = this.parsedList.get(0);
        assertEquals(first.getUrl(), "https://en.wikipedia.org/wiki/United_States");
        assertEquals(first.getTitle(), "United States");
        assertEquals(first.getWordsCount(), 5);
        Map<String, Integer> firstWords = first.getAllFrequencies();
        assertTrue(firstWords.get("the") > 0);
        assertTrue(firstWords.get("america") > 0);

        Website second = this.parsedList.get(1);
        assertEquals(second.getUrl(), "https://en.wikipedia.org/wiki/Denmark");
        assertEquals(second.getTitle(), "Denmark");
        assertEquals(second.getWordsCount(), 12);
        Map<String, Integer> secondWords = second.getAllFrequencies();
        assertTrue(secondWords.get("denmark") > 0);
        assertTrue(secondWords.get("europe") > 0);

        Website third = this.parsedList.get(2);
        assertEquals(third.getUrl(), "https://en.wikipedia.org/wiki/Japan");
        assertEquals(third.getTitle(), "Japan");
        assertEquals(third.getWordsCount(), 15);
        Map<String, Integer> thirdWords = third.getAllFrequencies();
        assertTrue(thirdWords.get("japan") > 0);
        assertTrue(thirdWords.get("ocean") > 0);
    }
}