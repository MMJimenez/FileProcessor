import java.io.*;
import java.util.HashMap;

public class FileHandler {
    private static String dirPath = "File_Processor_Data";

    public static String getDirPath() {
        return dirPath;
    }

    public static void setDirPath(String dirPath) {
        FileHandler.dirPath = dirPath;
        //TODO: Modify in config.properties
    }

    private static String csvTextFormatter(HashMap<String, Integer> map) {
        StringBuilder csv = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            csv.append(key).append(",").append(value.toString()).append("\n");
        }
        //substring() using for remove the 2 last chars "\n"
        return csv.substring(0, csv.length() - 2);
    }

    private static void makeDirectory() {
        File directory = new File(dirPath);
        if (!directory.exists()) directory.mkdir();
    }

    public static void createCsvFile(String text, String name)
            throws IOException {
        String path = dirPath + File.separatorChar + name;
        String extension = ".csv";

        File file = new File(path + extension);
        Integer numeration = 1;
        while (file.exists()) {
            String tempPath = (path + "(" + numeration + ")" + extension);
            file = new File(tempPath);
            numeration++;
        }
        if (file.createNewFile()) {
            //TODO
            //writeCsvInFile(file, text);
        } else {
            System.out.println("El archivo no se ha creado.");
        }
    }

    private static void writeCsvInFile(File file) throws IOException {
        for (String word: TextAnalyzer.wordsFrequency.keySet()) {
            String key = word.toString();
            String value = TextAnalyzer.wordsFrequency.get(word).toString();
            System.out.println(key + " " + value);
        }
    }

    public static void analyzeFileToHistogram(String path) throws IOException {
        var bufferedReader = new BufferedReader(new FileReader(path));
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            TextAnalyzer.countWords(string);
        }
    }

    public static void showLinesOfFile(String path, Integer numberOfLines) throws IOException {
        var bufferedReader = new BufferedReader(new FileReader(path));
        String string = "";
        for (int i = 0; i < numberOfLines && ((string = bufferedReader.readLine()) != null); i++) {
            System.out.println(string);
        }
    }

}

