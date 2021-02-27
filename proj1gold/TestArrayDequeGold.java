import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    private Integer call = 500;
    private String message = "";

    private void randomAdd(double random, Integer i, StudentArrayDeque<Integer> sad, ArrayDequeSolution<Integer> ads) {
        if (random < 0.5) {
            sad.addLast(i);
            ads.addLast(i);
            message += "\naddLast(" + i + ")\nremoveLast()";
        } else {
            sad.addFirst(i);
            ads.addFirst(i);
            message += "\naddFirst(" + i + ")\nremoveFirst()";
        }
    }

    private void randomRemove(double random, Integer i, StudentArrayDeque<Integer> sad, ArrayDequeSolution<Integer> ads) {
        Integer actual, expected;
        if (random < 0.5) {
            expected = sad.removeLast();
            actual = ads.removeLast();
            message += "\nremoveLast()";
        } else {
            expected = sad.removeFirst();
            actual = ads.removeFirst();
            message += "\nremoveFirst()";
        }
        assertEquals(message, expected, actual);
    }

    @Test
    public void testArrayDeque() {
        /** @source: StudentArrayDequeLauncher.java */
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        for (int i = 0; i < call; i += 1) {
            if (sad.isEmpty()) {
                randomAdd(0.25, i, sad, ads);
                randomAdd(0.75, i, sad, ads);
            } else {
                double randomAddOrRemove = StdRandom.uniform();
                double randomWhere = StdRandom.uniform();
                if (randomAddOrRemove < 0.5) {
                    randomAdd(randomWhere, i, sad, ads);
                } else {
                    randomRemove(randomWhere, i, sad, ads);
                }
            }
        }
    }
    
}
