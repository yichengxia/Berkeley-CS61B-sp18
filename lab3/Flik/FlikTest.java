import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void testFlik() {
        boolean test1 = Flik.isSameNumber(127, 127);
        boolean test2 = Flik.isSameNumber(128, 228);
        assertTrue(test1);
        assertTrue(test2);
    }
}
