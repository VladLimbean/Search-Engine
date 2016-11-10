package searchengine;

/**
 * Created by Vlad on 10/11/2016.
 */
public class InverseDocumentFrequency implements Score {

    public static double getScore(String keyWord, Website website, Index index) {
        int numberOfWebsites = index.getSize();
        int numberOfMatches = index.lookup(keyWord).size();

        double result = Math.log10((double)numberOfWebsites / (double)numberOfMatches) / Math.log10(2);

        return result;
    }
}
