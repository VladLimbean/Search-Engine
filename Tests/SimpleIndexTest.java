import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Vlad on 11/10/2016.
 */
public class SimpleIndexTest {
    private List<Page> testListWebsites;
    private SimpleIndex testIndex;

    @Before
    public void setUp() throws Exception {
        this.testIndex = new SimpleIndex();
        this.testListWebsites = new ArrayList<Page>();
        testListWebsites.add(new Page("kim.com", "Kim", new ArrayList<String>()));
        testListWebsites.add(new Page("4chan.org", "m00t", new ArrayList<String>()));
        testIndex.build(testListWebsites);
    }

    @After
    public void tearDown() throws Exception {
        this.testListWebsites = null;
    }

    @Test
    public void lookup() throws Exception {
        assertEquals(new ArrayList<Page>(), testIndex.lookup(""));
    }

}