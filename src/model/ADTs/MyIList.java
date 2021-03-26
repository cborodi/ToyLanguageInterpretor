package model.ADTs;

import java.util.stream.Stream;

public interface MyIList<T> {
    void add(T v);
    void delete(T v);
    Stream<T> stream();
    int size();
    T get(int i);
}
