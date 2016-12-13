package searchengine.Crawler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

import java.net.*;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Wikipedia website crawler used by the Group-C search engine.
 *
 * It makes use of the Wikipedia API (https://en.wikipedia.org/wiki/Special:ApiSandbox) in order to standardize
 * the crawl path.
 *
 * The program saves the crawled websites in the same format as the original prototype .txt file.
 *
 * Format of .txt:
 *
 * PAGE*: HTTP://url.com
 * Title
 * word_1
 * word_2
 * ...
 * word_n
 *
 */
public class Crawler {
    private final String preURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts%7Cinfo%7Clinks&titles=";
    private final String postURL = "&utf8=1&exlimit=max&exintro=1&explaintext=1&inprop=url&pllimit=max";
    private final int maxWebsites = 20000;
    private final int maxWebsitesInOneRequest = 20;
    private final String finalFileName = "Data/crawled-medium.txt";

    private Map<String, WikiJson> partiallyCrawledSites;
    private Set<String> sitesToCrawl;
    private Queue<String> titlesQueue;
    private int crawlCount = 0;
    private PrintWriter saveFile;

    public Crawler() throws FileNotFoundException
    {
        File newFile = new File(finalFileName);
        saveFile = new PrintWriter(newFile);

        titlesQueue = new ArrayDeque<>();
        titlesQueue.add("Communism");

        sitesToCrawl = new HashSet<>();
        sitesToCrawl.add("Communism");

        partiallyCrawledSites = new HashMap<>();
    }

    /**
     * Establishes a connection with the Wikipedia pages containing a given list of titles. The method retrieves
     * a response from the Wikipedia API.
     *
     * @param titles            A list of titles of wikipedia pages.
     * @param continueStatement A url post-fix received a previous request to the wikipedia API
     * @throws MalformedURLException        Exception thrown in case of unreadable URL.
     * @throws UnsupportedEncodingException Exception thrown in case of an URL encoding issue.
     * @throws InterruptedException         Exception thrown in case Thread.sleep is interrupted (during testing).
     */
    public void requestMaker(List<String> titles, String continueStatement) throws MalformedURLException, UnsupportedEncodingException, InterruptedException
    {
        // wait gave us an IllegalMonitorException
        Thread.sleep(500);

        StringBuilder allTitlesString = new StringBuilder();
        for (int i = 0; i < titles.size(); i++)
        {
            allTitlesString.append(titles.get(i));
            if (i < titles.size() - 1)
            {
                allTitlesString.append("|");
            }
        }
        System.out.println("Sending request for " + allTitlesString.toString());

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

            wikiReader(result, titles);
        }
        catch (UnknownHostException uhe)
        {
            uhe.printStackTrace();
            //Try again
            requestMaker(titles, continueStatement);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reads a json object, converts it to a java class (WikiJson) and stores the information in a hash map.
     *
     * @param result                            json object retrieved from the Wikipedia API via crawelerExe.
     * @throws MalformedURLException            Exception thrown in case of unreadable URL.
     * @throws UnsupportedEncodingException     Exception thrown in case of an URL encoding issue.
     * @throws InterruptedException             Exception thrown in case Thread.sleep is interrupted (during testing).
     */
    public void wikiReader(String result, List<String> sitesWhenContinuing) throws UnsupportedEncodingException, MalformedURLException, InterruptedException
    {
        Gson jsonLoader = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject firstParse = parser.parse(result).getAsJsonObject();

        if (!firstParse.has("query"))
        {
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

            if (partiallyCrawledSites.containsKey(wikiPage.title))
            {
                WikiJson savedSite = partiallyCrawledSites.get(wikiPage.title);
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

                partiallyCrawledSites.put(savedSite.title, savedSite);
            }
            else
            {
                partiallyCrawledSites.put(wikiPage.title, wikiPage);
            }
        }

        if (!continueStatement.isEmpty())
        {
            String finalContinueStatement = "&plcontinue=" + URLEncoder.encode(continueStatement, "UTF-8");
            requestMaker(sitesWhenContinuing, finalContinueStatement);
        }
    }

    /**
     * Prints the crawled websites into a .txt file in the format below:
     *
     * Example:
     *
     * PAGE*:http://siteurl.com
     * Title of page
     * word_1
     * word_2
     * ...
     * ...
     * word_n
     *
     * @throws FileNotFoundException   Exception thrown in case a file cannot be open due to faulty path.
     */
    public void wikiWriter() throws FileNotFoundException
    {
        for (WikiJson wikiPage : partiallyCrawledSites.values())
        {
            System.out.println("Printing page in txt file: " + wikiPage.title);

            if (wikiPage.extract == null || wikiPage.extract.isEmpty())
            {
                System.out.println("Article with title " + wikiPage.title + " has no extract");
                crawlCount--;
                continue;
            }

            saveFile.println("*PAGE:" + wikiPage.fullurl);
            saveFile.println(wikiPage.title);

            String[] extractWords = wikiPage.extract.split(" ");
            for (String currentWord : extractWords)
            {
                if (!currentWord.isEmpty() && !currentWord.equals("\n"))
                {
                    saveFile.println(currentWord);
                }
            }
        }
        
        saveFile.flush();
    }

    /**
     * Adds further links for the crawler, ensuring the same page is not crawled more than once.
     */
    private void addTitlesToQueue()
    {
        for (WikiJson site : partiallyCrawledSites.values())
        {
            for (WikiJson.JsonWikiLinks link : site.links)
            {
                if (link.title.startsWith("Category:") ||
                    link.title.startsWith("Portal:") ||
                    link.title.startsWith("Help:") ||
                    link.title.startsWith("Template talk:") ||
                    link.title.startsWith("Template:") ||
                    link.title.startsWith("File:") ||
                    link.title.startsWith("Wikipedia:") ||
                    link.title.contains("(disambiguation)") ||
                    link.title.startsWith("List of"))
                {
                    continue;
                }

                if (!sitesToCrawl.contains(link.title))
                {
                    sitesToCrawl.add(link.title);
                    titlesQueue.offer(link.title);
                }
            }
        }
    }

    /**
     * Limits the number of websites to be crawled and updates the command line with the current status of the class.
     *
     * @throws InterruptedException             Exception thrown in case Thread.sleep is interrupted (during testing).
     * @throws MalformedURLException            Exception thrown in case of unreadable URL.
     * @throws UnsupportedEncodingException     Exception thrown in case of an URL encoding issue.
     */
    public void queueCrawler() throws MalformedURLException, InterruptedException, UnsupportedEncodingException, FileNotFoundException {
        while (titlesQueue.size() > 0 && crawlCount < maxWebsites)
        {
            List<String> titlesToCrawlTogether = new ArrayList<>();

            while (titlesQueue.size() > 0 && titlesToCrawlTogether.size() < maxWebsitesInOneRequest && crawlCount < maxWebsites)
            {
                String currentTitle = titlesQueue.poll();
                System.out.println("Adding new title: " + currentTitle);
                titlesToCrawlTogether.add(currentTitle);
                crawlCount++;
            }
            partiallyCrawledSites.clear();
            requestMaker(titlesToCrawlTogether, "");

            //After all requests for the 20 titles finish, write them to a file
            wikiWriter();
            addTitlesToQueue();

            System.out.println(crawlCount + " communist websites crawled and printed to text file.");
        }

        // because it needs to close the file at the end of the crawl
        saveFile.close();
    }

    /**
     * Initiates the crawler.
     *
     * @param args                              the program does not take any command line arguments.
     * @throws InterruptedException             Exception thrown in case Thread.sleep is interrupted (during testing).
     * @throws UnsupportedEncodingException     Exception thrown in case of an URL encoding issue.
     * @throws FileNotFoundException            Exception thrown in case a file cannot be open due to faulty path.
     */
    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException, FileNotFoundException, MalformedURLException {
        Crawler jumboTurbo = new Crawler();
        // poll takes the first element of the queue hands it over then removes it.
        jumboTurbo.queueCrawler();
    }
}