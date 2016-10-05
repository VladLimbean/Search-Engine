import sun.plugin.net.protocol.jar.CachedJarURLConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Vlad on 05/10/2016.
 */
public class TextReader {
    String url;
    String title;
    HashMap<String, List<Page>> map;

    public HashMap<String, List<Page>> readTxt(String fileName) {

        Scanner sc = new Scanner(fileName);
            map = new HashMap<String,List<Page>>();
            while (sc.hasNext()) {

                String currentLine = sc.next();
                if (currentLine.startsWith("*PAGE:")){
                    this.title = null;

                    this.url = currentLine;
                }
                else if (this.url!=null && this.title==null){
                    this.title = currentLine;
                }
                else if (this.url!=null && this.title!=null){
                    if (map.containsKey(currentLine)){
                       List<Page> currentList =  map.get(currentLine);
                       Page currentPage= new Page(this.url, this.title);
                        currentList.add(currentPage);
                        map.put(currentLine, currentList);
                    }
                    else {
                        List<Page> newList = new ArrayList<Page>();
                        Page newPage = new Page(this.url, this.title);
                        newList.add(newPage);
                        map.put(currentLine,newList);
                    }


                }
            }
            return map;
    }
}

