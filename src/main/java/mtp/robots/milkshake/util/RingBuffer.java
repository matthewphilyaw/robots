package mtp.robots.milkshake.util;

import java.math.BigInteger;
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

        for (int i = buffer.size() - 1; i >=0; i--) {
            items.add(buffer.get(i));
        }
        return items;
    }

    public T getHead() {
        return this.buffer.get(this.size - 1);
    }

    public RingBuffer copyCurrentBuffer() {
        RingBuffer copy = new RingBuffer(this.size);
        for (int i = 0; i < this.getSize(); i++) {
            copy.add(this.buffer.get(i));
        }
        return copy;
    }

    public int getSize() {
        return size;
    }

    public Long getFnvHash() {
        byte[] buff = new byte[buffer.size() * 4];
        int count = 0;
        for (T k : buffer) {
            for (byte b : BigInteger.valueOf(k.hashCode()).toByteArray()) {
                buff[count] = b;
            }
            count++;
        }

        Long h = 2166136261L;
        for (byte b : buff) {
            h = (h * 16777619) ^ b;
        }
       
        return h;
    }
}

