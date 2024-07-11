package titov.utils;

import lombok.experimental.UtilityClass;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesLoader {
    private Properties loadProperties() {
        try {
            Properties configuration = new Properties();
            InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties");
            configuration.load(inputStream);
            inputStream.close();
            return configuration;
        } catch (IOException е) {
            System.out.println("Ошибка!");
        }
        return null;
    }

    public  String getValue(String key)  {
        return PropertiesLoader.loadProperties().getProperty(key);
    }

}