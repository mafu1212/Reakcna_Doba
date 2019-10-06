package base;

public class Record {

    public String name;
    public long record;
    public int poradie;

    public Record(String name, long record) {
        this.name = name;
        this.record = record;
        poradie = 0;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return record;
    }

    public int getPoradie() {
        return poradie;
    }

}
