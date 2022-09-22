import java.util.HashMap;

public class PrubasMain {
    public static void main(String[] args) {
        Pruebas.number = 0;
        Integer numberMain = Pruebas.number;

        Pruebas.add(2);

        //System.out.println("number: " + Pruebas.number + " // numberMain: " + numberMain);
        String testText = "sentido. También es una composición de caracteres imprimibles generados por un algoritmo de cifrado que, aunque no tienen sentido para cualquier persona, sí puede ser descifrado por su destinatario";
        TextAnalizer.countWords(testText);

        for (String name: TextAnalizer.wordsFrequency.keySet()) {
            String key = name.toString();
            String value = TextAnalizer.wordsFrequency.get(name).toString();
            System.out.println(key + " " + value);
        }

        TextAnalizer.countWords(testText);

        for (String name: TextAnalizer.wordsFrequency.keySet()) {
            String key = name.toString();
            String value = TextAnalizer.wordsFrequency.get(name).toString();
            System.out.println(key + " " + value);
        }

    }
}
