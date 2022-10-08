import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileHandler {
    private static String dirName = "File_Processor_Data";
    private static String fullPath = "";

    public static String csvTextFormatter(HashMap<String, Integer> map) {
        StringBuilder csv = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            csv.append(key).append(",").append(value.toString()).append("\n");
        }
        //substring() using for remove the 2 last chars "\n"
        return csv.substring(0, csv.length() - 2);
    }

    public static void makeDirectory() {
        File directory = new File(fullPath + dirName);
        if (!directory.exists()) directory.mkdir();
    }

    public static void createCsvFile(String text, String name)
            throws IOException {
        String path = dirName + File.separatorChar + name;
        String extension = ".csv";

        File file = new File(path + extension);
        Integer numeration = 1;
        while (file.exists()) {
            String tempPath = (path + "(" + numeration + ")" + extension);
            file = new File(tempPath);
            numeration++;
        }

        file.createNewFile();
        writeInFile(file, text);

    }

    private static void writeInFile(File file, String text) throws IOException {
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(text);
        myWriter.close();
    }

//    private static String readFileToString(File file) {
//        return "";
//    }
}

