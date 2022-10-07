import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileHandler {
    private static String dirName = "File_Processor_Data";
    private static String fullPath = "";

    public static String csvFormatter(HashMap<String, Integer> map) {
        StringBuilder csv = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            csv.append(key).append(",").append(value.toString()).append("\n");
        }
        return csv.toString();
    }

    public static void makeDirectory() {
        File directory = new File(fullPath + dirName);
        if (!directory.exists()) directory.mkdir();
    }

    public static void createFile(String text, String name)
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

        text = "abc";
        var bufferedWriter = new BufferedWriter(new FileWriter(file));
        System.out.println("TEXT: " + text);
        while (text==null) {
            bufferedWriter.write(text);
        }
        System.out.println("====================");
        System.out.println("TEXT: " + text);
    }
}

