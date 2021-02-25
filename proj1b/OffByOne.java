public class OffByOne implements CharacterComparator {
    public boolean equalChars(char a, char b) {
        return ((a - b) == 1 || (a - b) == -1);
    }
}
