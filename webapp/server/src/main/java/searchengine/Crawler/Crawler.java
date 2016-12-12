package searchengine.Crawler;

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
 * Wikipedia website crawler used by the Group-C search engine.
 *
 * It makes use of the Wikipedia API sandbox (https://en.wikipedia.org/wiki/Special:ApiSandbox) in order to standardize
 * the crawl path.
 */
public class Crawler
{
    private final String preURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts%7Cinfo%7Clinks&titles=";
    private final String postURL = "&utf8=1&exlimit=max&exintro=1&explaintext=1&inprop=url&pllimit=max";
    private final int maxWebsites = 20000;
    private final int maxWebsitesInOneRequest = 20;
    private final String finalFileName = "Data/final.txt";

    private Map<String, WikiJson> crawledSites;
    private Set<String> writtenSites;
    private Queue<String> titles;
    private int crawlCount = 0;
    private PrintWriter saveFile;


    public Crawler() throws FileNotFoundException
    {
        File newFile = new File(finalFileName);
        saveFile = new PrintWriter(newFile);

        titles = new ArrayDeque<>();
        titles.add("Communism");

        writtenSites = new HashSet<>();
        writtenSites.add("Communism");

        crawledSites = new HashMap<>();
    }

    /**
     * Establishes a connection with the Wikipedia pages containing a given list of titles.
     *
     * @param titles                            A list of titles of wikipedia pages.
     * @param continueStatement                 ???
     * @throws MalformedURLException            Exception thrown in case of unreadable URL.
     * @throws UnsupportedEncodingException     ???
     * @throws InterruptedException
     */
    public void crawlerExe(List<String> titles, String continueStatement) throws MalformedURLException, UnsupportedEncodingException, InterruptedException {
        // wait gave us an IllegalMonitorException
        Thread.sleep(2000);

        StringBuilder allTitlesString = new StringBuilder();
        for (int i = 0; i < titles.size(); i++)
        {
            allTitlesString.append(titles.get(i));
            if (i < titles.size() - 1)
            {
                allTitlesString.append("|");
            }
        }

        //Encode the title (because URL's shouldn't have whitespace and other symbols
        String titlesURL = URLEncoder.encode(allTitlesString.toString(), "UTF-8");
        //Create the final URL
        String currentURL = preURL.concat(titlesURL).concat(postURL).concat(continueStatement);

        URL currentLink = new URL(currentURL);

        try
        {
            URLConnection beyond = currentLink.openConnection();
            InputStream websiteReader = beyond.getInputStream();

            Scanner sc = new Scanner(new BufferedInputStream(websiteReader), "UTF-8");
            // the scanner expects the website to be US

            String result = sc.useDelimiter(Pattern.compile("\\A")).next();

            wikiReader(result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reads a json object, converts it to java readable code and stores the information in a hash map.
     *
     * @param result                        json object received from crawlerEXE.
     * @throws FileNotFoundException        ???
     * @throws MalformedURLException        ???
     * @throws UnsupportedEncodingException ???
     * @throws InterruptedException         ???
     */
    public void wikiReader(String result) throws FileNotFoundException, MalformedURLException, UnsupportedEncodingException, InterruptedException {
        Gson jsonLoader = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject firstParse = parser.parse(result).getAsJsonObject();

        if (!firstParse.has("query"))
        {
            wikiWriter();
            addNewLinksToCrawl();
            return;
        }

        JsonObject jsonObject = firstParse.getAsJsonObject("query").getAsJsonObject("pages");

        //See if there needs to be a "continue" statement in the response
        String continueStatement = "";
        if (firstParse.has("continue"))
        {
            JsonObject continueObject = firstParse.getAsJsonObject("continue");
            if (continueObject.has("plcontinue"))
            {
                continueStatement = firstParse.getAsJsonObject("continue").getAsJsonPrimitive("plcontinue").getAsString();
            }
        }

        for(Map.Entry<String, JsonElement> currentLine : jsonObject.entrySet())
        {
            WikiJson wikiPage = jsonLoader.fromJson(currentLine.getValue(), WikiJson.class);
            if (crawledSites.containsKey(wikiPage.title))
            {
                WikiJson savedSite = crawledSites.get(wikiPage.title);
                if (savedSite.extract == null && wikiPage.extract != null)
                {
                    savedSite.extract = wikiPage.extract;
                }

                if (savedSite.links == null)
                {
                    savedSite.links = new ArrayList<>();
                }

                if (wikiPage.links != null)
                {
                    savedSite.links.addAll(wikiPage.links);
                }

                crawledSites.put(savedSite.title, savedSite);
            }
            else
            {
                crawledSites.put(wikiPage.title, wikiPage);
            }
        }

        if (continueStatement.isEmpty())
        {
            wikiWriter();
            addNewLinksToCrawl();
        }
        else
        {
            String finalContinueStatement = "&plcontinue=" + URLEncoder.encode(continueStatement, "UTF-8");
            List<String> sites = new ArrayList<>(crawledSites.keySet());
            crawlerExe(sites, finalContinueStatement);
        }
    }

    /**
     * Prints the crawled websites into a .txt file in the format below:
     *
     * PAGE*:http://siteurl.com
     * Title of page
     * word1
     * word2
     * ...
     * ...
     * wordn
     *
     * @throws FileNotFoundException
     */
    public void wikiWriter() throws FileNotFoundException
    {
        for (WikiJson wikiPage : crawledSites.values())
        {
            if (wikiPage.extract == null || wikiPage.extract.isEmpty())
            {
                return;
            }

            saveFile.println("*PAGE:" + wikiPage.fullurl);
            saveFile.println(wikiPage.title);

            //regular expression which tells split to only takes alphanumeric characters
            String[] extractWords = wikiPage.extract.split(" ");//.split("[^a-zA-Z0-9']");
            for (String currentWord : extractWords)
            {
                if (!currentWord.isEmpty())
                {
                    saveFile.println(currentWord);
                }
            }
        }
    }

    /**
     * Adds further links for the crawler ensuring the same page is not crawled more than once.
     */
    private void addNewLinksToCrawl()
    {
        for (WikiJson site : crawledSites.values())
        {
            for (WikiJson.JsonWikiLinks link : site.links)
            {
                if (link.title.startsWith("Category:") ||
                    link.title.startsWith("Portal:") ||
                    link.title.startsWith("Help:") ||
                    link.title.startsWith("Template talk:") ||
                    link.title.startsWith("File:") ||
                    link.title.startsWith("Wikipedia:"))
                {
                    continue;
                }

                if (!writtenSites.contains(link.title))
                {
                    titles.offer(link.title);
                }
            }
        }
    }

    /**
     * Limits the number of websites to be crawled and updates the command line with the current status of the class.
     *
     * @throws MalformedURLException            ???
     * @throws InterruptedException             ???
     * @throws UnsupportedEncodingException     ???
     */
    public void queueCrawler() throws MalformedURLException, InterruptedException, UnsupportedEncodingException
    {
        while (titles.size() > 0 && crawlCount < maxWebsites)
        {
            List<String> titlesToCrawlTogether = new ArrayList<>();

            while (titles.size() > 0 && titlesToCrawlTogether.size() < maxWebsitesInOneRequest && crawlCount < maxWebsites)
            {
                String currentTitle = titles.poll();
                System.out.println("Adding new title: " + currentTitle);
                titlesToCrawlTogether.add(currentTitle);
                crawlCount++;
            }
            crawledSites.clear();
            crawlerExe(titlesToCrawlTogether, "");

            System.out.println(crawlCount + " communist websites crawled.");
        }

        // because it needs to close the file at the end of the crawl
        saveFile.close();
    }

    /**
     * Initiates the crawler.
     *
     * @param args                          the program does not take any command line arguments.
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException, FileNotFoundException {
        Crawler jumboTurbo = new Crawler();
        try
        {
            // poll takes the first element of the queue hands it over then removes it.
            jumboTurbo.queueCrawler();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }
}
