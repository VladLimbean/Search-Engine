import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by maau on 27/09/16.
 */
public class FileHelper {
    public static List<Page> parseFile(String arg) {
        List<Page> result = new ArrayList<Page>();
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
                    // if checks if url, title and wordList are set
                    if ((url != null)&&(title!=null)&&(wordList!=null)) {
                        result.add(new Page(url, title, wordList));
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
            // if checks if url, title and wordList are set
            if ((url != null)&&(title!=null)&&(wordList!=null)) {
                result.add(new Page(url, title, wordList));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}