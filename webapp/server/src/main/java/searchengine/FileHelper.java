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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IRCT on 11/20/2016.
 * File helper build a list of websites from provided file in a main method!
 */
public class FileHelper {
    // return list of Websites from File passed from Main Method
    public static List<Website> providedFile(String file) {
        // Define basic webiste variables!
        String url = null;
        String title = null;
        List<String> content = null;
        // hold a finished list of websites in an array!
        List<Website> listOfWebsites = new ArrayList<Website>();
        // try initializing scanner on the file and catch any errors, in case file get suddenly unreadable
        try {
            Scanner sc = new Scanner(new File(file), "UTF-8");
            //go trough each line in file provided
            while (sc.hasNext()) {
                //each line is stored in line variable with removed white space
                String line = sc.next().trim();
                // if line starts with *Page and Webiste URL is empty
                if (line.startsWith("*PAGE:")) {
                    if (url != null) {
                        listOfWebsites.add(new Website(url, title, content));
                    }
                    title = null;
                    content = null;
                    //url equals to 6th character of a line
                    url = line.substring(6);
                } else if (url != null && title == null) {
                    title = line;
                } else if (url != null && title != null) {
                    if (content == null) {
                        content = new ArrayList<String>();
                    }
                    content.add(line);
                }
            }
            //Check condition and add new Website object into a list of all websites! Muhahaha!
            if (url != null && title != null && content != null) {
                listOfWebsites.add(new Website(url, title, content));
            }
            //catch the sneaky error bastard!
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //return the finished list of Website created from our while loop
        return listOfWebsites;
    }
}


