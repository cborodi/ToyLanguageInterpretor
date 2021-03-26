package model.ADTs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class MyHeap<T1, T2> implements MyIHeap<T1, T2> {
    HashMap<T1, T2> map;
    int nextFreeLocation;

    public MyHeap() {
        this.map = new HashMap<>();
        this.nextFreeLocation = 0;
    }

    @Override
    public void add(T1 k, T2 v) {
        this.map.put(k, v);
    }

    @Override
    public void delete(T1 k) {
        this.map.remove(k);
    }

    public Set<Map.Entry<T1, T2>> entrySet() {
        return map.entrySet();
    }

    @Override
    public void update(T1 k, T2 v) {
        this.map.replace(k, v);
    }

    @Override
    public T2 lookup(T1 k) {
        return map.get(k);
    }

    @Override
    public int getNextFreeLocation() {
        return ++this.nextFreeLocation;
    }

    @Override
    public void setContent(Map<T1, T2> newContent) {
        this.map = (HashMap<T1, T2>) newContent;
    }

    @Override
    public Map<T1, T2> getContent() {
        return this.map;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
