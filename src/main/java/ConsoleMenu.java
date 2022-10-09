import java.io.File;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static java.util.Map.entry;

public class ConsoleMenu {
    private enum ERROR_KEY {
        WRONG_NUMBER, FILE_NOT_FOUND, NOT_Y_OR_N
    }
    Map<ERROR_KEY, String> ERROR_MSG = Map.ofEntries(
            entry(ERROR_KEY.WRONG_NUMBER, "Se espera un número"),
            entry(ERROR_KEY.FILE_NOT_FOUND, "No se ha encontrado la carpeta"),
            entry(ERROR_KEY.NOT_Y_OR_N, "Se espera: 'Y' o 'N'")
    );


    public void screenCleaner() {
        for (int i = 0; i < 20; i++) System.out.println();
    }

    public void title() {
        System.out.println("FILE PROCESSOR");
        System.out.println("==============");
        System.out.println("Procesador de textos en histogramas de frecuencia de textos.");
        System.out.println();
    }

    public void menuMain() {
        System.out.println("Menu Principal");
        System.out.println("1. Iniciar histograma.");
        System.out.println("2. Configurar ruta de guardado.");
        System.out.println("3. Salir");
    }

    public void menuHistogram() {
        System.out.println("Histograma");
        System.out.println("Indica la ruta donde esta el archivo de texto.");
    }

    public void mainLoop() {
        title();
        mainMenuController();

    }

    public void mainMenuController() {
        menuMain();
        Integer optionNum = getInputNumber();
        switch (optionNum) {
            case 1:
                menuHistogramController();
                break;
            case 2:

            default:
        }
    }

    private void menuHistogramController() {
        menuHistogram();
        String containerPath = getInputPath();
        FileHandler.
    }


    private int getInputNumber() {
        Scanner input = new Scanner(System.in);
        try {
            return input.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println(ERROR_MSG.get(ERROR_KEY.WRONG_NUMBER));
            return getInputNumber();
        }
    }

    private String getInputPath() {
        Scanner input = new Scanner(System.in);

        String containerPath = input.nextLine();
        var file = new File(containerPath);

        while (!file.exists()) {
            System.out.println(ERROR_MSG.get(ERROR_KEY.FILE_NOT_FOUND));
            containerPath = input.nextLine();
            file = new File(containerPath);
        }
        return containerPath;
    }

    public Boolean askYesOrNot() {
        Scanner input = new Scanner(System.in);

        String answer = input.next();
        answer = String.valueOf(answer.charAt(0));

        while(!"nNyY".contains(answer)) {
            System.out.println(ERROR_MSG.get(ERROR_KEY.NOT_Y_OR_N));
            answer = input.next();
            answer = String.valueOf(answer.charAt(0));
        }
        return answer.equalsIgnoreCase("y");
    }

}
