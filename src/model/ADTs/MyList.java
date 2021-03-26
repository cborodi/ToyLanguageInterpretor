package model.ADTs;

import java.util.ArrayList;
import java.util.stream.Stream;

public class MyList<T> implements MyIList<T> {
    ArrayList<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public void delete(T v) {
        list.remove(v);
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    @Override
    public Stream<T> stream() {
        return list.stream();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int i) {
        return list.get(i);
    }
}
