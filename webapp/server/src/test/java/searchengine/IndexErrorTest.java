package searchengine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Joso on 29.11.2016.
 */
public class IndexErrorTest {

    private Index indexToTest;

    @Before
    public void setUp() throws Exception {
        List<Website> testList = FileHelper.parseFile("test-resources/test-errors.txt");
        this.indexToTest = new SimpleIndex();
        this.indexToTest.build(testList);
    }

    @After
    public void tearDown() throws Exception {
        indexToTest = null;
    }

    @Test
    public void firstLookup() throws Exception{
        List<Website> word1Test = this.indexToTest.lookup("word1");
        // Verifies that word1 occurs in 1 website.
        assertEquals(1, word1Test.size());
        // Verifies that word1 occurs in 1 website.
        assertEquals("Title", word1Test.get(0).getTitle());
        // Verifies that url related to the word japan is "https://thisshouldwork.com"
        assertTrue(word1Test.get(0).getUrl().equals("https://thisshouldwork.com"));
    }

    @Test
    public void secondLookup() throws Exception{
        List<Website> japanTest = this.indexToTest.lookup("japan");
        // Verifies that japan occurs in 1 website.
        assertEquals(1, japanTest.size());
        // Verifies that title related to word1 is "Japan"
        assertEquals("Japan", japanTest.get(0).getTitle());
        // Verifies that url related to the word japan is "https://en.wikipedia.org/wiki/Japan"
        assertTrue(japanTest.get(0).getUrl().equals("https://en.wikipedia.org/wiki/Japan"));
    }
}