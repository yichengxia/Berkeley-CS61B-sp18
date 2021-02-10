public class ClassNameHere {
    /** Returns the maximum value from m. */
    public static int forMax(int[] m) {
        int l = m.length, n = m [0];
        for (int i = 1; i < l; i++) {
            if (m [i] > n) {
                n = m [i];
            }
        }
        return n;
    }
    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
       System.out.println(forMax(numbers));      
    }
}