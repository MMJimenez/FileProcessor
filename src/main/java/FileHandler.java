import java.util.HashMap;

public class FileHandler {
    public static String csvFormatter(HashMap<String, Integer> map) {
        StringBuilder csv = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            csv.append(key).append(",").append(value.toString()).append("\n");
        }
        return csv.toString();
    }

    
}
