package mtp.robots.milkshake.test.analytics.RingBuffer;

import mtp.robots.milkshake.util.RingBuffer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;
import java.math.*;

public class HashTestCase {

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
            if (rl1.get(i).intValue() != rl2.get(i).intValue()) {
                contentsMatch = false;
                break;
            }
        }

        if (contentsMatch != (rb1.getHash().intValue() == rb2.getHash().intValue())) {
            for (int i = 0; i < n; i++) {
                System.out.println("rl1(" + Integer.valueOf(i).toString() + ") = " + rl1.get(i).toString() + " rl2(" + Integer.valueOf(i).toString() + ") = " + rl2.get(i).toString());
            }
            System.out.println("mismatch between how it should match (content matches " + contentsMatch.toString() + ") and how it matches");
        }

        assertTrue(contentsMatch == (rb1.getHash().intValue() == rb2.getHash().intValue()));
    }

    @Test
    public void TwoRingBuffersWithSameContentsProduceSameHash() {
        int n = 360;
        RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);
        RingBuffer<Integer> rb2 = new RingBuffer<Integer>(n);

        for (int i = 0; i < n * 4; i++) { rb1.add(i); rb2.add(i); }
        assertTrue(rb1.getHash().intValue() == rb2.getHash().intValue());
    }

    @Test
    public void TwoRingBuffersWithDifferentContentsDoesntMatch() {
        int n = 360;
        RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);
        RingBuffer<Integer> rb2 = new RingBuffer<Integer>(n);

        for (int i = 0; i < n; i++) { rb1.add(i); }
        for (int i = n; i >= 0 ; --i) { rb2.add(i); }

        assertFalse(rb1.getHash().intValue() == rb2.getHash().intValue());
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

        assertFalse(rb1.getHash().intValue() == rb2.getHash().intValue());
    }

    @Test
    public void TwoBuffersDontMatchExampleProvided() {
        int n = 3;
        RingBuffer<Double> a = new RingBuffer<Double>(n);
        RingBuffer<Double> b = new RingBuffer<Double>(n);

        a.add(Double.valueOf(290.4));
        a.add(Double.valueOf(322.4));
        a.add(Double.valueOf(354.4));
        b.add(Double.valueOf(288.4));
        b.add(Double.valueOf(292.4));
        b.add(Double.valueOf(324.4));

        assertFalse(a.getHash().intValue() == b.getHash().intValue());
    }

    @Test
    public void TwoBuffersDontMatchExampleProvidedFailedOnce() {
        int n = 3;
        RingBuffer<Integer> a = new RingBuffer<Integer>(n);
        RingBuffer<Integer> b = new RingBuffer<Integer>(n);

        a.add(6);
        a.add(5);
        a.add(5);
        b.add(6);
        b.add(8);
        b.add(8);

        assertFalse(a.getHash().intValue() == b.getHash().intValue());
    }

    @Test
    public void copyOfBufferMatchesOriginal() {
        int n = 3;

        for (int numTimes = 0; numTimes < 1000; numTimes++) {
            RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);

            Random r = new Random();
            for (int i = 0; i < n * 3; i++) {
                int num = r.nextInt(n * 3);
                rb1.add(num);
            }

            assertTrue(rb1.getHash().intValue() == rb1.copyCurrentBuffer().getHash().intValue());
        }
    }

    @Test
    public void copyOfBufferHasSameItems() {
        int n = 3;
        RingBuffer<Integer> rb1 = new RingBuffer<Integer>(n);

        rb1.add(23);
        rb1.add(60);
        rb1.add(90);

        RingBuffer<Integer> copy = rb1.copyCurrentBuffer();
        for (int i = 0; i < rb1.getSize(); i++) {
            assertTrue(rb1.getItems().get(i).intValue() == copy.getItems().get(i).intValue());
        }

        assertTrue(rb1.getHash().intValue() == copy.getHash().intValue());
    }
}

