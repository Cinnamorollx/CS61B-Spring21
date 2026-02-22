package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void testMyArrayDeque() {
        ArrayDeque<String> ad = new ArrayDeque<>();

        ad.addLast("last1");
        ad.addLast("last2");
        ad.addFirst("first1");
        ad.addFirst("first2");
        ad.addLast("last3");
        ad.addLast("last4");
        ad.addLast("last5");
        ad.addFirst("first3");
        ad.addFirst("first4");
        ad.addLast("last6");
        assertEquals(ad.removeFirst(), "first4");
        assertEquals(ad.removeLast(), "last6");
        ad.addFirst("first5");
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.addLast("last7");
        ad.removeFirst();
        ad.removeFirst();
        ad.removeLast();
        ad.removeFirst();

        ad.printDeque();
    }

    @Test
    public void randomizeTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        LinkedListDeque<Integer> ld = new LinkedListDeque<>();
        int N = 5000;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 8);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 1000);
                ld.addFirst(randVal);
                ad.addFirst(randVal);
            } else if (operationNumber == 1) {
                int randVal = StdRandom.uniform(0, 1000);
                ld.addLast(randVal);
                ad.addLast(randVal);
            } else if (operationNumber == 2) {
               Integer val1 = ld.removeFirst();
               Integer val2 = ad.removeFirst();
               assertEquals(val1, val2);
            } else if (operationNumber == 3) {
                Integer val1 = ld.removeLast();
                Integer val2 = ad.removeLast();
                assertEquals(val1, val2);
            } else if (operationNumber == 4 ) {
                assertEquals(ld.size(),ad.size());
            } else if (operationNumber == 5) {
                ad.printDeque();
                ld.printDeque();
            } else if (operationNumber == 6) {
                assertEquals(ad.isEmpty(), ld.isEmpty());
            } else if (operationNumber == 7) {
                int randVal = StdRandom.uniform(0, 1000);
                Integer val1 = ld.get(randVal);
                Integer val2 = ad.get(randVal);
                assertEquals(val1, val2);
            }
        }
    }
}
