package searchengine;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Initial crawler build -  Group-C
 */

public class Crawler {
    private List<String> titles;
    private final String preURL;
    private final String postURL;

    public Crawler(){
        titles = new ArrayList<>();
        titles.add("Communism");
        preURL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts%7Cinfo%7Clinks&titles=";
        postURL = "&utf8=1&exintro=1&explaintext=1&inprop=url&pllimit=max";
    }

    public void crawlerExe(String title) throws MalformedURLException {
        String currentURL = preURL.concat(title).concat(postURL);

        URL currentLink = new URL(currentURL);

        try {
            URLConnection beyond = currentLink.openConnection();
            InputStream websiteReader = beyond.getInputStream();

            Scanner sc = new Scanner(new BufferedInputStream(websiteReader), "UTF-8");
            // the scanner expects the website to be US

            String result = sc.useDelimiter(Pattern.compile("\\A")).next();
            Gson googleShit = new Gson();
            WikiJson currentData = googleShit.fromJson(result, WikiJson.class);

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Crawler jumboTurbo = new Crawler();
        try {
            jumboTurbo.crawlerExe(jumboTurbo.titles.get(0));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
