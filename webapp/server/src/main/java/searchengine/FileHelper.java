package searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The class reads a .txt file and creates a list of websites based on its content.
 */
public class FileHelper
{
    /**
     * Reads the .txt file and returns a list of websites.
     *
     * @param arg path of the .txt file.
     * @return A list of websites based on the contents of the .txt file.
     */
    public static List<Website> parseFile(String arg)
    {
        //Initialize the list that will hold all websites
        List<Website> finalList = new ArrayList<>();

        //Initialize the variables that hold the website's information
        String url = null;
        String title = null;
        List<String> wordList = new ArrayList<>();

        try
        {
            //Create the scanner object which will read the .txt
            Scanner sc = new Scanner(new File(arg));

            //Read every single line
            while (sc.hasNextLine())
            {
                //Read a line in the file
                String currentLine = sc.nextLine().trim();

                //Check what type of line are we looking at
                if (isLineURL(currentLine))
                {
                    //This line is a URL

                    //This means that we are starting a new website from here on out
                    //Try to save the previous website and add it to the list first
                    if (canAddWebsite(url, title, wordList))
                    {
                        finalList.add( new Website(url, title, wordList));
                    }

                    //Since we are starting a new website, reset all values
                    wordList = new ArrayList<>();
                    title = null;
                    url = currentLine.substring(6);
                }
                else if (isLineTitle(title))
                {
                    //This line is a title

                    //Set the title variable
                    title = currentLine;
                }
                else
                {
                    //This line is a keyword, because both URL and title are already set

                    //Add the current line as a keyword (make sure it's all lower case)
                    wordList.add(currentLine.toLowerCase());
                }
            }

            //The scanner loop finished
            //However, we need to add the last website in the .txt file
            if (canAddWebsite(url, title, wordList))
            {
                finalList.add(new Website(url, title, wordList));
            }
        }
        catch (FileNotFoundException e)
        {
            //Throw an exception if something wrong happens
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            //Throw an exception if something wrong happens when creating website objects
            e.printStackTrace();
        }

        //Return the list of websites after the whole .txt is read
        return finalList;
    }

    /**
     * Checks if the current line in the .txt represents the website URL.
     *
     * @param line The current line.
     * @return True if the current line represents a website URL.
     */
    private static boolean isLineURL(String line)
    {
        return line.startsWith("*PAGE:");
    }

    /**
     * Checks if the current line in the .txt file represents the title of the website.
     *
     * @param title line in the .txt file to be checked.
     * @return True if the current line represents the website title.
     */
    private static boolean isLineTitle(String title)
    {
        return (title == null);
    }

    /**
     * Checks if a website can be created based on the values stored in URL, Title, and associated list of words.
     *
     * @param url The current value of the URL variable.
     * @param title The current value of the title variable.
     * @param keywords The current value of the keywords list variable.
     * @return True if a new website can be added.
     */
    private static boolean canAddWebsite(String url, String title, List<String> keywords)
    {
        return (url != null && title != null && keywords.size() > 0);
    }
}