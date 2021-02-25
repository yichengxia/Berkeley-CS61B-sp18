public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word.equals("")) {
            return true;
        }
        if (word.length() == 1) {
            return true;
        }
        for (int i = 0, j = word.length() - 1; i < j; i += 1, j -= 1) {
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.equals("")) {
            return true;
        }
        if (word.length() == 1) {
            return true;
        }
        for (int i = 0, j = word.length() - 1; i < j; i += 1, j -= 1) {
            if (!cc.equalChars(word.charAt(i), word.charAt(j))) {
                return false;
            }
        }
        return true;
    }
}
