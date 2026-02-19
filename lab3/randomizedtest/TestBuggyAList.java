package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void randomizeTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BL = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BL.addLast(randVal);
                System.out.println("AL addLast(" + randVal + ")");
                System.out.println("BL addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("AL size: " + size);
                int size1 = BL.size();
                System.out.println("BL size: " + size1);
                assertEquals(size, size1);
            } else if (operationNumber == 2){
                if (L.size() > 0) {
                    int last = L.getLast();
                    System.out.println("AL getLast: " + last);
                    int last1 = L.getLast();
                    System.out.println("BL getLast: " + last1);
                    assertEquals(last, last1);
                }
            } else if (operationNumber == 3) {
                if (L.size() > 0) {
                    int last = L.removeLast();
                    System.out.println("remove Last: " + last);
                    int last1 = BL.removeLast();
                    assertEquals(last, last1);
                }
            }
        }
    }
}
