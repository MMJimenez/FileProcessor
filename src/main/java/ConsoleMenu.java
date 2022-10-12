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

    private Boolean useConfiguredSaveDir = false;
    private String yesOrNotText() { return "'Y'/'N'"; }
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
        System.out.println("\t2. Opciones.");
        System.out.println("\t3. Salir");
    }

    public void menuHistogramText() {
        System.out.println("Histograma");
        System.out.println("Indica la ruta donde esta el archivo de texto.");
    }

    public void menuConfigurationText() {
        System.out.println("Opciones");
        System.out.print("\t1. Configuración carpeta guardado: ");
        System.out.println(useConfiguredSaveDir ? "ACTIVADA" : "DESACTIVADA");
        System.out.println("\t2. Volver sin hacer cambios.");
    }

    public void mainLoop() {
        while (true) {
            mainMenuController();
        }
    }


    //menu controllers
    public void mainMenuController() {
        screenCleaner();
        title();
        menuMainText();

        Boolean isValidOption = false;
        while (!isValidOption) {
            Integer optionNum = getInputNumber();
            switch (optionNum) {
                case 1:
                    isValidOption = true;
                    menuHistogramController();
                    break;
                case 2:
                    isValidOption = true;
                    menuConfigurationController();
                    break;
                case 3:
                    isValidOption = true;
                    System.exit(0);
                    break;
                default:
                    System.out.println(ERROR_MSG.get(ERROR_KEY.OPTION_NOT_EXIST));
            }
        }
    }

    private void menuHistogramController() {
        screenCleaner();
        String fileTextPath = menuSelectTextFile();
        if (!useConfiguredSaveDir) menuSelectDirSavePath(fileTextPath);

        try {
            // Starts the histogram creation.
            FileHandler.createCsvFromFile(new File(fileTextPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void menuConfigurationController() {
        screenCleaner();
        menuConfigurationText();

        Boolean isValidOption = false;
        while (!isValidOption) {
            Integer optionNum = getInputNumber();
            switch (optionNum) {
                case 1:
                    isValidOption = true;
                    //Configurar path
                    menuSelectSaveDirectory();
                    break;
                case 2:
                    isValidOption = true;
                    //Vuelve al menu anterior
                    break;
                default:
                    System.out.println(ERROR_MSG.get(ERROR_KEY.OPTION_NOT_EXIST));
            }
        }
    }

    private void menuSelectSaveDirectory() {
        System.out.println("Configuracion de carpeta de guardado.");
        System.out.println("Configura una carpeta para guardar los csv resultantes.");
        System.out.println("\tCuando esta Activado se te preguntará si usarla durante la función de histograma.");
        System.out.println("\tCuando esta Desactivada no se la carpeta de guardado durante el histograma.");
        System.out.println();
        System.out.println("¿Quieres " + (useConfiguredSaveDir ? "Mantener Activada" : "Activar") + " la opcion? " + yesOrNotText());
        if (askYesOrNot()) {
            useConfiguredSaveDir = true;
            var file = new File(FileHandler.getDirPath());

            System.out.println("Última carpeta de guardado registrada: ");
            System.out.println("\t" + file.getAbsolutePath());
            System.out.println("¿Quieres usar otra?: " + yesOrNotText());

            if (askYesOrNot()) {
                System.out.println("Indica la ruta de la carpeta");
                String newDirPath = getInputDirPath();
                var config = new ConfigHandler();
                try {
                    config.saveProperty("csv_save_path", newDirPath);
                    System.out.println("Carpeta de guardado actualizada correctamente");
                } catch (IOException e) {
                    System.out.println("No se ha podido resgistrar la nueva carpeta de guardado");
                    e.printStackTrace();
                } finally {
                    FileHandler.setDirPath(newDirPath);
                }
                pressEnterToContinue();
            }

        } else {
            useConfiguredSaveDir = false;
        }
    }

    //menuSelect... Methods
    private void menuSelectDirSavePath(String filePath) {
        String dirPath = getContainerFile(filePath);
        System.out.println("¿Quieres guardar el CSV resultante en la misma carpeta? " + yesOrNotText());
        System.out.println("\t" + dirPath);
        //Confirm if user want to change the dir for the writer
        Boolean confirmDir = askYesOrNot();
        if (!confirmDir) {
            System.out.println("Indica la ruta donde quieres guardar el archivo csv");
            dirPath = getInputDirPath();
        }

        FileHandler.setDirPath(dirPath);
    }

    private String menuSelectTextFile() {
        String fileTextPath = "";
        Boolean isFile = false;

        while (!isFile) {
            menuHistogramText();
            fileTextPath = getInputFilePath();

            try {
                screenCleaner();
                //Print the file
                FileHandler.showFile(fileTextPath);
            } catch (IOException e) {
                System.out.println("No se ha podido mostrar el contenido del texto.");
                e.printStackTrace();
            }
            //Ask for confirmation that this is the correct text file
            System.out.println("\n¿Es este el archivo? " + 'Y'/'N');
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

    private void pressEnterToContinue() {
        System.out.println("Presiona Enter para continuar");
        Scanner input = new Scanner(System.in);
        input.next();
    }
}
