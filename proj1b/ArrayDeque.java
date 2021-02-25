public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int capacity;

    private int initialCapacity = 8;
    private static int eScale = 2; // Expanding scale
    private static int cScale = 2; // Contracting scale

    /** Creates an empty array deque. */
    public ArrayDeque() {
        capacity = initialCapacity;
        items = (T[]) new Object[initialCapacity];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    @Override
    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
        expand();
    }

    @Override
    /** Adds an item of type T to the back of the deque */
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
        expand();
    }

    @Override
    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    @Override
    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[plusOne(nextFirst)].toString() + " ");
        }
        System.out.println();
    }

    @Override
    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int p = plusOne(nextFirst);
        T i = items[p];
        items[p] = null;
        nextFirst = p;
        size -= 1;
        contract();
        return i;
    }

    @Override
    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int p = minusOne(nextLast);
        T i = items[p];
        items[p] = null;
        nextLast = p;
        size -= 1;
        contract();
        return i;
    }

    @Override
    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            int p = index + nextFirst + 1;
            if (p >= capacity) {
                p -= capacity;
            }
            return items[p];
        }
    }

    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        int iFirst = plusOne(nextFirst); // items start
        int iLast = minusOne(nextLast); // items end
        if (iFirst < iLast) {
            System.arraycopy(items, iFirst, newItems, 0, size);
            nextFirst = newCapacity - 1;
            nextLast = size;
        } else {
            int sizeFirst = capacity - iFirst;
            int sizeLast = nextLast;
            System.arraycopy(items, iFirst, newItems, 0, sizeFirst);
            System.arraycopy(items, 0, newItems, sizeFirst, sizeLast);
            nextFirst = newCapacity - 1;
            nextLast = size;
        }
        capacity = newCapacity;
        items = newItems;
    }

    private void expand() {
        if (size == capacity) {
            int newCapacity = capacity * eScale;
            resize(newCapacity);
        }
    }

    private void contract() {
        if (capacity >= 16 && (double) size / capacity < 0.25) {
            int newCapacity = capacity / cScale;
            resize(newCapacity);
        }
    }

    private int minusOne(int index) {
        if (index == 0) {
            return capacity - 1;
        } else {
            return index - 1;
        }
    }

    private int plusOne(int index) {
        if (index == capacity - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }

}
