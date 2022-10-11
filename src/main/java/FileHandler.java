import java.io.*;
import java.util.HashMap;

public class FileHandler {
    private static String dirPath = "file_processor_data";

    public static String getDirPath() {
        return dirPath;
    }

    public static void setDirPath(String dirPath) {
        FileHandler.dirPath = dirPath;
        //TODO: Modify in file_processor.config
    }

    static {
        var configHandler = new ConfigHandler();
        try {
            setDirPath(
                configHandler.loadPropertyOrRestoreIt("csv_save_path")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeDirectory() {
        File directory = new File(getDirPath());
        if (!directory.exists()) directory.mkdir();
    }

    public static void createCsvFromFile(File textFile)
            throws IOException {
        String name = textFile.getName().split("\\.")[0];
        //TODO revisar lo del dirPath
        String path = getDirPath() + File.separatorChar + name;
        String extension = ".csv";

        File file = new File(path + extension);
        Integer numeration = 1;
        while (file.exists()) {
            String tempPath = (path + "(" + numeration + ")" + extension);
            file = new File(tempPath);
            numeration++;
        }

        //Feeds the TextAnalyzer.wordsFrequency
        analyzeFileToHistogram(textFile);

        if (file.createNewFile()) {
            writeCsvIntoFile(file);
        } else {
            System.out.println("El archivo csv no ha podido crearse.");
        }
        //clear the wordsFrequency hashmap
        TextAnalyzer.wordsFrequency.clear();
    }

    private static void writeCsvIntoFile(File file) throws IOException {
        var bufferedWriter = new BufferedWriter(new FileWriter(file));

        for (HashMap.Entry<String, Integer> entry : TextAnalyzer.wordsFrequency.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            String line = key + "," + value.toString();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public static void analyzeFileToHistogram(File textFile) throws IOException {
        var bufferedReader = new BufferedReader(new FileReader(textFile));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            TextAnalyzer.countWords(line);
        }
        bufferedReader.close();
    }

    public static void showFile(String path) throws IOException {
        var bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }
}

