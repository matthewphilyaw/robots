package mtp.robots.milkshake.test.analytics.RingBuffer;

import mtp.robots.milkshake.util.RingBuffer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class General {
    @Test
    public void VerifyKeepsLast3() {
        int n = 3;
        Integer total = 3 * n;
        RingBuffer<Integer> rb = new RingBuffer<Integer>(n);
        for (int i = 0; i < total; i++) {
            rb.add(i);
        }

        List<Integer> items = rb.getItems();
        assertEquals(n, items.size());
        total--;
        for (int i = 0; i < n; i++) {
            assertEquals(total, items.get(i));
            total--;
        }
    }
    @Test
    public void VerifyZeroOnCreation() {
        RingBuffer<Integer> rb = new RingBuffer<Integer>(1);
        assertEquals(0 , rb.getItems().size());
    }
}
