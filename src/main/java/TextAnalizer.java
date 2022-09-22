import java.util.HashMap;

public class TextAnalizer {
    static HashMap<String, Integer> wordsFrequency = new HashMap<String, Integer>();
    static Integer minWordsLenth = 2;
    //maxWordsLenth equals -1, the methods do not take care the maximum word length.
    static Integer maxWordsLenth = -1;

    public static void countWords(String text) {
        String[] words = text.split("\\P{L}+");
        for (String word : words) {
            word = word.toLowerCase();
            if (word.length() <= maxWordsLenth || maxWordsLenth == -1) {
                if (word.length() >= minWordsLenth) {
                    Integer frequency = wordsFrequency.containsKey(word) ? wordsFrequency.get(word) + 1 : 1;
                    wordsFrequency.put(word, frequency);
                }
            }
        }
    }
}
