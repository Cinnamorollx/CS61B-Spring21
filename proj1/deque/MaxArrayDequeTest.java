package deque;

import org.junit.Test;

import javax.naming.Name;
import java.util.Comparator;

public class MaxArrayDequeTest {

    @Test
    public void myTest() {
        String a = "sadad";
        String b = "arimaKana";
        String c = "HoshinoRuby";
        String d = "HoshinoAqua";

        NameComparator cccc = new NameComparator();

        MaxArrayDeque<String> mad = new MaxArrayDeque<>(cccc);

        mad.addFirst(a);
        mad.addLast(b);
        mad.addLast(c);
        mad.addFirst(d);

        String largeName = mad.max();

        System.out.println("large name is: " + largeName);

        Comparator<String> normalC = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };

        String normalLargeName = mad.max(normalC);
        System.out.println("normal large name: " + normalLargeName);


    }

    private static class NameComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.charAt(1) - o2.charAt(1);
        }
    }
}
