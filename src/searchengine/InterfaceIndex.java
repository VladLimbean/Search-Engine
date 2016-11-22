import java.util.List;

/**
 * Created by IRCT on 11/20/2016.
 */
public class InterfaceIndex {

    //simple inteface contains global create list & find Keyword method, for all indexes implementing interface
    public interface IndexInterface {

    void createList (List<Website> listWebsites);

    List<Website> findKeyword(String key);
    }
}
