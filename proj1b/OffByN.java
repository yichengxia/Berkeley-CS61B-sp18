public class OffByN implements CharacterComparator {

    private int n;

    public OffByN(int N) {
        n = N;
    }
    
    public boolean equalChars(char a, char b) {
        return ((a - b) == n || (a - b) == -n);
    }
}
