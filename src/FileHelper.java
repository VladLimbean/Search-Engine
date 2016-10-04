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
                                    //new entry starts
                                    //save the old one
                                    //do not add the website if url, title and wordList are not set
                                    if ((url != null) && (title!=null) && (wordList!=null)) {
                                        result.add(new Website(url, title, wordList));
                                    }

                                    //Reset the title and word list
                    title = null;
                    wordList = null;
                    //Set the url to be equal to this line - the *PAGE: prefix
                    url = line.substring(6);
                }
                else if (url != null && title == null) {
                    //This code enters when the line following *PAGE: is read
                    //This means this is the title
                    title = line;
                }
                else if (url != null && title != null) {
                    //This code enters when both url and title are set
                    //This means that this is every line that is not the 1st or 2nd one

                    //Create the list if it is null, so no errors happen
                    if (wordList == null) {
                        wordList = new ArrayList<String>();
                    }
                    //Add the word to the list of words
                    wordList.add(line);
                }
            }
            //Loop exits
            //Add the last website, but only if url, title and word list are set
            if ((url != null) && (title!=null) && (wordList!=null)) {
                result.add(new Website(url, title, wordList));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}
