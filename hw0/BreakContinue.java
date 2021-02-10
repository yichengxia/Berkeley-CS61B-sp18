public class BreakContinue {
    public static void windowPosSum(int[] a, int n) {
        int l = a.length, s;
        for (int i = 0; i < l; i++) {
            if (a [i] > 0) {
                if (i + n < l) {
                    s = 0;
                    for (int j = i; j <= i + n; j++) {
                        s = s + a [j];
                    }
                    a [i] = s;
                } else {
                    s = 0;
                    for (int j = i; j <= l - 1; j++) {
                        s = s + a [j];
                    }
                    a [i] = s;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSum(a, n);

        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }
}