package searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {
    /**
     * The method reads a text file line by line and constructs websites.
     * Each website is added to a list
     *
     * @param arg
     * @return list of websites
     */
    public static List<Website> fileReader(String arg){

        List<Website> listOfWebsites = new ArrayList<>();
        String url = null;
        String title = null;
        List<String> words = null;

        try {
            Scanner sc = new Scanner(new File(arg));

            while (sc.hasNext()) {
                String currentLine = sc.next().trim();

                if (currentLine.startsWith("*PAGE:")) {
                    if (url != null && title != null && words != null) {
                        listOfWebsites.add(new Website(url, title, words));
                    }
                    title = null;
                    words = null;
                    url = currentLine.substring(6);
                }
                else if (url != null && title == null) {
                    title = currentLine;
                }
                else if (url != null) {
                    if (words == null) {
                        words = new ArrayList<>();
                    }
                    words.add(currentLine);
                }
            }
            if (url != null && title != null && words != null) {
                listOfWebsites.add(new Website(url, title, words));
            }
        }
        catch(FileNotFoundException e){
                e.printStackTrace();
        }
        return listOfWebsites;
    }
}
