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

        for (int i = buffer.size() - 1; i >= 0; i--) {
            items.add(buffer.get(i));
        }
        return items;
    }

    public int getSize() {
        return size;
    }

    public long getFnvHash() {
        byte[] buff = new byte[buffer.size() * 4];
        int count = 0;
        for (T k : buffer) {
            for (byte b : BigInteger.valueOf(k.hashCode()).toByteArray()) {
                buff[count] = b;
            }
            count++;
        }

        long h = 2166136261L;
        for (byte b : buff) {
            h = (h * 16777619) ^ b;
        }

        return h;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = buffer.size() - 1; i >= 0; i--) {
            sb.append(buffer.get(i).toString());
            sb.append(" ");
        }
        sb.deleteCharAt(sb.lastIndexOf(" "));
        return sb.toString();
    }
}
