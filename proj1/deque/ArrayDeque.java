package deque;


public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirstIndex;
    private int nextLastIndex;
    private int currentOriginIndex; // 目前的circular起始点

    public ArrayDeque() {
        int startSize = 8;
        items = (T[]) new Object[startSize];
        size = 0;
        currentOriginIndex = 0;
        nextFirstIndex = 0;
        nextLastIndex = 1;
    }

    public void addFirst(T item) {

        if (size == items.length) {
            resize(size * 2);
        }

        items[nextFirstIndex] = item;
        // 维护nextFirstIndex状态
        nextFirstIndex = getIndexNextOffsetHelper(nextFirstIndex, -1);
        size++;
    }

    public void addLast(T item) {

        if (size == items.length) {
            resize(size * 2);
        }

        items[nextLastIndex] = item;
        // 维护nextLastIndex状态
        nextLastIndex = getIndexNextOffsetHelper(nextLastIndex, 1);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        int firstIndex = getIndexNextOffsetHelper(nextFirstIndex, 1);
        int lastIndex = getIndexNextOffsetHelper(nextLastIndex, - 1);

        if (firstIndex <= lastIndex) {
            for (int i = firstIndex; i <= lastIndex; i++) {
                if (items[i] != null) {
                    sb.append(items[i].toString()).append(" ");
                }
            }
        } else {
            for (int i = firstIndex; i < items.length; i++) {
                if (items[i] != null) {
                    sb.append(items[i].toString()).append(" ");
                }
            }

            for (int i = 0; i <= lastIndex; i++) {
                if (items[i] != null) {
                    sb.append(items[i].toString()).append(" ");
                }
            }
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        System.out.println(sb.toString());
    }

    public T removeFirst() {
        if (size > 0) {
            int firstIndex = getIndexNextOffsetHelper(nextFirstIndex, 1);
            T result = items[firstIndex];
            items[firstIndex] = null;
            nextFirstIndex = firstIndex;
            size--;
            if (size <= items.length / 4 && size > 8) {
                resize(items.length / 2);
            }
            return result;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (size > 0) {
            int lastIndex = getIndexNextOffsetHelper(nextLastIndex, -1);
            T result = items[lastIndex];
            items[lastIndex] = null;
            nextLastIndex = lastIndex;
            size--;
            if (size <= (items.length / 4) && size > 8) {
                resize(items.length / 2);
            }
            return result;
        } else {
            return null;
        }
    }

    public T get(int index) {
        if (index < size && index >= 0) {
            int first = getIndexNextOffsetHelper(nextFirstIndex, 1);
            int realIndex = getIndexNextOffsetHelper(first, index);
            return items[realIndex];
        }
        return null;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        // 没有 addFirst 过
        int firstIndex = getIndexNextOffsetHelper(nextFirstIndex, 1);
        int lastIndex = getIndexNextOffsetHelper(nextLastIndex, -1);

        if (firstIndex > lastIndex) { //分段式
            System.arraycopy(items, firstIndex, a, 0, items.length - firstIndex);
            System.arraycopy(items, 0, a, items.length - firstIndex, lastIndex - 0 + 1);
        } else { //continuous   firstIndex == lastIndex 只有一个元素的时候
            //when firstIndex <= lastIndex
            System.arraycopy(items, firstIndex, a, 0, lastIndex - firstIndex + 1);
        }

        items = a;
        nextFirstIndex = capacity - 1;
        nextLastIndex = getIndexNextOffsetHelper(size - 1, 1);
    }

    private int getIndexNextOffsetHelper(int index, int offset) {
        if (index + offset >= items.length) {
            return index + offset - items.length;
        } else if (index + offset < 0) {
            return items.length + (index + offset);
        } else {
            return index + offset;
        }
    }
}
