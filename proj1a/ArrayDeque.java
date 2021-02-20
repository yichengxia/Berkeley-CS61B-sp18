public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int capacity;

    private int initialCapacity = 8;

    /** Creates an empty array deque. */
    public ArrayDeque() {
        capacity = initialCapacity;
        items = (T[]) new Object[initialCapacity];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        ifExpand();
        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = nextFirst - 1 + capacity;
        } else {
            nextFirst -= 1;
        }
        size += 1;
    }

    /** Adds an item of type T to the back of the deque */
    public void addLast(T item) {
        ifExpand();
        items[nextLast] = item;
        if (nextLast == capacity - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            if (i + nextFirst < capacity) {
                System.out.print(items[i + nextFirst + 1].toString() + " ");
            } else {
                System.out.print(items[i + nextFirst - capacity].toString() + " ");
            }
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if ((nextLast - nextFirst == 1) || (nextFirst - nextLast == capacity - 1)) {
            return null;
        }
        size -= 1;
        if (nextFirst == capacity - 1) {
            nextFirst = 0;
            ifContract();
            return items[0];
        } else {
            nextFirst += 1;
            T i = items[nextFirst];
            items[nextFirst] = null;
            ifContract();
            return i;
        }
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if ((nextLast - nextFirst == 1) || (nextFirst - nextLast == capacity - 1)) {
            return null;
        }
        size -= 1;
        T i;
        if (nextLast == 0) {
            nextLast = capacity - 1;
            i = items[nextLast];
            items[nextLast] = null;
            ifContract();
            return i;
        } else {
            nextLast -= 1;
            i = items[nextLast];
            items[nextLast] = null;
            ifContract();
            return i;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        int p = nextFirst + index + 1;
        if (p > capacity - 1) {
            p -= capacity;
        }
        if (nextLast > nextFirst) {
            if (p > nextFirst && p < nextLast) {
                return items[p];
            } else {
                return null;
            }
        } else if (p >= nextLast && p <= nextFirst) {
            return null;
        } else {
            return items[p];
        }
    }

    public void ifExpand(){
        if (size == capacity - 2) {
            capacity += 1;
            T[] a = (T[]) new Object[capacity];
            System.arraycopy(items, 0, a, 0, capacity);
            items = a;
        }
    }

    public void ifContract() {
        int i = capacity;
        while (capacity >= 16 && size / capacity < 0.25) {
            capacity -= 1;
        }
        if (i != capacity) {
            T[] a = (T[]) new Object[capacity];
            System.arraycopy(items, 0, a, 0, capacity);
            items = a;
        }
    }

}