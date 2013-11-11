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

        for (int i = buffer.size() - 1; i >=0; i--) {
            items.add(buffer.get(i));
        }
        return items;
    }

    public T getHead() {
        int size = this.buffer.size() < this.size ? this.buffer.size() : this.size;
        return this.buffer.get(size - 1);
    }

    public RingBuffer<T> copyCurrentBuffer() {
        RingBuffer<T> copy = new RingBuffer<T>(this.size);
        int size = this.buffer.size() < this.size ? this.buffer.size() : this.size;
        for (int i = 0; i < size; i++) {
            copy.add(this.buffer.get(i));
        }
        return copy;
    }

    public int getSize() {
        return size;
    }

    public Integer getHash() {
        StringBuilder sb = new StringBuilder();
        for (T p : this.buffer) {
            sb.append(p.toString());
        }

        return sb.toString().hashCode();
    }

    private byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 8),
                (byte)(value >>> 16),
                (byte)value
        };
    }

}

