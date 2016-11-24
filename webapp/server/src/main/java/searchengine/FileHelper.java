package searchengine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Joso on 15.11.2016.
 */
public class FileHelper {

    public static List<Website> parseFile(String arg){
        String url = null;
        String title = null;
        List <String> wordList = null;

        List<Website> result = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(arg), "UTF-8");
            while(sc.hasNext()){
                String line = sc.next().trim();
                if (line.startsWith("*PAGE:")){
                    if(url!= null){
                        result.add(new Website(url, title,wordList));
                    }
                    title = null;
                    wordList = null;
                    url = line.substring(6);
                }
                else if(url!= null && title == null){
                    title = line;
                }
                else if(url!= null && title != null){
                    if(wordList == null) {
                        wordList = new ArrayList<String>();
                    }
                        wordList.add(line);
                    }
            }

                if (url != null && title != null && wordList != null){
                    result.add(new Website(url,title,wordList));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
