package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    ItemNode sentinel;


    public LinkedListDeque() {
        this.size = 0;
        this.sentinel = new ItemNode();
        this.connectNodes(this.sentinel, this.sentinel); //this.sentinel.next = this.sentinel; this.sentinel.prev = this.sentinel;
    }

    public class ItemNode {
        private T item;
        private ItemNode next;
        private ItemNode prev;

        public ItemNode() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }

        public ItemNode(T item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    @Override
    public void addFirst(T item) {
        ItemNode currentHeadNode = this.sentinel.next;

        ItemNode newItemNode = new ItemNode(item);

        this.connectNodes(this.sentinel, newItemNode);
        this.connectNodes(newItemNode, currentHeadNode);

//        if (currentHeadNode != null) {
//            this.sentinel.next = new ItemNode(item);
//            this.sentinel.next.prev = this.sentinel;
////            this.sentinel.next.next = currentHeadNode;
////            currentHeadNode.prev = this.sentinel.next;
//            connectNodes(this.sentinel.next, currentHeadNode);
//        } else {
//            // first time add node
//            this.sentinel.next = new ItemNode(item);
//            this.sentinel.next.prev = this.sentinel;
//            // circular
////            this.sentinel.next.next = this.sentinel;
////            this.sentinel.prev = this.sentinel.next;
//            this.connectNodes(this.sentinel.next, this.sentinel);
//        }

        this.size++;
    }

    @Override
    public void addLast(T item) {

        ItemNode currentTailNode = this.sentinel.prev;

        ItemNode newItemNode= new ItemNode(item);

        this.connectNodes(newItemNode, this.sentinel);
        this.connectNodes(currentTailNode, newItemNode);


//        if (currentTailNode != null) {
//            this.sentinel.prev = new ItemNode(item);
//            this.sentinel.prev.next = this.sentinel;
//            this.sentinel.prev.prev = currentTailNode;
//            currentTailNode.next = this.sentinel.prev;
//        } else {
//            // first time add node
//            this.sentinel.prev = new ItemNode(item);
//            this.sentinel.prev.next = this.sentinel;
//            this.sentinel.next = this.sentinel.prev;
//            this.sentinel.prev.prev = this.sentinel;
//        }

        this.size++;
    }

    @Override
    public T removeFirst() {
        if (this.size() > 0) {
            ItemNode headNode = this.sentinel.next;
//            if (headNode.next == this.sentinel) {
//                headNode.next = null;
//                headNode.prev = null;
//                this.sentinel.next = this.sentinel;
//                this.sentinel.prev = this.sentinel;
//            } else {
//                this.connectNodes(this.sentinel, headNode.next);
//            }
            this.connectNodes(this.sentinel, headNode.next);
            this.size--;
            return headNode.item;
        } else {
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (this.size() > 0) {
            ItemNode tailNode = this.sentinel.prev;
//            if (tailNode.prev == this.sentinel) {
//                tailNode.prev = null;
//                tailNode.next = null;
//                this.sentinel.prev = null;
//                this.sentinel.next = null;
//            } else {
//                connectNodes(tailNode.prev, this.sentinel);
//            }
            this.connectNodes(tailNode.prev, this.sentinel);
            this.size--;
            return tailNode.item;
        } else {
            return null;
        }
    }

    private void connectNodes(ItemNode firstNode, ItemNode secondNode) {
        firstNode.next = secondNode;
        secondNode.prev = firstNode;
    }

    @Override
    public T get(int index) {
        if (index >= this.size()) {
            return null;
        }

        ItemNode curNode = this.sentinel.next;

        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }

        return curNode.item;
    }

    public T getRecursive(int index) {
        if (index >= this.size()) {
            return null;
        }
        ItemNode targetNode = getRecursiveNode(index, this.sentinel);
        return targetNode.item;
    }

    private ItemNode getRecursiveNode(int index, ItemNode curNode) {
        if (index == 0) {
            return curNode;
        } else {
            return getRecursiveNode(index --, curNode.next);
        }
    }

    @Override
    public void printDeque() {

        if (size == 0) {
            return;
        }

        ItemNode curNode = this.sentinel.next;
        String result = "";
        result += curNode.item.toString();
        curNode = curNode.next;

        while (curNode != null && curNode != this.sentinel) {
            result += " " + curNode.item.toString();
            curNode = curNode.next;
        }

        System.out.println(result);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {

        private ItemNode curNode = LinkedListDeque.this.sentinel.next;

        @Override
        public boolean hasNext() {
            return curNode!= null && curNode.next != LinkedListDeque.this.sentinel;
        }

        @Override
        public T next() {
            T currentItem = curNode.item;
            curNode = curNode.next;
            return currentItem;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        LinkedListDeque<T> o = (LinkedListDeque<T>) obj;

        if (o.size() != this.size()) {
            return false;
        }
        Iterator<T> iterator1 = this.iterator();
        Iterator<T> iterator2 = o.iterator();

        while (iterator1.hasNext()) {
            if (!iterator2.hasNext()) {
                return false;
            }
            T val1 = iterator1.next();
            T val2 = iterator2.next();
            if (!val1.equals(val2)) {
                return false;
            }
        }

        return true;

    }
}
