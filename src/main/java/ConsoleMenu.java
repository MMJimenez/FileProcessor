import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static java.util.Map.entry;

public class ConsoleMenu {
    private enum ERROR_KEY {
        OPTION_NOT_EXIST, WRONG_NUMBER, FILE_NOT_FOUND, NOT_Y_OR_N, IS_NOT_FILE, IS_NOT_DIR, TRY_AGAIN
    }

    Map<ERROR_KEY, String> ERROR_MSG = Map.ofEntries(
            entry(ERROR_KEY.OPTION_NOT_EXIST, "Esa opción no existe."),
            entry(ERROR_KEY.WRONG_NUMBER, "Se espera un número."),
            entry(ERROR_KEY.FILE_NOT_FOUND, "No se ha encontrado."),
            entry(ERROR_KEY.NOT_Y_OR_N, "Se espera: 'Y' o 'N'."),
            entry(ERROR_KEY.IS_NOT_FILE, "Se espera un Archivo, no una carpeta."),
            entry(ERROR_KEY.IS_NOT_DIR, "Se espera un Archivo, no una carpeta.")
            //entry(ERROR_KEY.TRY_AGAIN, "Porfavor, vuelva a intentarlo.")
    );

    private void printInputMark() {
        System.out.print("-> ");
    }

    public void screenCleaner() {
        for (int i = 0; i < 20; i++) System.out.println();
    }

    public void title() {
        System.out.println("FILE PROCESSOR");
        System.out.println("==============");
        System.out.println("Procesador de textos en histogramas de frecuencia de textos.");
        System.out.println();
    }

    public void menuMainText() {
        System.out.println("Menu Principal");
        System.out.println("\t1. Iniciar histograma.");
        System.out.println("\t2. Configurar ruta de guardado.");
        System.out.println("\t3. Salir");
    }

    public void menuHistogramText() {
        System.out.println("Histograma");
        System.out.println("Indica la ruta donde esta el archivo de texto.");
    }

    public void mainLoop() {
        title();
        mainMenuController();

    }

    public void mainMenuController() {
        menuMainText();

        Boolean isAnOption = false;
        Integer optionNum = getInputNumber();
        switch (optionNum) {
            case 1:
                menuHistogramController();
                break;
            case 2:
                //TODO: hay que terminar poraqui
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println(ERROR_MSG.get(ERROR_KEY.OPTION_NOT_EXIST));
        }
    }

    private void menuHistogramController() {
        screenCleaner();
        Boolean isFile = false;
        String fileTextPath = "";
        while (!isFile) {
            menuHistogramText();
            fileTextPath = getInputFilePath();

            try {
                //Print the file
                FileHandler.showFile(fileTextPath);
            } catch (IOException e) {
                System.out.println("No se ha podido mostrar el contenido del texto.");
                e.printStackTrace();
            }
            //Ask for confirmation that this is the correct text file
            System.out.println("\n¿Es este el archivo? 'Y'/'N'");
            isFile = askYesOrNot();
        }

        String dirPath = getContainerFile(fileTextPath);
        System.out.println("¿Quieres guardar el CSV resultante en la misma carpeta? 'Y'/'N'");
        System.out.println("\t" + dirPath);
        //Confirm if user want to change the dir for the writer
        Boolean confirmDir = askYesOrNot();
        if (!confirmDir) {
            System.out.println("Indica la ruta donde quieres guardar el archivo csv");
            dirPath = getInputDirPath();
        }
        // Saves the dirPath.
        FileHandler.setDirPath(dirPath);
        try {
            // Starts the histogram creation.
            FileHandler.createCsvFromFile(new File(fileTextPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String menuSelectTextFileController(String fileTextPath) {
        Boolean isFile = false;

        while (!isFile) {
            menuHistogramText();
            fileTextPath = getInputFilePath();

            try {
                //Print the file
                FileHandler.showFile(fileTextPath);
            } catch (IOException e) {
                System.out.println("No se ha podido mostrar el contenido del texto.");
                e.printStackTrace();
            }
            //Ask for confirmation that this is the correct text file
            System.out.println("\n¿Es este el archivo? 'Y'/'N'");
            isFile = askYesOrNot();
        }

        return fileTextPath;
    }


    //getInput... Methods
    private int getInputNumber() {
        printInputMark();
        Scanner input = new Scanner(System.in);
        try {
            return input.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println(ERROR_MSG.get(ERROR_KEY.WRONG_NUMBER));
            return getInputNumber();
        }
    }

    private String getInputFilePath() {
        Scanner input = new Scanner(System.in);

        printInputMark();
        String filePath = input.nextLine();
        var file = new File(filePath);
        Boolean isCorrect = false;

        while (!isCorrect) {
            //Checks if the file is correct.
            if (!file.exists()) {
                System.out.println(ERROR_MSG.get(ERROR_KEY.FILE_NOT_FOUND));
                //System.out.println(ERROR_MSG.get(ERROR_KEY.TRY_AGAIN));
            } else if (file.isDirectory()) {
                System.out.println(ERROR_MSG.get(ERROR_KEY.IS_NOT_FILE));
                //System.out.println(ERROR_MSG.get(ERROR_KEY.TRY_AGAIN));
            } else {
                isCorrect = true;
            }
            //Ask for new path file
            if (!isCorrect) {
                printInputMark();
                filePath = input.nextLine();
                file = new File(filePath);
            }
        }
        return filePath;
    }

    //
    private String getInputDirPath() {
        Scanner input = new Scanner(System.in);

        printInputMark();
        String filePath = input.nextLine();
        var file = new File(filePath);
        Boolean isCorrect = false;

        while (!isCorrect) {
            //Checks if the file is correct.
            if (!file.exists()) {
                System.out.println(ERROR_MSG.get(ERROR_KEY.FILE_NOT_FOUND));
                //System.out.println(ERROR_MSG.get(ERROR_KEY.TRY_AGAIN));
            } else if (!file.isDirectory()) {
                System.out.println(ERROR_MSG.get(ERROR_KEY.IS_NOT_DIR));
                //System.out.println(ERROR_MSG.get(ERROR_KEY.TRY_AGAIN));
            } else {
                isCorrect = true;
            }
            //Ask for new path file
            if (!isCorrect) {
                printInputMark();
                filePath = input.nextLine();
                file = new File(filePath);
            }
        }
        return filePath;
    }

    private String getNameFile(String filePath) {
        var file = new File(filePath);
        if (file.exists()) return file.getName();
        else return "result_csv";
    }

    private String getContainerFile(String filePath) {
        var file = new File(filePath);
        if (file.exists()) return file.getParentFile().getAbsolutePath();
        else return "";
    }

    private Boolean askYesOrNot() {
        Scanner input = new Scanner(System.in);

        printInputMark();
        String answer = input.next();
        answer = String.valueOf(answer.charAt(0));

        while (!"nNyY".contains(answer)) {
            System.out.println(ERROR_MSG.get(ERROR_KEY.NOT_Y_OR_N));
            printInputMark();
            answer = input.next();
            answer = String.valueOf(answer.charAt(0));
        }
        return answer.equalsIgnoreCase("y");
    }

}
