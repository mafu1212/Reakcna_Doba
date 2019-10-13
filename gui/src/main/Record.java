package main;

public class Record {

    int order;
    String name;
    long score;

    public Record(String name, long score) {
        order = 0;
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }

    public int getOrder() {
        return order;
    }



}
