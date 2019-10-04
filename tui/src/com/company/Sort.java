package com.company;

import java.util.Comparator;

public class Sort implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return (int) (o1.record - o2.record);
    }
}
