package main;

import java.util.Comparator;

public class SortTheDatabase implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return (int) (o1.score - o2.score);
    }
}
