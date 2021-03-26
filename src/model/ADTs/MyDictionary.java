package model.ADTs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {
    HashMap<T1, T2> map;

    public MyDictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(T1 k, T2 v) {
        this.map.put(k, v);
    }

    @Override
    public void delete(T1 k) {
        this.map.remove(k);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
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
    public Map<T1, T2> getContent() {
        return this.map;
    }

    public MyIDictionary<T1, T2> deepCopy() {
        MyIDictionary<T1, T2> di = new MyDictionary<>();
        for(T1 key: map.keySet()) {
            di.add(key, map.get(key));
        }
        return di;
    }


    @Override
    public String toString() {
        return map.toString();
    }
}
