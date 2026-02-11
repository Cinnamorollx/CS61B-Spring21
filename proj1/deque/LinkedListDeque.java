package deque;

public class LinkedListDeque<T> {
    private int size;
    ItemNode sentinel;


    public LinkedListDeque() {
        this.size = 0;
        sentinel = new ItemNode();
    }

    public class ItemNode {
        public T item;
        public ItemNode next;
        public ItemNode prev;

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

    public void addFirst(T item) {
        ItemNode currentHeadNode = this.sentinel.next;
        if (currentHeadNode != null) {
            this.sentinel.next = new ItemNode(item);
            this.sentinel.next.prev = this.sentinel;
//            this.sentinel.next.next = currentHeadNode;
//            currentHeadNode.prev = this.sentinel.next;
            connectNodes(this.sentinel.next, currentHeadNode);
        } else {
            // first time add node
            this.sentinel.next = new ItemNode(item);
            this.sentinel.next.prev = this.sentinel;
            // circular
//            this.sentinel.next.next = this.sentinel;
//            this.sentinel.prev = this.sentinel.next;
            this.connectNodes(this.sentinel.next, this.sentinel);
        }

        this.size++;
    }

    public void addLast(T item) {

        ItemNode currentTailNode = this.sentinel.prev;

        if (currentTailNode != null) {
            this.sentinel.prev = new ItemNode(item);
            this.sentinel.prev.next = this.sentinel;
            this.sentinel.prev.prev = currentTailNode;
            currentTailNode.next = this.sentinel.prev;
        } else {
            // first time add node
            this.sentinel.prev = new ItemNode(item);
            this.sentinel.prev.next = this.sentinel;
            this.sentinel.next = this.sentinel.prev;
            this.sentinel.prev.prev = this.sentinel;
        }

        this.size++;
    }

    public T removeFirst() {
        if (this.size() > 0) {
            ItemNode headNode = this.sentinel.next;
            if (headNode.next == this.sentinel) {
                headNode.next = null;
                headNode.prev = null;
                this.sentinel.next = null;
                this.sentinel.prev = null;
            } else {
                this.connectNodes(this.sentinel, headNode.next);
            }
            this.size--;
            return headNode.item;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (this.size() > 0) {
            ItemNode tailNode = this.sentinel.prev;
            if (tailNode.prev == this.sentinel) {
                tailNode.prev = null;
                tailNode.next = null;
                this.sentinel.prev = null;
                this.sentinel.next = null;
            } else {
                connectNodes(tailNode.prev, this.sentinel);
            }
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

    public T get(int index) {
        if (index >= this.size()) {
            return null;
        }

        ItemNode curNode = this.sentinel;

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

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public void printDeque() {
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

    public int size() {
        return this.size;
    }

}
