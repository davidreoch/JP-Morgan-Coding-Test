package com.jpmorgan.messageprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author David Reoch
 */
public class Main {

    private static final String propertyFileName = "config.properties";

    public static void main(String[] args) {
        System.out.println();

        try (InputStream input = new FileInputStream(System.getProperty("user.dir") + File.separator + propertyFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            
            MessageProcessor mp = new MessageProcessor(prop);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage() + ", Please move the config.properties file to the same directory as the JAR.");
        }

    }
}
