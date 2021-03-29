import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Yicheng Xia
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int N = asciis.length;
        int R = 256;
        String[] aux = new String[N];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i += 1) {
            max = max > asciis[i].length() ? max : asciis[i].length();
        }
        // FAILED TESTS b001) Radix Sort Times: Try to match (or beat) staff solution runtime. (0.0/2.0)
        // org.junit.runners.model.TestTimedOutException: test timed out after 10000 milliseconds
        // at RadixSort.sort:28 (RadixSort.java)
        // at AGTestRadixSort.testRadixSortTime:33 (AGTestRadixSort.java)
        // TO BE SOLVED
        for (int d = max - 1; d >= 0; d--) {
            int[] count = new int[R];
            int[] start = new int[R];
            for (int i = 0; i < N; i++) {
                if (d < asciis[i].length()) {
                    count[(int) asciis[i].charAt(d)]++;
                } else {
                    count[0]++;
                }
            }
            start[1] = count[0];
            for (int r = 1; r < R; r++) {
                start[r] = count[r - 1] + start[r - 1];
            }
            for (int i = 0; i < N; i++) {
                if (d < asciis[i].length()) {
                    aux[start[(int) asciis[i].charAt(d)]++] = asciis[i];
                } else {
                    aux[start[0]++] = asciis[i];
                }
            }
            for (int i = 0; i < N; i++) {
                asciis[i] = aux[i];
            }
        }
        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    // @source https://github.com/ema00/Berkeley-CS61B-sp18/blob/master/lab13/RadixSort.java
    public static void main(String[] args) {
        String[] unsorted0 = {"blala", "asdasc", "fdsd", "eef", "e", "aaaaaaaaaaa"};
        String[] unsorted1 = {"b", "c", "f", "e", "d", "a"};
        String[] unsorted2 = {"ba", "ca", "fa", "ea", "da", "aa"};
        String[] unsorted3 = {"ab", "ac", "af", "ae", "ad", "aa"};
        String[] unsorted4 = {"abc", "ab", "aba", "ae", "ac", "aa"};
        String[] unsorted5 = {"ab", "abc", "aba", "eef", "ee", "ac", "aa"};
        String[] sorted0 = sort(unsorted0);
        String[] sorted1 = sort(unsorted1);
        String[] sorted2 = sort(unsorted2);
        String[] sorted3 = sort(unsorted3);
        String[] sorted4 = sort(unsorted4);
        String[] sorted5 = sort(unsorted5);
        System.out.println(Arrays.asList(sorted0));
        System.out.println(Arrays.asList(sorted1));
        System.out.println(Arrays.asList(sorted2));
        System.out.println(Arrays.asList(sorted3));
        System.out.println(Arrays.asList(sorted4));
        System.out.println(Arrays.asList(sorted5));
    }
}
