import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vlad on 10/10/2016.
 */
public class InvertedIndex implements Index {
    public AbstractMap<String, List<Page>> mainMap;

    public void build(List<Page> listPages) {
        this.mainMap = new HashMap<String, List<Page>>();

        for (Page a : listPages) {
            for (String b : a.getWords()) {
                if (mainMap.containsKey(b)) {
                    List<Page> currentList = mainMap.get(b);
                    currentList.add(a);
                    mainMap.put(b, currentList);
                } else {
                    List<Page> startingList = new ArrayList<>();
                    startingList.add(a);
                    mainMap.put(b, startingList);
                }
            }
        }
    }


    public List<Page> lookup(String key) {
        if (mainMap.containsKey(key)) {
            return mainMap.get(key);
        }
        else {
            return new ArrayList<>();
        }
    }
}
