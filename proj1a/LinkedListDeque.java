public class LinkedListDeque<T> {

    private class Node {
        T value;
        Node prev;
        Node next;
        Node(T item) {
            this.value = item;
            this.prev = null;
            this.next = null;
        }
    }

    private Node sentinel;
    private int size;

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        Node i = new Node(item);
        i.prev = sentinel;
        i.next = sentinel.next;
        sentinel.next.prev = i;
        sentinel.next = i;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque */
    public void addLast(T item) {
        Node i = new Node(item);
        i.next = sentinel;
        i.prev = sentinel.prev;
        sentinel.prev.next = i;
        sentinel.prev = i;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        Node p = sentinel;
        for (int i = 0; i < size; i += 1) {
            System.out.print(p.next.value.toString() + " ");
            p = p.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (sentinel.next.equals(sentinel)) {
            return null;
        } else {
            T i = sentinel.next.value;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return i;
        }
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (sentinel.prev.equals(sentinel)) {
            return null;
        } else {
            T i = sentinel.prev.value;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size -= 1;
            return i;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        Node p = sentinel.next;
        if (index > (size - 1)) {
            return null;
        } else if (index == 0) {
            return p.value;
        } else {
            for (int i = 0; i < index; index -= 1) {
                p = p.next;
            }
            return p.value;
        }
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        Node p = sentinel.next;
        if (index > (size - 1)) {
            return null;
        } else if (index == 0) {
            return p.value;
        } else {
            p = p.next;
            index -= 1;
            return getRecursive(index);
        }
    }

}
