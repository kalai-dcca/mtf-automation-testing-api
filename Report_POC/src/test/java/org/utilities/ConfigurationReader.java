package org.utilities;

import org.DTO.BaseClass;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties properties;

    static {
        System.setProperty("dateTime", BaseClass.reportPath);
        try{
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        }catch (Exception ignored){

        }
    }

    public static String getProperty(String keyName) {
        return properties.getProperty(keyName);
    };
}
