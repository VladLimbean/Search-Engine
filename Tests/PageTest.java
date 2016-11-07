import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Joso on 11.10.2016.
 */
public class PageTest {

    private Website testPage;

    @org.junit.Before
    public void setUp() throws Exception {
        List<String> testWords = new ArrayList<String>();
        testWords.add("the");
        testWords.add("king");
        this.testPage = new Website("www.wikipedia.com", "United" , testWords);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        this.testPage = null;

    }

    @org.junit.Test
    public void containsWord() throws Exception {
        assertEquals(true, this.testPage.getWords().contains("the"));
        assertEquals(false, this.testPage.getWords().contains("4chan"));
    }

    @org.junit.Test
    public void getUrl() throws Exception {
        assertEquals( "www.wikipedia.com" , this.testPage.getUrl());

    }

    @org.junit.Test
    public void getTitle() throws Exception {
        assertEquals("United" , this.testPage.getTitle() );

    }

    @org.junit.Test
    public void getWords() throws Exception {
        assertEquals("the" , this.testPage.getWords().get(0));

    }

}