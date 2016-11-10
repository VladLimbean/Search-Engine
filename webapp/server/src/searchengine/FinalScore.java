package searchengine;

/**
 * Created by Vlad on 10/11/2016.
 */
public class FinalScore implements Score{

    public static double getScore(String keyWord, Website website, Index index) {
        double score = TermFrequency.getScore(keyWord, website, index);
        double score2 = InverseDocumentFrequency.getScore(keyWord, website, index);
        double totalScore = score * score2;

        return totalScore;
    }
}
