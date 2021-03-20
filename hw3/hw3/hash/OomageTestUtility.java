package hw3.hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        Map<Integer, Integer> buckets = new HashMap<>();
        for (Oomage oomage : oomages) {
            int bucket = (oomage.hashCode() & 0x7FFFFFFF) % M;
            if (buckets.containsKey(bucket)) {
                buckets.put(bucket, buckets.get(bucket) + 1);
            } else {
                buckets.put(bucket, 1);
            }
        }

        int N = oomages.size();
        for (int bucket : buckets.keySet()) {
            int num = buckets.get(bucket);
            if (num >= N / 2.5 || num <= N / 50) {
                return false;
            }
        }
        return true;
    }
}
