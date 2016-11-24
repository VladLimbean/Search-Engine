import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Joso on 21.11.2016.
 */
public class FileHelperTest {
// Has to read the datafile and produce the expected output
    @Test
    public void parseFile() throws Exception {
        List<Website> sites = FileHelper.parseFile("test-file.txt");
        assertEquals(2,sites.size());
        assertEquals("title1",sites.get(0).getTitle());
        assertEquals("true",sites.get(0).containsWord("word1"));
        assertEquals("false",sites.get(0).containsWord("word3"));
        assertEquals("title2",sites.get(1).getTitle());
        assertEquals("true",sites.get(1).containsWord("word1"));
        assertEquals("true",sites.get(1).containsWord("word3"));

    }
}