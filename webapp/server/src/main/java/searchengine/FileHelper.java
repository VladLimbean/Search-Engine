package searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class takes care of reading an input database file and
 * transforming it into {@code Website} objects.
 *
 * @author Martin Aumüller
 */
public class FileHelper {

    /**
     * This methods transforms a database file into a list of {@code Website}
     * objects. The list of {@code Website} objects has the same order as the entries
     * in the database file.
     *
     * @param arg the filename of the database
     * @return the list of websites that are contained in the database file.
     */
    public static List<Website> parseFile(String arg) {
        List<Website> result = new ArrayList<Website>();
        String url = null;
        String title = null;
        List<String> wordList = null;
        try {
            Scanner sc = new Scanner(new File(arg), "UTF-8");
            while (sc.hasNext()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("*PAGE:")) {
                    // new entry starts
                    // save the old one
                    if (url != null) {
                        result.add(new Website(url, title, wordList));
                    }
                    title = null;
                    wordList = null;
                    url = line.substring(6);
                } else if (url != null && title == null) {
                    title = line;
                } else if (url != null && title != null) {
                    if (wordList == null) {
                        wordList = new ArrayList<String>();
                    }
                    wordList.add(line);
                }
            }
            if (url != null) {
                result.add(new Website(url, title, wordList));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
