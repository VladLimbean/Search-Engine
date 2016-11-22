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
        List<Website> arrayListOfWebsites = new ArrayList<Website>();

        //Try following and catch any errors
        try {
            //initiate new scanner that goes trough provided file
            Scanner sc = new Scanner(new File(file), "UTF-8");
            // while loop to trim the url, title and content from each other
            // loop finishes until there is no more lines to go trough
            while (sc.hasNext()) {
                //trim first/next line
                String line = sc.nextLine().trim();
                //if line *PAGE:
                if (line.startsWith("*PAGE:")) {
                    //then following fields remain same
                    title = null;
                    content = null;
                    //the url is represented on the same line, substring gets rid of *PAGE: and get us full URL
                    url = line.substring(6);

                // if urls is not empty and title is empty then add next line as a title of a website!
                } else if (url != null && title == null) {
                    title = line;
                    //only if content is empty then create a new araay list of strings and add next line to
                } else if (url != null && title != null && content == null) {
                        content = new ArrayList<String>();
                    }
                    //add following lines into the content array!
                    content.add(line);
                }

            if (url != null && title != null && content != null) {
                // create a new object of websites and add it to an arrayListOfWebsites
                arrayListOfWebsites.add(new Website(url, title, content));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //return the completed list of All websites
        return arrayListOfWebsites;
    }

}
