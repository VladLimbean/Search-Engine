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
 * Initial crawler build -  Group-C
 *
 * it's exception-tastic
 */
public class Crawler
{
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

    public void crawlerExe(List<String> titles, String continueStatement) throws MalformedURLException, UnsupportedEncodingException, InterruptedException
    {
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
        catch (UnknownHostException uhe)
        {
            uhe.printStackTrace();
            //Try again
            crawlerExe(titles, continueStatement);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void wikiReader(String result) throws FileNotFoundException, MalformedURLException, UnsupportedEncodingException, InterruptedException {
        Gson jsonLoader = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject firstParse = parser.parse(result).getAsJsonObject();

        if (!firstParse.has("query"))
        {
            wikiWriter();
            addTitlesToQueue();
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

        if (continueStatement.isEmpty())
        {
            wikiWriter();
            addTitlesToQueue();
        }
        else
        {
            String finalContinueStatement = "&plcontinue=" + URLEncoder.encode(continueStatement, "UTF-8");
            List<String> sites = new ArrayList<>(partiallyCrawledSites.keySet());
            crawlerExe(sites, finalContinueStatement);
        }
    }

    public void wikiWriter() throws FileNotFoundException
    {
        for (WikiJson wikiPage : partiallyCrawledSites.values())
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
                    link.title.contains("(disambiguation)"))
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

    public void queueCrawler() throws MalformedURLException, InterruptedException, UnsupportedEncodingException
    {
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
            crawlerExe(titlesToCrawlTogether, "");

            System.out.println(crawlCount + " communist websites crawled.");
        }

        // because it needs to close the file at the end of the crawl
        saveFile.close();
    }

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
