package mtp.robots.milkshake.util;

import java.util.ArrayList;
import java.util.List;

public class RingBuffer<T> {
    List<T> buffer = new ArrayList<T>();
    int size = 0;

    public RingBuffer(int size) {
        this.size = size;
    }

    public void add(T item) {
        if (buffer.size() < size) { buffer.add(item); return; }
        buffer.remove(0);
        buffer.add(item);
    }

    public List<T> getItems() {
        List<T> items = new ArrayList<T>();
        if (buffer.size() == 0) { return items; }

        for (int i = size - 1; i >=0; i--) {
            items.add(buffer.get(i));
        }
        return items;
    }

    public int getSize() {
        return size;
    }
}
