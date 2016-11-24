package searchengine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by maau on 04/10/16.
 */
public class WebsiteTest {
    private Website w;

    @Before
    public void setUp() throws Exception {
        List<String> words = new ArrayList<String>();
        words.add("word1");
        words.add("word2");
        w = new Website("http://itu.dk", "ITU", words);
    }

    @After
    public void tearDown() throws Exception {
        w = null;
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("http://itu.dk", w.getUrl());
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("ITU", w.getTitle());
    }

    @Test
    public void containsWord() throws Exception {
        assertEquals(true, w.containsWord("word1"));
        assertEquals(true, w.containsWord("word2"));
        assertEquals(false, w.containsWord("word3"));
    }

}