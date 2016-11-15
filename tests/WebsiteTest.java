import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the Website class
 */
public class WebsiteTest
{
    private Website websiteToTest;
    private List<String> keywordsToTest;

    @Before
    public void setUp() throws Exception
    {
        String url = "http://wikipedia.org";
        String title = "Wikipedia";

        List<String> keywords = new ArrayList<>();
        keywords.add("wikipedia");
        keywords.add("test");
        keywords.add("usa");
        keywords.add("denmark");
        keywords.add("copenhagen");

        this.keywordsToTest = keywords;
        this.websiteToTest = new Website(url, title, keywords);
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

    @Test(expected = IllegalArgumentException.class)
    public void tryFakeUrlTest() throws Exception
    {
        Website fakeWebsite = new Website("fakeurl", "title", new ArrayList<>());
    }

    @Test
    public void getTitleTest() throws Exception
    {
        assertEquals("Wikipedia", this.websiteToTest.getTitle());
    }

    @Test
    public void getKeywordsTest() throws Exception
    {
        assertEquals(this.keywordsToTest, this.websiteToTest.getKeywords());
        assertTrue(this.websiteToTest.getKeywords().contains(this.keywordsToTest.get(0)));
        assertTrue(this.websiteToTest.getKeywords().contains(this.keywordsToTest.get(1)));
        assertTrue(this.websiteToTest.getKeywords().contains(this.keywordsToTest.get(2)));
        assertTrue(this.websiteToTest.getKeywords().contains(this.keywordsToTest.get(3)));
        assertFalse(this.websiteToTest.getKeywords().contains("nonexistantword"));
    }
}