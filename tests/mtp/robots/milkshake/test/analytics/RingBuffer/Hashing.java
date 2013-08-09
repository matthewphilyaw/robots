package mtp.robots.milkshake.test.analytics.RingBuffer;

import mtp.robots.milkshake.util.RingBuffer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

public class Hashing {

    private void ringBufferFilledRandomlyCheckHash(int n) {
        RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);
        RingBuffer<Integer> rb2 = new RingBuffer<Integer>(n);

        Random r = new Random();
        for (int i = 0; i < n * 3; i++) {
            int num = r.nextInt(n * 3);
            rb1.add(num);
        }

        r = new Random();
        for (int i = 0; i < n * 3; i++) {
            int num = r.nextInt(n * 3);
            rb2.add(num);
        }

        List<Integer> rl1 = rb1.getItems();
        List<Integer> rl2 = rb2.getItems();

        Boolean contentsMatch = true;
        for (int i = 0; i < n; i++) {
            if (!rl1.get(i).equals(rl2.get(i))) {
                contentsMatch = false;
                break;
            }
        }
        assertEquals(contentsMatch, rb1.getFnvHash() == rb2.getFnvHash());
    }


    @Test
    public void TwoRingBuffersWithSameContentsProduceSameHash() {
        int n = 360;
        RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);
        RingBuffer<Integer> rb2 = new RingBuffer<Integer>(n);

        for (int i = 0; i < n * 4; i++) { rb1.add(i); rb2.add(i); }

        assertEquals(rb1.getFnvHash(), rb2.getFnvHash());
    }

    @Test
    public void TwoRingBuffersWithDifferentContentsDoesntMatch() {
        int n = 360;
        RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);
        RingBuffer<Integer> rb2 = new RingBuffer<Integer>(n);

        for (int i = 0; i < n; i++) { rb1.add(i); }
        for (int i = n; i >= 0 ; --i) { rb2.add(i); }

        assertFalse(rb1.getFnvHash() == rb2.getFnvHash());
    }

    @Test
    public void TwoRandomBuffersDontMatchUnlessContentMatchesSize360() {
        for (int iter = 0; iter < 1000; iter++) {
            ringBufferFilledRandomlyCheckHash(360);
        }
    }

    @Test
    public void TwoRandomBuffersDontMatchUnlessContentMatchesSize3() {
        for (int iter = 0; iter < 1000; iter++) {
            ringBufferFilledRandomlyCheckHash(3);
        }
    }

    @Test
    public void TwoBuffersWithDifferentLengthsDontMatch() {
        int n = 3;
        RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);
        RingBuffer<Integer> rb2 = new RingBuffer<Integer>(n);

        for (int i = 0; i < n; i++) { rb1.add(i); }
        for (int i = 0; i < n -1; i++) { rb1.add(i); }

        assertFalse(rb1.getFnvHash() == rb2.getFnvHash());
    }
}
