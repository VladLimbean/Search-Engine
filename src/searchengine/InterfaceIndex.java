import java.util.List;

/**
 * Created by IRCT on 11/21/2016.
 */
public interface InterfaceIndex {
    //Imterface method shared by all Indexes, this one builds the list of Websites
    void buildList(List<Website> listWebsite);
    //This one gets a string and search for it in the list of Websites
    List<Website> findKeyword(String query);
}
