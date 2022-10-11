import java.io.*;
import java.util.Properties;

public class ConfigHandler {
    private String configPath = ".";
    private String configFileName = "file_processor.config";
    private String fullConfigPath = getConfigPath() + File.separatorChar + getConfigFileName();
    private final String COMMENT_STORE = "FileProcessor config file";

    public String getConfigPath() {
        return configPath;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public String getFullConfigPath() {
        return fullConfigPath;
    }

    public void createConfigFile() throws IOException {
        var configFile = new File(getFullConfigPath());
        configFile.createNewFile();

        InputStream inputStream = Main.class.getResourceAsStream("file_processor.config");
        var fileOutputStream = new FileOutputStream(getFullConfigPath());

        int b = 0;
        while ((b = inputStream.read()) > 0) fileOutputStream.write(b);
        System.out.println("Se crea");
    }

    public String loadPropertyOrRestoreIt(String propertyName) throws IOException {
        //if the config file not exists, create it
        if (!new File(getFullConfigPath()).exists()) {
            createConfigFile();
        }
        var configFile = new FileInputStream(getFullConfigPath());

        var configurations = new Properties();
        configurations.load(configFile);

        String propertyValue;
        //if the property is not find, restore it from the default config file
        if (!configurations.containsKey(propertyName)) {
            propertyValue = restorePropertyFromDefaultConfigFile(propertyName);
        } else {
            propertyValue = configurations.getProperty(propertyName);
        }

        return propertyValue;
    }

    public void saveProperty(String name, String value) throws IOException {
        //if the config file not exists, create it
        if (!new File(getFullConfigPath()).exists()) {
            createConfigFile();
        }
        var configFile = new FileInputStream(getFullConfigPath());
        var configProperties = new Properties();
        configProperties.load(configFile);
        configProperties.setProperty(name, value);
        var fileOutputStream = new FileOutputStream(getFullConfigPath());
        configProperties.store(fileOutputStream, COMMENT_STORE);
    }

    private String restorePropertyFromDefaultConfigFile(String propertyName) throws IOException {
        var configFile = new FileInputStream(getFullConfigPath());
        var configProperties = new Properties();
        configProperties.load(configFile);

        InputStream inputStream = Main.class.getResourceAsStream("file_processor.config");
        var defaultConfig = new Properties();
        defaultConfig.load(inputStream);

        String propertyValue = defaultConfig.getProperty(propertyName);

        configProperties.setProperty(propertyName, propertyValue);
        var fileOutputStream = new FileOutputStream(getFullConfigPath());
        configProperties.store(fileOutputStream, COMMENT_STORE);

        configFile.close();
        if (inputStream != null ) inputStream.close();
        fileOutputStream.close();

        //In case you need to use
        return propertyValue;
    }

}
