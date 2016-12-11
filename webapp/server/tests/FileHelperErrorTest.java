import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchengine.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 28-Nov-16.
 */
public class FileHelperErrorTest
{
    List<Website> parsedList;

    @Before
    public void setUp() throws Exception
    {
        this.parsedList = FileHelper.parseFile("test-resources/test-errors.txt");
    }

    @After
    public void tearDown() throws Exception
    {
        this.parsedList = null;
    }

    @Test
    public void parseFile() throws Exception
    {
        assertEquals(2, this.parsedList.size());

        assertEquals("Title", this.parsedList.get(0).getTitle());
        assertEquals("https://thisshouldwork.com", this.parsedList.get(0).getUrl());
        assertEquals(1, this.parsedList.get(0).getTermFrequency("word1"));

        assertEquals("Japan", this.parsedList.get(1).getTitle());
        assertEquals("https://en.wikipedia.org/wiki/Japan", this.parsedList.get(1).getUrl());
        assertEquals(13, this.parsedList.get(1).getWordsCount());
    }
}