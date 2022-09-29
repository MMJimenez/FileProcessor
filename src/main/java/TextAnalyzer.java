import java.util.HashMap;
import java.text.Normalizer;

public class TextAnalyzer {
    static HashMap<String, Integer> wordsFrequency = new HashMap<String, Integer>();
    static Integer minWordsLength = 2;
    //maxWordsLength equals -1, the methods do not take care the maximum word length.
    static Integer maxWordsLength = -1;

    public static void countWords(String text) {
        String[] words = text.split("\\P{L}+");
        for (String word : words) {
            word = word.toLowerCase();
            word = stripAccents(word);
            //Compare the length of the word
            if (word.length() <= maxWordsLength || maxWordsLength == -1) {
                if (word.length() >= minWordsLength) {
                    Integer frequency = wordsFrequency.containsKey(word) ? wordsFrequency.get(word) + 1 : 1;
                    wordsFrequency.put(word, frequency);
                }
            }
        }
    }

    public static String stripAccents(String string)
    {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return string;
    }
}
