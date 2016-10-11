import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InvertedIndexTest {
    private List<Page> testListWebsites;
    private InvertedIndex testIndex;

    @Before
    public void setUp() throws Exception {
        this.testIndex = new InvertedIndex(new HashMap<String, List<Page>>());
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