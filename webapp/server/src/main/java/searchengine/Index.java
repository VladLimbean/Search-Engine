package searchengine;
import java.util.List;

/**
 * Created by Vlad on 10/10/2016.
 */
public interface Index
{
    void build(List<Website> listWebsites);

    List<Website> lookup(String key);
}