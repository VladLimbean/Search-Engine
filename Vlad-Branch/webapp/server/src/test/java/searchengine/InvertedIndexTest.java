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
public class InvertedIndexTest {
    private List<Website> testList;
    private InvertedIndex index;

    @Before
    public void setUp(){
        testList = new ArrayList<Website>();
        testList.add(new Website("4chan.org", "m00t", new ArrayList<String>()));
        index.build(testList);
    }
    @After
    public void tearDown(){
        testList = null;
    }
    @Test
    public void lookupTest(){
        assertEquals(new ArrayList<Website>(), index.lookup(""));
    }
}
