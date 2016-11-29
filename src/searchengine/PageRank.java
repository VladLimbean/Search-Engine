/**
 * Created by IRCT on 11/27/2016.
 */
public class PageRank implements InterfaceRanking {
    //store the index somewhere
    private InterfaceIndex index;
    //store variables and make it double
    private double finalScore;
    private long counter;

    public double getScore(String keyword, Website website){
        finalScore= 0;
        return finalScore;
    }

    //calculate term frequency, if you can
    private long termFrequency(String keyword, Website website) {
            counter = 0;
            //lop tha wordz in de contnt..
            for(String word : website.getContent()){
                if(word.equals(keyword))
                {
                    //this should do it
                    counter++;
                }
            }

            //Return the final term frequency, Like an Eistein!
        return counter;
    }
}
