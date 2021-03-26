package model.ADTs;

import java.util.Map;
import java.util.Set;

public interface MyIHeap<T1, T2> {
    void add(T1 k, T2 v);
    void delete(T1 k);
    void update(T1 k, T2 v);
    T2 lookup(T1 k);
    Set<Map.Entry<T1, T2>> entrySet();
    int getNextFreeLocation();
    void setContent(Map<T1, T2> newContent);
    Map<T1, T2> getContent();
}
