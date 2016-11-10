package searchengine;

/**
 * Created by Vlad on 10/11/2016.
 */
public class TermFrequency implements Score {


    public static double getScore(String keyWord, Website website, Index index) {
        int counter =0;
        for(String word: website.getWords()){
            if(word.equals(keyWord)){
                counter ++;
            }
        }
        return counter;
    }
}
