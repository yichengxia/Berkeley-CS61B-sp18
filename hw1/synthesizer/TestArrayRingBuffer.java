package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {

    @Test
    public void queueTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
        assertEquals((Integer) 0, arb.dequeue());
        assertEquals((Integer) 1, arb.peek());
        assertEquals((Integer) 1, arb.dequeue());
        assertEquals((Integer) 2, arb.peek());
    }

    @Test
    public void isEmptyTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertTrue(arb.isEmpty());
    }

    @Test
    public void isFullTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertFalse(arb.isFull());
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
        assertTrue(arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
