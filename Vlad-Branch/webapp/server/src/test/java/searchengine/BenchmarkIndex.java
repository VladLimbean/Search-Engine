package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vlad on 28/11/2016.
 */
public class BenchmarkIndex {

    private Index testIndex;

    public void setUp(){
        String file = "Data/enwiki-medium.txt";
        List<Website> list = new ArrayList<Website>();

        list = FileHelper.fileReader(file);
    }
}
