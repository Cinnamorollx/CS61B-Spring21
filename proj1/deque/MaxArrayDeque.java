package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        return this.max(this.comparator);
    }

    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }

        int maxIndex = 0;

        for (int i = 0; i < this.size(); i++) {
            int cmp = c.compare(this.get(i), this.get(maxIndex));
            if (cmp > 0) {
                maxIndex = i;
            }
        }
        return this.get(maxIndex);
    }

}
