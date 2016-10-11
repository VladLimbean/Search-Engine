import java.util.*;

/**
 * Created by Vlad on 10/10/2016.
 */
public class InvertedIndex implements Index {
    public Map<String, List<Page>> mainMap;

    public InvertedIndex(Map<String, List<Page>> map)
    {
        this.mainMap = map;
    }

    public void build(List<Page> listPages)
    {
        for (Page page : listPages) {
            for (String keyWord : page.getWords()) {
                List<Page> emptyList;

                if (mainMap.containsKey(keyWord)) {
                    emptyList = mainMap.get(keyWord);
                }
                else {
                    emptyList = new ArrayList<Page>();
                }
                if (!emptyList.contains(page)){
                emptyList.add(page);
                }

                mainMap.put(keyWord, emptyList);
            }
        }
    }

    public List<Page> lookup(String key)
    {
        if (mainMap.containsKey(key)) {
            return mainMap.get(key);
        }
        else {
            return new ArrayList<>();
        }
    }
}
