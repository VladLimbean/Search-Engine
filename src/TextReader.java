import sun.plugin.net.protocol.jar.CachedJarURLConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Vlad on 05/10/2016.
 * This class reads an input file and creats a hashmap.
 *
 * @author Vlad Limbean
 */
public class TextReader {
    String url;
    String title;
    HashMap<String, List<Page>> map;
    // function reads the input txt file and returns hashmap with key words
    public HashMap<String, List<Page>> readTxt(String fileName) {
        // attempts to find file, retrun error message if no file found
        try {
            Scanner sc = new Scanner(new File(fileName));

            map = new HashMap<String, List<Page>>();
            //goes through every line of text in the input file
            while (sc.hasNext()) {
                // checks next line
                String currentLine = sc.next();
                // checks if if current line is webpage
                if (currentLine.startsWith("*PAGE:")) {
                    this.title = null;
                    // url value will not be printed with "*PAGE" prefix
                    this.url = currentLine.substring(6);
                }
                // sets the title
                else if (this.url != null && this.title == null) {
                    this.title = currentLine;
                }
                // builds the hashmap around key words
                else if (this.url != null && this.title != null) {
                    // checks if keywords is present in hashmap
                    if (map.containsKey(currentLine)) {
                        // receive pages associated with key word
                        List<Page> currentList = map.get(currentLine);
                        // checks if the site is added already
                        boolean isWebsiteAlreadyAdded = false;
                        for ( Page a : currentList) {
                            // checks if title and url have already been added for key word
                            if (a.getTitle() == this.title && a.getUrl() == this.url){
                             isWebsiteAlreadyAdded = true;
                                break;
                            }
                        }
                        // if not already added, it will add it to the hash map
                        if(!isWebsiteAlreadyAdded) {
                            Page currentPage = new Page(this.url, this.title, new ArrayList<>());
                            currentList.add(currentPage);
                            map.put(currentLine, currentList);
                        }
                    }
                    // if word is not featured in hashmap already it will now add it
                    else {
                        List<Page> newList = new ArrayList<Page>();
                        Page newPage = new Page(this.url, this.title, new ArrayList<>());
                        newList.add(newPage);
                        map.put(currentLine, newList);
                    }


                }
            }
            return map;
        } catch(FileNotFoundException ex){
            System.out.println("File not found!");
            return null;

            }
    }
}

