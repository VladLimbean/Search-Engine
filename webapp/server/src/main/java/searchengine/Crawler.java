package searchengine;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Initial crawler build -  Group-C
 *
 * it's exception-tastic
 */

public class Crawler {
    private Queue<String> titles;
    private final String preURL;
    private final String postURL;
    private int crawlCount = 0;
    private final int maxWebsites = 50;
    private PrintWriter saveFile;

    public Crawler() throws FileNotFoundException {
        File newFile = new File("Data/test.txt");
        saveFile = new PrintWriter(newFile);
        titles = new ArrayDeque<>();
        titles.add("Communism");
        preURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts%7Cinfo%7Clinks&titles=";
        postURL = "&utf8=1&exintro=1&explaintext=1&inprop=url&pllimit=max";
    }

    public void crawlerExe(String title) throws MalformedURLException, UnsupportedEncodingException {
        title = URLEncoder.encode(title, "UTF-8");
        String currentURL = preURL.concat(title).concat(postURL);

        URL currentLink = new URL(currentURL);

        try {
            URLConnection beyond = currentLink.openConnection();
            InputStream websiteReader = beyond.getInputStream();

            Scanner sc = new Scanner(new BufferedInputStream(websiteReader), "UTF-8");
            // the scanner expects the website to be US

            String result = sc.useDelimiter(Pattern.compile("\\A")).next();

            wikiReader(result);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void wikiWriter(WikiJson wikiPage) throws FileNotFoundException {

        if (wikiPage.extract.isEmpty()){
            return;
        }

        saveFile.println("*PAGE:" + wikiPage.fullurl);
        saveFile.println(wikiPage.title);

        //regular expression which tells split to only takes alphanumeric characters
        String[] extractWords = wikiPage.extract.split("[^a-zA-Z0-9']");
        for(String currentWord : extractWords){
            if(!currentWord.isEmpty())
                saveFile.println(currentWord);
        }

    }

    public void wikiReader(String result) throws FileNotFoundException {
        Gson jsonLoader = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(result).getAsJsonObject().getAsJsonObject("query").getAsJsonObject("pages");

        for(Map.Entry<String, JsonElement> currentLine : jsonObject.entrySet()){
            WikiJson wikiPage = jsonLoader.fromJson(currentLine.getValue(), WikiJson.class);
            for( WikiJson.JsonWikiLinks wikiTitles  : wikiPage.links){
                titles.offer(wikiTitles.title);
            }
            wikiWriter(wikiPage);
        }
    }

    public void queueCrawler() throws MalformedURLException, InterruptedException, UnsupportedEncodingException {
        while (titles.size()>0 && crawlCount < maxWebsites){
            String currentTitle = titles.poll();
            crawlerExe(currentTitle);
            // wait gave us an IllegalMonitorException
            Thread.sleep(2000);

            crawlCount++;
            System.out.println(crawlCount + " communist websites crawled. Title: " + currentTitle);
        }
        // because it needs to close the file at the end of the crawl
        saveFile.close();
    }

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException, FileNotFoundException {
        Crawler jumboTurbo = new Crawler();
        try {
            // poll takes the first element of the queue hands it over then removes it.
            jumboTurbo.queueCrawler();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
