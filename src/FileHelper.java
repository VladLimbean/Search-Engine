import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by maau on 27/09/16.
 */
public class FileHelper {
    public static List<Website> parseFile(String arg) {
        List<Website> result = new ArrayList<Website>();
        String url = null;
        String title = null;
        List<String> wordList = null;
        try {
            Scanner sc = new Scanner(new File(arg));
            while (sc.hasNext()) {
                String line = sc.next().trim();
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
