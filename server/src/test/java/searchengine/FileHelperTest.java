package searchengine;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by maau on 05/10/16.
 */
public class FileHelperTest {
    @Test
    public void parseFileSimple() throws Exception {
        List<Website> sites = FileHelper.parseFile("test-resources/test-file.txt");
        assertEquals(2,sites.size());
        assertEquals("title1",sites.get(0).getTitle());
        assertEquals(true, sites.get(0).containsWord("word1"));
        assertEquals(false, sites.get(0).containsWord("word3"));
        assertEquals("title2", sites.get(1).getTitle());
        assertEquals(true, sites.get(1).containsWord("word1"));
        assertEquals(true, sites.get(1).containsWord("word3"));
    }

    @Test
    public void parseFileWithErrors() throws Exception {
        List<Website> sites = FileHelper.parseFile("test-resources/test-file-with-errors.txt");
        // Please make sure to uncomment these lines after you've implemented assignment 2.
//        assertEquals(2,sites.size());
//        assertEquals("title1",sites.get(0).getTitle());
//        assertEquals(true, sites.get(0).containsWord("word1"));
//        assertEquals(false, sites.get(0).containsWord("word3"));
//        assertEquals("title2", sites.get(1).getTitle());
//        assertEquals(true, sites.get(1).containsWord("word1"));
//        assertEquals(true, sites.get(1).containsWord("word3"));
    }

}