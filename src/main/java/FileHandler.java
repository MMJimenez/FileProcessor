import java.io.*;
import java.util.HashMap;

public class FileHandler {
    private static String dirPath = "File_Processor_Data";



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
            writeInFile(file, text);
        } else {
            System.out.println("El archivo no se ha creado.");
        }
    }

    private static void writeInFile(File file, String text) throws IOException {
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(text);
        myWriter.close();
    }

    public static String readFileToString(String path) throws IOException {
        var bufferedReader = new BufferedReader(new FileReader(path));
        String string;
        String text = "";
        while ((string = bufferedReader.readLine()) != null) {
            text = text.concat(string);
            text = text.concat("\n");
        }
        return text;
    }
}

