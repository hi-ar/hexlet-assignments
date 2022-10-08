package exercise;

import java.util.NoSuchElementException;

/*
В файле ReversedSequence.java реализуйте класс ReversedSequence, который реализует стандартный интерфейс java.lang CharSequence.
Конструктор класса принимает на вход строку. Ваша реализация должна представлять последовательность в перевернутом виде.
CharSequence text = new ReversedSequence("abcdef");
text.toString(); // "fedcba"
text.charAt(1); // 'e'
text.length(); // 6
text.subSequence(1, 4).toString(); // "edc"
 */
// BEGIN
class ReversedSequence implements CharSequence {
    private String sentence;
    private char[] reversedArr;
    private String revSentence;
    public ReversedSequence(String sentence) {
        this.sentence = sentence;
        char[] charsArr = sentence.toCharArray();
        char[] reversedArr = new char[charsArr.length];
        for(int i = charsArr.length - 1; i >= 0; i--) {
            reversedArr[charsArr.length - 1 - i] = charsArr[i];
        }
        this.reversedArr = reversedArr;
        this.revSentence = new String(reversedArr);
    }

    @Override
    public int length() {
        return revSentence.length();
    }

    @Override
    public char charAt(int index) throws NoSuchElementException {
        return reversedArr[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) throws NoSuchElementException {
        if (start < 0 || end < 0
                || start != 0 && start >= this.sentence.length()
                || end != 0 && end >= this.sentence.length()
                || start > end) {
            throw new NoSuchElementException();
        }
        StringBuilder subSeq = new StringBuilder();
        for (int i = start; i < end; i++) {
            subSeq.append(this.reversedArr[i]);
        }
        return subSeq.toString();
    }

    public String  toString() {
        return revSentence;
    }
}
// END
