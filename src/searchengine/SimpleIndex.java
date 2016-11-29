import java.util.ArrayList;
import java.util.List;

/**
 * Created by IRCT on 11/21/2016.
 */
public class SimpleIndex implements InterfaceIndex {
    private List<Website> listOfWebsites;

    @Override
    public void buildList (List<Website> listWebsite) {
        this.listOfWebsites = listWebsite;
    }

    @Override
    public List<Website> findKeyword(String query) {

        List<Website> resultsFoundArray = new ArrayList<>();

        for(Website webpage : this.listOfWebsites){
            if(webpage.hasKeyword(query)){
                resultsFoundArray.add(webpage);
            }
        }
        return resultsFoundArray;
    }
}
