package searchengine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vlad on 22/11/2016.
 */
public class SimpleIndexTest {
    private List<Website> testList;
    private List<String> testWords;
    private SimpleIndex index;

    @Before
    public void setUp() throws Exception{
        testList = new ArrayList<>();
        index = new SimpleIndex();

        testWords = new ArrayList<>();
        testWords.add("power");
        testWords.add("absolute");

        Website testWebsite = new Website("amazon.com", "amazon", testWords);

        testList.add(testWebsite);

        index.build(testList);
    }
    public void tearDown() throws Exception{
        index = null;

    }
    @Test
    public void lookupTest() throws Exception {
        assertEquals(new ArrayList<Website>(), index.lookup(""));
        assertEquals(1, testList.size());
    }
    @Test
    public void lookupTest2() throws Exception{
        assertEquals("absolute", testWords.get(1));
        assertEquals(new ArrayList<Website>(), index.lookup("frgfgh"));
        assertEquals(testList, index.lookup("absolute"));
    }
}
