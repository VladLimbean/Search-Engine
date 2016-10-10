import java.util.AbstractMap;
import java.util.List;

/**
 * Created by Vlad on 10/10/2016.
 */
public interface Index {

    void build(List<Page> listPages);

    List<Page> lookup(String key);

}
