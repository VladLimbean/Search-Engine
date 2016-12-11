import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchengine.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests the Website class
 */
public class WebsiteTest
{
    private Website websiteToTest;
    private Map<String, Integer> keywordsToTest;

    @Before
    public void setUp() throws Exception
    {
        String url = "http://wikipedia.org";
        String title = "Wikipedia";

        Map<String, Integer> freq = new HashMap<>();
        freq.put("wikipedia", 1);
        freq.put("test", 1);
        freq.put("usa", 1);
        freq.put("denmark", 1);
        freq.put("copenhagen", 1);

        this.keywordsToTest = freq;
        this.websiteToTest = new Website(url, title, "", freq, 5);
    }

    @After
    public void tearDown() throws Exception
    {
        this.websiteToTest = null;
    }

    @Test
    public void getUrlTest() throws Exception
    {
        assertEquals("http://wikipedia.org", this.websiteToTest.getUrl());
    }

    @Test(expected = MalformedURLException.class)
    public void tryFakeUrlTest() throws Exception
    {
        Website fakeWebsite = new Website("fakeurl", "title", "", new HashMap<>(), 0);
    }

    @Test
    public void getTitleTest() throws Exception
    {
        assertEquals("Wikipedia", this.websiteToTest.getTitle());
    }

    @Test
    public void getKeywordsTest() throws Exception
    {
        assertEquals(this.keywordsToTest, this.websiteToTest.getAllFrequencies());
        assertEquals(1, this.websiteToTest.getTermFrequency("wikipedia"));
        assertEquals(1, this.websiteToTest.getTermFrequency("test"));
        assertEquals(1, this.websiteToTest.getTermFrequency("usa"));
        assertEquals(1, this.websiteToTest.getTermFrequency("denmark"));
        assertEquals(0, this.websiteToTest.getTermFrequency("nonexistantword"));
    }
}