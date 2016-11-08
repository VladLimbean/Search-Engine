package searchengine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Static class which parses a .txt file and turns it
 * into a list of Website objects
 */
public class FileHelper
{
    public static List<Website> parseFile(String arg)
    {
        //Initialize all needed variables
        List<Website> result = new ArrayList<Website>();
        String url = null;
        String title = null;
        List<String> wordList = null;

        try
        {
            //Create a scanner which reads every line of the txt file
            Scanner sc = new Scanner(new File(arg));

            //Read every single line
            while (sc.hasNext())
            {
                //Trim the current line so we don't get trailing spaces
                String line = sc.next().trim();

                //Check if the line represents a URL
                if (line.startsWith("*PAGE:"))
                {
                    //If it does, that means we should start a new website
                    if ((url != null) && (title!=null) && (wordList!=null))
                    {
                        //Save the previous website
                        //Only if title, url, and list of words are set
                        result.add(new Website(url, title, wordList));
                    }

                    //Make the title and the list of websites empty (because we are starting a new Website)
                    title = null;
                    wordList = null;
                    //Set the URL to be whatever the "*PAGE*:" line is, without the PAGE prefix
                    url = line.substring(6);
                }
                else if (url != null && title == null)
                {
                    //If the line is not an URL, and the title is still not set,
                    //that means that the current line is the Title
                    title = line;
                }
                else if (url != null && title != null)
                {
                    //If both URL and Title is set, the current line is a keyword

                    //Create a new list if it's not already created
                    if (wordList == null)
                    {
                        wordList = new ArrayList<String>();
                    }

                    //Add the current line to the list of keywords
                    wordList.add(line);
                }
            }

            //Adds the last website after the while loop finishes
            //Needed, because while loop finishes before actually adding the last line
            if ((url != null)&&(title!=null)&&(wordList!=null))
            {
                result.add(new Website(url, title, wordList));
            }
        }
        catch (FileNotFoundException e)
        {
            //Throw an exception if something wrong happens
            e.printStackTrace();
        }

        //Return the final list of websites after parsing the whole .txt
        return result;
    }
}